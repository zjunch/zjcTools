package com.android.zjctools.appupdate;

import android.content.Context;
import android.support.v4.content.FileProvider;

public class ZAppInstallProvider extends FileProvider {

    /**
     * 用于解决 provider 冲突
     *
     * @param context 上下文
     * @return
     */
    public static String getAuthority(Context context) {
        return context.getPackageName() + ".provider";
    }

}