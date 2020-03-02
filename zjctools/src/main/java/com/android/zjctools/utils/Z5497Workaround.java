package com.android.zjctools.utils;

import android.app.Activity;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

/**
 * 解决输入将顶部title推出页面
 *
 * 需要在AndroidManifest该activity下添加：
 * android:windowSoftInputMode="adjustResize|stateHidden"
 *
 * 在该activity代码界面，在setContentView语句下添加
 * AndroidBug5497Workaround.assistActivity(MainActivity.this);
 *
 */


public class Z5497Workaround {

    public static void assistActivity(Activity content) {
        new Z5497Workaround(content);
    }

    private View mChildOfContent;

    private int usableHeightPrevious;

    private ViewGroup.LayoutParams frameLayoutParams;


    private Z5497Workaround(Activity activity) {
        FrameLayout content = (FrameLayout) activity.findViewById(android.R.id.content);
        mChildOfContent = content.getChildAt(0);

        mChildOfContent.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {


            public void onGlobalLayout() {
                possiblyResizeChildOfContent();
            }

        });
        frameLayoutParams = mChildOfContent.getLayoutParams();
    }


    private void possiblyResizeChildOfContent() {
        int usableHeightNow = computeUsableHeight();
        if (usableHeightNow != usableHeightPrevious) {
//如果两次高度不一致
//将计算的可视高度设置成视图的高度
            frameLayoutParams.height = usableHeightNow;
            mChildOfContent.requestLayout();//请求重新布局
            usableHeightPrevious = usableHeightNow;
        }
    }


    private int computeUsableHeight() {
//计算视图可视高度
        Rect r = new Rect();
        mChildOfContent.getWindowVisibleDisplayFrame(r);
        return (r.bottom);
    }
}
