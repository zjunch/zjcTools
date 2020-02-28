package com.android.zjctools;

import android.app.Activity;

import com.android.zjctools.router.ZRouter;

public class Router extends ZRouter {
    public static  void goMain2(Activity activity){
        overlay(activity,Main2Activity.class);
    }
}
