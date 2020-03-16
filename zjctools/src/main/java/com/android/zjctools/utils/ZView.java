package com.android.zjctools.utils;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class ZView {

    /**
     * 获取view坐标
     * @param targetView
     * @return
     */
    public static  int[] getScreenLocation(View targetView){
        int[] location = new int[2];
        targetView.getLocationOnScreen(location);
        return  location;
    }

    /**
     * EditText获取焦点，一般用于进入页面拉起软键盘
     * @param
     * @return
     */
    public static void  setEditTextFocusable(EditText targetView){
        targetView.setFocusable(true);
        targetView.setFocusableInTouchMode(true);
        targetView.requestFocus();
        Activity activity= (Activity) targetView.getContext();
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }

    /**
     * 隐藏软件盘
     * @param targetView
     */
    public static  void hideSoftKey(EditText targetView){
        Activity activity= (Activity) targetView.getContext();
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if(imm!=null){
            imm.hideSoftInputFromWindow(targetView.getWindowToken(), 0); //强制隐藏键盘
        }

    }

    /**
     * 显示键盘
     */
    public static  void showSoftKey( EditText targetView) {
        Activity activity= (Activity) targetView.getContext();
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if(imm!=null){
            imm.showSoftInput(targetView, InputMethodManager.SHOW_IMPLICIT);
        }

    }

    /**
     * 向EditText指定光标位置插入字符串
     * @param targetView
     * @param insertContent
     */
    public static void insertInputText(EditText targetView, String  insertContent) {
        if(targetView.getText()==null||targetView.getText().toString().equals("")){
            targetView.setText(insertContent);
            return;
        }
        targetView.getText().insert(targetView.getSelectionStart(), insertContent);
    }




    /**
     * 输入键盘监听
     */
    public interface ZInputListener {
        void onTextChange(String text);
        boolean onDoneAction();
    }
    /**
     * 输入框文本监听
     */
    public static void setInputListener(EditText targetView,ZInputListener inputListener) {
        targetView.setOnEditorActionListener((v, actionId, event) -> {
            if (inputListener != null) {
                hideSoftKey(targetView);
                return inputListener.onDoneAction();
            }
            return false;
        });
        //根据输入框输入值的改变来过滤搜索
        targetView.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                if (inputListener != null) {
                    inputListener.onTextChange(s.toString());
                }
            }
        });
    }
}
