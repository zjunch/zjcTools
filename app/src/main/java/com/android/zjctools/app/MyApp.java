package com.android.zjctools.app;

import android.app.Application;
import com.android.zjctools.utils.ZTools;

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ZTools.INSTANCE.init(this);
    }
}
