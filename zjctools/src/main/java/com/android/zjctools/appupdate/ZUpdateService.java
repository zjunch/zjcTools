package com.android.zjctools.appupdate;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;

import com.android.zjctools.base.ZConstant;
import com.android.zjctools.interface_function.ZCallback;
import com.android.zjctools.interface_function.ZFunctionManager;
import com.android.zjctools.utils.ZFile;
import com.android.zjctools.utils.ZLog;
import com.android.zjctools.utils.ZToast;
import com.android.zjcutils.R;

import java.io.File;


public class ZUpdateService extends IntentService  {


    private String apkUrl,apkDirectory;
    private int appLogoIconRes;
    private ZNotificationUtils notificationUtils;
    private File downApkFile;
    int progress=0;

    public ZUpdateService() {
        super("UpdateService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            notificationUtils =  ZNotificationUtils.getInstance(getApplicationContext());
            apkUrl = intent.getStringExtra(ZConstant.APK_URL);
            apkDirectory = intent.getStringExtra(ZConstant.APK_DIRECTORY);
            appLogoIconRes=intent.getIntExtra(ZConstant.APP_LOGO,-1);
            createNotification();
            download(apkUrl, apkDirectory);
        }
    }

    private void createNotification() {
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.zjc_widget_notifacion_layout);
        if(appLogoIconRes!=-1){
            remoteViews.setImageViewResource(R.id.notify_download_iv,appLogoIconRes);
        }
        remoteViews.setViewVisibility(R.id.notify_download_iv, View.VISIBLE);
        remoteViews.setTextViewText(R.id.notify_tv, "正在下载...");
        remoteViews.setProgressBar(R.id.notify_progress_pb, 100, 0, false);
        remoteViews.setTextViewText(R.id.notify_progress_tv, "0%");
        notificationUtils.sendNotification( 0,"", "", remoteViews, null);
    }







    /**
     * @param url          下载连接
     */
    public void download(final String url, final String apkDirectory) {
        ZFile.downloadFileByNetWork(url, apkDirectory, ".apk", new ZCallback<File>() {
            @Override
            public void onSuccess(File file) {
                downApkFile=file;
                sendMessage(1,100);
            }

            @Override
            public void onError(int code, String desc) {
                ZLog.e(desc);
                sendMessage(2,0);
            }

            @Override
            public void onProgress(int progress, String desc) {
                sendMessage(0,progress);
            }
        });
    }




    public void sendMessage(int what, int mprogress) {
        if(what==0&&mprogress==progress){
            return;
        }else if(what==0||what==1){
            progress=mprogress;
            Message msg0 = new Message();
            msg0.what = what;
            msg0.arg1 = progress;
            mHandler.sendMessage(msg0);
        }else if(what==2){
            Message msg0 = new Message();
            msg0.what = what;
            mHandler.sendMessage(msg0);
        }

    }


    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    //正在下载中
                    ZLog.e("appProgress:"+msg.arg1);
                    ZFunctionManager.getInstance().invokeOnlyPramFunc(ZConstant.App_UPDATE_PROGRESS,msg.arg1);//下载中更新进度条
                    RemoteViews contentView = notificationUtils.getNotification().contentView;
                    contentView.setTextViewText(R.id.notify_tv, "更新包下载中...");
                    contentView.setProgressBar(R.id.notify_progress_pb, 100, msg.arg1, false);
                    contentView.setTextViewText(R.id.notify_progress_tv, msg.arg1 + "%");
                    // 更新UI
                    notificationUtils.notifyNotifaction(0);
                    break;
                case 1://下载完成
                    notificationUtils.cancelNotification(0);
                    ZFunctionManager.getInstance().invokeOnlyPramFunc(ZConstant.APP_DOWNLOAD_FINISH, downApkFile);
                    break;
                case 2:
                    //下载失败
                    ZFunctionManager.getInstance().invokeOnlyPramFunc(ZConstant.App_UPDATE_PROGRESS,-1);//下载中更新进度条
                    break;
            }
            return true;
        }
    });

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}