package com.android.zjctools.glide;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;

import com.android.zjctools.pick.ZPicker;
import com.android.zjctools.pick.bean.ZPictureBean;
import com.android.zjctools.widget.ZCropView;


import java.util.List;

/**
 * Create by lzan13 on 2020-01-01 10:14
 * 图片选择管理类
 */
public class IMManager {

    /**
     * 图片管理相关初始化
     */
    public static void init(Context context) {
        // 如果使用 VMTools 内部封装的图片选择器，需要实现图片加载接口
        ZPicker.getInstance().setPictureLoader(new IMGPickerLoader());
    }

    /**
     * 选择单张图
     *
     * @param t   打开图片选择器的界面
     * @param <T>
     */
    public static <T> void showSinglePicker(T t) {
        showPicker(t, false, 1,null);
    }

    /**
     * 选择多张图
     *
     * @param t              打开图片选择器的界面
     * @param selectPictures 已经选择的图片
     * @param <T>
     */
    public static <T> void showMultiPicker(T t, int maxCounts,List<ZPictureBean> selectPictures) {
        showPicker(t, true, maxCounts,selectPictures);
    }

    /**
     * 统一处理图片选择展示
     *
     * @param t              上下文对象
     * @param isMultiMode    是否多选
     * @param selectPictures 已选择图片
     * @param <T>
     */
    private static <T> void showPicker(T t, boolean isMultiMode,int maxCounts, List<ZPictureBean> selectPictures) {

        ZCropView.Style cropStyle = ZCropView.Style.RECTANGLE;
        boolean isSaveRectangle = true;
        boolean isShowCamera = true;

        ZPicker.getInstance()
            .setMultiMode(isMultiMode)
            //.setPictureLoader(new PickerLoader())
            .setCrop(true)
            //                    .setCropFocusWidth(mCropFocusWidth)
            //                    .setCropFocusHeight(mCropFocusHeight)
            //                    .setCropOutWidth(mCropOutWidth)
            //                    .setCropOutHeight(mCropOutHeight)
            .setCropStyle(cropStyle)
            .setSaveRectangle(isSaveRectangle)
            .setSelectLimit(maxCounts)
            .setShowCamera(isShowCamera)
            .setSelectedPictures(selectPictures);

        if (t instanceof Activity) {
            Activity activity = (Activity) t;
            ZPicker.getInstance().startPicker(activity);
        } else if (t instanceof Fragment) {
            Fragment fragment = (Fragment) t;
            ZPicker.getInstance().startPicker(fragment);
        }
    }
}
