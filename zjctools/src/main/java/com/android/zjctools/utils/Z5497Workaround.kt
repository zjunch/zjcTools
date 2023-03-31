package com.android.zjctools.utils

import android.R
import android.app.Activity
import android.graphics.Rect
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout

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
class Z5497Workaround private constructor(activity: Activity) {
    private val mChildOfContent: View
    private var usableHeightPrevious = 0
    private val frameLayoutParams: ViewGroup.LayoutParams
    private fun possiblyResizeChildOfContent() {
        val usableHeightNow = computeUsableHeight()
        if (usableHeightNow != usableHeightPrevious) {
//如果两次高度不一致
//将计算的可视高度设置成视图的高度
            frameLayoutParams.height = usableHeightNow
            mChildOfContent.requestLayout() //请求重新布局
            usableHeightPrevious = usableHeightNow
        }
    }

    private fun computeUsableHeight(): Int {
//计算视图可视高度
        val r = Rect()
        mChildOfContent.getWindowVisibleDisplayFrame(r)
        return r.bottom
    }

    companion object {
        fun assistActivity(content: Activity) {
            Z5497Workaround(content)
        }
    }

    init {
        val content = activity.findViewById<View>(R.id.content) as FrameLayout
        mChildOfContent = content.getChildAt(0)
        mChildOfContent.viewTreeObserver.addOnGlobalLayoutListener { possiblyResizeChildOfContent() }
        frameLayoutParams = mChildOfContent.layoutParams
    }
}