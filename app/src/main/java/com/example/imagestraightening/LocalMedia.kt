package com.example.imagestraightening

/**
 * @author：luck
 * @date：2017-5-24 16:21
 * @describe：Media Entity
 */
class LocalMedia(
    /**
     * file to ID
     */
    var id: Long,
    /**
     * original path
     */
    var path: String,
    /**
     * The real path，But you can't get access from AndroidQ
     * It could be empty
     */
    var realPath: String,
    /**
     * file name
     */
    var fileName: String,
    /**
     * Parent  Folder Name
     */
    var parentFolderName: String,
    /**
     * video duration
     */
    var duration: Long,
    /**
     * Gallery selection mode
     */
    var chooseModel: Int,
    /**
     * The media resource type
     */
    var mimeType: String,
    /**
     * image or video width
     *
     *
     * # If zero occurs, the developer needs to handle it extra
     */
    var width: Int,
    /**
     * image or video height
     *
     *
     * # If zero occurs, the developer needs to handle it extra
     */
    var height: Int,
    /**
     * file size
     */
    var size: Long,

    var isExists : Boolean = true

) {

    var coverPath: String? = null

    /**
     * # Check the original button to get the return value
     * original path
     */
    var originalPath: String? = null

    /**
     * compress path
     */
    var compressPath: String? = null

    /**
     * cut path
     */
    var cutPath: String? = null

    /**
     * Note: this field is only returned in Android Q version
     *
     *
     * Android Q image or video path
     */
    var androidQToPath: String? = null

    /**
     * If the selected
     * # Internal use
     */
    var isChecked = false

    /**
     * If the cut
     */
    var isCut = false

    /**
     * media position of list
     */
    var position = 0

    /**
     * The media number of qq choose styles
     */
    var num = 0

    /**
     * If the compressed
     */
    var isCompressed = false

    /**
     * Whether the original image is displayed
     */
    var isOriginal = false

    /**
     * orientation info
     * # For internal use only
     */
    var orientation = -1

    /**
     * loadLongImageStatus
     * # For internal use only
     */

    /**
     * isLongImage
     * # For internal use only
     */
    var isLongImage = false

    /**
     * bucketId
     */
    var bucketId: Long = -1

    /**
     * isMaxSelectEnabledMask
     * # For internal use only
     */
    var isMaxSelectEnabledMask = false


}