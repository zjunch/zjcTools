package com.android.zjctools.permission

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class ZPermissionBean(
    var permission: String = "",
    // 资源 id
    var resId: Int= 0,
    // 权限名称
    var name: String = "",
    // 权限理由
    var reason: String = ""
):Parcelable {
}