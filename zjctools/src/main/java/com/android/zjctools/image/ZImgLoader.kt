package com.android.zjctools.image
import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import com.android.zjctools.utils.ZDimen
import com.android.zjcutils.R
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.model.LazyHeaders
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions



/**
 * Create by zjun on 2023/3/22 13:24
 * 图片加载简单封装
 */
object ZImgLoader {

    /**
     * 创建控件
     */
    fun createView(context: Context): ImageView {
        return ImageView(context)
    }


    /**
     * 加载圆形图，一般是头像
     *
     * @param iv 目标 view
     * @param avatar 图片地址
     */
    fun loadAvatar(
        iv: ImageView,
        avatar: Any?,
        isCircle: Boolean = true,
        isRadius: Boolean = false,
        radiusSize: Int = 4, ) {
        val options = Options(avatar, R.drawable.z_default_avatar, isCircle, isRadius, radiusSize)
        load(options, iv)
    }

    /**
     * 加载封面图   加载本地图片有问题
     *
     * @param iv 目标 view
     * @param cover 图片地址
     * @param isRadius 是否圆角
     * @param radiusSize 圆角大小
     * @param isBlur 是否模糊
     */
    fun loadCover(
        iv: ImageView,
        cover: Any?,
        isRadius: Boolean = false,
        radiusSize: Int = 8,
        radiusTL: Int = 0,
        radiusTR: Int = 0,
        radiusBL: Int = 0,
        radiusBR: Int = 0,
        isBlur: Boolean = false,
        thumbnailUrl: String = "",
        defaultResId: Int = R.drawable.z_picture_default,
    ) {
        val options = Options(
            cover,
            defaultResId,
            isRadius = isRadius,
            radiusSize = radiusSize,
            radiusTL = radiusTL,
            radiusTR = radiusTR,
            radiusBL = radiusBL,
            radiusBR = radiusBR,
            isBlur = isBlur,
            thumbnailUrl = thumbnailUrl
        )
        load(options, iv)
    }

    /**
     * 加载缩略图
     *
     * @param iv 目标 view
     * @param path 图片地址
     * @param isRadius 是否圆角
     * @param radiusSize 圆角大小
     * @param isBlur 是否模糊
     */
    fun loadThumbnail(
        iv: ImageView,
        path: Any?,
        isRadius: Boolean = true,
        radiusSize: Int = 4,
        size: Int = 256,
    ) {
        val options = Options(path, isRadius = isRadius, radiusSize = radiusSize, isThumbnail = true, thumbnailSize = size)
        load(options, iv)
    }

    /**
     * 加载图片
     *
     * @param options   加载图片配置
     * @param imageView 目标 view
     */
    private fun load(options: Options, imageView: ImageView) {
        val requestOptions = RequestOptions()
        if (options.isCircle) {
            requestOptions.circleCrop()
        } else if (options.isRadius) {
            requestOptions.transform(MultiTransformation(CenterCrop(), RoundedCorners(ZDimen.dp2px(options.radiusSize))))
        }
        if (options.isBlur) {
            requestOptions.transform(ZBlurTransformation())
        }
        if (options.isThumbnail) {
            requestOptions.format(DecodeFormat.PREFER_RGB_565).override(options.thumbnailSize)
        }

        val wOptions = wrapOptions(options)
        if (options.defaultResId == 0) {
            GlideApp.with(imageView.context)
                .load(wOptions.res)
                .apply(requestOptions)
                .into(imageView)
        } else {
            val thumbnail = thumbnail(imageView.context, wOptions)
            if (options.isCircle || options.isRadius) {
                val placeholder = placeholder(imageView.context, wOptions)
                GlideApp.with(imageView.context)
                    .load(wOptions.res)
                    .thumbnail(thumbnail)
                    .thumbnail(placeholder)
                    .apply(requestOptions)
                    .into(imageView)
            } else {
                GlideApp.with(imageView.context)
                    .load(wOptions.res)
                    .apply(requestOptions)
                    .thumbnail(thumbnail)
                    .placeholder(wOptions.defaultResId)
                    .into(imageView)
            }
        }
    }

    /**
     * 处理占位图
     *
     * @param context 上下文对象
     * @param options 加载配置
     * @return
     */
    private fun placeholder(context: Context, options: Options): RequestBuilder<Drawable> {
        val requestOptions = RequestOptions()
        if (options.isCircle) {
            requestOptions.circleCrop()
        } else if (options.isRadius) {
            requestOptions.transform(MultiTransformation(CenterCrop(), RoundedCorners(ZDimen.dp2px(options.radiusSize))))
        }
        if (options.isBlur) {
            requestOptions.transform(ZBlurTransformation())
        }
        return GlideApp.with(context).load(options.defaultResId).apply(requestOptions)
    }

    /**
     * 处理缩略图
     *
     * @param context 上下文对象
     * @param options 加载配置
     * @return
     */
    private fun thumbnail(context: Context, options: Options): RequestBuilder<Drawable> {
        val requestOptions = RequestOptions()
        if (options.isCircle) {
            requestOptions.circleCrop()
        } else if (options.isRadius) {
            requestOptions.transform(MultiTransformation(CenterCrop(), RoundedCorners(ZDimen.dp2px(options.radiusSize))))
        }
        if (options.isBlur) {
            requestOptions.transform(ZBlurTransformation())
        }
        return GlideApp.with(context).load(options.thumbnailUrl).apply(requestOptions)
    }

    /**
     * 统一处理加载图片请求头
     */
    private fun headers(referer: String): LazyHeaders {
        return LazyHeaders.Builder()
            .addHeader("accept-encoding", "gzip, deflate, br")
            .addHeader("accept-language", "zh-CN,zh;q=0.9")
            .addHeader("referer", referer)
            .build()
    }

    /**
     * 包装下图片加载属性
     */
    private fun wrapOptions(options: Options): Options {
        if (options.res is String) {
            if ((options.res as String).indexOf("http") == 0) {
                options.res =  options.res
            } else if ((options.res as String).indexOf("file:///") == 0 || (options.res as String).indexOf("content:///") == 0) {
                options.res = Uri.parse(options.res as String)
            }else if ((options.res as String).indexOf("/storage") == 0) {
                options.res = Uri.parse(options.res as String)
            }
        }
        return options
    }


    /**
     * 加载图片配置
     */
    data class Options(
        // 图片资源，可以为 Uri/String/resId
        var res: Any?,
        // 默认资源
        var defaultResId: Int = R.drawable.z_picture_default,
        // 是否圆形
        var isCircle: Boolean = false,
        // 圆角
        var isRadius: Boolean = false,
        var radiusSize: Int = 4,
        var radiusTL: Int = 0,
        var radiusTR: Int = 0,
        var radiusBL: Int = 0,
        var radiusBR: Int = 0,
        // 是否模糊
        var isBlur: Boolean = false,
        // 缩略图
        var thumbnailUrl: String = "",
        var isThumbnail: Boolean = false,
        var thumbnailSize: Int = 256,

        // 参考参数，防盗链使用
        var referer: String = "",
    )

}