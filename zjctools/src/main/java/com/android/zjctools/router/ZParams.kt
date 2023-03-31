package com.android.zjctools.router

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ZParams(
    var what: Int? = -1,
    var arg0: Int? = 0,
    var arg1: Int ? = 0,
    var str0: String? = "",
    var str1: String? = "",
    var strList: MutableList<String>? = mutableListOf(),
    var obj: Parcelable? = null
):Parcelable {
}

const val ZM_ROUTER_PARAMS = "zv_router_params"