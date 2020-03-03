package com.android.zjctools.pick;

import android.content.Context;
import android.support.v4.content.FileProvider;

/**
 * Create by lzan13 on 2019/05/16/21:06
 *
 * 自定义一个 Provider，以免和引入的项目的provider冲突
 */
public class ZPickerProvider extends FileProvider {

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
