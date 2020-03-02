package com.android.zjctools.glide;

import android.content.Context;

import com.android.zjctools.ZConstants;
import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;

import com.bumptech.glide.load.engine.cache.ExternalPreferredCacheDiskCacheFactory;
import com.bumptech.glide.module.AppGlideModule;

@GlideModule
public class ZGlideModule extends AppGlideModule {

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        //        super.applyOptions(context, builder);
        int size = 200 * 1024 * 1024;
        String dir = ZConstants.CACHE_IMAGES;
        builder.setDiskCache(new ExternalPreferredCacheDiskCacheFactory(context, dir, size));
    }

    @Override
    public void registerComponents(Context context, Glide glide, Registry registry) {
        super.registerComponents(context, glide, registry);
    }

    @Override
    public boolean isManifestParsingEnabled() {
        return super.isManifestParsingEnabled();
    }
}
