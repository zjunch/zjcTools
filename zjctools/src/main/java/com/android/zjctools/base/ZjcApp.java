package com.android.zjctools.base;

import android.app.Application;
import android.content.Context;

import com.android.zjctools.utils.ZTools;

import java.util.ArrayList;
import java.util.List;

public class ZjcApp extends Application {

    /**
     * 借用牛逼哄哄的 lzan13 代码
     */

    protected static Context mContext;
    public static List<ZActivity> activityList = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        //初始化工具库
        ZTools.init(this);
    }

    /**
     * 获取项目上下文对象
     */
    public static Context getContext() {
        return mContext;
    }

    /**
     * 添加当前活动的 Activity 到栈顶
     *
     * @param activity 当前活动的 mActivity
     */
    public static void putActivity(ZActivity activity) {
        if (!activityList.contains(activity)) {
            activityList.add(0, activity);
        }
    }

    /**
     * finish 所有的activity
     */
    public static void finishAllActivity() {
        if (activityList.size()>0) {
            for (int i = 0; i <activityList.size() ; i++) {
                activityList.get(i).finish();
            }
        }
        activityList.clear();
    }



    /**
     * 移除一个 mActivity
     *
     * @param activity 要销毁的 mActivity
     */
    public static void removeActivity(ZActivity activity) {
        activityList.remove(activity);
    }

    /**
     * 获取栈顶的 mActivity
     */
    public static ZActivity getTopActivity() {
        if (activityList.size() > 0) {
            return activityList.get(0);
        }
        return null;
    }
}
