package com.android.zjctools.interface_function

/**
 * Created by zjun on 2017/6/13.
 * 自定义请求回调
 */
interface ZDownloadCallback<T> {
    /**
     * 成功的回调
     * @param t 成功回调内容，可空
     */
    fun onSuccess(t: T)

    /**
     * 失败的回调
     * @param code 失败错误码
     * @param desc 失败描述
     */
    fun onError(code: Int, desc: String?)

    /**
     * 当前进度回调
     * @param progress 当前进度百分比
     * @param desc     当前进度描述
     */
    fun onProgress(progress: Int, desc: String?)
}