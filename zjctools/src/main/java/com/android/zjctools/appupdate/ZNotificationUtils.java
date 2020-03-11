package com.android.zjctools.appupdate;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.RemoteViews;

import com.android.zjcutils.BuildConfig;
import com.android.zjcutils.R;


public class ZNotificationUtils extends ContextWrapper {

    private NotificationManager mManager;
    public static final String ANDROID_CHANNEL_ID = BuildConfig.APPLICATION_ID+"ANDROID";
    public static final String ANDROID_CHANNEL_NAME = "ANDROID CHANNEL";
    private Notification notification;
    static ZNotificationUtils instance;
    private   int notifyId = 0;
    public static ZNotificationUtils getInstance(Context context){
        if(instance==null){
            synchronized(ZNotificationUtils.class){
                if(instance==null){
                    instance=new ZNotificationUtils(context);
                }
            }
        }
        return  instance;
    }
    public ZNotificationUtils(Context base) {
        super(base);
        if (Build.VERSION.SDK_INT >= 26) {
            createChannels();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void createChannels() {
        // create android channel
        NotificationChannel androidChannel = new NotificationChannel(ANDROID_CHANNEL_ID,
                ANDROID_CHANNEL_NAME,NotificationManager.IMPORTANCE_DEFAULT);
        // Sets whether notifications posted to this channel should display notification lights
        androidChannel.enableLights(true);
//        // Sets whether notification posted to this channel should vibrate.
        androidChannel.enableVibration(true);
        androidChannel.setLightColor(Color.GREEN);
//        // Sets whether notifications posted to this channel appear on the lockscreen or not
        androidChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

        getManager().createNotificationChannel(androidChannel);
    }

    public NotificationManager getManager() {
        if (mManager == null) {
            mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return mManager;
    }

    public void  notifyNotifaction(int notifyId) {
        this.notifyId=notifyId;
        getManager().notify(notifyId, getNotification());
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Notification.Builder getAndroidChannelNotification(String title, String content) {
        return new Notification.Builder(getApplicationContext(), ANDROID_CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(content)
                .setSmallIcon(android.R.drawable.stat_notify_more)
                .setOnlyAlertOnce(true);
    }

    public NotificationCompat.Builder getNotification_25(String title, String content) {
        return new NotificationCompat.Builder(getApplicationContext())
                .setContentTitle(title)
                .setContentText(content)
                .setSmallIcon(android.R.drawable.stat_notify_more)
                .setDefaults(NotificationCompat.FLAG_ONLY_ALERT_ONCE)
                .setOnlyAlertOnce(true);
    }

    public void sendNotification( int notifyId ,String title, String content, RemoteViews remoteViews, PendingIntent intent) {
        this.notifyId=notifyId;
        remoteViews.setViewVisibility(R.id.notify_download_iv, View.VISIBLE);
        if (Build.VERSION.SDK_INT >= 26) {
            notification = getAndroidChannelNotification(title, content).setCustomContentView(remoteViews).setContentIntent(intent).build();
            getManager().notify(notifyId, notification);
        } else {
            notification = getNotification_25(title, content).setCustomContentView(remoteViews).setContentIntent(intent).build();
            getManager().notify(notifyId, notification);
        }
    }

    public void cancelNotification(int notifyId) {
        if(getManager()!=null){
            getManager().cancel(notifyId);
        }
    }

    public Notification getNotification() {
        if (notification != null) {
            return notification;
        }
        return null;
    }
}
