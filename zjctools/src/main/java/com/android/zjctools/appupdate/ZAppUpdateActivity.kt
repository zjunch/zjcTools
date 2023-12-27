package com.android.zjctools.appupdate

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.text.TextUtils
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.android.zjctools.base.ZBActivity
import com.android.zjctools.base.ZConstant
import com.android.zjctools.interface_function.ZDownloadCallback
import com.android.zjctools.permission.ZPermission
import com.android.zjctools.permission.ZPermission.PCallback
import com.android.zjctools.utils.ZColor
import com.android.zjctools.utils.ZDimen
import com.android.zjctools.utils.ZLog
import com.android.zjctools.utils.ZToast
import com.android.zjcutils.R
import kotlinx.android.synthetic.main.z_activity_app_update.*
import java.io.File

class ZAppUpdateActivity : ZBActivity() {
    var mUpdateApk:File?=null

    override fun layoutId(): Int =R.layout.z_activity_app_update

    override fun initUI() {
        super.initUI()
        if(ZUpdateManager.appVersion==null){
            finish()
            return
        }
        if (!TextUtils.isEmpty(ZUpdateManager.appVersion!!.apkNewVersion)) {
            zvUpdateVersion?.text = "发现新版本:${ZUpdateManager.appVersion!!.apkNewVersion}"
        }
        //添加文案
        ZUpdateManager.appVersion!!.updateContents?.forEach {
            addView(zvUpdateContentLv, it)
        }
        zvUpdateCancelTv?.visibility =if (ZUpdateManager.appVersion!!.isForce) View.GONE else View.VISIBLE
        //更换图
        if (ZUpdateManager.appVersion!!.bgIcon != -1) {
            zvUpdateBg?.setImageResource(ZUpdateManager.appVersion!!.bgIcon)
        }
        zvUpdateProgressRg.progressDrawable= ContextCompat.getDrawable(mActivity,ZUpdateManager.appVersion!!.progressDrawableResId)
    }


    override fun initData() {
        //取消按钮
        zvUpdateCancelTv?.setOnClickListener { finish() }
        //下载按钮
        zvUpdateDownLoadTv?.setOnClickListener {
            if(ZUpdateManager.appVersion!!.isOutDownload){
                goOutDownload()
                return@setOnClickListener
            }
            requestStorePermission()
        }
    }


    private fun goOutDownload(){
        val intent = Intent()
        intent.action = "android.intent.action.VIEW"
        intent.data = Uri.parse(ZUpdateManager.appVersion!!.apkUrl)
        startActivity(intent)
    }


    private fun downloadApk(){
        //下载进度监听
        zvUpdateProgressRg!!.visibility = View.VISIBLE
        zvUpdateProgressRg!!.progress = 0
        zvUpdateButtonRv!!.visibility = View.GONE
        ZUpdateManager.setDownloadCallBack(object : ZDownloadCallback<File> {
            override fun onSuccess(t: File) {
                mUpdateApk=t
                checkInstallPermission()
            }

            override fun onError(code: Int, desc: String?) {
                ZToast.showErrorBottom("下载失败")
                resetDownView()
            }
            override fun onProgress(progress: Int, desc: String?) {
                //更新进度
                zvUpdateProgressRg!!.progress = progress
            }

        })

    }
    /**
     * 添加更新文案
     * @param linearLayout
     * @param updateContent
     */
    private fun addView(linearLayout: LinearLayout?, updateContent: String) {
        val textView = TextView(mActivity)
        textView.setPadding(ZDimen.dp2px(18), ZDimen.dp2px(3), ZDimen.dp2px(18), ZDimen.dp2px(3))
        textView.setTextColor(ZColor.byRes(R.color.zGray3))
        textView.textSize = 11f
        textView.text = updateContent
        linearLayout!!.addView(textView)
    }

    /**
     * 开启下载，并添加下载完成回调
     */
    fun toUpdateServiceDownload() {
        val intent = Intent(mActivity, ZUpdateService::class.java)
        intent.putExtra(ZConstant.APK_URL, ZUpdateManager.appVersion!!.apkUrl)
        intent.putExtra(ZConstant.APP_LOGO, ZUpdateManager.appVersion!!.appLogoIcon)
        if (TextUtils.isEmpty(ZUpdateManager.appVersion!!.apkDirectory)) {
            intent.putExtra(ZConstant.APK_DIRECTORY, ZConstant.CACHE_APK)
        } else {
            intent.putExtra(ZConstant.APK_DIRECTORY, ZUpdateManager.appVersion!!.apkDirectory)
        }
        //添加下载监听
        downloadApk()
        //开启下载
        startService(intent)
    }

    /**
     * 获取权限
     */
    private fun requestStorePermission() {
        ZPermission.requestStorage(mActivity,object : PCallback {
            override fun onReject() {}
            override fun onComplete() {
                toUpdateServiceDownload()
            }
        })
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private fun startInstallPermissionSettingActivity() {
        val packageURI = Uri.parse("package:$packageName")
        //注意这个是8.0新API
        val intent = Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, packageURI)
        mActivity.startActivityForResult(intent, ZConstant.INSTALL_PERMISSION)
    }


    /**
     * 恢复到下载之前状态
     */
    private fun resetDownView() {
        zvUpdateProgressRg!!.progress = 0
        zvUpdateProgressRg!!.visibility = View.GONE
        zvUpdateButtonRv!!.visibility = View.VISIBLE
    }

    /**
     * 8.0需要安装的权限
     */
    private fun checkInstallPermission() {
        //8.0 之前 直接安装
        if(Build.VERSION.SDK_INT<Build.VERSION_CODES.O){
            installApp()
            return
        }
        //先获取是否有安装未知来源应用的权限
        val haveInstallPermission = packageManager.canRequestPackageInstalls()
        if (!haveInstallPermission) {
            startInstallPermissionSettingActivity()
        } else {
            installApp()
        }
    }

    /**
     * 安装应用
     * @param file
     */
    fun installApp() {
        mUpdateApk?.let {
            val intent = Intent()
            intent.action = Intent.ACTION_VIEW
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            val uri: Uri
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                uri = FileProvider.getUriForFile(applicationContext, ZAppInstallProvider.getAuthority(mActivity)!!, mUpdateApk!!)
                //添加这一句表示对目标应用临时授权该Uri所代表的文件
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                intent.setDataAndType(uri, "application/vnd.android.package-archive")
            } else {
                uri = Uri.fromFile(mUpdateApk)
                intent.setDataAndType(uri, "application/vnd.android.package-archive")
            }
            startActivityForResult(intent, ZConstant.INSTALL_RESULT)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ZConstant.INSTALL_PERMISSION) {
            if (resultCode == Activity.RESULT_OK) {
                installApp()
            } else {
                resetDownView()
            }
        } else if (requestCode == ZConstant.INSTALL_RESULT) {
            if (resultCode == Activity.RESULT_OK) {
                ZLog.e("安装")
            } else {
                resetDownView()
            }
        }
    }

    override fun onBackPressed() {
        if(ZUpdateManager.appVersion?.isForce == true)return
        setResult(Activity.RESULT_OK)
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        ZUpdateManager.removeUpdateCallback()
    }
}