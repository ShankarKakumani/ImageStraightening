package com.example.imagestraightening

import android.content.ContentUris
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.imagestraightening.crop.CropImage
import com.example.imagestraightening.crop.CropImageView
import com.example.imagestraightening.databinding.ActivityMainBinding
import java.math.BigDecimal
import kotlin.math.*


class MainActivity : AppCompatActivity(), SeekBar.OnSeekBarChangeListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var rulerAdapter: RulerAdapter
    private lateinit var rulerLayoutManager: CenterLayoutManager
    private val rulerSnapHelper = CenterSnapHelper()

    private lateinit var mImageView: ImageView
    private var mMatrix: Matrix? = null
    private val imagesList = ArrayList<LocalMedia>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initComponents()
    }

    private fun initComponents() {

        picturePaths

        initRecyclerViews()

        // load bitmap from resource
        var image = BitmapFactory.decodeResource(resources, R.drawable.ic_pic)


        // calculate suitable width / height
        val WIDTH = windowManager.defaultDisplay.width
        val HEIGHT = windowManager.defaultDisplay.width


        // scale image to be visible on screen
        image = Bitmap.createScaledBitmap(image, WIDTH, HEIGHT, false)

        // configure image view accordingly
        mImageView = binding.imageView
        mImageView.layoutParams = RelativeLayout.LayoutParams(WIDTH, HEIGHT)
        mImageView.setImageBitmap(image)

        binding.rotateBar.setOnSeekBarChangeListener(this)
    }

    private fun initRecyclerViews() {

        rulerLayoutManager = object : CenterLayoutManager(this, HORIZONTAL, false) {
            /** To scroll the ruler position to center by default */
            override fun onLayoutCompleted(state: RecyclerView.State?) {
                super.onLayoutCompleted(state)
                binding.rulerRecycler.smoothScrollToPosition(100)

            }
        }

        rulerAdapter = RulerAdapter()

        binding.rulerRecycler.apply {
            layoutManager = rulerLayoutManager
            addItemDecoration(CenterDecoration(0))
            rulerSnapHelper.attachToRecyclerView(this)
            adapter = rulerAdapter
        }


        binding.rulerRecycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val centerView: View? = rulerSnapHelper.findSnapView(rulerLayoutManager)
                val pos: Int = rulerLayoutManager.getPosition(centerView!!)

                binding.rulerPositionTV.text = getPositionTextFromRuler(pos.toBigDecimal())

                updateImageAngle(pos-56)
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {

                }
            }
        })

    }

    private fun getPositionTextFromRuler(rulerPosition: BigDecimal): String {

        return rulerPosition.minus(BigDecimal(100)).toString()

    }

    fun updateImageAngle(progress: Int) {

        val angle = (progress - 45).toFloat()
        var width: Float = mImageView.drawable.intrinsicWidth.toFloat()
        var height: Float = mImageView.drawable.intrinsicHeight.toFloat()

        if (width > height) {
            width = mImageView.drawable.intrinsicHeight.toFloat()
            height = mImageView.drawable.intrinsicWidth.toFloat()
        }

        val a = atan((height / width).toDouble()).toFloat()

        // the length from the center to the corner of the green

        // the length from the center to the corner of the green
        val len1 = width / 2 / cos(a - abs(Math.toRadians(angle.toDouble())))
            .toFloat()
        // the length from the center to the corner of the black
        // the length from the center to the corner of the black
        val len2 = sqrt(
            (width / 2).toDouble().pow(2.0) + (height / 2).toDouble().pow(2.0)
        )
            .toFloat()
        // compute the scaling factor
        // compute the scaling factor
        val scale = len2 / len1

        var matrix: Matrix = mImageView.imageMatrix
        if (mMatrix == null) {
            mMatrix = Matrix(matrix)
        }
        matrix = Matrix(mMatrix)

        val newX: Float = mImageView.width / 2 * (1 - scale)
        val newY: Float = mImageView.height / 2 * (1 - scale)
        matrix.postScale(scale, scale)
        matrix.postTranslate(newX, newY)
        matrix.postRotate(angle, mImageView.width.toFloat() / 2, mImageView.height.toFloat() / 2)
        mImageView.imageMatrix = matrix
    }
    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {

        updateImageAngle(progress)
    }

    override fun onStartTrackingTouch(p0: SeekBar?) {

    }

    override fun onStopTrackingTouch(p0: SeekBar?) {

    }


    private val picturePaths: Unit
        get() {
            val allImagesUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            val projection = arrayOf(MediaStore.Images.ImageColumns.DATA, MediaStore.Images.Media._ID)
            try {

                val cursor = this.contentResolver.query(
                    allImagesUri,
                    projection,
                    null,
                    null,
                    MediaStore.Images.Media.DATE_ADDED
                )
                if (cursor != null && cursor.moveToFirst()) {
                    do {
                        val dataPath = ContentUris.withAppendedId(
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                            cursor.getLong(cursor.getColumnIndex(MediaStore.Images.Media._ID))
                        ).toString()

                        val image = LocalMedia(
                            cursor.getColumnIndex(MediaStore.Images.Media._ID).toLong(),
                            dataPath,
                            "absolutePath",
                            "fileName",
                            "folderName",
                            0,
                            1,
                            "mimeType",
                            100,
                            100,
                            100,
                            true
                        )

                        imagesList.add(0, image)

                    } while (cursor.moveToNext())

                    displayFirstImage()
                    cursor.close()
                }
            } catch (ignored: Exception) {
            }
        }

    private fun displayFirstImage() {
        CropImage.activity(Uri.parse(imagesList[0].path)).setGuidelines(CropImageView.Guidelines.ON).start(this)

    }

}