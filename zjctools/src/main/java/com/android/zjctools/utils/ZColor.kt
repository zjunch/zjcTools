package com.android.zjctools.utils

import android.content.Context
import androidx.core.content.ContextCompat
import com.android.zjctools.utils.ZTools.context

object ZColor {

    /**
     * 通过资源 id 获取颜色值  ,如果预览
     */
    fun byRes(resId: Int): Int {
        return byRes(context, resId)
    }

    /**
     * 通过资源 id 获取颜色值
     */
    fun byRes(context: Context?, resId: Int): Int {
        return ContextCompat.getColor(context!!, resId)
    }
}