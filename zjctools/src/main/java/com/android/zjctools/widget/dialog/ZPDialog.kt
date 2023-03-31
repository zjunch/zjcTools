package com.android.zjctools.widget.dialog

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.text.InputFilter
import android.text.SpannableString
import android.text.Spanned
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import android.widget.TextView
import com.android.zjctools.utils.ZColor
import com.android.zjctools.utils.ZDimen
import com.android.zjctools.utils.ZStr
import com.android.zjctools.utils.ZToast
import com.android.zjcutils.R

class ZPDialog(private val context: Context, private val type: Type) {
    private var dialog: Dialog? = null
    private var view: View? = null
    private var titleTV: TextView? = null
    private var messageTV: TextView? = null
    private var contentET: EditText? = null
    var cancelTV: TextView? = null
    var confirmTV: TextView? = null
    private var isNeedContent   = false//输入框是否是必填
    private var contentAlert : String? = null//输入框必填时，如未填写应该给的提示
    private var titleLine: View? = null

    init {
        view = when (type) {
            Type.ALERT -> LayoutInflater.from(context).inflate(R.layout.z_widget_dialog_alert, null)
            Type.PROGRESS -> LayoutInflater.from(context).inflate(R.layout.z_widget_dialog_progress, null)
            Type.SELECT -> LayoutInflater.from(context).inflate(R.layout.z_widget_dialog_select, null)
            Type.EDIT -> LayoutInflater.from(context).inflate(R.layout.z_widget_dialog_edit, null)
        }
        initDialog()
    }

    /**
     * 初始化对话框
     */
    private fun initDialog() {
        titleTV = view!!.findViewById(R.id.z_dialog_title_tv)
        messageTV = view!!.findViewById(R.id.z_dialog_message_tv)
        contentET = view!!.findViewById(R.id.z_dialog_content_et)
        cancelTV = view!!.findViewById(R.id.z_dialog_cancel_btn)
        confirmTV = view!!.findViewById(R.id.z_dialog_confirm_btn)
        titleLine = view!!.findViewById(R.id.z_dialog_title_divide)
        if (dialog == null) {
            dialog = if (type == Type.PROGRESS) {
                Dialog(context)
            } else {
                Dialog(context, R.style.ZDialogStyle)
            }
            dialog!!.setContentView(view!!)
            dialog!!.setCanceledOnTouchOutside(true)
            if (cancelTV != null) {
                cancelTV!!.setOnClickListener { v: View? -> dialog!!.dismiss() }
            }
        }
        setContentEtMaxLength(20)
    }

    fun setTouchOutside(enable: Boolean) {
        dialog!!.setCanceledOnTouchOutside(enable)
    }

    /**
     * 标题文字大小
     */
    fun setTitleSize(size: Int) {
        titleTV!!.textSize = ZDimen.sp2px(size).toFloat()
    }

    fun setTitleShow(isShow: Boolean) {
        titleTV!!.visibility = if (isShow) View.VISIBLE else View.GONE
    }

    fun setMessageShow(isShow: Boolean) {
        messageTV!!.visibility = if (isShow) View.VISIBLE else View.GONE
    }

    fun setTitleId(resId: Int) {
        titleTV!!.text = ZStr.byRes(resId)
    }

    fun setTitle(title: String?) {
        setTitle(title, ZColor.byRes(R.color.z_black_87))
    }

    fun setTitle(title: String?, colorId: Int) {
        if (ZStr.isEmpty(title)) {
            titleTV!!.visibility = View.GONE
        } else {
            titleTV!!.visibility = View.VISIBLE
            titleTV!!.text = title
            titleTV!!.setTextColor(colorId)
        }
    }

    /**
     * 标题下面的分割线
     * @param isShow
     */
    fun setTitleLineEnable(isShow: Boolean) {
        if (titleLine != null) {
            titleLine!!.visibility = if (isShow) View.VISIBLE else View.GONE
        }
    }

    /**
     * 标题下面的分割线
     * @param isShow
     */
    fun setTitleLineEnable(isShow: Boolean, colorId: Int) {
        titleLine!!.visibility = View.VISIBLE
        setTitleLineColor(colorId)
    }

    /**
     * 标题下面的分割线颜色
     */
    fun setTitleLineColor(colorId: Int) {
        if (titleLine != null) {
            titleLine!!.setBackgroundColor(ZColor.byRes(colorId))
        }
    }

    /**
     * 提示内容文字大小
     */
    fun setMessageSize(size: Int) {
        messageTV!!.textSize = ZDimen.sp2px(size).toFloat()
    }

    fun setMessageRes(resId: Int) {
        messageTV!!.text = ZStr.byRes(resId)
    }

    fun setMessage(msg: String?="") {
        setMessage(msg, ZColor.byRes(R.color.z_black_87))
    }

    fun setMessage(message: String?="", colorId: Int) {
        if (ZStr.isEmpty(message)) {
            messageTV!!.visibility = View.GONE
        } else {
            messageTV!!.visibility = View.VISIBLE
            messageTV!!.text = message
            messageTV!!.setTextColor(colorId)
        }
    }

    fun setSpanTitle(title: SpannableString?) {
        messageTV!!.text = title
    }

    fun setSpanTitle(title: Spanned?) {
        messageTV!!.text = title
    }

    fun setSpanMessage(message: SpannableString?) {
        messageTV!!.text = message
    }

    fun setMessage(message: Spanned?) {
        messageTV!!.text = message
    }

    /**
     * 获取输入的内容
     */
    val content: String
        get() = if (contentET != null) {
            contentET!!.text.toString().trim { it <= ' ' }
        } else ""

    /**
     * 设置输入框行数
     */
    fun setContentETLine(lines: Int) {
        if (contentET != null) {
            contentET!!.setLines(lines)
        }
    }

    /**
     * 设置输入提示的内容
     */
    fun setContentTextSize(size: Int) {
        if (contentET != null) {
            contentET!!.textSize = ZDimen.sp2px(size).toFloat()
        }
    }

    /**
     * 设置输入提示的内容
     */
    fun setContentEtHintColor(hintColor: Int) {
        if (contentET != null) {
            contentET!!.setHintTextColor(ZColor.byRes(hintColor))
        }
    }

    /**
     * 设置输入字体颜色
     */
    fun setContentEtTextColor(hintColor: Int) {
        if (contentET != null) {
            contentET!!.setTextColor(ZColor.byRes(hintColor))
        }
    }

    /**
     * 设置输入提示的内容
     */
    fun setContentEtHint(hintText: String?) {
        if (contentET != null) {
            contentET!!.hint = hintText
        }
    }

    /**
     * 设置输入框最多字体
     */
    fun setContentEtMaxLength(maxLength: Int) {
        if (contentET != null) {
            contentET!!.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(maxLength))
        }
    }

    /**
     * 设置输入框背景
     */
    fun setContentEtBgRes(bgRes: Int) {
        if (contentET != null) {
            contentET!!.setBackgroundResource(bgRes)
        }
    }

    /**
     * 设置输入默认的内容
     */
    fun setContentEt(hintText: String?) {
        if (contentET != null) {
            contentET!!.setText(hintText)
        }
    }

    fun setNeedContent(isNeed: Boolean, alert: String?) {
        isNeedContent = isNeed
        contentAlert = if (!TextUtils.isEmpty(alert)) {
            alert
        } else {
            ZStr.byRes(R.string.z_input_default_toast)
        }
    }

    fun setCancelTVBg(resId: Int) {
        if (cancelTV != null) {
            cancelTV!!.setBackgroundResource(resId)
        }
    }

    fun setCancelTVColor(colorId: Int) {
        if (cancelTV != null) {
            cancelTV!!.setTextColor(ZColor.byRes(colorId))
        }
    }

    fun setConfirmTVBg(resId: Int) {
        if (confirmTV != null) {
            confirmTV!!.setBackgroundResource(resId)
        }
    }

    /**
     * 设置点击监听
     */
    fun setConfirmClick(listener: View.OnClickListener?) {
        confirmTV!!.setOnClickListener { v: View? ->
            if (isNeedContent && (contentET!!.text == null || contentET!!.text.toString()
                    .trim { it <= ' ' } == "")
            ) {
                alertContent()
                return@setOnClickListener
            }
            listener?.onClick(v)
            dismiss()
        }
    }

    fun setConfirmClick(id: Int, listener: View.OnClickListener?) {
        confirmTV!!.setText(id)
        confirmTV!!.setOnClickListener { v: View? ->
            if (isNeedContent && (contentET!!.text == null || contentET!!.text.toString()
                    .trim { it <= ' ' } == "")
            ) {
                alertContent()
                return@setOnClickListener
            }
            listener?.onClick(v)
            dismiss()
        }
    }

    fun alertContent() {
        if (!TextUtils.isEmpty(contentAlert)) {
            ZToast.showNormal(contentAlert)
        }
    }

    fun setCancel(resId: Int) {
        cancelTV!!.setText(resId)
    }

    fun setCancel(cancel: String?) {
        cancelTV!!.text = cancel
    }

    fun setCancelClick(listener: View.OnClickListener?) {
        cancelTV!!.setOnClickListener(listener)
    }

    fun setCancelClick(id: Int, listener: View.OnClickListener?) {
        confirmTV!!.setText(id)
        confirmTV!!.setOnClickListener(listener)
    }

    fun setOnDimissListener(listener: DialogInterface.OnDismissListener?) {
        dialog!!.setOnDismissListener(listener)
    }

    fun setOnKeyListener(listener: DialogInterface.OnKeyListener?) {
        dialog!!.setOnKeyListener(listener)
    }

    val isShowing: Boolean
        get() = if (dialog != null) {
            dialog!!.isShowing
        } else false

    fun show() {
        if (dialog != null && !dialog!!.isShowing) {
            dialog!!.show()
            val lp = dialog!!.window!!.attributes
            if (type == Type.PROGRESS) {
                lp.width = WindowManager.LayoutParams.WRAP_CONTENT
            } else {
                lp.width = ZDimen.screenWidth * 5 / 6 //设置宽度
            }
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT //设置宽度
            dialog!!.window!!.attributes = lp
        }
    }

    fun dismiss() {
        if (dialog != null && dialog!!.isShowing) {
            dialog!!.dismiss()
        }
    }

    enum class Type {
        ALERT, PROGRESS, SELECT, EDIT
    }


}