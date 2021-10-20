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
    LinearLayout zLvTitle;   //整个标题的头部
    ZStatusBarHeightView zStatusBarHeightView;//模拟状态栏
    RelativeLayout zRvTitleBar,zRvEnd; //除去状态栏高度的部分，、  最后面的保存按钮或者收藏等icon
    TextView zLeftTitle,zCenterTitle,zTvEnd;//左侧标题， 中间标题， 右侧保存
    ImageView zIvBack,zIvEnd,zIvEndSecond; //左侧的返回键，右侧的icon， 右侧第二个icon（从右往左）
    View zBottomLine;

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
        initBackView(R.id.z_bar_iv_back);
        initListener();
    }

    public boolean getHandleTitleBar(){
        return handleTitleBar;
    }


    private  void initTitleBar(){
        if(findViewById(R.id.z_lv_title)!=null){
            zLvTitle=findViewById(R.id.z_lv_title);
            zStatusBarHeightView=findViewById(R.id.z_bar_status_View);
            zRvTitleBar=findViewById(R.id.z_bar_rv_title);
            zLeftTitle=findViewById(R.id.z_bar_tv_left_title);
            zCenterTitle=findViewById(R.id.z_bar_tv_center_title);
            zRvEnd=findViewById(R.id.z_rv_end);
            zTvEnd=findViewById(R.id.z_bar_tv_end);
            zIvBack=findViewById(R.id.z_bar_iv_back);
            zIvEnd=findViewById(R.id.z_bar_iv_end);
            zIvEndSecond=findViewById(R.id.z_bar_iv_end_second);
            zBottomLine=findViewById(R.id.z_bar_bottom_line);
        }
    }


    /**
     * 设置表栏颜色
     * @param color
     */
    public  void setHeaderBackColor(int color){//R.color.white
        if(zLvTitle!=null){
            zLvTitle.setBackgroundColor(ZColor.byRes(color));
        }
    }

    public  LinearLayout getHeader(){
        return  zLvTitle;
    }

    /**
     * 获取titlebar.注意使用时注意空的情况
     * @return
     */
    public RelativeLayout getTitleBar(){
        return zRvTitleBar;
    }

    /**
     * 获取
     * @return
     */
    public RelativeLayout getRvEnd(){
        return zRvEnd;
    }

    /**
     * 获取返回键view
     * @return
     */
    public ImageView getImageBack(){
        return zIvBack;
    }


    /**
     * 获取状态栏等高view
     * @return
     */
    public ZStatusBarHeightView getStatusView(){
        return zStatusBarHeightView;
    }


    /**
     * 获取左侧标题
     * @return
     */
    public TextView getLeftTitleView(){
        return zLeftTitle;
    }


    /**
     * 获取中间标题
     * @return
     */
    public TextView getCenterTitleView(){
        return zCenterTitle;
    }


    /**
     * 获取右侧保存
     * @return
     */
    public TextView getEndTvView(){
        return zTvEnd;
    }



    /**
     * 获取右侧图片
     * @return
     */
    public ImageView getEndIvView(){
        return zIvEnd;
    }

    /**
     * 获取倒数第二图片
     * @return
     */
    public ImageView getEndIvSecondView(){
        return zIvEndSecond;
    }


    /**
     * 获取下划线
     * @return
     */
    public View getBottomLine(){
        return zBottomLine;
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
