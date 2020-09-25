package com.android.zjctools.glide;

import android.app.Activity;
import android.content.Context;

import com.android.zjctools.pick.ZPicker;
import com.android.zjctools.pick.bean.ZPictureBean;
import com.android.zjctools.widget.ZCropView;

import java.util.List;

import androidx.fragment.app.Fragment;

/**
 * Create by zjun on 2020-01-01 10:14
 * 图片选择管理类
 */
public class ZIMManager {

    /**
     * 图片管理相关初始化
     */
    public static void init(Context context) {
        // 如果使用 VMTools 内部封装的图片选择器，需要实现图片加载接口
        ZPicker.getInstance().setPictureLoader(new ZIMGPickerLoader());
    }

    /**
     * 选择单张图
     *
     * @param t   打开图片选择器的界面
     * @param <T>
     */
    public static <T> void showSinglePicker(T t) {
        showPicker(t, 1, null, 0, 4,true);
    }




    /**
     * 选择多张图
     *
     * @param t              打开图片选择器的界面
     * @param selectPictures 已经选择的图片
     * @param <T>
     */
    public static <T> void showMultiPicker(T t, int maxCounts, List<ZPictureBean> selectPictures,boolean isCrop) {
        showPicker(t, maxCounts, selectPictures, 0, 4,false);
    }

    /**
     * 选择多张图
     *
     * @param t              打开图片选择器的界面
     * @param selectPictures 已经选择的图片
     * @param <T>
     */
    public static <T> void showMultiPicker(T t, int maxCounts, List<ZPictureBean> selectPictures,int spanCount,boolean isCrop) {
        showPicker(t, maxCounts, selectPictures, 0, spanCount,false);
    }

    /**
     * 选择多张图
     *
     * @param t              打开图片选择器的界面
     * @param selectPictures 已经选择的图片
     * @param <T>
     */
    public static <T> void showMultiPicker(T t, int maxCounts, List<ZPictureBean> selectPictures) {
        showPicker(t, maxCounts, selectPictures, 0, 4,false);
    }


    /**
     * 选择多张图
     *
     * @param t              打开图片选择器的界面
     * @param selectPictures 已经选择的图片
     * @param <T>
     * @param spanCounts        一行展示几张图片
     */
    public static <T> void showMultiPicker(T t, int maxCounts, List<ZPictureBean> selectPictures, int spanCounts) {
        showPicker(t, maxCounts, selectPictures, 0, spanCounts,false);
    }


    /**
     * 选择多张图
     *
     * @param t              打开图片选择器的界面
     * @param selectPictures 已经选择的图片
     * @param <T>
     * @param colorId        图片区域的背景色
     * @param spanCounts     一行展示几张图片
     */
    public static <T> void showMultiPicker(T t, int maxCounts, List<ZPictureBean> selectPictures, int colorId, int spanCounts,boolean isCrop) {
        showPicker(t, maxCounts, selectPictures, colorId, spanCounts,isCrop);
    }

    /**
     * 统一处理图片选择展示
     *
     * @param t              上下文对象
     * @param selectPictures 已选择图片
     * @param <T>
     */
    private static <T> void showPicker(T t, int maxCounts, List<ZPictureBean> selectPictures, int colorId, int spanCounts,boolean isCrop) {
        ZCropView.Style cropStyle = ZCropView.Style.RECTANGLE;
        boolean isSaveRectangle = true;
        boolean isShowCamera = true;
        boolean isNeedCrop = false;
        boolean isMultiMode = true;//是否多选
        isNeedCrop=isCrop;
        if(maxCounts==1){
            isMultiMode=false;
        }
        ZPicker.getInstance()
                .setMultiMode(isMultiMode)
                //.setPictureLoader(new PickerLoader())
                .setCrop(isNeedCrop)
                .setColorResIg(colorId)
                //                    .setCropFocusWidth(mCropFocusWidth)
                //                    .setCropFocusHeight(mCropFocusHeight)
                //                    .setCropOutWidth(mCropOutWidth)
                //                    .setCropOutHeight(mCropOutHeight)
                .setCropStyle(cropStyle)
                .setSpanSiZe(spanCounts)
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
