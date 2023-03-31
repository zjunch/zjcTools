package com.android.zjctools.imagepicker.bean

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class ZPictureBean(
    var url: String? = "", ////图片的网络地址
    var name: String? = "",//图片的名字
    var path : String? = "", //图片的路径
    var size : Long = 0 ,//图片的大小
    var width : Int = 0,//图片的宽度
    var height: Int = 0, //图片的高度
    var mimeType : String? = "",//图片的类型

    var addTime : Long = 0,//图片的创建时间

    var compressPath: String? = "", //图片的压缩路径，如果有值，则已经压缩，防止重复压缩

    var isCamera : Boolean = false,//是否是拍照的图片

    var cropPath : String? = ""//剪切后的图片，如果不是空,则已经剪切过不需要重复剪切

) :Parcelable{
}