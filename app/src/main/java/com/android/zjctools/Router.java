package com.android.zjctools;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import com.android.zjctools.display.ZDisplayMultiActivity;
import com.android.zjctools.file.TestFileActivity;
import com.android.zjctools.ninepicture.NineMediaActivity;
import com.android.zjctools.ninepicture.NinePictureActivity;
import com.android.zjctools.router.ZParams;
import com.android.zjctools.router.ZRouter;

import java.util.List;

public class Router  {

    public static  void goMainBottomWindow(Activity activity){
        ZRouter.overlay(activity, BottomWindowActivity.class);
    }

    public static  void goTestGlideCach(Activity activity){
        ZRouter.overlay(activity, NinePictureActivity.class);
    }

    public static  void goSelectPictures(Activity activity){
        ZRouter. overlay(activity, SelectActivity.class);
    }

    public static  void goFile(Activity activity){
        ZRouter.overlay(activity, TestFileActivity.class);
    }




    public static  void goColorView(Activity activity){
        Intent intent=new Intent(activity,ColorViewActivity.class);
        ZRouter.overlay(activity,intent);
    }


    public static  void goNineMedia(Activity activity){
        ZRouter.overlay(activity, NineMediaActivity.class);
    }


    /**
     * 展示图片
     */
    public static void goDisplayMulti(Context context, int position, List<String> pictureList) {
       ZParams params = new ZParams();
        params.setWhat(position);
        params.setStrList(pictureList);
        ZRouter.overlay(context, ZDisplayMultiActivity.class, params);
    }
}
