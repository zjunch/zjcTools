package com.android.zjctools.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputFilter;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.android.zjctools.utils.ZColor;
import com.android.zjctools.utils.ZDimen;
import com.android.zjctools.utils.ZStr;
import com.android.zjctools.utils.ZToast;
import com.android.zjcutils.R;


public class ZPDialog {

    private Context context;

    private Dialog dialog;

    private View view;
    private Type type;
    private TextView titleTV;
    private TextView messageTV;
    private EditText contentET;
    private TextView cancelTV;
    private TextView confirmTV;
    private boolean isNeedContent;//输入框是否是必填
    private String contentAlert;//输入框必填时，如未填写应该给的提示
    private View titleLine;

    public ZPDialog(Context context, Type type) {
        this.context = context;
        this.type = type;
        switch (type) {
        case ALERT:
            view = LayoutInflater.from(context).inflate(R.layout.zjc_widget_dialog_alert, null);
            break;
        case PROGRESS:
            view = LayoutInflater.from(context).inflate(R.layout.zjc_widget_dialog_progress, null);
            break;
        case SELECT:
            view = LayoutInflater.from(context).inflate(R.layout.zjc_widget_dialog_select, null);
            break;
        case EDIT:
            view = LayoutInflater.from(context).inflate(R.layout.zjc_widget_dialog_edit, null);
            break;
        }
        initDialog();
    }

    /**
     * 初始化对话框
     */
    private void initDialog() {
        titleTV = view.findViewById(R.id.zjc_dialog_title_tv);
        messageTV = view.findViewById(R.id.zjc_dialog_message_tv);
        contentET = view.findViewById(R.id.zjc_dialog_content_et);
        cancelTV = view.findViewById(R.id.zjc_dialog_cancel_btn);
        confirmTV = view.findViewById(R.id.zjc_dialog_confirm_btn);
        titleLine = view.findViewById(R.id.zjc_dialog_title_divide);
        if (dialog == null) {
            if (type == Type.PROGRESS) {
                dialog = new Dialog(context);
            } else {
                dialog = new Dialog(context, R.style.ZDialogStyle);
            }
            dialog.setContentView(view);
            dialog.setCanceledOnTouchOutside(true);
            if (cancelTV != null) {
                cancelTV.setOnClickListener(v -> dialog.dismiss());
            }
        }
        setContentEtMaxLength(20);
    }



    public  void setTouchOutside(boolean enable){
        dialog.setCanceledOnTouchOutside(enable);
    }


    /**
     * 标题文字大小
     */
    public void setTitleSize(int  size) {
        titleTV.setTextSize(ZDimen.sp2px(size));
    }


    public void setTitleShow(boolean  isShow) {
        titleTV.setVisibility(isShow?View.VISIBLE:View.GONE);
    }


    public void setMessageShow(boolean  isShow) {
        messageTV.setVisibility(isShow?View.VISIBLE:View.GONE);
    }

    public void setTitleId(int resId) {
        titleTV.setText(ZStr.byRes(resId));
    }

    public void setTitle(String title) {
        setTitle(title,ZColor.byRes(R.color.zjc_black_87));
    }
    public void setTitle(String title, int colorId) {
        if (ZStr.isEmpty(title)) {
            titleTV.setVisibility(View.GONE);
        } else {
            titleTV.setVisibility(View.VISIBLE);
            titleTV.setText(title);
            titleTV.setTextColor(colorId);
        }
    }

    /**
     * 标题下面的分割线
     * @param isShow
     */
    public void setTitleLineEnable(boolean isShow){
        if(titleLine!=null){
            titleLine.setVisibility(isShow?View.VISIBLE:View.GONE);
        }
    }

    /**
     * 标题下面的分割线
     * @param isShow
     */
    public void setTitleLineEnable(boolean isShow,int colorId){
        titleLine.setVisibility(View.VISIBLE);
        setTitleLineColor(colorId);
    }


    /**
     * 标题下面的分割线颜色
     */
    public void setTitleLineColor(int colorId){
        if(titleLine!=null){
            titleLine.setBackgroundColor(ZColor.byRes(colorId));
        }
    }


    /**
     * 提示内容文字大小
     */
    public void setMessageSize(int  size) {
        messageTV.setTextSize(ZDimen.sp2px(size));
    }

    public void setMessageRes(int resId) {
        messageTV.setText(ZStr.byRes(resId));
    }
    public void setMessage(String  msg) {
        setMessage(msg,ZColor.byRes(R.color.zjc_black_87));
    }

    public void setMessage(String message,int colorId) {
        if (ZStr.isEmpty(message)) {
            messageTV.setVisibility(View.GONE);
        } else {
            messageTV.setVisibility(View.VISIBLE);
            messageTV.setText(message);
            messageTV.setTextColor(colorId);
        }
    }


    public void setSpanTitle(SpannableString title) {
        messageTV.setText(title);
    }
    public void setSpanTitle(Spanned title) {
        messageTV.setText(title);
    }


    public void setSpanMessage(SpannableString message) {
        messageTV.setText(message);
    }
    public void setMessage(Spanned message) {
        messageTV.setText(message);
    }




    /**
     * 获取输入的内容
     */
    public String getContent() {
        if (contentET != null) {
            return contentET.getText().toString().trim();
        }
        return "";
    }

    /**
     * 设置输入框行数
     */
    public void setContentETLine(int lines) {
        if (contentET != null) {
            contentET.setLines(lines);
        }
    }


    /**
     * 设置输入提示的内容
     */
    public void setContentTextSize(int size) {
        if (contentET != null) {
            contentET.setTextSize(ZDimen.sp2px(size));
        }
    }


    /**
     * 设置输入提示的内容
     */
    public void setContentEtHintColor(int hintColor) {
        if (contentET != null) {
            contentET.setHintTextColor(ZColor.byRes(hintColor));
        }
    }

    /**
     * 设置输入字体颜色
     */
    public void setContentEtTextColor(int hintColor) {
        if (contentET != null) {
            contentET.setTextColor(ZColor.byRes(hintColor));
        }
    }


    /**
     * 设置输入提示的内容
     */
    public void setContentEtHint(String hintText) {
        if (contentET != null) {
            contentET.setHint(hintText);
        }
    }

    /**
     * 设置输入框最多字体
     */
    public void setContentEtMaxLength(int maxLength) {
        if (contentET != null) {
            contentET.setFilters(new InputFilter[] { new InputFilter.LengthFilter(maxLength) });
        }
    }

    /**
     * 设置输入框背景
     */
    public void setContentEtBgRes(int bgRes) {
        if (contentET != null) {
            contentET.setBackgroundResource(bgRes);
        }
    }


    /**
     * 设置输入默认的内容
     */
    public void setContentEt(String hintText) {
        if (contentET != null) {
            contentET.setText(hintText);
        }
    }

    public void setNeedContent(boolean isNeed,String  alert) {
        this.isNeedContent = isNeed;
        if(!TextUtils.isEmpty(alert)){
            contentAlert=alert;
        }else{
            contentAlert=ZStr.byRes(R.string.zjc_input_default_toast);
        }
    }


    public  void setCancelTVBg(int resId){
        if(cancelTV!=null){
            cancelTV.setBackgroundResource(resId);
        }
    }

    public  void setCancelTVColor(int colorId){
        if(cancelTV!=null){
            cancelTV.setTextColor(ZColor.byRes(colorId));
        }
    }

    public  void setConfirmTVBg(int resId){
        if(confirmTV!=null){
            confirmTV.setBackgroundResource(resId);
        }
    }


    public TextView getCancelTV(){
        return  cancelTV;
    }

    public TextView getConfirmTV(){
        return  confirmTV;
    }

    /**
     * 设置点击监听
     */
    public void setConfirmClick(View.OnClickListener listener) {
        confirmTV.setOnClickListener(v -> {
            if (isNeedContent && (contentET.getText() == null || contentET.getText().toString().trim().equals(""))) {
                alertContent();
                return;
            }
            if (listener != null) {
                listener.onClick(v);
            }
            dismiss();
        });
    }

    public void setConfirmClick(int id, View.OnClickListener listener) {
        confirmTV.setText(id);
        confirmTV.setOnClickListener(v -> {
            if (isNeedContent && (contentET.getText() == null || contentET.getText().toString().trim().equals(""))) {
                alertContent();
                return;
            }
            if (listener != null) {
                listener.onClick(v);
            }
            dismiss();
        });
    }

    public void alertContent() {
        if(!TextUtils.isEmpty(contentAlert)){
            ZToast.create().showNormal(contentAlert);
        }
    }

    public void setCancel(int resId) {
        cancelTV.setText(resId);
    }

    public void setCancel(String cancel) {
        cancelTV.setText(cancel);
    }

    public void setCancelClick(View.OnClickListener listener) {
        cancelTV.setOnClickListener(listener);
    }

    public void setCancelClick(int id, View.OnClickListener listener) {
        confirmTV.setText(id);
        confirmTV.setOnClickListener(listener);
    }

    public void setOnDimissListener(DialogInterface.OnDismissListener listener) {
        dialog.setOnDismissListener(listener);
    }

    public void setOnKeyListener(DialogInterface.OnKeyListener listener) {
        dialog.setOnKeyListener(listener);
    }

    public boolean isShowing() {
        if (dialog != null) {
            return dialog.isShowing();
        }
        return false;
    }

    public void show() {
        if (dialog != null && !dialog.isShowing()) {
            dialog.show();
            WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
            if (type == Type.PROGRESS) {
                lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
            } else {
                lp.width = ZDimen.getScreenWidth()*5/6; //设置宽度
            }
            lp.height =  WindowManager.LayoutParams.WRAP_CONTENT; //设置宽度
            dialog.getWindow().setAttributes(lp);
        }
    }

    public void dismiss() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    public enum Type {
        ALERT, PROGRESS, SELECT, EDIT
    }
}