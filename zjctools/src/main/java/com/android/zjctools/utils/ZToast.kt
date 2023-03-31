package com.android.zjctools.utils

import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import com.android.zjctools.utils.ZTools.context
import com.android.zjcutils.R

object ZToast {


    //正常资源背景
    private var normalBg: Int = R.drawable.z_shape_toast_normal

    //成功资源背景
    private var successBg: Int = R.drawable.z_shape_toast_success

    //失败资源背景
    private var failBg: Int = R.drawable.z_shape_toast_fail

    //成功资源图片id
    private var successResId: Int = R.drawable.z_ic_success

    //失败资源图片id
    private var failResId: Int = R.drawable.z_ic_error


    private var mToast: Toast? = null


    /**
     * 重置资源
     */
    fun resetRes() {
        successResId = R.drawable.z_ic_success
        failResId = R.drawable.z_ic_error
        successBg = R.drawable.z_shape_toast_success
        failBg = R.drawable.z_shape_toast_success
        normalBg = R.drawable.z_shape_toast_normal
    }

    /**
     * 正常背景
     *
     * @param normalBg
     */
    fun setNormalBg(normalBg: Int) {
        this.normalBg = normalBg
    }

    /**
     * 设置成功背景
     *
     * @param successBg
     */
    fun setSuccessBg(successBg: Int) {
        this.successBg = successBg
    }

    /**
     * 设置失败背景
     *
     * @param failBg
     */
    fun setFailBg(failBg: Int) {
        this.failBg = failBg
    }

    /**
     * 设置成功图片
     *
     * @param successResId
     */
    fun setSuccessResId(successResId: Int) {
        this.successResId = successResId
    }

    /**
     * 设置失败图片
     *
     * @param failResId
     */
    fun setFailResId(failResId: Int) {
        this.failResId = failResId
    }


    /**
     * 正常的toast
     *
     * @param content
     */
    fun showNormal(content: String?) {
        showToast(ZToastType.NORMAL, content, ZGravity.BOTTOM)
    }


    /**
     * 中间的toast
     *
     * @param content
     */
    fun showCenter(content: String?) {
        showToast(ZToastType.NORMAL, content, ZGravity.CENTER)
    }


    /**
     * 失败，底部显示
     *
     * @param content
     */
    fun showErrorBottom(content: String?) {
        showToast(ZToastType.FAIL, content, ZGravity.BOTTOM)
    }

    /**
     * 失败，中间显示
     *
     * @param content
     */
    fun showErrorCenter(content: String?) {
        showToast(ZToastType.FAIL, content, ZGravity.CENTER)
    }


    /**
     * 成功，底部显示
     *
     * @param content
     */
    fun showSuccessBottom(content: String?) {
        showToast(ZToastType.SUCCESS, content, ZGravity.BOTTOM)
    }

    /**
     * 成功，中间显示
     *
     * @param content
     */
    fun showSuccessCenter(content: String?) {
        showToast(ZToastType.SUCCESS, content, ZGravity.CENTER)
    }

    /**
     * 显示弹窗
     *
     * @param content
     */
    fun showToast(zToastType: ZToastType, content: String?, zGravity: ZGravity) {
        if (mToast == null) {
            mToast = Toast(context)
        }
        setToastView(zToastType, content, zGravity)
        mToast!!.duration = Toast.LENGTH_SHORT
        mToast!!.show()
    }


    /**
     * 设置自定的view
     *
     * @param view
     */
    fun setCustomerView(view: View?, zGravity: ZGravity) {
        if (mToast == null) {
            mToast = Toast(context)
        }
        if (zGravity == ZGravity.CENTER) {
            mToast!!.setGravity(Gravity.CENTER, 0, 0)
        } else if (zGravity == ZGravity.BOTTOM) {
            mToast!!.setGravity(Gravity.BOTTOM, 0, 200)
        }
        mToast!!.view = view
        mToast!!.duration = Toast.LENGTH_SHORT
        mToast!!.show()
    }


    /**
     * 定义弹窗布局
     *
     * @param content
     * @param zGravity
     */
    private fun setToastView(zToastType: ZToastType, content: String?, zGravity: ZGravity) {
        val myToastView: View = LayoutInflater.from(context).inflate(R.layout.z_layout_toast, null)
        val myToastMessage = myToastView.findViewById<View>(R.id.z_toast_content) as TextView
        val myToastImage = myToastView.findViewById<View>(R.id.zjv_toast_icon) as ImageView
        val myToastBg = myToastView.findViewById<RelativeLayout>(R.id.z_toast_bg)
        myToastImage.visibility = View.GONE
        if (zToastType == ZToastType.SUCCESS) {
            myToastBg.setBackgroundResource(successBg) //成功背景
            myToastImage.setImageResource(successResId) //成功图片
            myToastImage.visibility = View.VISIBLE
        } else if (zToastType == ZToastType.FAIL) {
            myToastBg.setBackgroundResource(failBg) //失败背景
            myToastImage.setImageResource(failResId) //失败图片
            myToastImage.visibility = View.VISIBLE
        } else if (zToastType == ZToastType.NORMAL) {
            myToastBg.setBackgroundResource(normalBg)
        }
        if (zGravity == ZGravity.CENTER) {
            mToast!!.setGravity(Gravity.CENTER, 0, 0)
        } else if (zGravity == ZGravity.BOTTOM) {
            mToast!!.setGravity(Gravity.BOTTOM, 0, 200)
        }
        myToastMessage.text = content
        mToast!!.duration = Toast.LENGTH_LONG //setDuration方法：设置持续时间，以毫秒为单位。该方法是设置补间动画时间长度的主要方法
        mToast!!.view = myToastView //添加视图文件
    }


    enum class ZGravity {
        BOTTOM, CENTER
    }


    enum class ZToastType {
        NORMAL, SUCCESS, FAIL
    }
}