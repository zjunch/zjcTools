package com.android.zjctools.utils;

import android.app.Activity;
import android.text.TextUtils;

import com.android.zjctools.glide.ZIMManager;
import com.android.zjctools.pick.bean.ZPictureBean;

import java.util.ArrayList;

public class ZPictureUtils {

    /**
     * 是否已经有添加图片
     */
    public static boolean isHasCamera(ArrayList<ZPictureBean> pictureBeans) {
        boolean isHas = false;
        for (int i = 0; i <pictureBeans.size() ; i++) {
            if (TextUtils.isEmpty(pictureBeans.get(i).path) && TextUtils.isEmpty(pictureBeans.get(i).url)) {
                isHas = true;
                break;
            }
        }
        return isHas;
    }



    /**
     * 选择图片
     */
    public static  void  gotoImageSelect(Activity mActivity, ArrayList<ZPictureBean> pictureBeans, int maxCounts) {
        gotoImageSelect(mActivity,pictureBeans,maxCounts,4);
    }


    /**
     * 选择图片
     */
    public static  void  gotoImageSelect(Activity mActivity, ArrayList<ZPictureBean> pictureBeans, int maxCounts,int spanCounts) {
        ArrayList listImage = new ArrayList<ZPictureBean>();
        int  urlCounts=0 ; //含有网络图片的个数
        for (int i = 0; i <pictureBeans.size() ; i++) {
            if(!TextUtils.isEmpty(pictureBeans.get(i).path)){
                listImage.add(pictureBeans.get(i));
            }
            if(!TextUtils.isEmpty(pictureBeans.get(i).url)){
                urlCounts+=1;
            }
        }
        ZIMManager.showMultiPicker(mActivity, maxCounts-urlCounts, listImage,spanCounts);//总数减去已经含有的网络图片的个数
    }

}
