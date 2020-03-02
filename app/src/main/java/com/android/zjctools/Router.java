package com.android.zjctools;

import android.app.Activity;

import com.android.zjctools.router.ZRouter;

public class Router extends ZRouter {
    public static  void goMainBottomWindow(Activity activity){
        overlay(activity, BottomWindowActivity.class);
    }
}
