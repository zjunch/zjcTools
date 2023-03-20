package com.android.zjctools.base

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.android.zjctools.utils.ZLog
import com.android.zjctools.widget.dialog.ZPDialog

/**
 * kotlin 适用kotlin 项目
 */
abstract class ZBaseFragment : Fragment(){
    private var mDialog: ZPDialog? = null

    // 记录 Fragment 是否已经加载过，这个一般是配合 ViewPager 使用
    protected var isLoaded: Boolean = false

    lateinit var mContext:Context

    override fun onAttach(context: Context) {
        super.onAttach(context)
        ZLog.v("onAttach: %s", javaClass.simpleName)
        mContext = context
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(layoutId(), container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initUI()
    }

    override fun onResume() {
        super.onResume()
        if (!isLoaded) {
            isLoaded = true
            initData()
        }
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




    fun getDialog(): ZPDialog? =mDialog

    /**
     * 显示对话框
     */
    open fun showLoading(title: String="") {
        activity?.let {
            mDialog = ZPDialog(activity, ZPDialog.Type.PROGRESS)
            if(!TextUtils.isEmpty(title)){
                mDialog!!.setMessage(title)
            }
            mDialog!!.show()
        }
    }

    open fun dismissDialog() {
        if (mDialog != null) {
            mDialog!!.dismiss()
            mDialog = null
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        dismissDialog()
    }


}