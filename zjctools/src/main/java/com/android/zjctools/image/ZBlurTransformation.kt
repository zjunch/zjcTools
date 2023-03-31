package com.android.zjctools.image

import android.graphics.Bitmap
import com.android.zjctools.utils.bitmap.ZBlur
import com.android.zjcutils.BuildConfig

import com.bumptech.glide.load.Key
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation

import java.security.MessageDigest

/**
 * Create by zjun on 2022/04/06 17:56
 * 描述：使用 RenderScript 模糊图片
 */
class ZBlurTransformation : BitmapTransformation() {
    private val id = "${BuildConfig.LIBRARY_PACKAGE_NAME}.glide.BlurTransformation"

    override fun transform(pool: BitmapPool, toTransform: Bitmap, outWidth: Int, outHeight: Int): Bitmap {
        val blurBitmap = ZBlur.stackBlurBitmap(toTransform, 15, 10, false)
        return blurBitmap!!
    }

    override fun updateDiskCacheKey(messageDigest: MessageDigest) {
        messageDigest.update(id.toByteArray(Key.CHARSET))
    }
}