package com.android.zjctools.utils;

import android.content.Context;

import com.android.zjctools.base.ZjcApp;

public class ZTools {

    /**
     * 借用牛逼哄哄的 lzan13 代码
     */


    static Context mContext;

    /**
     * 初始化工具类库
     */
    public static void init(Context context) {
        mContext = context;
    }

    /**
     * 初始化工具类库
     *
     * @param context  上下文对象
     * @param logLevel 日志级别
     */
    public static void init(Context context, int logLevel) {
        mContext = context;
        ZLog.setDebug(logLevel);
    }

    /**
     * 获取工具类库当前保存的上下文对象:
     * 如果没有进行初始化就从自定义的 VMApp 获取
     * 如果项目也没有继承自 VMApp 则为空
     * 这个主要是为了方便工具类库中的其他接口直接使用上下文对象，不需要在调用相关方法时都传递一个 context 或者 mActivity
     */
    public static Context getContext() {
        if (mContext == null) {
            mContext = ZjcApp.getContext();
        }
        if (mContext == null) {
            throw new NullPointerException("请初始化 VMTools 或者继承自 VMApp 类");
        }
        return mContext;
    }
}
