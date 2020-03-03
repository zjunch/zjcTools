package com.android.zjctools.glide;

import android.content.Context;
import android.widget.ImageView;

import com.android.zjctools.pick.ZPickerLoader;
import com.github.chrisbanes.photoview.PhotoView;


/**
 * Create by lzan13 on 2019-12-23 17:38
 * 图片加载接口实现
 */
public class IMGPickerLoader extends ZPickerLoader {
    @Override
    public void load(Context context, Options options, ImageView imageView) {
        IMGLoader.load(context, options, imageView);
    }

    @Override
    public ImageView createView(Context context) {
        //return super.createView(context);
        return new PhotoView(context);
    }
}
