package com.android.zjctools.utils;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.zjcutils.R;

/**
 * created  zjun 2019 12 21
 */
public class ZToast {


    //正常资源背景
    private int normalBg = R.drawable.zjc_shape_toast_normal;

    //成功资源背景
    private int successBg = R.drawable.zjc_shape_toast_success;

    //失败资源背景
    private int failBg = R.drawable.zjc_shape_toast_fail;

    //成功资源图片id
    private int successResId = R.drawable.zjc_ic_success;

    //失败资源图片id
    private int failResId = R.drawable.zjc_ic_error;


    private static Toast mToast = null;

    public static ZToast create() {
        return SingleTon.singleton;
    }


    public static class SingleTon {
        public static final ZToast singleton = new ZToast();
    }

    /**
     * 重置资源
     */
    public void resetRes() {
        successResId = R.drawable.zjc_ic_success;
        failResId = R.drawable.zjc_ic_error;
        successBg = R.drawable.zjc_shape_toast_success;
        failBg = R.drawable.zjc_shape_toast_success;
        normalBg = R.drawable.zjc_shape_toast_normal;
    }

    /**
     * 正常背景
     *
     * @param normalBg
     */
    public void setNormalBg(int normalBg) {
        this.normalBg = normalBg;
    }

    /**
     * 设置成功背景
     *
     * @param successBg
     */
    public void setSuccessBg(int successBg) {
        this.successBg = successBg;
    }

    /**
     * 设置失败背景
     *
     * @param failBg
     */
    public void setFailBg(int failBg) {
        this.failBg = failBg;
    }

    /**
     * 设置成功图片
     *
     * @param successResId
     */
    public void setSuccessResId(int successResId) {
        this.successResId = successResId;
    }

    /**
     * 设置失败图片
     *
     * @param failResId
     */
    public void setFailResId(int failResId) {
        this.failResId = failResId;
    }


    /**
     * 正常的toast
     *
     * @param content
     */
    public void showNormal(String content) {
        showToast(ZToastType.NORMAL, content, ZGravity.BOTTOM);
    }


    /**
     * 中间的toast
     *
     * @param content
     */
    public void showCenter(String content) {
        showToast(ZToastType.NORMAL, content, ZGravity.CENTER);
    }


    /**
     * 失败，底部显示
     *
     * @param content
     */
    public void showErrorBottom(String content) {
        showToast(ZToastType.FAIL, content, ZGravity.BOTTOM);
    }

    /**
     * 失败，中间显示
     *
     * @param content
     */
    public void showErrorCenter(String content) {
        showToast(ZToastType.FAIL, content, ZGravity.CENTER);
    }


    /**
     * 成功，底部显示
     *
     * @param content
     */
    public void showSuccessBottom(String content) {
        showToast(ZToastType.SUCCESS, content, ZGravity.BOTTOM);
    }

    /**
     * 成功，中间显示
     *
     * @param content
     */
    public void showSuccessCenter(String content) {
        showToast(ZToastType.SUCCESS, content, ZGravity.CENTER);
    }

    /**
     * 显示弹窗
     *
     * @param content
     */
    public void showToast(ZToastType zToastType, String content, ZGravity zGravity) {
        if (mToast == null) {
            mToast = new Toast(ZTools.getContext());
        }
        setToastView(zToastType, content, zGravity);
        mToast.setDuration(Toast.LENGTH_SHORT);
        mToast.show();
    }


    /**
     * 设置自定的view
     *
     * @param view
     */
    public void setCustomerView(View view, ZGravity zGravity) {
        if (mToast == null) {
            mToast = new Toast(ZTools.getContext());
        }
        if (zGravity == ZGravity.CENTER) {
            mToast.setGravity(Gravity.CENTER, 0, 0);
        } else if (zGravity == ZGravity.BOTTOM) {
            mToast.setGravity(Gravity.BOTTOM, 0, 200);
        }
        mToast.setView(view);
        mToast.setDuration(Toast.LENGTH_SHORT);
        mToast.show();
    }


    /**
     * 定义弹窗布局
     *
     * @param content
     * @param zGravity
     */
    private void setToastView(ZToastType zToastType, String content, ZGravity zGravity) {
        View myToastView = LayoutInflater.from(ZTools.getContext()).inflate(R.layout.zjc_layout_toast, null);
        TextView myToastMessage = (TextView) myToastView.findViewById(R.id.zjc_toast_content);
        ImageView myToastImage = (ImageView) myToastView.findViewById(R.id.zjv_toast_icon);
        RelativeLayout myToastBg = myToastView.findViewById(R.id.zjc_toast_bg);
        myToastImage.setVisibility(View.GONE);
        if (zToastType == ZToastType.SUCCESS) {
            myToastBg.setBackgroundResource(successBg);//成功背景
            myToastImage.setImageResource(successResId);//成功图片
            myToastImage.setVisibility(View.VISIBLE);
        } else if (zToastType == ZToastType.FAIL) {
            myToastBg.setBackgroundResource(failBg);//失败背景
            myToastImage.setImageResource(failResId);//失败图片
            myToastImage.setVisibility(View.VISIBLE);
        } else if (zToastType == ZToastType.NORMAL) {
            myToastBg.setBackgroundResource(normalBg);
        }
        if (zGravity == ZGravity.CENTER) {
            mToast.setGravity(Gravity.CENTER, 0, 0);
        } else if (zGravity == ZGravity.BOTTOM) {
            mToast.setGravity(Gravity.BOTTOM, 0, 200);
        }
        myToastMessage.setText(content);
        mToast.setDuration(Toast.LENGTH_LONG);//setDuration方法：设置持续时间，以毫秒为单位。该方法是设置补间动画时间长度的主要方法
        mToast.setView(myToastView); //添加视图文件
    }


    public enum ZGravity {
        BOTTOM, CENTER
    }


    public enum ZToastType {
        NORMAL, SUCCESS, FAIL
    }
}