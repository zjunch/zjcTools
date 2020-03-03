package com.android.zjctools;

import android.app.Activity;

import com.android.zjctools.router.ZRouter;

public class Router extends ZRouter {

    public static  void goMainBottomWindow(Activity activity){
        overlay(activity, BottomWindowActivity.class);
    }

    public static  void goTestGlideCach(Activity activity){
        overlay(activity, GlideTestCachActivity.class);
    }

    public static  void goToast(Activity activity){
        overlay(activity, ToastActivity.class);
    }
}
