package com.android.zjctools.base

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.android.zjctools.toorbar.StatusBarUtil
import com.android.zjctools.utils.ZColor
import com.android.zjctools.utils.ZLog
import com.android.zjctools.utils.ZToastUtils
import com.android.zjctools.widget.dialog.ZPDialog
import com.android.zjcutils.R
import kotlinx.android.synthetic.main.z_bar_title.*

abstract  class ZBFragment:Fragment() {


    protected var mDialog: ZPDialog? = null

    protected lateinit var mContext: Context

    // 是否设置黑色状态栏
    open var isDarkStatusBar: Boolean = true

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(layoutId(), container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initUI()
    }

    /**
     * 布局资源 id
     */
    abstract fun layoutId(): Int

    /**
     * 初始化 UI
     */
    abstract fun initUI()

    /**
     * 初始化数据
     */
    abstract fun initData()



    fun show(message:String?){
        mContext?.let {
            if(!TextUtils.isEmpty(message)){
                ZToastUtils.show(it, message!!)
            }
        }

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