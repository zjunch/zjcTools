package com.android.zjctools.glide

import android.content.Context

import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.engine.cache.ExternalPreferredCacheDiskCacheFactory
import com.bumptech.glide.module.AppGlideModule



@GlideModule
class ZGlideModule : AppGlideModule() {
    override fun applyOptions(context: Context, builder: GlideBuilder) {
        //        super.applyOptions(context, builder);
        val size = 256 * 1024 * 1024
        val dir = "imgs"
        builder.setDiskCache(ExternalPreferredCacheDiskCacheFactory(context, dir, size.toLong()))
    }

}