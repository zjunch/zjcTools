package com.android.zjctools.router

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Parcelable
import android.provider.Settings
import androidx.fragment.app.Fragment
import com.android.zjctools.permission.ZPermissionActivity
import java.io.Serializable

/**
 * Created by zjun on 2020/3/24.
 *
 * 项目跳转导航路由器
 */
object ZRouter {
    /**
     * 回到手机桌面
     */
    @JvmStatic
    fun goLauncher(context: Context) {
        val intent = Intent(Intent.ACTION_MAIN)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        intent.addCategory(Intent.CATEGORY_HOME)
        context.startActivity(intent)
    }

    /**
     * 唤起权限申请界面
     *
     * @param context 上下文对象
     * @param intent  权限申请 Intent 包含参数
     */
    @JvmStatic
    fun goPermission(context: Context, intent: Intent) {
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        intent.setClass(context, ZPermissionActivity::class.java)
        overlay(context, intent)
    }

    /**
     * 打开 App 的详细设置页面
     */
    @JvmStatic
    fun goSettingDetail(context: Context, requestCode: Int) {
        val packageURI = Uri.parse("package:" + context.packageName)
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI)
        overlayResult(context, intent, requestCode)
    }

    /**
     * ------------------- 传递 Intent 参数进行跳转-------------------
     *
     * @param context 开始界面上下文
     * @param intent  目标 Intent
     */
    @JvmStatic
    fun overlay(context: Context, intent: Intent) {
        context.startActivity(intent)
    }

    /**
     * ------------------- 正常跳转，直接跳到下一个界面，当前界面处于 stop 状态 -------------------
     *
     * 最普通的跳转
     *
     * @param context 开始界面上下文
     * @param target  目标界面
     */
    @JvmStatic
    fun overlay(context: Context, target: Class<out Activity>) {
        val intent = Intent(context, target)
        context.startActivity(intent)
    }

    /**
     * 带有可序列化参数跳转
     *
     * @param context 开始界面上下文
     * @param target  目标界面
     */
    @JvmStatic
    fun overlay(
        context: Context,
        target: Class<out Activity?>?,
        parcelable: Parcelable?) {
        val intent = Intent(context, target)
        putParams(intent, parcelable)
        context.startActivity(intent)
    }

    /**
     * 带有 flags
     *
     * @param context 开始界面上下文
     * @param target  目标界面
     * @param flags   条件
     */
    @JvmStatic
    fun overlay(context: Context, target: Class<out Activity?>?, flags: Int) {
        val intent = Intent(context, target)
        intent.flags = flags
        context.startActivity(intent)
    }

    /**
     * 带有可序列化参数，以及 flags
     *
     * @param context 开始界面上下文
     * @param target  目标界面
     */
    @JvmStatic
    fun overlay(
        context: Context,
        target: Class<out Activity?>?,
        flags: Int,
        parcelable: Parcelable?) {
        val intent = Intent(context, target)
        intent.flags = flags
        putParams(intent, parcelable)
        context.startActivity(intent)
    }

    /**
     * ---------------------------- 向前跳转，跳转结束会 finish 当前界面 ----------------------------
     *
     * 普通的 finish 跳转
     *
     * @param context 上下文对象
     * @param target  目标
     */
    @JvmStatic
    fun forward(context: Context, target: Class<out Activity?>?) {
        val intent = Intent(context, target)
        context.startActivity(intent)
        if (isActivity(context)) {
            (context as Activity).finish()
        }
    }

    /**
     * 带有序列化参数的 finish 跳转
     *
     * @param context 上下文对象
     * @param target  目标
     */
    @JvmStatic
    fun forward(
        context: Context,
        target: Class<out Activity?>?,
        parcelable: Parcelable?) {
        val intent = Intent(context, target)
        putParams(intent, parcelable)
        context.startActivity(intent)
        if (isActivity(context)) {
            (context as Activity).finish()
        }
    }

    /**
     * 带有 flag 的 finish 跳转
     *
     * @param context 上下文对象
     * @param target  目标
     * @param flags   条件
     */
    @JvmStatic
    fun forward(context: Context, target: Class<out Activity?>?, flags: Int) {
        val intent = Intent(context, target)
        setFlags(intent, flags)
        context.startActivity(intent)
        if (isActivity(context)) {
            (context as Activity).finish()
        }
    }

    /**
     * 带有 flag 和序列化参数的 finish 跳转
     *
     * @param context 上下文对象
     * @param target  目标
     * @param flags   条件
     */
    fun forward(context: Context, target: Class<out Activity?>?,
        flags: Int, parcelable: Parcelable?) {
        val intent = Intent(context, target)
        setFlags(intent, flags)
        putParams(intent, parcelable)
        context.startActivity(intent)
        if (isActivity(context)) {
            (context as Activity).finish()
        }
    }

    /**
     * ------------------- 需要返回值的跳转 -------------------
     */
    fun <T> overlayResult(activityOrfragment: T, intent: Intent?, resultCode: Int) {
        if (activityOrfragment is Activity) {
            val activity = activityOrfragment as Activity
            activity.startActivityForResult(intent, resultCode)
        } else if (activityOrfragment is Fragment) {
            val fragment = activityOrfragment as Fragment
            fragment.startActivityForResult(intent, resultCode)
        }
    }

    /**
     * 设置返回值
     */
    fun setResult(activity: Activity, intent: Intent?, parcelable: Parcelable?) {
        putParams(intent, parcelable)
        activity.setResult(Activity.RESULT_OK, intent)
        activity.finish()
    }

    /**
     * 设置返回值
     */
    fun setResult(activity: Activity, intent: Intent?, serializable: Serializable?) {
        putParams(intent, serializable)
        activity.setResult(Activity.RESULT_OK, intent)
        activity.finish()
    }

    /**
     * ---------------------------- 界面跳转参数传递 ----------------------------
     * 获取序列化的参数
     */
    fun getParcelable(activity: Activity): Parcelable {
        return activity.intent.getParcelableExtra(ZM_ROUTER_PARAMS)
    }

    /**
     * 获取序列化参数
     */
    fun getParcelable(data: Intent): Parcelable {
        return data.getParcelableExtra(ZM_ROUTER_PARAMS)
    }

    /**
     * 获取序列化参数
     */
    fun getSerializable(activity: Activity): Serializable {
        return activity.intent.getSerializableExtra(ZM_ROUTER_PARAMS)
    }
    /**
     * 添加可序列化的参数对象
     */
    @JvmStatic
    fun putParams(intent: Intent?, parcelable: Parcelable?) {
        intent?.let {
            parcelable?.let {
                intent.putExtra(ZM_ROUTER_PARAMS, parcelable)
            }
        }


    }

    /**
     * 添加可序列化的参数对象
     */
    private fun putParams(intent: Intent?, serializable: Serializable?) {
        if (intent == null) {
            return
        }
        intent.putExtra(ZM_ROUTER_PARAMS, serializable)
    }

    /**
     * 设置标记
     */
    private fun setFlags(intent: Intent, flags: Int) {
        if (flags < 0) {
            return
        }
        intent.flags = flags
    }

    /**
     * 判断当前上下文是不是 mActivity
     *
     * @param context 上下文
     */
    private fun isActivity(context: Context?): Boolean {
        return context is Activity
    }
}