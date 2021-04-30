package com.android.zjctools.permission;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.zjcutils.R;


/**
 * Create by zjun
 *
 * 权限申请简单展示控件
 */
public class ZPermissionView extends LinearLayout {

    private ImageView iconView;
    private TextView titleView;

    public ZPermissionView(Context context) {
        this(context, null);
    }

    public ZPermissionView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ZPermissionView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    /**
     * 初始化方法
     */
    public void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.z_widget_permission_item, this);
        iconView = findViewById(R.id.zjc_permission_item_icon_iv);
        titleView = findViewById(R.id.zjc_permission_item_name_iv);
    }

    /**
     * 设置权限申请展示图标
     *
     * @param resId 图标资源 id
     */
    public void setPermissionIcon(int resId) {
        if (resId > 0) {
            iconView.setImageResource(resId);
        }
    }

    /**
     * 设置权限申请标题
     *
     * @param name 权限名
     */
    public void setPermissionName(String name) {
        titleView.setText(name);
    }
}
