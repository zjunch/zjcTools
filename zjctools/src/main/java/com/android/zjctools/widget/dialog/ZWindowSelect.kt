package com.android.zjctools.widget.dialog

import android.app.Activity
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.PopupWindow
import com.android.zjctools.base.ZBItemDelegate
import com.android.zjctools.utils.ZColor
import com.android.zjctools.utils.ZDimen
import com.android.zjcutils.R

class ZWindowSelect( activity: Activity,layoutId:Int=R.layout.z_widget_window_layout) {
    val mActivity=activity
    var mLayoutView: View = LayoutInflater.from(mActivity).inflate(layoutId, null)
    var mPWindow: PopupWindow?=null
    init {
        initWindow()
    }

    /**
     * 初始化对话框
     */
    private fun initWindow() {
        if (mPWindow == null) {
            mPWindow = PopupWindow(mActivity)
            mPWindow?.contentView = mLayoutView
            mPWindow?.width = ViewGroup.LayoutParams.MATCH_PARENT
            mPWindow?.height = ViewGroup.LayoutParams.MATCH_PARENT

            //设置获取焦点
            mPWindow?.isFocusable = true
            //设置可触摸
            mPWindow?.isTouchable = true
            //设置外部可以点击
            mPWindow?.isOutsideTouchable = true
            //设置空背景，必须加上，可以让外部点击事件被触发
            mPWindow?.setBackgroundDrawable(null)
            mLayoutView.setOnClickListener(View.OnClickListener { v: View? -> dismiss() })
        }
    }





    fun show() {
        if (mPWindow != null && !mPWindow!!.isShowing) {
            mPWindow?.showAtLocation(mActivity.window.decorView, Gravity.BOTTOM, 0, 0)
        }
    }

    fun dismiss() {
        if (mPWindow != null && mPWindow!!.isShowing) {
            mPWindow?.dismiss()
            mPWindow = null
        }
    }


}