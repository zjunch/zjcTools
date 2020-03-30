package com.android.zjctools.base;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.android.zjctools.toorbar.StatusBarUtil;
import com.android.zjctools.widget.ZStatusBarHeightView;
import com.android.zjcutils.R;

import androidx.fragment.app.FragmentActivity;

public abstract class ZBActivity extends ZjcActivity {
    ZStatusBarHeightView zjcStatusBarHeightView;
    RelativeLayout zjcRvTitleBar,zjcRvEnd;
    TextView zjcLeftTitle,zjcCenterTitle,zjcTvEnd;
    ImageView zjcIvBack,zjcIvEnd,zjcIvEndSecond;
    View zjcBottomLine;

    public FragmentActivity mActivity;
    private boolean isDarkTextStatusBar=true;  //状态栏字体颜色默认黑色
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity=this;
        //状态栏设置
        initStatusBar();
        setContentView(layoutId());
        //初始化标题view
        initTitleBar();
        //获取intent传参
        getValues();
        //初始话布局
        initUI();
        //设置状态栏电量等信息的字体颜色
        setStatusBar();
        //数据处理
        initData();
        //返回键处理
        initBackView(R.id.zjc_bar_iv_back);
        initListener();
    }

    private  void initTitleBar(){
        if(findViewById(R.id.zjc_bar_rv_title)!=null){
            zjcStatusBarHeightView=findViewById(R.id.zjc_bar_status_View);
            zjcRvTitleBar=findViewById(R.id.zjc_bar_rv_title);
            zjcLeftTitle=findViewById(R.id.zjc_bar_tv_left_title);
            zjcCenterTitle=findViewById(R.id.zjc_bar_tv_center_title);
            zjcRvEnd=findViewById(R.id.zjc_rv_end);
            zjcTvEnd=findViewById(R.id.zjc_bar_tv_end);
            zjcIvBack=findViewById(R.id.zjc_bar_iv_back);
            zjcIvEnd=findViewById(R.id.zjc_bar_iv_end);
            zjcIvEndSecond=findViewById(R.id.zjc_bar_iv_end_second);
        }
    }



    /**
     * 获取titlebar.注意使用时注意空的情况
     * @return
     */
    public RelativeLayout getTitleBar(){
        return zjcRvTitleBar;
    }

    /**
     * 获取
     * @return
     */
    public RelativeLayout getRvEnd(){
        return zjcRvEnd;
    }

    /**
     * 获取返回键view
     * @return
     */
    public ImageView getImageBack(){
        return zjcIvBack;
    }


    /**
     * 获取状态栏等高view
     * @return
     */
    public ZStatusBarHeightView getStatusView(){
        return zjcStatusBarHeightView;
    }


    /**
     * 获取左侧标题
     * @return
     */
    public TextView getLeftTitleView(){
        return zjcLeftTitle;
    }


    /**
     * 获取中间标题
     * @return
     */
    public TextView getCenterTitleView(){
        return zjcCenterTitle;
    }


    /**
     * 获取右侧保存
     * @return
     */
    public TextView getEndTvView(){
        return zjcTvEnd;
    }



    /**
     * 获取右侧图片
     * @return
     */
    public ImageView getEndIvView(){
        return zjcIvEnd;
    }

    /**
     * 获取倒数第二图片
     * @return
     */
    public ImageView getEndIvSecondView(){
        return zjcIvEndSecond;
    }


    /**
     * 获取下划线
     * @return
     */
    public View getBottomLine(){
        return zjcBottomLine;
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
