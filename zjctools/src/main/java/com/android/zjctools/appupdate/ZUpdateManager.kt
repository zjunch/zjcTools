package com.android.zjctools.appupdate

import android.app.Activity
import android.content.Intent
import com.android.zjctools.interface_function.ZDownloadCallback
import com.android.zjctools.router.ZRouter.overlay
import com.android.zjctools.router.ZRouter.putParams
import java.io.File

object ZUpdateManager {


    var appVersion: ZAppVersionInfo? = null
    var mUpdateUpdateCallback:ZDownloadCallback <File>?=null

    /**
     * 去更新
     */
    fun startAppUpdate(activity: Activity, appVersion:ZAppVersionInfo) {
        this.appVersion=appVersion
        val intent = Intent(activity, ZAppUpdateActivity::class.java)
        overlay(activity!!, intent)
    }

    /**
     * 添加更新回调
     */
    fun  setDownloadCallBack(updateCallback:ZDownloadCallback<File>){
        mUpdateUpdateCallback=updateCallback
    }

    /**
     * 下载进度
     */
    fun  onProgressProgress(progress:Int,content:String="下载中"){
        mUpdateUpdateCallback?.onProgress(progress,content)
    }


    /**
     * 下载失败
     */
    fun  onFail(progress:Int,content:String="下载失败"){
        mUpdateUpdateCallback?.onError(progress,content)
        mUpdateUpdateCallback=null
    }

    /**
     * 成功
     */
    fun  onSuccess(file:File){
        mUpdateUpdateCallback?.onSuccess(file)
        mUpdateUpdateCallback=null
    }


    /**
     * 移除回调
     */
    fun removeUpdateCallback(){
        mUpdateUpdateCallback=null
    }


}