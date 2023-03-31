package com.android.zjctools.interface_function

/**
 * Created by zjun on 2017/6/13.
 * 自定义请求回调
 */
interface ZCCallback<T> {
    /**
     * 成功的回调
     * @param t 成功回调内容，可空
     */
    fun callback(t: T?)

}