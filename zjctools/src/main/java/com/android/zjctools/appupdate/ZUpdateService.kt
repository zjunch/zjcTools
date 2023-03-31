package com.android.zjctools.appupdate

import android.app.IntentService
import android.content.Intent
import android.os.Handler
import android.os.Message
import android.view.View
import android.widget.RemoteViews
import com.android.zjctools.base.ZConstant
import com.android.zjctools.interface_function.ZDownloadCallback
import com.android.zjctools.utils.ZFile
import com.android.zjctools.utils.ZLog
import com.android.zjcutils.R
import java.io.File


class ZUpdateService : IntentService("UpdateService") {
    private var notificationUtils: ZNotificationUtils? = null

    private var downApkFile: File? = null
    var progress = 0
    override fun onCreate() {
        super.onCreate()
    }

    override fun onHandleIntent(intent: Intent?) {
        if (intent != null) {
            //是否需要处理Notification
            if(ZUpdateManager.appVersion!!.needShowNotification){
                handleNotification()
            }
            download(ZUpdateManager.appVersion!!.apkUrl, ZUpdateManager.appVersion!!.apkDirectory?:"")
        }
    }


    /**
     * 处理Notification
     */
    private fun handleNotification(){
        notificationUtils = ZNotificationUtils.getInstance(applicationContext)
        createNotification()
    }


    /**
     * 创建Notification
     */
    private fun createNotification() {
        val remoteViews = RemoteViews(packageName, R.layout.z_widget_notifacion_layout)
        if (ZUpdateManager.appVersion!!.appLogoIcon != -1) {
            remoteViews.setImageViewResource(R.id.notify_download_iv, ZUpdateManager.appVersion!!.appLogoIcon)
        }
        remoteViews.setViewVisibility(R.id.notify_download_iv, View.VISIBLE)
        remoteViews.setTextViewText(R.id.notify_tv, "正在下载...")
        remoteViews.setProgressBar(R.id.notify_progress_pb, 100, 0, false)
        remoteViews.setTextViewText(R.id.notify_progress_tv, "0%")
        notificationUtils?.sendNotification(0, "", "", remoteViews, null)
    }


    /**
     * 更新通知栏进度条
     */
    private fun showNotificationProgress(process: Int){
        notificationUtils?.let {
            val contentView = notificationUtils!!.getNotification()!!.contentView
            contentView.setTextViewText(R.id.notify_tv, "更新包下载中...")
            contentView.setProgressBar(R.id.notify_progress_pb, 100, process, false)
            contentView.setTextViewText(R.id.notify_progress_tv,  "${process}%")
        }
    }
    /**
     * @param url          下载连接
     */
   private fun download(url: String?="", apkDirectory: String) {
        ZFile.downloadFileByNetWork(url?:"", apkDirectory, ".apk", object : ZDownloadCallback<File> {
            override fun onSuccess(file: File) {
                file?.let {
                    downApkFile = file
                    sendMessage(1, 100)
                }
            }
            override fun onError(code: Int, desc: String?) {
                sendMessage(2, 0)
            }

            override fun onProgress(progress: Int, desc: String?) {
                sendMessage(0, progress)
            }
        })
    }

   private fun sendMessage(what: Int, mprogress: Int) {
        if (what == 0 && mprogress == progress) {
            return
        } else if (what == 0 || what == 1) {
            progress = mprogress
            val msg0 = Message()
            msg0.what = what
            msg0.arg1 = progress
            mHandler.sendMessage(msg0)
        } else if (what == 2) {
            val msg0 = Message()
            msg0.what = what
            mHandler.sendMessage(msg0)
        }
    }

    private val mHandler = Handler { msg ->
        when (msg.what) {
            0 -> {
                //正在下载中
                ZLog.e("appProgress:" + msg.arg1)
                ZUpdateManager.onProgressProgress(msg.arg1)
                showNotificationProgress(msg.arg1)
                // 更新UI
                notificationUtils?.notifyNotifaction(0)
            }
            1 -> { //下载成功
                notificationUtils?.cancelNotification(0)
                downApkFile?.let {
                    ZUpdateManager.onSuccess(it)
                }
            }
            2 -> {   //下载失败
                ZUpdateManager.onFail(-1)
                notificationUtils?.cancelNotification(0)
            }

        }
        true
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}