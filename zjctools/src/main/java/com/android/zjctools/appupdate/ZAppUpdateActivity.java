package com.android.zjctools.appupdate;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.zjctools.base.ZBActivity;
import com.android.zjctools.base.ZConstant;
import com.android.zjctools.interface_function.ZFunctionManager;
import com.android.zjctools.interface_function.ZFunctionOnlyParam;
import com.android.zjctools.permission.ZPermission;
import com.android.zjctools.router.ZRouter;
import com.android.zjctools.utils.ZColor;
import com.android.zjctools.utils.ZDimen;
import com.android.zjctools.utils.ZLog;
import com.android.zjctools.utils.ZToast;
import com.android.zjcutils.R;
import java.io.File;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.FileProvider;


public class ZAppUpdateActivity extends ZBActivity {
    ZAppUpdateBean appUpdateBean;
    ImageView iv_bg;
    TextView tv_version, tv_cancel, tv_load;
    LinearLayout lv_content;
    ProgressBar pg_update;
    private RelativeLayout rvUpdate;
    private  boolean isForce;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected int layoutId() {
        return R.layout.zjc_activity_app_update;
    }

    @Override
    protected void getValues() {
        super.getValues();
        appUpdateBean= (ZAppUpdateBean) ZRouter.getSerializable(mActivity);
        if(appUpdateBean==null){
            finish();
        }
    }

    @Override
    protected void initUI() {
        iv_bg = findViewById(R.id.zjc_iv_bg);
        tv_version = findViewById(R.id.zjc_tv_version);
        tv_cancel = findViewById(R.id.zjv_tv_cancel);
        lv_content = findViewById(R.id.zjc_lv_content);
        pg_update = findViewById(R.id.zjc_pg_update);
        rvUpdate = findViewById(R.id.zjc_rv_update);
        if (!TextUtils.isEmpty(appUpdateBean.apkNewVersion)) {
            tv_version.setText("发现新版本:" + appUpdateBean.apkNewVersion);
        }
        if(appUpdateBean.updateContents!=null&&appUpdateBean.updateContents.size()>0){
            for (int i = 0; i < appUpdateBean.updateContents.size(); i++) {
                addView(lv_content, appUpdateBean.updateContents.get(i));
            }
        }
        tv_load = findViewById(R.id.zjv_tv_load);
        isForce=appUpdateBean.isForce;
        if (appUpdateBean.isForce) {
            tv_cancel.setVisibility(View.GONE);
        }
        if(appUpdateBean.bgIcon!=-1){
            iv_bg.setImageResource(appUpdateBean.bgIcon);
        }
    }

    @Override
    public void onBackPressed() {
        if(isForce){
            return;
        }else{
            setResult(RESULT_OK);
            finish();
        }

    }

    @Override
    protected void initListener() {
        super.initListener();
        tv_cancel.setOnClickListener(v -> {
            finish();
        });
        tv_load.setOnClickListener(v -> {
            requestStorePermission();
        });
    }


    @Override
    protected void initData() {
        //下载进度监听
        ZFunctionManager.getInstance().addFunction(new ZFunctionOnlyParam<Integer>(ZConstant.App_UPDATE_PROGRESS) {
            @Override
            public void function(Integer o) {
                try {
                    if (pg_update != null) {
                        int progress = (int) o;
                        if (progress == -1) {  //下载失败
                            ZToast.create().showErrorBottom("下载失败");
                        } else { //下载进度
                            pg_update.setVisibility(View.VISIBLE);
                            pg_update.setProgress(progress);
                            rvUpdate.setVisibility(View.GONE);
                        }
                    }
                } catch (Exception e) {
                }
            }
        });
    }



    /**
     * 添加更新文案
     *
     * @param linearLayout
     * @param updateContent
     */
    private void addView(LinearLayout linearLayout, String updateContent) {
        TextView textView = new TextView(mActivity);
        textView.setPadding(ZDimen.dp2px(18), ZDimen.dp2px(3), ZDimen.dp2px(18), ZDimen.dp2px(3));
        textView.setTextColor(ZColor.byRes(R.color.zjcGray3));
        textView.setTextSize(11);
        textView.setText(updateContent);
        linearLayout.addView(textView);
    }

    /**
     * 开启下载，并添加下载完成回调
     */
    public void toUpdateServiceDownload() {
        ZFunctionManager.getInstance().addFunction(new ZFunctionOnlyParam<File>(ZConstant.APP_DOWNLOAD_FINISH) {
            @Override
            public void function(File file) {
                if (file != null) {
                    checkInstallPermission(file);
                }
            }
        });
        Intent intent = new Intent(mActivity, ZUpdateService.class);
        intent.putExtra(ZConstant.APK_URL, appUpdateBean.apkUrl);
        intent.putExtra(ZConstant.APP_LOGO, appUpdateBean.appLogoIcon);
        if(TextUtils.isEmpty(appUpdateBean.apkDirectory)){
            intent.putExtra(ZConstant.APK_DIRECTORY, ZConstant.CACHE_APK);
        }else{
            intent.putExtra(ZConstant.APK_DIRECTORY, appUpdateBean.apkDirectory);
        }
        startService(intent);
    }


    private void requestStorePermission() {
        ZPermission.getInstance(this).requestStorage(new ZPermission.PCallback() {
            @Override
            public void onReject() {
            }

            @Override
            public void onComplete() {
                toUpdateServiceDownload();
            }
        });
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void startInstallPermissionSettingActivity(final File file) {
        Uri packageURI = Uri.parse("package:" + getPackageName());
        //注意这个是8.0新API
        Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, packageURI);
        ZFunctionManager.getInstance().addFunction(new ZFunctionOnlyParam<Boolean>(ZConstant.App_INSTALL_APP) {
            @Override
            public void function(Boolean isHas) {
                if (isHas) {
                    openFile(file);
                } else {
                    resetDownView();
                }
            }
        });
        mActivity.startActivityForResult(intent, ZConstant.INSTALL_PERMISSION);
    }


    /**
     * 恢复到下载之前状态
     */
    private  void resetDownView(){
        pg_update.setProgress(0);
        pg_update.setVisibility(View.GONE);
        rvUpdate.setVisibility(View.VISIBLE);
    }
    /**
     * 8.0需要安装的权限
     */
    private void checkInstallPermission(final File file) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //先获取是否有安装未知来源应用的权限
            boolean haveInstallPermission = getPackageManager().canRequestPackageInstalls();
            if (!haveInstallPermission) {
                startInstallPermissionSettingActivity(file);
            } else {
                openFile(file);
            }
        } else {
            openFile(file);
        }
    }

    /**
     * 安装应用
     * @param file
     */
    public void openFile(File file) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            uri = FileProvider.getUriForFile(getApplicationContext(), ZAppInstallProvider.getAuthority(mActivity), file);
            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(uri, "application/vnd.android.package-archive");
        } else {
            uri = Uri.fromFile(file);
            intent.setDataAndType(uri, "application/vnd.android.package-archive");
        }
        startActivityForResult(intent,ZConstant.INSTALL_RESULT);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ZConstant.INSTALL_PERMISSION) {
            if (resultCode == RESULT_OK) {
                ZFunctionManager.getInstance().invokeOnlyPramFunc(ZConstant.App_INSTALL_APP, true);//执行安装流程
            } else {
                ZFunctionManager.getInstance().invokeOnlyPramFunc(ZConstant.App_INSTALL_APP, false);//不允许安装流程
            }

        }else if(requestCode == ZConstant.INSTALL_RESULT){
            if (resultCode == RESULT_OK) {
                ZLog.e("安装");
            } else {
                resetDownView();
            }
        }
    }
}
