package com.android.zjctools.appupdate

import android.graphics.drawable.ColorDrawable
import android.media.MediaSession2Service
import android.os.Parcelable
import com.android.zjcutils.R
import kotlinx.android.parcel.Parcelize

@Parcelize
class ZAppVersionInfo(
    var bgIcon: Int = -1, //更新顶部背景图
    var updateContents: MutableList<String> = mutableListOf(),
    var isForce: Boolean = false,//是否时强更
    var apkUrl: String = "",
    var apkDirectory: String = "",//文件下载目录 只需添加目录即可如 zTool ,下载时会 拼接 download
    var apkNewVersion: String? = "",
    var appLogoIcon: Int = -1,
    var needShowNotification: Boolean=true, //是否显示通知栏
    var isOutDownload:Boolean=true,//是否开启外部浏览器下载，（防止更新失败问题）
    var progressDrawableResId: Int= R.drawable.z_progress_update_version
):Parcelable {
}