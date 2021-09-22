package com.example.imagestraightening

import android.content.Context
import android.graphics.Matrix
import android.view.ContextMenu
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textview.MaterialTextView
import kotlin.math.*

object Utils {

    fun updateImageAngle(progress: Int, mImageView : ImageView) {

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

        val matrix: Matrix = mImageView.imageMatrix

        val newX: Float = mImageView.width / 2 * (1 - scale)
        val newY: Float = mImageView.height / 2 * (1 - scale)
        matrix.postScale(scale, scale)
        matrix.postTranslate(newX, newY)
        matrix.postRotate(angle, mImageView.width.toFloat() / 2, mImageView.height.toFloat() / 2)
        mImageView.imageMatrix = matrix
    }

    fun initRecyclerViews(context: Context, recyclerView: RecyclerView, textView: MaterialTextView, rulerInterface: OnRulerPositionChanged) {
        val snapHelper = CenterSnapHelper()
        val layoutManager = object : CenterLayoutManager(context, HORIZONTAL, false) {
            /** To scroll the ruler position to center by default */
            override fun onLayoutCompleted(state: RecyclerView.State?) {
                super.onLayoutCompleted(state)
                recyclerView.smoothScrollToPosition(100)

            }
        }

        recyclerView.apply {
            this.layoutManager = layoutManager
            addItemDecoration(CenterDecoration(0))
            snapHelper.attachToRecyclerView(this)
            adapter = RulerAdapter()
        }


        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val centerView: View? = snapHelper.findSnapView(layoutManager)
                val pos: Int? = centerView?.let { layoutManager.getPosition(it) }

                pos?.let {
                    textView.text = (pos - 100).toString()
                    rulerInterface.onPositionChanged(pos - 100)
                }

            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {

                }
            }
        })

    }

    interface OnRulerPositionChanged {
        fun onPositionChanged(position: Int){}
    }
}