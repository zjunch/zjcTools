package com.android.zjctools.utils

import android.app.Activity
import android.content.Context
import android.view.View
import com.android.zjctools.widget.dialog.ZPDialog
import com.android.zjctools.widget.dialog.ZWindowSelect
import com.android.zjcutils.R


object ZDialogUtils {

    /**
     * 显示选择对话框
     */
    fun showSelectDialog(context: Context, title: String?,
                         message: String?, listener: View.OnClickListener?): ZPDialog? {
        val dialog = ZPDialog(context, ZPDialog.Type.SELECT)
        dialog.setTitle(title)
        dialog.setMessage(message)
        dialog.setConfirmClick(listener)
        dialog.show()
        return dialog
    }

    /**
     * 显示选择对话框
     */
    fun showSelectDialog(
        context: Context, title: String?, message: String?, confirmResId: Int,
        listener: View.OnClickListener?
    ): ZPDialog? {
        val dialog = ZPDialog(context, ZPDialog.Type.SELECT)
        dialog.setTitle(title)
        dialog.setMessage(message)
        dialog.setConfirmClick(confirmResId, listener)
        dialog.show()
        return dialog
    }

    /**
     * 显示选择对话框
     */
    fun showSelectDialog(
        context: Context, title: String?, message: String?, cancelResId: Int, confirmResId: Int,
        listener: View.OnClickListener?
    ): ZPDialog? {
        val dialog = ZPDialog(context, ZPDialog.Type.SELECT)
        dialog.setTitle(title)
        dialog.setMessage(message)
        dialog.setCancel(cancelResId)
        dialog.setConfirmClick(confirmResId, listener)
        dialog.show()
        return dialog
    }

    /**
     * 显示选择对话框
     */
    fun showEditDialog(
        context: Context,
        title: String?,
        listener: View.OnClickListener?
    ): ZPDialog? {
        val dialog = ZPDialog(context, ZPDialog.Type.EDIT)
        dialog.setTitle(title)
        dialog.setConfirmClick(listener)
        dialog.show()
        return dialog
    }

    /**
     * 显示提示对话框
     */
    fun showAlertDialog(
        context: Context,
        message: String?,
        listener: View.OnClickListener?
    ): ZPDialog? {
        val dialog = ZPDialog(context, ZPDialog.Type.ALERT)
        dialog.setConfirmClick(listener)
        dialog.setMessage(message)
        dialog.show()
        return dialog
    }


    /**
     * 显示底部选项弹窗
     */
    fun showBottomWindow(activity: Activity?, layoutId:Int= R.layout.z_widget_window_layout): ZWindowSelect? {
        val window = ZWindowSelect(activity!!,layoutId)
        window.show()
        return window
    }
}