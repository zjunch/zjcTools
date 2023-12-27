package com.android.zjctools.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class ZContact(
var  name:String="",
var  id:String="",
var  phone:String="",
var  email:String="",
var  qq:String="") :Parcelable{
}