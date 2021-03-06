package com.android.zjctools.permission;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;


import com.android.zjctools.utils.ZSystem;
import com.android.zjctools.base.ZConstant;
import com.android.zjctools.base.ZjcActivity;
import com.android.zjctools.router.ZRouter;
import com.android.zjctools.widget.ZViewGroup;
import com.android.zjcutils.R;
import com.android.zjctools.utils.ZStr;

import java.util.Iterator;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


/**
 * Create by zjun
 *
 * 处理权限请求界面
 */
public class ZPermissionActivity extends ZjcActivity {

    // 权限申请
    private static final int REQUEST_PERMISSION = 100;
    // 被拒绝后再次申请
    private static final int REQUEST_PERMISSION_AGAIN = 101;
    // 调起设置界面设置权限
    private static final int REQUEST_SETTING = 200;

    /**
     * 重新申请权限数组的索引
     */
    private int mAgainIndex;
    // 是否显示申请权限弹窗
    private Boolean mEnableDialog;
    // 申请权限弹窗标题
    private String mTitle;
    // 申请权限弹窗描述
    private String mMessage;
    // 权限列表
    private List<ZPermissionBean> mPermissions;
    // 权限列表拷贝
    private List<ZPermissionBean> mPermissionsCopy;
    // 授权提示框
    private AlertDialog mDialog;
    private String mAppName;
    private ZPermission.PCallback mCallback;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    /**
     * 权限请求初始化操作
     */
    private void init() {
        // 初始化获取数据
        mAppName = ZSystem.getAppName(mActivity);
        mCallback = ZPermission.getInstance(mActivity).getPermissionCallback();
        mEnableDialog = getIntent().getBooleanExtra(ZConstant.ZJC_KEY_PERMISSION_ENABLE_DIALOG, false);
        mTitle = getIntent().getStringExtra(ZConstant.ZJC_KEY_PERMISSION_TITLE);
        mMessage = getIntent().getStringExtra(ZConstant.ZJC_KEY_PERMISSION_MSG);
        mPermissions = getIntent().getParcelableArrayListExtra(ZConstant.ZJC_KEY_PERMISSION_LIST);
        mPermissionsCopy = mPermissions;

        if (mPermissions == null || mPermissions.size() == 0) {
            onFinish();
            return;
        }
        // 根据需要弹出说明对话框
        if (mEnableDialog) {
            showPermissionDialog();
        } else {
            requestPermission(getPermissionArray(), REQUEST_PERMISSION);
        }
    }

    /**
     * 获取申请权限集合
     */
    private String[] getPermissionArray() {
        String[] permissionArray = new String[mPermissions.size()];
        for (int i = 0; i < mPermissions.size(); i++) {
            ZPermissionBean item = mPermissions.get(i);
            permissionArray[i] = item.permission;
        }
        return permissionArray;
    }

    /**
     * 根据权限名获取申请权限的实体类
     */
    private ZPermissionBean getPermissionItem(String permission) {
        for (int i = 0; i < mPermissions.size(); i++) {
            ZPermissionBean item = mPermissions.get(i);
            if (item.permission.equals(permission)) {
                return item;
            }
        }
        return null;
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
    private void showAlertDialog(String title, String message, String cancelStr, String okStr, DialogInterface.OnClickListener listener) {
        AlertDialog alertDialog = new AlertDialog.Builder(mActivity).setTitle(title)
                .setMessage(message)
                .setCancelable(false)
                .setNegativeButton(cancelStr, (dialog, which) -> {
                    dialog.dismiss();
                    onPermissionReject();
                    onFinish();
                })
                .setPositiveButton(okStr, listener)
                .create();
        alertDialog.show();
    }

    /**
     * 弹出授权窗口
     */
    private void showPermissionDialog() {
        View view = LayoutInflater.from(mActivity)
                .inflate(R.layout.zjc_widget_permission_dialog, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        if (ZStr.isEmpty(mTitle)) {
            mTitle = ZStr.byRes(R.string.zjc_permission_title);
        }
        builder.setTitle(mTitle);
        builder.setView(view);
        // 设置提醒信息
        if (ZStr.isEmpty(mMessage)) {
            mMessage = ZStr.byRes(R.string.zjc_permission_reason);
        }
        TextView contentView = view.findViewById(R.id.zjc_permission_dialog_content_tv);
        contentView.setText(mMessage);
        ZViewGroup viewGroup = view.findViewById(R.id.zjc_permission_dialog_custom_vg);
        for (ZPermissionBean bean : mPermissions) {
            ZPermissionView pView = new ZPermissionView(mActivity);
            pView.setPermissionIcon(bean.resId);
            pView.setPermissionName(bean.name);
            viewGroup.addView(pView);
        }

        view.findViewById(R.id.zjc_i_know_btn).setOnClickListener(v -> {
            if (mDialog != null && mDialog.isShowing()) {
                mDialog.dismiss();
            }
            // 开始申请权限
            requestPermission(getPermissionArray(), REQUEST_PERMISSION);
        });
        mDialog = builder.create();
        mDialog.setCancelable(false);
        mDialog.show();
    }

    /**
     * 申请权限，内部方法
     *
     * @param permissions 权限列表
     * @param requestCode 请求码
     */
    private void requestPermission(String[] permissions, int requestCode) {
        ActivityCompat.requestPermissions(mActivity, permissions, requestCode);
    }

    /**
     * 再次申请权限
     *
     * @param item 要申请的权限
     */
    private void requestPermissionAgain(final ZPermissionBean item) {
        String alertTitle = String.format(getString(R.string.zjc_permission_again_title), item.name);
        String msg = String.format(getString(R.string.zjc_permission_again_reason), item.name, item.reason);
        showAlertDialog(alertTitle, msg, getString(R.string.zjc_btn_cancel), getString(R.string.zjc_btn_ok), (dialog, which) -> {
            dialog.dismiss();
            requestPermission(new String[] { item.permission }, REQUEST_PERMISSION_AGAIN);
        });
    }

    /**
     * 申请权限回调
     *
     * @param requestCode  权限申请请求码
     * @param permissions  申请的权限数组
     * @param grantResults 授权结果集合
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_PERMISSION:
                for (int i = 0; i < grantResults.length; i++) {
                    // 权限允许后，删除需要检查的权限
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        mPermissions.remove(getPermissionItem(permissions[i]));
                    }
                }
                if (mPermissions.size() > 0) {
                    //用户拒绝了某个或多个权限，重新申请
                    requestPermissionAgain(mPermissions.get(mAgainIndex));
                } else {
                    onPermissionComplete();
                    finish();
                }
                break;
            case REQUEST_PERMISSION_AGAIN:
                if (permissions.length == 0 || grantResults.length == 0) {
                    onPermissionReject();
                    onFinish();
                    break;
                }
                if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                    // 重新申请后再次拒绝，弹框警告
                    // permissions 可能返回空数组，所以try-catch
                    ZPermissionBean item = mPermissions.get(0);
                    String title = String.format(getString(R.string.zjc_permission_again_title), item.name);
                    String msg = String.format(getString(R.string.zjc_permission_denied_setting), mAppName, item.name, mAppName);
                    showAlertDialog(title, msg, getString(R.string.zjc_btn_reject), getString(R.string.zjc_btn_go_to_setting), (dialog, which) -> ZRouter.goSettingDetail(mActivity, REQUEST_SETTING));
                } else {
                    if (mAgainIndex < mPermissions.size() - 1) {
                        // 继续申请下一个被拒绝的权限
                        requestPermissionAgain(mPermissions.get(++mAgainIndex));
                    } else {
                        // 全部允许了
                        onPermissionComplete();
                        onFinish();
                    }
                }
                break;
        }
    }

    /**
     * 回调用户手动设置授权结果，同时再次对权限进行检查，防止用户关闭了某些权限
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_SETTING) {
            if (mDialog != null && mDialog.isShowing()) {
                mDialog.dismiss();
            }
            checkPermission();
            if (mPermissions.size() > 0) {
                mAgainIndex = 0;
                requestPermissionAgain(mPermissions.get(mAgainIndex));
            } else {
                onPermissionComplete();
                onFinish();
            }
        }
    }

    /**
     * 循环检查权限，移除已授权权限
     * 这里的检查是一个完整的检查，为的是防止用户打开设置后取消了某些已授权的权限
     */
    private void checkPermission() {
        mPermissions = mPermissionsCopy;
        Iterator<ZPermissionBean> iterator = mPermissions.listIterator();
        while (iterator.hasNext()) {
            int checkPermission = ContextCompat.checkSelfPermission(mActivity, iterator.next().permission);
            if (checkPermission == PackageManager.PERMISSION_GRANTED) {
                iterator.remove();
            }
        }
    }

    /**
     * 回调权限申请被拒绝
     */
    private void onPermissionReject() {
        if (mCallback != null) {
            mCallback.onReject();
        }
    }

    /**
     * 回调权限申请通过
     */
    private void onPermissionComplete() {
        if (mCallback != null) {
            mCallback.onComplete();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCallback = null;
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }
}
