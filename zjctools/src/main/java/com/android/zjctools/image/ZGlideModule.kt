package com.android.zjctools.image

import android.content.Context
import androidx.annotation.Keep
import com.android.zjctools.base.ZConstant

import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.engine.cache.ExternalPreferredCacheDiskCacheFactory
import com.bumptech.glide.module.AppGlideModule

/**
 * Create by zjun on 2022/04/06 17:56
 * 描述：使用 RenderScript 模糊图片
 */
@Keep
@GlideModule
class ZGlideModule : AppGlideModule() {
    override fun applyOptions(context: Context, builder: GlideBuilder) {
        //        super.applyOptions(context, builder);
        val size = 256 * 1024 * 1024
        val dir =ZConstant.CACHE_IMAGES
        builder.setDiskCache(ExternalPreferredCacheDiskCacheFactory(context, dir, size.toLong()))
    }

    override fun isManifestParsingEnabled(): Boolean {
        return false
    }

}