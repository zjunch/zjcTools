package com.android.zjctools.appupdate

import android.content.Context
import androidx.core.content.FileProvider

class ZAppInstallProvider: FileProvider() {

    /**
     * 用于解决 provider 冲突
     *
     * @param context 上下文
     * @return
     */
    companion object{
        @JvmStatic
        fun getAuthority(context: Context): String? {
            return context.packageName + ".provider"
        }
    }

}