package com.android.zjctools;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.android.zjctools.appupdate.ZAppUpdateBean;
import com.android.zjctools.appupdate.ZAppUpdateActivity;
import com.android.zjctools.display.ZDisplayMultiActivity;
import com.android.zjctools.file.TestFileActivity;
import com.android.zjctools.ninepicture.NineMediaActivity;
import com.android.zjctools.ninepicture.NinePictureActivity;
import com.android.zjctools.router.ZParams;
import com.android.zjctools.router.ZRouter;
import com.android.zjctools.tab.TabviewActivity;

import java.util.List;

public class Router extends ZRouter {

    public static  void goMainBottomWindow(Activity activity){
        overlay(activity, BottomWindowActivity.class);
    }

    public static  void goTestGlideCach(Activity activity){
        overlay(activity, NinePictureActivity.class);
    }

    public static  void goToast(Activity activity){
        overlay(activity, ToastActivity.class);
    }
    public static  void goSelectPictures(Activity activity){
        overlay(activity, SelectActivity.class);
    }

    public static  void goFile(Activity activity){
        overlay(activity, TestFileActivity.class);
    }

    public static  void goTabView(Activity activity){
        overlay(activity, TabviewActivity.class);
    }

    public static  void goAppUpdate(Activity activity, ZAppUpdateBean appUpdateBean){
        Intent intent=new Intent(activity,ZAppUpdateActivity.class);
        putParams(intent,appUpdateBean);
        overlay(activity,intent);
    }

    public static  void goColorView(Activity activity){
        Intent intent=new Intent(activity,ColorViewActivity.class);
        overlay(activity,intent);
    }


    public static  void goNineMedia(Activity activity){
        overlay(activity, NineMediaActivity.class);
    }


    /**
     * 展示图片
     */
    public static void goDisplayMulti(Context context, int position, List<String> pictureList) {
       ZParams params = new ZParams();
        params.what = position;
        params.strList = pictureList;
        overlay(context, ZDisplayMultiActivity.class, params);
    }
}
