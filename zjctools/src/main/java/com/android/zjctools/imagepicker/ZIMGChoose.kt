package com.android.zjctools.imagepicker

import android.app.Activity
import com.android.zjctools.base.ZConstant
import com.android.zjctools.imagepicker.bean.MimeType
import com.android.zjctools.imagepicker.bean.SelectMode
import com.android.zjctools.imagepicker.bean.selectconfig.MultiSelectConfig
import com.android.zjctools.utils.ZColor
import com.android.zjctools.utils.ZDate
import com.android.zjctools.utils.ZDimen
import com.android.zjctools.utils.ZFile
import com.android.zjcutils.R

object ZIMGChoose {


    // 拍照保存图片目录
    private val picturePath = "${ZFile.pictures}${ZConstant.CACHE_IMAGES}"

    /**
     * 直接拍照
     * isCopyInDCIM 照片是否保存到系统相册文件
     */
    fun takePicture(activity: Activity, isCopyInDCIM:Boolean=true,callback: (Any) -> Unit) {
        // 照片文件名
        val name = "IMG_" + ZDate.sdfFilenameDateTime
        // 照片是否保存到系统相册文件
        ImagePicker.takePhoto(activity, name, isCopyInDCIM) {
            // 拍照结果回调，主线程
            callback.invoke(it[0].uri)
        }
    }

    /**
     * 直接拍摄视频
     */
    fun takeVideo(
        activity: Activity,
        maxDuration:Long=15999,  // 可录制的最大时常，单位毫秒ms
        isCopyInDCIM:Boolean=true,   // 照片是否保存到系统相册文件
        callback: (Any) -> Unit) {
        // 照片文件名
        val name = "IMG_" + ZDate.sdfFilenameDateTime
        ImagePicker.takeVideo(activity, name, maxDuration, isCopyInDCIM) {
            // 拍摄结果回调，主线程
            callback.invoke(it)
        }
    }

    /**
     * 单选并裁剪
     */
    fun singleCrop(activity: Activity,

                   callback: (Any) -> Unit) {
        ImagePicker.withMulti(IMGPicker()) //指定presenter
            .cropSaveInDCIM(false) // true：存储在DCIM下 false：存储在 data/包名/files/imagePicker/ 目录下
            .cropRectMinMargin(ZDimen.dp2px(8)) // 裁剪间距
            .cropStyle(MultiSelectConfig.STYLE_FILL) // 裁剪模式 1-充满/2-留白
            .cropGapBackgroundColor(ZColor.byRes(R.color.app_bg)) // 裁剪留白背景色
            .filterMimeTypes(MimeType.GIF) // 设置需要过滤掉加载的文件类型
            .mimeTypes(MimeType.ofImage()) // 设置要加载的文件类型，可指定单一类型
            .setSingleCropCutNeedTop(true) // 裁剪框在最上层
            .showCamera(true) //显示拍照
            .setColumnCount(4) // 设置列数
            .setCropRatio(1, 1) // 裁剪比例
//            .setMaxCount(1) // 设置选择的最大数
//            .setOriginal(true) //显示原图
            .setSinglePickImageOrVideoType(true)// 设置图片和视频单一类型选择
            .setSinglePickWithAutoComplete(true) // 当单选或者视频单选时，点击item直接回调，无需点击完成按钮
            .setSelectMode(SelectMode.MODE_CROP) // 设置模式，当maxCount==1时，可执行单选（下次选中会取消上一次选中）
            .crop(activity) {
                //图片选择回调，主线程，不需要实现 onActivityResult
                callback.invoke(it[0].cropUrl)
            }
    }

    /**
     * 单选图片
     */
    fun singlePicture(activity: Activity, callback: (Any) -> Unit) {
        ImagePicker.withMulti(IMGPicker()) //指定presenter
//            .filterMimeTypes(MimeType.GIF) // 设置需要过滤掉加载的文件类型
            .mimeTypes(MimeType.ofImage()) // 设置要加载的文件类型，可指定单一类型
            .showCamera(true) //显示拍照
            .setColumnCount(4) // 设置列数
            .setSelectMode(SelectMode.MODE_SINGLE)
            .setMaxCount(1) // 设置选择的最大数
            .setLastImageList<Any>(null) // 设置上一次操作的图片列表，下次选择时默认恢复上一次选择的状态
//            .setMaxVideoDuration(CConstants.timeMinute) // 设置视频可选取的最大时长
//            .setMinVideoDuration(CConstants.timeSecond) // 设置视频可选取的最小时长
            .setOriginal(true) //显示原图
            .setPreview(true) //开启预览
            .setPreviewVideo(true) // 大图预览时是否支持预览视频
            .setSinglePickImageOrVideoType(true)// 设置图片和视频单一类型选择
            .setSinglePickWithAutoComplete(true) // 当单选或者视频单选时，点击item直接回调，无需点击完成按钮
            .setSelectMode(SelectMode.MODE_SINGLE) // 设置模式，当maxCount==1时，可执行单选（下次选中会取消上一次选中）
            .setShieldList<Any>(null) // 设置需要屏蔽掉的图片列表，下次选择时已屏蔽的文件不可选择
            .setVideoSinglePick(true) // 设置视频单选
            .pick(activity) {
                //图片选择回调，主线程，不需要实现 onActivityResult
//                val path = (if (it[0].uri != null) it[0].uri else it[0].path) ?: ""
                callback.invoke(it[0])
            }
    }

    /**
     * 单选视频
     */
    fun singleVideo(activity: Activity, callback: (Any) -> Unit) {
        ImagePicker.withMulti(IMGPicker()) //指定presenter
            .mimeTypes(MimeType.ofVideo()) // 设置要加载的文件类型，可指定单一类型
            .showCamera(true) // 是否显示拍照
            .setColumnCount(4) // 设置列数
            .cropSaveInDCIM(false)
            .setMaxCount(1) // 设置选择的最大数
            .setLastImageList<Any>(null) // 设置上一次操作的图片列表，下次选择时默认恢复上一次选择的状态
            .setMaxVideoDuration(CConstants.videoMaxTime) // 设置视频可选取的最大时长
//            .setMinVideoDuration(CConstants.timeSecond) // 设置视频可选取的最小时长
            .setOriginal(true) //显示原图
            .setPreview(true) //开启预览
            .setPreviewVideo(true) // 大图预览时是否支持预览视频
            .setSinglePickImageOrVideoType(true)// 设置图片和视频单一类型选择
            .setSinglePickWithAutoComplete(true) // 当单选或者视频单选时，点击item直接回调，无需点击完成按钮
            .setSelectMode(SelectMode.MODE_SINGLE) // 设置模式，当maxCount==1时，可执行单选（下次选中会取消上一次选中）
            .setShieldList<Any>(null) // 设置需要屏蔽掉的列表，下次选择时已屏蔽的文件不可选择
            .setVideoSinglePick(true) // 设置视频单选
            .pick(activity) {
                //图片选择回调，主线程，不需要实现 onActivityResult
//                val path = (if (it[0].uri != null) it[0].uri else it[0].path) ?: ""
                callback.invoke(it)
            }
    }


    /**
     * 多选
     */
    fun multiAllMedia(activity: Activity, lastList:ArrayList<Any>, maxCount:Int=9, callback: (List<ImageItem>) -> Unit) {
        ImagePicker.withMulti(IMGPicker()) //指定presenter
            .filterMimeTypes(MimeType.GIF) // 设置需要过滤掉加载的文件类型
            .mimeTypes(MimeType.ofAll()) // 设置要加载的文件类型，可指定单一类型
            .showCamera(true) //显示拍照
            .setColumnCount(4) // 设置列数
            .setMaxCount(maxCount) // 设置选择的最大数
            .setLastImageList<Any>(lastList) // 设置上一次操作的图片列表，下次选择时默认恢复上一次选择的状态
            .setMaxVideoDuration(60000L) // 设置视频可选取的最大时长
            .setMinVideoDuration(1000L) // 设置视频可选取的最小时长
            .setOriginal(true) //显示原图
            .setPreview(true) //开启预览
            .setPreviewVideo(true) // 大图预览时是否支持预览视频
            .setSelectMode(SelectMode.MODE_MULTI) // 设置模式，当maxCount==1时，可执行单选（下次选中会取消上一次选中）
            .setShieldList<Any>(null) // 设置需要屏蔽掉的图片列表，下次选择时已屏蔽的文件不可选择
            .setVideoSinglePick(false) // 设置视频单选
            .pick(activity) {
                //图片选择回调，主线程，不需要实现 onActivityResult
                callback.invoke(it)
            }
    }


    /**
     * 多选
     */
    fun multiPictures(activity: Activity, lastList:ArrayList<Any>, maxCount:Int=9, callback: (List<ImageItem>) -> Unit) {
        ImagePicker.withMulti(IMGPicker()) //指定presenter
            .filterMimeTypes(MimeType.GIF) // 设置需要过滤掉加载的文件类型
            .mimeTypes(MimeType.ofImage()) // 设置要加载的文件类型，可指定单一类型
            .showCamera(true) //显示拍照
            .setColumnCount(4) // 设置列数
            .setMaxCount(maxCount) // 设置选择的最大数
            .setLastImageList<Any>(lastList) // 设置上一次操作的图片列表，下次选择时默认恢复上一次选择的状态
            .setMaxVideoDuration(60000L) // 设置视频可选取的最大时长
            .setMinVideoDuration(1000L) // 设置视频可选取的最小时长
            .setOriginal(true) //显示原图
            .setPreview(true) //开启预览
            .setPreviewVideo(true) // 大图预览时是否支持预览视频
            .setSelectMode(SelectMode.MODE_MULTI) // 设置模式，当maxCount==1时，可执行单选（下次选中会取消上一次选中）
            .setShieldList<Any>(null) // 设置需要屏蔽掉的图片列表，下次选择时已屏蔽的文件不可选择
            .setVideoSinglePick(false) // 设置视频单选
            .pick(activity) {
                //图片选择回调，主线程，不需要实现 onActivityResult
                callback.invoke(it)
            }
    }
}