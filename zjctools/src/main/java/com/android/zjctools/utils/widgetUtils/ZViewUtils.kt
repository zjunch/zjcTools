package com.android.zjctools.utils.widgetUtils

import android.app.Activity
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import kotlin.math.abs

object ZViewUtils {


    /**
     * 获取view坐标
     * @param targetView
     * @return
     */
    fun getViewLocation(targetView: View): IntArray? {
        val location = IntArray(2)
        targetView.getLocationOnScreen(location)
        return location
    }




    /**
     * 跟进滚动高度设置透明度
     */
    fun  setViewAlpByScrollHeight(viewBg: View,scrollY:Int,targetY:Int){
        var alpha= abs(scrollY) *255/targetY
        viewBg.background.alpha= alpha.coerceAtMost(255)
    }
}