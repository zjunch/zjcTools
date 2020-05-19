package com.android.zjctools.app;

import com.android.zjctools.base.ZjcApp;
import com.android.zjctools.glide.ZIMManager;
import com.android.zjctools.pick.ZPicker;
import com.android.zjctools.utils.ZTools;

public class MyApp extends ZjcApp {
    @Override
    public void onCreate() {
        super.onCreate();
        ZTools.init(mContext);
    }
}
