package com.android.zjctools.utils;

import android.app.Activity;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;

/**
 * 导航栏的工具类，主要用于适配华为 EMUI 系统上虚拟导航栏可随时收起和展开的情况
 */
public class ZNavBarUtil implements ViewTreeObserver.OnGlobalLayoutListener {

    // 监听竖屏模式导航栏的显示和隐藏
    public static final int ORIENTATION_VERTICAL = 1;
    // 监听横屏模式导航栏的显示和隐藏
    public static final int ORIENTATION_HORIZONTAL = 2;

    private Rect mRect;
    private View mRootView;
    private boolean isShowNavigationBar = false;
    private int orientation;
    private OnNavBarChangeListener listener;

    public static ZNavBarUtil with(View rootView) {
        return with(rootView, ORIENTATION_VERTICAL);
    }

    public static ZNavBarUtil with(Activity activity) {
        return with(activity.findViewById(android.R.id.content), ORIENTATION_VERTICAL);
    }

    public static ZNavBarUtil with(View rootView, int orientation) {
        ZNavBarUtil changeListener = new ZNavBarUtil(rootView, orientation);
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(changeListener);
        return changeListener;
    }

    public static ZNavBarUtil with(Activity activity, int orientation) {
        return with(activity.findViewById(android.R.id.content), orientation);
    }

    private ZNavBarUtil(View rootView, int orientation) {
        this.mRootView = rootView;
        this.orientation = orientation;
        mRect = new Rect();
    }

    @Override
    public void onGlobalLayout() {
        mRect.setEmpty();
        mRootView.getWindowVisibleDisplayFrame(mRect);
        int heightDiff = 0;
        if (orientation == ORIENTATION_VERTICAL) {
            heightDiff = mRootView.getHeight() - (mRect.bottom - mRect.top);
        } else if (orientation == ORIENTATION_HORIZONTAL) {
            heightDiff = mRootView.getWidth() - (mRect.right - mRect.left);
        }
        int navigationBarHeight = ZDimen.hasNavigationBar() ? ZDimen.getNavigationBarHeight() : 0;
        if (heightDiff >= navigationBarHeight && heightDiff < navigationBarHeight * 2) {
            if (!isShowNavigationBar && listener != null) {
                listener.onShow(orientation, heightDiff);
            }
            isShowNavigationBar = true;
        } else {
            if (isShowNavigationBar && listener != null) {
                listener.onHide(orientation);
            }
            isShowNavigationBar = false;
        }
    }

    /**
     * 设置界面变化监听
     */
    public void setListener(OnNavBarChangeListener listener) {
        this.listener = listener;
    }

    /**
     * 定义界面变化监听
     */
    public interface OnNavBarChangeListener {
        void onShow(int orientation, int height);

        void onHide(int orientation);
    }
}
