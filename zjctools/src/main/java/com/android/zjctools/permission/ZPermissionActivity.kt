package com.android.zjctools.permission

import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.android.zjctools.base.ZConstant
import com.android.zjctools.permission.ZPermission.PCallback
import com.android.zjctools.router.ZRouter.goSettingDetail
import com.android.zjctools.utils.ZStr.byRes
import com.android.zjctools.utils.ZStr.isEmpty
import com.android.zjctools.utils.ZSystem.getAppName
import com.android.zjctools.widget.ZViewGroup
import com.android.zjcutils.R

/**
 * Create by zjun
 *
 * 处理权限请求界面
 */
class ZPermissionActivity : AppCompatActivity() {
    /**
     * 重新申请权限数组的索引
     */
    private var mAgainIndex = 0

    // 是否显示申请权限弹窗
    private var mEnableDialog: Boolean= false
    private var mEnableAgain = true //第一次拒绝是否需要请求两次
    private var mEnableRejectDialog = true //拒绝时是否显示拒绝的弹窗
    private var mEnableSettingDialog = true //拒绝时是否显示拒绝的弹窗

    // 申请权限弹窗标题
    private var mTitle: String? = null

    // 申请权限弹窗描述
    private var mMessage: String? = null

    // 权限列表
    private var mPermissions: ArrayList<ZPermissionBean> = arrayListOf()

    // 权限列表拷贝
    private var mPermissionsCopy: ArrayList<ZPermissionBean> = arrayListOf()

    // 授权提示框
    private var mDialog: AlertDialog? = null
    private var mAppName: String? = null
    private var mCallback: PCallback? = null
    private var isInSetting = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    /**
     * 权限请求初始化操作
     */
    private fun init() {
        // 初始化获取数据
        mAppName = getAppName(this)
        mCallback = ZPermission.permissionCallback
        mEnableDialog = intent.getBooleanExtra(ZConstant.Z_KEY_PERMISSION_ENABLE_DIALOG, false)
        mEnableAgain = intent.getBooleanExtra(ZConstant.Z_KEY_PERMISSION_AGAIN, true)
        mEnableRejectDialog = intent.getBooleanExtra(ZConstant.Z_KEY_PERMISSION_REJECT_DIALOG, true)
        mEnableSettingDialog =
            intent.getBooleanExtra(ZConstant.Z_KEY_PERMISSION_SETTING_DIALOG, true)
        mTitle = intent.getStringExtra(ZConstant.Z_KEY_PERMISSION_TITLE)
        mMessage = intent.getStringExtra(ZConstant.Z_KEY_PERMISSION_MSG)
        mPermissions = intent.getParcelableArrayListExtra<ZPermissionBean>(ZConstant.Z_KEY_PERMISSION_LIST) !!
        mPermissionsCopy = mPermissions
        if (mPermissions == null || mPermissions.size == 0) {
            finish()
            return
        }
        // 根据需要弹出说明对话框
        if (mEnableDialog) {
            showPermissionDialog()
        } else {
            requestPermission(permissionArray, REQUEST_PERMISSION)
        }
    }

    /**
     * 获取申请权限集合
     */
    private val permissionArray: Array<String>
        private get() {
            var permissions=mPermissions.map { it.permission }
            return permissions.toTypedArray()
        }






    /**
     * 根据权限名获取申请权限的实体类
     */
    private fun getPermissionItem(permission: String): ZPermissionBean? {
        for (i in mPermissions!!.indices) {
            val item = mPermissions!![i]
            if (item!!.permission == permission) {
                return item
            }
        }
        return null
    }

    /**
     * 显示提醒对话框
     *
     * @param title     标题
     * @param message   内容
     * @param cancelStr 取消按钮文本
     * @param okStr     确认按钮文本
     * @param listener  确认事件回调
     */
    private fun showAlertDialog(
        title: String,
        message: String,
        cancelStr: String,
        okStr: String,
        listener: DialogInterface.OnClickListener
    ) {
        val alertDialog = AlertDialog.Builder(this).setTitle(title)
            .setMessage(message)
            .setCancelable(false)
            .setNegativeButton(cancelStr) { dialog: DialogInterface, which: Int ->
                dialog.dismiss()
                onPermissionReject()
                finish()
            }
            .setPositiveButton(okStr, listener)
            .create()
        alertDialog.show()
    }

    /**
     * 弹出授权窗口
     */
    private fun showPermissionDialog() {
        val view = LayoutInflater.from(this)
            .inflate(R.layout.z_widget_permission_dialog, null)
        val builder = AlertDialog.Builder(this)
        if (isEmpty(mTitle)) {
            mTitle = byRes(R.string.z_permission_title)
        }
        builder.setTitle(mTitle)
        builder.setView(view)
        // 设置提醒信息
        if (isEmpty(mMessage)) {
            mMessage = byRes(R.string.z_permission_reason)
        }
        val contentView = view.findViewById<TextView>(R.id.z_permission_dialog_content_tv)
        contentView.text = mMessage
        val viewGroup: ZViewGroup = view.findViewById(R.id.z_permission_dialog_custom_vg)
        for (bean in mPermissions!!) {
            val pView = ZPermissionView(this)
            pView.setPermissionIcon(bean!!.resId)
            pView.setPermissionName(bean.name)
            viewGroup.addView(pView)
        }
        view.findViewById<View>(R.id.z_i_know_btn).setOnClickListener { v: View? ->
            if (mDialog != null && mDialog!!.isShowing) {
                mDialog!!.dismiss()
            }
            // 开始申请权限
            requestPermission(
                permissionArray,
                REQUEST_PERMISSION
            )
        }
        mDialog = builder.create()
        mDialog!!.setCancelable(false)
        mDialog!!.show()
    }

    /**
     * 申请权限，内部方法
     *
     * @param permissions 权限列表
     * @param requestCode 请求码
     */
    private fun requestPermission(permissions: Array<String>, requestCode: Int) {
        ActivityCompat.requestPermissions(this, permissions, requestCode)
    }

    /**
     * 再次申请权限
     *
     * @param item 要申请的权限
     */
    private fun requestPermissionAgain(item: ZPermissionBean?) {
        val alertTitle = String.format(getString(R.string.z_permission_again_title), item!!.name)
        val msg =
            String.format(getString(R.string.z_permission_again_reason), item.name, item.reason)
        if (!mEnableRejectDialog) { //需要显示已经拒绝再次提示的弹窗,直接去请求
            requestPermission(arrayOf(item.permission), REQUEST_PERMISSION_AGAIN)
            return
        }
        showAlertDialog(
            alertTitle, msg, getString(R.string.z_btn_cancel), getString(R.string.z_btn_ok)
        ) { dialog: DialogInterface, which: Int ->
            dialog.dismiss()
            requestPermission(
                arrayOf(item.permission),
                REQUEST_PERMISSION_AGAIN
            )
        }
    }

    /**
     * 申请权限回调
     *
     * @param requestCode  权限申请请求码
     * @param permissions  申请的权限数组
     * @param grantResults 授权结果集合
     */
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_PERMISSION -> {
                var i = 0
                while (i < grantResults.size) {

                    // 权限允许后，删除需要检查的权限
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        mPermissions!!.remove(getPermissionItem(permissions[i]))
                    }
                    i++
                }
                if (mPermissions!!.size > 0) {
                    //用户拒绝了某个或多个权限，重新申请
                    if (mEnableAgain) {
                        requestPermissionAgain(mPermissions!![mAgainIndex])
                    } else {
                        onPermissionReject()
                        finish()
                    }
                } else {
                    onPermissionComplete()
                    finish()
                }
            }
            REQUEST_PERMISSION_AGAIN -> {
                if (permissions.size == 0 || grantResults.size == 0) {
                    onPermissionReject()
                    finish()
                    return
                }
                if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                    // 重新申请后再次拒绝，弹框警告
                    // permissions 可能返回空数组，所以try-catch
                    if (!mEnableSettingDialog) {
                        goSettingDetail(this, REQUEST_SETTING)
                        return
                    }
                    val item = mPermissions!![0]
                    val title = String.format(getString(R.string.z_permission_again_title), item!!.name)
                    val msg = String.format(getString(R.string.z_permission_denied_setting), mAppName, item.name, mAppName)
                    showAlertDialog(title, msg, getString(R.string.z_btn_reject), getString(R.string.z_btn_go_to_setting)) { dialog: DialogInterface?, which: Int ->
                        goSettingDetail(this, REQUEST_SETTING
                        )
                    }
                } else {
                    if (mAgainIndex < mPermissions!!.size - 1) {
                        // 继续申请下一个被拒绝的权限
                        requestPermissionAgain(mPermissions!![++mAgainIndex])
                    } else {
                        // 全部允许了
                        onPermissionComplete()
                        finish()
                    }
                }
            }
        }
    }

    /**
     * 回调用户手动设置授权结果，同时再次对权限进行检查，防止用户关闭了某些权限
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_SETTING) {
            isInSetting = true
            if (mDialog != null && mDialog!!.isShowing) {
                mDialog!!.dismiss()
            }
            checkPermission()
            if (mPermissions!!.size > 0) {
                if (isInSetting && !mEnableSettingDialog) { //已经进入过了
                    onPermissionReject()
                    finish()
                    return
                }
                mAgainIndex = 0
                requestPermissionAgain(mPermissions!![mAgainIndex])
            } else {
                onPermissionComplete()
                finish()
            }
        }
    }

    /**
     * 循环检查权限，移除已授权权限
     * 这里的检查是一个完整的检查，为的是防止用户打开设置后取消了某些已授权的权限
     */
    private fun checkPermission() {
        mPermissions = mPermissionsCopy
        val iterator: MutableIterator<ZPermissionBean?> = mPermissions!!.listIterator()
        while (iterator.hasNext()) {
            val checkPermission =
                ContextCompat.checkSelfPermission(this, iterator.next()!!.permission)
            if (checkPermission == PackageManager.PERMISSION_GRANTED) {
                iterator.remove()
            }
        }
    }

    /**
     * 回调权限申请被拒绝
     */
    private fun onPermissionReject() {
        if (mCallback != null) {
            mCallback!!.onReject()
        }
    }

    /**
     * 回调权限申请通过
     */
    private fun onPermissionComplete() {
        if (mCallback != null) {
            mCallback!!.onComplete()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mCallback = null
        if (mDialog != null && mDialog!!.isShowing) {
            mDialog!!.dismiss()
        }
    }

    companion object {
        // 权限申请
        private const val REQUEST_PERMISSION = 100

        // 被拒绝后再次申请
        private const val REQUEST_PERMISSION_AGAIN = 101

        // 调起设置界面设置权限
        private const val REQUEST_SETTING = 200
    }
}