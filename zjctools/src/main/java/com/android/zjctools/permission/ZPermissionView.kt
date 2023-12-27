package com.android.zjctools.permission

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.android.zjcutils.R

/**
 * Create by zjun
 *
 * 权限申请简单展示控件
 */
class ZPermissionView @JvmOverloads constructor(context: Context?, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : LinearLayout(context, attrs, defStyleAttr) {
    private var iconView: ImageView? = null
    private var titleView: TextView? = null

    /**
     * 初始化方法
     */
    fun init(context: Context?) {
        LayoutInflater.from(context).inflate(R.layout.z_widget_permission_item, this)
        iconView = findViewById(R.id.z_permission_item_icon_iv)
        titleView = findViewById(R.id.z_permission_item_name_iv)
    }

    /**
     * 设置权限申请展示图标
     *
     * @param resId 图标资源 id
     */
    fun setPermissionIcon(resId: Int) {
        if (resId > 0) {
            iconView!!.setImageResource(resId)
        }
    }

    /**
     * 设置权限申请标题
     *
     * @param name 权限名
     */
    fun setPermissionName(name: String?) {
        titleView!!.text = name
    }

    init {
        init(context)
    }
}