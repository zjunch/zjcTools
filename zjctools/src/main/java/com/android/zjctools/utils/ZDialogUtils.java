package com.android.zjctools.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import com.android.zjctools.widget.dialog.ZPDialog;
import com.android.zjctools.widget.dialog.ZPWindow;

import java.util.List;

public class ZDialogUtils {

    /**
     * 显示选择对话框
     */
    public static ZPDialog showSelectDialog(Context context, String title, String message, View.OnClickListener listener) {
        ZPDialog dialog = new ZPDialog(context, ZPDialog.Type.SELECT);
        dialog.setTitle(title);
        dialog.setMessage(message);
        dialog.setConfirmClick(listener);
        dialog.show();
        return dialog;
    }

    /**
     * 显示选择对话框
     */
    public static ZPDialog showSelectDialog(Context context, String title, String message, int confirmResId,
                                           View.OnClickListener listener) {
        ZPDialog dialog = new ZPDialog(context, ZPDialog.Type.SELECT);
        dialog.setTitle(title);
        dialog.setMessage(message);
        dialog.setConfirmClick(confirmResId, listener);
        dialog.show();
        return dialog;
    }

    /**
     * 显示选择对话框
     */
    public static ZPDialog showSelectDialog(Context context, String title, String message, int cancelResId, int confirmResId,
                                           View.OnClickListener listener) {
        ZPDialog dialog = new ZPDialog(context, ZPDialog.Type.SELECT);
        dialog.setTitle(title);
        dialog.setMessage(message);
        dialog.setCancel(cancelResId);
        dialog.setConfirmClick(confirmResId, listener);
        dialog.show();
        return dialog;
    }

    /**
     * 显示选择对话框
     */
    public static ZPDialog showEditDialog(Context context, String title, View.OnClickListener listener) {
        ZPDialog dialog = new ZPDialog(context, ZPDialog.Type.EDIT);
        dialog.setTitle(title);
        dialog.setConfirmClick(listener);
        dialog.show();
        return dialog;
    }

    /**
     * 显示提示对话框
     */
    public static ZPDialog showAlertDialog(Context context, String message, View.OnClickListener listener) {
        ZPDialog dialog = new ZPDialog(context, ZPDialog.Type.ALERT);
        dialog.setConfirmClick(listener);
        dialog.setMessage(message);
        dialog.show();
        return dialog;
    }


    /**
     * 显示底部选项弹窗
     */
    public static ZPWindow showBottomWindow(Activity activity, List<String> list, ZPWindow.OnWindowListener listener) {
        ZPWindow window = new ZPWindow(activity, list);
        window.setWindowListener(listener);
        window.show();
        return window;
    }
}
