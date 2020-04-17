package com.android.zjctools.app;

import com.android.zjctools.base.ZjcApp;
import com.android.zjctools.glide.ZIMManager;

public class MyApp extends ZjcApp {
    @Override
    public void onCreate() {
        super.onCreate();
        ZIMManager.init(mContext);
    }
}
