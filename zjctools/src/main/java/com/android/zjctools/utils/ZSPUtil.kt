package com.android.zjctools.utils

import android.content.Context
import android.content.SharedPreferences
import com.android.zjctools.utils.ZTools.context

object ZSPUtil {

    /**
     * 保存在手机里面的文件名
     */
    private const val ZM_SP_PREFIX = "zm_sp_"
    private const val ZM_SP_SUFFIX = ".sp"

    private fun getSharedPreferences(context: Context): SharedPreferences {
        val spFileName = ZM_SP_PREFIX + context.packageName + ZM_SP_SUFFIX
        return context.getSharedPreferences(spFileName, Context.MODE_PRIVATE)
    }

    /**
     * 保存数据的方法，这里不需要传入上下文对象
     */
    fun put(key: String?, `object`: Any) {
        put(context, key, `object`)
    }

    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     */
    fun put(context: Context, key: String?, `object`: Any) {
        val sp = getSharedPreferences(context)
        val editor = sp.edit()
        if (`object` is String) {
            editor.putString(key, `object`)
        } else if (`object` is Int) {
            editor.putInt(key, `object`)
        } else if (`object` is Boolean) {
            editor.putBoolean(key, `object`)
        } else if (`object` is Float) {
            editor.putFloat(key, `object`)
        } else if (`object` is Long) {
            editor.putLong(key, `object`)
        } else {
            editor.putString(key, `object`.toString())
        }
        editor.commit()
    }

    /**
     * 得到保存数据的方法，这里不需要传入上下文对象
     */
    operator fun get(key: String?, defaultObject: Any?): Any? {
        return get(context, key, defaultObject)
    }

    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     */
    operator fun get(context: Context, key: String?, defaultObject: Any?): Any? {
        val sp = getSharedPreferences(context)
        if (defaultObject is String) {
            return sp.getString(key, defaultObject as String?)
        } else if (defaultObject is Int) {
            return sp.getInt(key, (defaultObject as Int?)!!)
        } else if (defaultObject is Boolean) {
            return sp.getBoolean(key, (defaultObject as Boolean?)!!)
        } else if (defaultObject is Float) {
            return sp.getFloat(key, (defaultObject as Float?)!!)
        } else if (defaultObject is Long) {
            return sp.getLong(key, (defaultObject as Long?)!!)
        }
        return null
    }

    /**
     * 移除某个key值已经对应的值
     */
    fun remove(key: String?) {
        remove(context, key)
    }

    /**
     * 移除某个key值已经对应的值
     */
    fun remove(context: Context, key: String?) {
        val sp = getSharedPreferences(context)
        val editor = sp.edit()
        editor.remove(key)
        editor.commit()
    }

    /**
     * 清除所有数据
     */
    fun clear() {
        clear(context)
    }

    /**
     * 清除所有数据
     */
    fun clear(context: Context) {
        val sp = getSharedPreferences(context)
        val editor = sp.edit()
        editor.clear()
        editor.commit()
    }

    /**
     * 查询某个key是否已经存在
     */
    operator fun contains(key: String?): Boolean {
        return contains(context, key)
    }

    /**
     * 查询某个key是否已经存在
     */
    fun contains(context: Context, key: String?): Boolean {
        val sp = getSharedPreferences(context)
        return sp.contains(key)
    }

    /**
     * 返回所有的键值对
     */
    fun getAll(): Map<String?, *>? {
        return getAll(context)
    }

    /**
     * 返回所有的键值对
     */
    fun getAll(context: Context): Map<String?, *>? {
        val sp = getSharedPreferences(context)
        return sp.all
    }
}