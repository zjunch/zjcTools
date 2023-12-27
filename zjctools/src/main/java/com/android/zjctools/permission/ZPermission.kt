package com.android.zjctools.permission

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Parcelable
import androidx.core.content.ContextCompat
import com.android.zjctools.base.ZConstant
import com.android.zjctools.router.ZRouter.goPermission
import java.util.*

/**
 * Create by zjun
 *
 * 处理权限请求操作类
 */
object ZPermission{

    /**
     * 获取回调
     */
    // 授权处理回调
    var permissionCallback: PCallback? = null
        private set
    private var mEnableDialog = false
    private var mTitle: String? = null
    private var mMessage: String? = null
    private val mPermissions: MutableList<ZPermissionBean> = ArrayList()
    private var isNeedAgain = true //第一次拒绝是否需要请求两次
    private var isShowRejectDialog = true //拒绝时是否显示拒绝的弹窗
    private var isShowSettingDialog = true //拒绝时是否显示拒绝的弹窗




    /**
     * 是否开启请求权限前的弹窗，默认为 false
     *
     * @param enable 控制弹窗
     */
    fun setEnableDialog(enable: Boolean): ZPermission {
        mEnableDialog = enable
        return this
    }

    /**
     * 是否开启第二次请求
     */
    fun setEnableAgain(enable: Boolean): ZPermission {
        isNeedAgain = enable
        return this
    }

    /**
     * 是否显示拒绝弹窗
     */
    fun setEnableRejectDialog(enable: Boolean): ZPermission {
        isShowRejectDialog = enable
        return this
    }

    /**
     * 如果用户禁止了提示，是否弹出去设置打开的弹窗
     */
    fun setEnableSettingDialog(enable: Boolean): ZPermission {
        isShowSettingDialog = enable
        return this
    }

    /**
     * 设置授权弹窗标题
     *
     * @param title 授权弹窗标题
     */
    fun setTitle(title: String?): ZPermission {
        mTitle = title
        return this
    }

    /**
     * 设置授权弹窗描述内容
     *
     * @param message 授权弹窗描述内容
     */
    fun setMessage(message: String?): ZPermission {
        mMessage = message
        return this
    }

    /**
     * 设置授权弹窗权限列表
     *
     * @param permission 权限
     */
    fun setPermission(permission: ZPermissionBean): ZPermission {
        mPermissions!!.clear()
        mPermissions.add(permission)
        return this
    }

    /**
     * 设置授权弹窗权限列表
     *
     * @param permissions 权限集合
     */
    fun setPermissionList(permissions: List<ZPermissionBean>): ZPermission {
        mPermissions!!.clear()
        mPermissions.addAll(permissions!!)
        return this
    }

    /**
     * 检查权限方法，这里只是判断是否已授权，并不会请求授权
     *
     * @param permission 需要检查的权限
     * @return 返回是否授权
     */
    fun checkPermission(context: Context,permission: String?): Boolean {
        val checkPermission = ContextCompat.checkSelfPermission(
            context!!,
            permission!!
        )
        return checkPermission == PackageManager.PERMISSION_GRANTED
    }

    /**
     * 检查权限方法，这里只是判断是否已授权，并不会请求授权
     *
     * @param permissions 需要检查的权限集合
     * @return 返回是否授权
     */
    fun checkPermission(context: Context,permissions: List<String>): Boolean {
        var result = true
        for (i in permissions.indices) {
            result = checkPermission(context,permissions[i])
            if (!result) {
                return false
            }
        }
        return result
    }

    /**
     * 检查权限
     *
     * @param callback 授权回调接口
     */
    fun requestPermission(context:Context,callback: PCallback?) {
        if (mPermissions == null || mPermissions.size == 0) {
            callback?.onComplete()
            return
        }
        /**
         * 运行在 6.0 以下设备上，直接返回授权完成
         */
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            callback?.onComplete()
            return
        }
        /**
         * 过滤已允许的权限
         */
        val iterator = mPermissions.listIterator()
        while (iterator.hasNext()) {
            if (checkPermission(context,iterator.next()!!.permission)) {
                iterator.remove()
            }
        }
        if (mPermissions.size == 0) {
            callback?.onComplete()
            return
        }
        permissionCallback = callback
        startActivity(context)
    }

    /**
     * 默认实现检查 访问相机 权限
     */
    fun checkCamera(context:Context): Boolean {
        return checkPermission(context,Manifest.permission.CAMERA)
    }

    /**
     * 默认实现请求 访问相机 权限
     *
     * @param callback 回调接口
     */
    fun requestCamera(context:Context,callback: PCallback?) {
        val bean = ZPermissionBean(Manifest.permission.CAMERA, name = "访问相机", reason = "拍摄照片需要 “访问相机” 权限，请授权此权限")
        setPermission(bean)
        requestPermission(context,callback)
    }

    /**
     * 默认实现检查 读写手机存储 权限
     */
    fun checkStorage(context:Context): Boolean {
        return checkPermission(context,Manifest.permission.WRITE_EXTERNAL_STORAGE)
    }

    /**
     * 默认实现请求 读写手机存储 权限
     *
     * @param callback 回调接口
     */
    fun requestStorage(context:Context,callback: PCallback?) {
        val bean = ZPermissionBean(
            Manifest.permission.WRITE_EXTERNAL_STORAGE, name = "读写手机存储",reason= "访问设备图片等文件需要 “访问手机存储” "
                    + "权限，请授权此权限"
        )
        val bean1 = ZPermissionBean(
            Manifest.permission.READ_EXTERNAL_STORAGE, name="读写手机存储",reason= "访问设备图片等文件需要 “访问手机存储” "
                    + "权限，请授权此权限"
        )
        val permissions: MutableList<ZPermissionBean> = ArrayList()
        permissions.add(bean)
        permissions.add(bean1)
        setPermissionList(permissions)
        requestPermission(context,callback)
    }

    /**
     * 默认实现检查 录音 权限
     */
    fun checkRecord(context:Context): Boolean {
        return checkPermission(context,Manifest.permission.RECORD_AUDIO)
    }

    /**
     * 默认实现请求 录音 权限
     *
     * @param callback 回调接口
     */
    fun requestRecord(context:Context,callback: PCallback?) {
        val bean = ZPermissionBean(Manifest.permission.RECORD_AUDIO, name = "录音", reason = "录制语音需要 “录音” 权限，请授权此权限")
        setPermission(bean)
        requestPermission(context,callback)
    }

    /**
     * 开启授权
     */
    private fun startActivity(context: Context) {
        val intent = Intent()
        intent.putExtra(ZConstant.Z_KEY_PERMISSION_ENABLE_DIALOG, mEnableDialog)
        intent.putExtra(ZConstant.Z_KEY_PERMISSION_SETTING_DIALOG, isShowSettingDialog)
        intent.putExtra(ZConstant.Z_KEY_PERMISSION_AGAIN, isNeedAgain)
        intent.putExtra(ZConstant.Z_KEY_PERMISSION_REJECT_DIALOG, isShowRejectDialog)
        intent.putExtra(ZConstant.Z_KEY_PERMISSION_TITLE, mTitle)
        intent.putExtra(ZConstant.Z_KEY_PERMISSION_MSG, mMessage)
        intent.putParcelableArrayListExtra(
            ZConstant.Z_KEY_PERMISSION_LIST,
            mPermissions as ArrayList<out Parcelable?>?
        )
        goPermission(context!!, intent)
    }

    /**
     * Create by lzan13 on 2019/04/25
     *
     * 权限申请授权结果回调接口
     */
    interface PCallback {
        /**
         * 权限申请被拒绝回调
         */
        fun onReject()

        /**
         * 权限申请完成回调
         */
        fun onComplete()
    }


}