package com.android.zjctools.widget.viewpager

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager

/**
 * Create by zjun on 2023/04/03 13:57
 *
 * 解决可缩放控件在 ViewPager 冲突的问题
 */
class ZViewPager : ViewPager {
    constructor(context: Context) : super(context!!) {}
    constructor(context: Context, attrs: AttributeSet?) : super(context!!, attrs) {}

    override fun onTouchEvent(ev: MotionEvent): Boolean {
        try {
            return super.onTouchEvent(ev)
        } catch (ex: IllegalArgumentException) {
            ex.printStackTrace()
        }
        return false
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        try {
            return super.onInterceptTouchEvent(ev)
        } catch (ex: IllegalArgumentException) {
            ex.printStackTrace()
        }
        return false
    }
}