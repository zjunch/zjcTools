package com.android.zjctools.appupdate

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.ContextWrapper
import android.graphics.Color
import android.os.Build
import android.view.View
import android.widget.RemoteViews
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.android.zjcutils.R


class ZNotificationUtils(base: Context?) : ContextWrapper(base) {
    private var mManager: NotificationManager? = null
    private var notification: Notification? = null
    private var notifyId = 0

    @RequiresApi(api = Build.VERSION_CODES.O)
    fun createChannels() {
        val androidChannel = NotificationChannel(
            ANDROID_CHANNEL_ID,
            ANDROID_CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT
        )
        // Sets whether notifications posted to this channel should display notification lights
        androidChannel.enableLights(true)
        // Sets whether notification posted to this channel should vibrate.
        androidChannel.enableVibration(true)
        androidChannel.lightColor = Color.GREEN
        // Sets whether notifications posted to this channel appear on the lockscreen or not
        androidChannel.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
        manager!!.createNotificationChannel(androidChannel)
    }

    val manager: NotificationManager?
        get() {
            if (mManager == null) {
                mManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            }
            return mManager
        }

    fun notifyNotifaction(notifyId: Int) {
        this.notifyId = notifyId
        manager!!.notify(notifyId, getNotification())
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    fun getAndroidChannelNotification(title: String?, content: String?): Notification.Builder {
        return Notification.Builder(applicationContext, ANDROID_CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(content)
            .setSmallIcon(android.R.drawable.stat_notify_more)
            .setOnlyAlertOnce(true)
    }

    fun getNotification_25(title: String?, content: String?): NotificationCompat.Builder {
        return NotificationCompat.Builder(applicationContext)
            .setContentTitle(title)
            .setContentText(content)
            .setSmallIcon(android.R.drawable.stat_notify_more)
            .setDefaults(NotificationCompat.FLAG_ONLY_ALERT_ONCE)
            .setOnlyAlertOnce(true)
    }

    fun sendNotification(notifyId: Int, title: String?, content: String?,
                         remoteViews: RemoteViews, intent: PendingIntent?) {
        this.notifyId = notifyId
        remoteViews.setViewVisibility(R.id.notify_download_iv, View.VISIBLE)
        if (Build.VERSION.SDK_INT >= 26) {
            notification =
                getAndroidChannelNotification(title, content).setCustomContentView(remoteViews)
                    .setContentIntent(intent).build()
            manager!!.notify(notifyId, notification)
        } else {
            notification = getNotification_25(title, content).setCustomContentView(remoteViews)
                .setContentIntent(intent).build()
            manager!!.notify(notifyId, notification)
        }
    }

    /**
     * 取消Notification
     */
    fun cancelNotification(notifyId: Int) {
        if (manager != null) {
            manager!!.cancel(notifyId)
        }
    }

    /**
     * 获取Notification
     */
    fun getNotification(): Notification? {
        return if (notification != null) {
            notification
        } else null
    }

    companion object {
        const val APPLICATION_ID = "com.android.zjcutils"
        const val ANDROID_CHANNEL_ID = APPLICATION_ID + "ANDROID"
        const val ANDROID_CHANNEL_NAME = "ANDROID CHANNEL"
        var instance: ZNotificationUtils? = null
        fun getInstance(context: Context?): ZNotificationUtils? {
            if (instance == null) {
                synchronized(ZNotificationUtils::class.java) {
                    if (instance == null) {
                        instance = ZNotificationUtils(context)
                    }
                }
            }
            return instance
        }
    }

    init {
        if (Build.VERSION.SDK_INT >= 26) {
            createChannels()
        }
    }
}
