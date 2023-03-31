package com.android.zjctools.base

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.android.zjctools.toorbar.StatusBarUtil
import com.android.zjctools.utils.ZColor
import com.android.zjctools.utils.ZToastUtils
import com.android.zjctools.widget.dialog.ZPDialog
import com.android.zjcutils.R
import com.drakeet.multitype.MultiTypeAdapter
import kotlinx.android.synthetic.main.z_bar_title.*


/**
 * Created by zjun on 2023/03/15 12:16
 * 描述：Fragment MVVM 框架基类
 */
abstract class ZBVMFragment<VM : BViewModel> : Fragment() {

    protected var mDialog: ZPDialog? = null

    // 是否隐藏顶部控件
    open var isHideTopSpace: Boolean = false

    // 是否居中显示标题
    open var isCenterTitle: Boolean = true

    // 是否设置黑色状态栏
    open var isDarkStatusBar: Boolean = true

    protected lateinit var mBinding: ViewDataBinding
    protected lateinit var mViewModel: VM

    // 记录 Fragment 是否已经加载过，这个一般是配合 ViewPager 使用
    protected var isLoaded: Boolean = false


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        mBinding = DataBindingUtil.inflate(inflater, layoutId(), container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mViewModel = initVM()
        mBinding.lifecycleOwner = this
        initUI()
        startObserve()
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        if (!isLoaded) {
            isLoaded = true
            initData()
        }
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
    }

    fun show(message:String?){
        activity?.let {
            if(!TextUtils.isEmpty(message)){
                ZToastUtils.show(it, message!!)
            }
        }

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





    /**
     * 隐藏软键盘(只适用于Activity，不适用于Fragment)
     */
    open fun hideSoftKeyboard(view: View) {
        val imm = view.context.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

}
