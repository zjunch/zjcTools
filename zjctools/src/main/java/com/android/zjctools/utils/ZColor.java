package com.android.zjctools.utils;

import android.content.Context;
import android.support.v4.content.ContextCompat;

public class ZColor {
    /**
     * 通过资源 id 获取颜色值
     */
    public static int byRes(int resId) {
        return byRes(ZTools.getContext(), resId);
    }

    /**
     * 通过资源 id 获取颜色值
     */
    public static int byRes(Context context, int resId) {
        return ContextCompat.getColor(ZTools.getContext(), resId);
    }
}
