package com.android.zjctools.utils;

import android.app.Activity;
import com.android.zjctools.widget.dialog.ZPWindow;

import java.util.List;

public class DialogUtils {

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
