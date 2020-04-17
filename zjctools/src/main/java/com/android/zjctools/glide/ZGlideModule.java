package com.android.zjctools.glide;


import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;

@GlideModule
public class ZGlideModule extends AppGlideModule {
    /**
     * 如需要设置图片缓存路径和缓存大小，则复制释放下面的方法即可，否则不需要重写
     */
//    @Override
//    public void applyOptions(Context context, GlideBuilder builder) {
//        //        super.applyOptions(context, builder);
//        int size = 200 * 1024 * 1024;
//        String dir = ZConstants.CACHE_IMAGES;
//        builder.setDiskCache(new ExternalPreferredCacheDiskCacheFactory(context, dir, size));
//    }
//
//    @Override
//    public void registerComponents(Context context, Glide glide, Registry registry) {
//        super.registerComponents(context, glide, registry);
//    }
//
//    @Override
//    public boolean isManifestParsingEnabled() {
//        return super.isManifestParsingEnabled();
//    }
}
