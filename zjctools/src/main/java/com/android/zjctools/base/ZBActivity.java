package com.android.zjctools.base;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.android.zjctools.toorbar.StatusBarUtil;
import com.android.zjctools.utils.ZColor;
import com.android.zjctools.widget.ZStatusBarHeightView;
import com.android.zjcutils.R;

import androidx.fragment.app.FragmentActivity;

public abstract class ZBActivity extends ZActivity {


    public FragmentActivity mActivity;

    /**
     * 状态栏字体颜色默认黑色 (如整个标题栏和状态栏为白色,则设置true黑色，反之为false)
     * initUI  方法中调用setDarkTextStatusBar（false） 。
     */
    private boolean isDarkTextStatusBar=true;
    private boolean handleTitleBar=true;  //侵入式 状态栏设置

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity=this;

        //状态栏设置
        initStatusBar();
        setContentView(layoutId());
        //初始化标题view
//        initTitleBar();
        //获取intent传参
        getValues();
        //初始话布局
        initUI();
        //设置状态栏电量等信息的字体颜色
        setStatusBar();
        //数据处理
        initData();
        //返回键处理
        initBackView(R.id.z_bar_iv_back);
        initListener();
    }

    public boolean getHandleTitleBar(){
        return handleTitleBar;
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
        if(!getHandleTitleBar()){//不处理状态栏等
            return;
        }
        StatusBarUtil.setStatusBarDarkTheme(this, isDarkTextStatusBar);
    }


    /**
     * 状态栏等设置
     */
    protected void initStatusBar() {
        if(!getHandleTitleBar()){//不处理状态栏等
            return;
        }
        //当FitsSystemWindows设置 true 时，会在屏幕最上方预留出状态栏高度的 padding
        StatusBarUtil.setRootViewFitsSystemWindows(this, false);
        StatusBarUtil.setTranslucentStatus(this);
    }


    /**
     * 获取传参等数据
     */
    protected  void  getValues(){}


    /**
     * 监听事件
     */
    protected  void  initListener(){}

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
