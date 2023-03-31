package com.android.zjctools.imagepicker.helper;

import android.app.Activity;

import com.android.zjctools.imagepicker.bean.PickerError;
import com.android.zjctools.imagepicker.data.OnImagePickCompleteListener;
import com.android.zjctools.imagepicker.data.OnImagePickCompleteListener2;

/**
 * Time: 2019/10/18 9:53
 * Author:ypx
 * Description: 调用选择器失败回调
 */
public class PickerErrorExecutor {

    public static void executeError(Activity activity, int code) {
        activity.setResult(code);
        activity.finish();
    }

    public static void executeError(OnImagePickCompleteListener listener, int code) {
        if (listener instanceof OnImagePickCompleteListener2) {
            ((OnImagePickCompleteListener2) listener).onPickFailed(PickerError.valueOf(code));
        }
    }
}
