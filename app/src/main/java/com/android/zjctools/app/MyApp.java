package com.android.zjctools.app;

import com.android.zjctools.base.ZjcApp;
import com.android.zjctools.glide.IMManager;

public class MyApp extends ZjcApp {
    @Override
    public void onCreate() {
        super.onCreate();
        IMManager.init(mContext);
    }
}
