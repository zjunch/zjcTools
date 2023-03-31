package com.android.zjctools.base

import android.app.Activity
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.android.zjctools.toorbar.StatusBarUtil
import com.android.zjctools.utils.ZColor
import com.android.zjctools.utils.ZToastUtils
import com.android.zjctools.widget.dialog.ZPDialog
import com.android.zjcutils.R
import com.drakeet.multitype.MultiTypeAdapter
import kotlinx.android.synthetic.main.z_bar_title.*

/**
 * Created by zjun on 2023/03/15 11:16
 * 描述：Activity MVVM 框架基类
 */
abstract class ZBVMActivity<VM : BViewModel> : AppCompatActivity() {

    protected var mDialog: ZPDialog? = null


    // 是否隐藏顶部控件
    open var isHideTopSpace: Boolean = false

    // 是否居中显示标题
    open var isCenterTitle: Boolean = true

    // 是否设置黑色状态栏
    open var isDarkStatusBar: Boolean = true

    protected lateinit var mActivity: Activity
    protected lateinit var mBinding: ViewDataBinding
    protected lateinit var mViewModel: VM


    val items by lazy { mutableListOf<Any>() }
    val adapter by lazy { MultiTypeAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mActivity = this

        mViewModel = initVM()

        mBinding = DataBindingUtil.setContentView(this, layoutId())
        mBinding.lifecycleOwner = this
        initUI()

        initData()

        startObserve()

    }

    /**
     * 布局资源 id
     */
    abstract fun layoutId(): Int

    /**
     * 初始化 ViewModel
     */
    abstract fun initVM(): VM

    /**
     * 初始化 UI
     */
    open fun initUI() {
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setupTobBar()
    }

    /**
     * 初始化数据
     */
    abstract fun initData()


    /**
     * 开始观察 View 生命周期
     */
    abstract fun startObserve()


    /**
     * 装载 TopBar
     */
    private fun setupTobBar() {
        //当FitsSystemWindows设置 true 时，会在屏幕最上方预留出状态栏高度的 padding
        StatusBarUtil.setRootViewFitsSystemWindows(this, false)
        StatusBarUtil.setTranslucentStatus(this)
    }


    /**
     * @param isDarkTextStatusBar 为true时状态栏文字为黑色
     */
    open fun setStatusBar(isDarkTextStatusBar:Boolean=false) {
        StatusBarUtil.setStatusBarDarkTheme(this, isDarkTextStatusBar)
    }
    /**
     * 设置顶部标题背景色
     */
    protected fun setTopBGColor(color: Int) {
        zTopBarLv?.setBackgroundColor(color)
    }

    /**
     * 设置顶部白色
     */
    protected fun setTopWhiteColor() {
        zTopBarLv?.setBackgroundColor(ZColor.byRes(R.color.zWhite))
    }

    /**
     * 设置左侧图标
     */
    protected fun setTopIcon(resId: Int) {
        zTopLeftIv?.setImageResource(resId)
    }

    /**
     * 设置标题
     */
    protected fun setTopTitle(resId: Int, colorId: Int= R.color.zBlack, fontSize:Int=16) {
        zTopCenterTile?.setText(resId)
        zTopCenterTile?.textSize = fontSize*1f
        zTopCenterTile.setText(ZColor.byRes(colorId))

    }

    /**
     * 设置标题
     */
    protected fun setTopTitle(title: String="") {
        zTopCenterTile?.text=title
    }

    /**
     * 设置二级标题
     */
    protected fun setTopSubTitle(colorId: Int= R.color.zBlack, fontSize:Int=16) {
        zTopLeftTitleTv?.textSize = fontSize*1f
        zTopLeftTitleTv.setText(ZColor.byRes(colorId))
    }


    /**
     * 设置子标题
     */
    protected fun setTopSubtitle(title: String="") {
        zTopLeftTitleTv?.text = title
    }


    /**
     * 设置 TopBar 右侧按钮
     */
    protected fun setTopEndBtn(colorId: Int= R.color.app_theme_color, fontSize:Int=14) {
        zTopEndTv?.textSize = fontSize*1f
        zTopEndTv.setText(ZColor.byRes(colorId))
    }

    /**
     * 设置 TopBar 右侧按钮监听
     */
    protected fun setTopEndBtnListener(str: String? = null, listener: View.OnClickListener?) {
        zTopEndTv.text=str
        zTopEndTv?.setOnClickListener(listener)
    }




    /**
     * 设置 TopBar 右侧图标按钮及监听
     */
    protected fun setTopEndIcon(resId: Int, listener: View.OnClickListener) {
        zTopEndIv?.setImageResource(resId)
        zTopEndIv?.setOnClickListener(listener)
    }



    public fun getCommonTopBar():View?{
        return  zTopBarLv
    }

    override fun onDestroy() {
        mDialog?.dismiss()
        super.onDestroy()
    }

}