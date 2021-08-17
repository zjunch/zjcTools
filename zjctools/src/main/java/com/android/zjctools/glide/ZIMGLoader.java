package com.android.zjctools.glide;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.android.zjctools.pick.ZImgLoaderListener;
import com.android.zjctools.utils.ZDimen;
import com.android.zjcutils.R;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;


/**
 * Create by zjun on 2019/5/22 13:24
 *
 * 图片加载简单封装
 */
public class ZIMGLoader {

    public  static int placeId=0;

    public  static  void  setImagePlaceId(int drawableId){
        placeId=drawableId;
    }

    /**
     * 加载封面
     *
     * @param context   上下文
     * @param cover     图片地址
     * @param imageView 目标 view
     */
    public static void load(Context context, String cover, ImageView imageView) {
        ZImgLoaderListener.Options options = new ZImgLoaderListener.Options(cover);
        load(context, options, imageView);
    }


    /**
     *
     * @param context   上下文
     * @param cover     图片地址
     * @param imageView 目标 view
     */
    public static void load(Context context, String cover, ImageView imageView,int placeId) {
        ZImgLoaderListener.Options options = new ZImgLoaderListener.Options(cover);
        load(context, options, imageView);
    }


    /**
     * 加载圆形图，一般是头像
     *
     * @param context   上下文
     * @param avatar    图片地址
     * @param imageView 目标 view
     */
    public static void loadCircleAvatar(Context context, String avatar, ImageView imageView,int placeId) {
        ZImgLoaderListener.Options options = new ZImgLoaderListener.Options(avatar);
        options.isCircle = true;
        load(context, options, imageView, placeId);
    }

    /**
     * 加载圆角图
     *
     * @param context   上下文
     * @param avatar    头像地址
     * @param imageView 目标 view
     */
    public static void loadRadiusAvatar(Context context, String avatar, ImageView imageView) {
        ZImgLoaderListener.Options options = new ZImgLoaderListener.Options(avatar);
        options.isRadius = true;
        options.radiusSize =ZDimen.dp2px(6);
        load(context, options, imageView,  placeId==0?R.drawable.z_picture_default :placeId);
    }

    /**
     * 加载图片
     *
     * @param context   上下文
     * @param options   加载图片配置
     * @param imageView 目标 view
     */
    public static void load(Context context, ZImgLoaderListener.Options options, ImageView imageView) {
        RequestOptions requestOptions = new RequestOptions();
        if (options.isCircle) {
            requestOptions.circleCrop();
        } else if (options.isRadius) {
            requestOptions.transform(new MultiTransformation<>(new CenterCrop(), new RoundedCorners(options.radiusSize)));
        }
        if (options.isBlur) {
            requestOptions.transform(new ZBlurTransformation());
        }
        if(context==null||(context instanceof Activity&&((Activity) context).isFinishing()))return;
        GlideApp.with(context).load(options.url).apply(requestOptions).thumbnail(placeholder(context, options)).into(imageView);
    }

    /**
     * 加载图片
     *
     * @param context   上下文
     * @param options   加载图片配置
     * @param imageView 目标 view
     */
    public static void load(Context context, ZImgLoaderListener.Options options, ImageView imageView, int resId) {
        RequestOptions requestOptions = new RequestOptions();
        if (options.isCircle) {
            requestOptions.circleCrop();
        } else if (options.isRadius) {
            requestOptions.transform(new MultiTransformation<>(new CenterCrop(), new RoundedCorners(options.radiusSize)));
        }
        if (options.isBlur) {
            requestOptions.transform(new ZBlurTransformation());
        }

        if(context==null||(context instanceof Activity&&((Activity) context).isFinishing()))return;
        GlideApp.with(context).load(options.url).apply(requestOptions).thumbnail(placeholder(context, options, resId)).into(imageView);
    }

    /**
     * 统一处理占位图
     *
     * @param context 上下文对象
     * @param options 加载配置
     * @return
     */
    private static RequestBuilder<Drawable> placeholder(Context context, ZImgLoaderListener.Options options) {
        int resId = placeId==0?R.drawable.z_picture_default :placeId;
        return placeholder(context, options, resId);
    }

    /**
     * 处理占位图
     *
     * @param context 上下文对象
     * @param options 加载配置
     * @param resId   默认资源图
     * @return
     */
    private static RequestBuilder<Drawable> placeholder(Context context, ZImgLoaderListener.Options options, int resId) {
        RequestOptions requestOptions = new RequestOptions();
        if (options.isCircle) {
            requestOptions.circleCrop();
        } else if (options.isRadius) {
            requestOptions.transform(new MultiTransformation<>(new CenterCrop(), new RoundedCorners(options.radiusSize)));
        }
        if (options.isBlur) {
            requestOptions.transform(new ZBlurTransformation());
        }
        return GlideApp.with(context).load(resId).apply(requestOptions);
    }

}
