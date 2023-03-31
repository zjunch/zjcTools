package com.android.zjctools.utils.bitmap

import android.text.TextUtils
import com.android.zjctools.imagepicker.bean.ZPictureBean
import com.android.zjctools.utils.ZLog
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.*


object ZCompressUtils {
    var needCompressCounts = 0 //需要压缩的个数
    var compressedCounts = 0 //已经压缩的个数
    var compressImageListener: CompressImageListener? = null

    interface CompressImageListener {
        fun onComplete()
        fun onError(errorMsg: String?)
    }

    /**
     * 压缩单张图片，裁剪之后的图片不压缩
     * @param pictureBean
     * @param compressImageListener
     */
    fun compressSinglePicture(
        pictureBean: ZPictureBean,
        compressImageListener: CompressImageListener?
    ) {
        compressSinglePicture(pictureBean, false, null, compressImageListener)
    }

    /**
     * 压缩单张图片，裁剪之后的图片不压缩
     * @param pictureBean
     * @param compressImageListener
     */
    fun compressSinglePicture(
        pictureBean: ZPictureBean,
        catalogueName: String?,
        compressImageListener: CompressImageListener?
    ) {
        compressSinglePicture(pictureBean, false, catalogueName, compressImageListener)
    }

    /**
     * 压缩单张图片，
     * @param pictureBean
     * @param compressImageListener
     * @param corpIsCompress    经过裁剪之后的图片是否继续压缩
     * @param  catalogueName     目录
     */
    fun compressSinglePicture(
        pictureBean: ZPictureBean,
        corpIsCompress: Boolean,
        catalogueName: String?,
        compressImageListener: CompressImageListener?) {
        val pictureBeans: MutableList<ZPictureBean> = ArrayList()
        pictureBeans.add(pictureBean)
        compressPictures(pictureBeans, corpIsCompress, catalogueName, compressImageListener)
    }

    /**
     * 压缩图片，裁剪之后的图片不压缩
     * @param pictureBeans
     * @param compressImageListener
     */
    fun compressPictures(pictureBeans: List<ZPictureBean>,
        compressImageListener: CompressImageListener?) {
        compressPictures(pictureBeans, false, null, compressImageListener)
    }

    /**
     * 压缩图片，裁剪之后的图片不压缩
     * @param pictureBeans
     * @param compressImageListener
     */
    fun compressPictures(
        pictureBeans: List<ZPictureBean>, catalogueName: String?,
        compressImageListener: CompressImageListener?) {
        compressPictures(pictureBeans, false, catalogueName, compressImageListener)
    }

    /**
     *
     * @param pictureBeans
     * @param compressImageListener
     * @param corpIsCompress    经过裁剪之后的图片是否继续压缩
     */
    fun compressPictures(
        pictureBeans: List<ZPictureBean>, corpIsCompress: Boolean,
        catalogueName: String?, compressImageListener: CompressImageListener?) {
        this.compressImageListener = compressImageListener
        compressPictures(pictureBeans, corpIsCompress, catalogueName)
    }

    /**
     *
     * @param pictureBeans
     * @param corpIsCompress    裁剪的图片是否压缩
     */
    private fun compressPictures(pictureBeans: List<ZPictureBean>,
        corpIsCompress: Boolean, catalogueName: String?) {
        var isNeedCompress = false
        for (i in pictureBeans.indices) {
            if (TextUtils.isEmpty(pictureBeans[i].url) && TextUtils.isEmpty(
                    pictureBeans[i].compressPath) && !TextUtils.isEmpty(pictureBeans[i].path)) {
                if (!corpIsCompress && !TextUtils.isEmpty(pictureBeans[i].cropPath)) { //剪切图片，且剪切图片不需要压缩
                    pictureBeans[i].compressPath = pictureBeans[i].cropPath //将剪切图片地址复制给压缩图片
                } else {
                    needCompressCounts += 1 //需要压缩处理的个数
                    isNeedCompress = true
                }
            }
        }
        if (!isNeedCompress && compressImageListener != null) {
            ZLog.e("无需压缩")
            compressImageListener!!.onComplete()
        }
        compressedCounts = 0 //当前压缩个数
        for (i in pictureBeans.indices) {
            if (!TextUtils.isEmpty(pictureBeans[i].url)) { //网络图片不需要压缩
                continue
            }
            if (!corpIsCompress && !TextUtils.isEmpty(pictureBeans[i].cropPath)) {  //剪切的图片不压缩,
                pictureBeans[i].compressPath = pictureBeans[i].cropPath
                continue
            }
            if (TextUtils.isEmpty(pictureBeans[i].compressPath) && !TextUtils.isEmpty(pictureBeans[i].path)) {
                compressPicture(pictureBeans[i], catalogueName)
            }
        }
    }

    private fun compressPicture(pictureBean: ZPictureBean, catalogueName: String?) {
        try {
            Observable.create(ObservableOnSubscribe { emitter: ObservableEmitter<String?> ->
                val compressPath =
                    ZBitmap.compressTempImage(pictureBean.path, catalogueName)
                emitter.onNext(compressPath)
            }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<String?> {
                    override fun onSubscribe(d: Disposable) {}
                    override fun onNext(compressPath: String) {
                        ZLog.e("compressPath:$compressPath")
                        pictureBean.compressPath = compressPath
                        compressedCounts += 1
                        if (needCompressCounts == compressedCounts && compressImageListener != null) {
                            compressImageListener!!.onComplete()
                        }
                    }

                    override fun onError(e: Throwable) {
                        if (compressImageListener != null) {
                            compressImageListener!!.onError(e.message)
                        }
                    }

                    override fun onComplete() {}
                })
        } catch (ex: Exception) {
        }
    }


    /**
     * 获取选择的图片
     * @param imagePaths     选择图片返回的url
     * @param selectedPictures  已经选择的图片
     * @return
     */
    fun getSelectPictureBeans(imagePaths: List<String?>, selectedPictures: ArrayList<ZPictureBean>): ArrayList<ZPictureBean> {
        val pictures = ArrayList<ZPictureBean>()
        for (i in selectedPictures.indices) {  //之前的网路图片对象
            if (!TextUtils.isEmpty(selectedPictures[i].url) && TextUtils.isEmpty(selectedPictures[i].path)) {
                pictures.add(selectedPictures[i])
            }
        }
        for (i in imagePaths.indices) {
            val pictureBean = ZPictureBean()
            pictureBean.path = imagePaths[i]
            pictures.add(pictureBean)
        }
        return pictures
    }

}
