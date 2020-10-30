package com.android.zjctools.display;

/**
 *  本文将带你了解Android应用开发之双指缩放的时候出现pointerIndex out of range问题，
 *  希望本文对大家学Android有所帮助
 * PhotoView+Viewpager开发图集效果的时候，在某些手机上双指缩放的时候出现
 * java.lang.IllegalArgumentException: pointerIndex out of range异常
 */

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.viewpager.widget.ViewPager;

/**
 * 图片缩放时java.lang.IllegalArgumentException: pointerIndex out of range解决方案
 *
 *
 */
public class ZFixedViewPager extends ViewPager {

    public ZFixedViewPager(Context context) {
        super(context);
    }

    public ZFixedViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        try {
            return super.onTouchEvent(ev);
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        try {
            return super.onInterceptTouchEvent(ev);
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        }
        return false;
    }
}