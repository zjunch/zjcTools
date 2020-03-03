package com.android.zjctools.pick.ui;

import android.os.Bundle;

import android.widget.RelativeLayout;

import com.android.zjctools.base.ZBActivity;
import com.android.zjctools.pick.ZPicker;
import com.android.zjctools.utils.ZDimen;
import com.android.zjctools.widget.ZTopBar;
import com.android.zjcutils.R;


/**
 * Create by lzan13 on 2019/05/19 20:20
 * 选择器基类
 */
public abstract class ZPickBaseActivity extends ZBActivity {

    // 统一的 TopBar
    protected ZTopBar mTopBar;

    @Override
    protected void initUI() {
        setupTopBar();
    }

    /**
     * 装载 TopBar
     */
    protected void setupTopBar() {
        mTopBar = findViewById(R.id.zjc_common_top_bar);
        if (mTopBar != null) {
            // 设置状态栏透明主题时，布局整体会上移，所以给头部加上状态栏的 margin 值，保证头部不会被覆盖
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mTopBar.getLayoutParams();
            params.topMargin = ZDimen.getStatusBarHeight();
            mTopBar.setLayoutParams(params);

            mTopBar.setIconListener(v -> onBackPressed());
        }
    }

    /**
     * 通用的获取 TopBar 方法
     */
    protected ZTopBar getTopBar() {
        return mTopBar;
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        ZPicker.getInstance().restoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        ZPicker.getInstance().saveInstanceState(outState);
    }
}
