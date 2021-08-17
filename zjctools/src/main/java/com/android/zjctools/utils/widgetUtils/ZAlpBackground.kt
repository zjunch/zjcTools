package com.android.zjctools.utils.widgetUtils

import android.view.View
import kotlin.math.abs

class ZAlpBackground {

    /**
     * 跟进滚动高度设置透明度
     */
    fun  setAlp255ByScrollHeight(viewBg: View,scrollY:Int,targetY:Int){
        var alpha= abs(scrollY) *255/targetY
        viewBg.background.alpha= alpha.coerceAtMost(255)
    }
}