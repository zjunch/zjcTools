package com.android.zjctools.display

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager

/**
 * 本文将带你了解Android应用开发之双指缩放的时候出现pointerIndex out of range问题，
 * 希望本文对大家学Android有所帮助
 * PhotoView+Viewpager开发图集效果的时候，在某些手机上双指缩放的时候出现
 * java.lang.IllegalArgumentException: pointerIndex out of range异常
 */
/**
 * 图片缩放时java.lang.IllegalArgumentException: pointerIndex out of range解决方案
 *
 *
 */
class ZFixedViewPager : ViewPager {
    constructor(context: Context?) : super(context!!) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(context!!, attrs) {}

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