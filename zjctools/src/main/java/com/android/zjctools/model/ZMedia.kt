package com.android.zjctools.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class ZMedia(
    var mediaType: Int = 0,  // 1 图片  2 视频
    var mediaUrl: String? = ""
) :Parcelable{
}