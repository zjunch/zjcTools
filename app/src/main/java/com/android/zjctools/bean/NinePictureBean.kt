package com.android.zjctools.bean

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class NinePictureBean(
     var urls: List<String> = mutableListOf()
):Parcelable {
}