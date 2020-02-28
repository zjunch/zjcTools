package com.android.zjctools.base;

import android.app.Activity;
import android.os.Bundle;
import com.android.zjctools.toorbar.StatusBarUtil;
import com.android.zjcutils.R;

public abstract class ZBActivity extends ZjcActivity {

    public Activity mActivity;
    private boolean isDarkTextStatusBar=true;  //状态栏字体颜色默认黑色
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity=this;
        //状态栏设置
        initStatusBar();
        setContentView(layoutId());
        //获取intent传参
        getValues();
        //初始话布局
        initUI();
        //设置状态栏电量等信息的字体颜色
        setStatusBar();
        //数据处理
        initData();
        //返回键处理
        initBackView(R.id.ivBack);
    }

    /**
     * 返回键默认返回
     * @param resId，返回图标id
     */
    protected void initBackView(int resId) {
        if (findViewById(resId) == null) return;
        findViewById(resId).setOnClickListener(v -> {
            onBackPressed();
        });
    }

    /**
     * 状态栏字体颜色是否是黑色
     * @param darkTextStatusBar
     */
    protected void setDarkTextStatusBar(boolean darkTextStatusBar) {
        isDarkTextStatusBar = darkTextStatusBar;
    }


    private void setStatusBar(){
        StatusBarUtil.setStatusBarDarkTheme(this, isDarkTextStatusBar);
    }


    /**
     * 状态栏等设置
     */
    protected void initStatusBar() {
        //当FitsSystemWindows设置 true 时，会在屏幕最上方预留出状态栏高度的 padding
        StatusBarUtil.setRootViewFitsSystemWindows(this, false);
        StatusBarUtil.setTranslucentStatus(this);
        StatusBarUtil.setStatusBarDarkTheme(this, true);
    }


    /**
     * 获取传参等数据
     */
    protected  void  getValues(){}
    /**
     * 加载布局 id
     * @return 返回布局 id
     */
    protected abstract int layoutId();

    /**
     * 初始化 UI
     */
    protected abstract void initUI();

    /**
     * 初始化数据
     */
    protected abstract void initData();
}
