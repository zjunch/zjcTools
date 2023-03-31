package com.android.zjctools.utils

import android.app.ActivityManager
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.os.Process
import com.android.zjctools.utils.ZTools.context
import java.util.concurrent.Executors

object ZSystem {


    /**
     * 根据系统可用处理器推荐默认线程池大小，[.getThreadPoolDefaultSize] *
     */
    val VM_THREAD_POOL_DEFAULT_SIZE = getThreadPoolDefaultSize()

    // 在UI线程中处理
    private val handler = Handler(Looper.getMainLooper())

    // 线程池
    private val mExecutorPool = Executors.newCachedThreadPool()

    private fun ZSystem() {
        throw AssertionError()
    }

    /**
     * 在 UI 线程中执行
     */
    fun runInUIThread(runnable: Runnable?) {
        runInUIThread(runnable, 0)
    }

    /**
     * 在 UI 线程中延迟执行
     */
    fun runInUIThread(runnable: Runnable?, delay: Long) {
        handler.postDelayed(runnable, delay)
    }

    /**
     * 异步任务
     */
    fun runTask(runnable: Runnable?) {
        mExecutorPool.execute(runnable)
    }

    /**
     * 复制到剪贴板
     */
    fun copyToClipboard(content: String?): Boolean {
        return copyToClipboard(context, content)
    }

    /**
     * 复制到剪贴板
     */
    fun copyToClipboard(context: Context, content: String?): Boolean {
        try {
            val c = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            c.setPrimaryClip(ClipData.newPlainText(context.packageName, content))
            return true
        } catch (e: Exception) {
            ZLog.e("copyToClipboard %s", e.message)
        }
        return false
    }

    /**
     * 获取线程池默认的大小
     */
    fun getThreadPoolDefaultSize(): Int {
        return getThreadPoolDefaultSize(8)
    }

    /**
     * 获取推荐的线程池大小
     *
     * @param max 线程池最大
     * @return 如果可用处理器*2 +1 小于最大线程数，则返回计算的线程池大小，否则返回传入的最大数
     */
    fun getThreadPoolDefaultSize(max: Int): Int {
        val availableProcessors = 2 * Runtime.getRuntime().availableProcessors() + 1
        return if (availableProcessors > max) max else availableProcessors
    }

    /**
     * 获取应用程序名称
     */
    fun getAppName(): String? {
        return getAppName(context)
    }

    /**
     * 获取应用程序名称
     *
     * @param context 上下文对象
     */
    fun getAppName(context: Context): String? {
        try {
            val packageManager = context.packageManager
            val packageInfo = packageManager.getPackageInfo(context.packageName, 0)
            val labelRes = packageInfo.applicationInfo.labelRes
            return context.resources.getString(labelRes)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    /**
     * 获取应用当前版本号
     */
    fun getVersionCode(): Long {
        return getVersionCode(context)
    }

    /**
     * 获取应用当前版本号
     *
     * @param context 上下文对象
     */
    fun getVersionCode(context: Context): Long {
        val manager = context.packageManager
        var code: Long = 0
        try {
            val info = manager.getPackageInfo(context.packageName, 0)
            code = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                info.longVersionCode
            } else {
                info.versionCode.toLong()
            }
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return code
    }

    /**
     * 获取当前应用版本名称
     */
    fun getVersionName(): String? {
        return getVersionName(context)
    }

    /**
     * 获取当前应用版本名称
     *
     * @param context 上下文对象
     */
    fun getVersionName(context: Context): String? {
        val manager = context.packageManager
        var name: String? = null
        try {
            val info = manager.getPackageInfo(context.packageName, 0)
            name = info.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return name
    }

    /**
     * 获取当前进程名
     */
    fun getProcessName(): String? {
        return getProcessName(context)
    }

    /**
     * 获取当前进程名
     *
     * @param context 上下文对象
     */
    fun getProcessName(context: Context): String? {
        val pid = Process.myPid()
        var processName: String? = null
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val list: List<*> = activityManager.runningAppProcesses
        val i = list.iterator()
        while (i.hasNext()) {
            val info = i.next() as ActivityManager.RunningAppProcessInfo
            try {
                if (info.pid == pid) {
                    // 根据进程的信息获取当前进程的名字
                    processName = info.processName
                    // 返回当前进程名
                    return processName
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        // 没有匹配的项，返回为null
        return processName
    }
}