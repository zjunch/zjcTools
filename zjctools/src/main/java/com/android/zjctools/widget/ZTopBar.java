package com.android.zjctools.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.zjctools.utils.ZColor;
import com.android.zjctools.utils.ZDimen;
import com.android.zjctools.utils.ZStr;
import com.android.zjcutils.R;


/**
 * Create by lzan13 on 2019/5/19 12:36
 *
 * 自定义 VMTopBar 控件
 */
public class ZTopBar extends RelativeLayout {

    // 控件
    private ImageView mIconBtn;
    private TextView mTitleView;
    private TextView mSubtitleView;
    private LinearLayout mEndContainer;
    private TextView mEndBtn;
    private ImageButton mEndIconBtn;

    // 设置数据
    private int mIcon;
    private String mTitle;
    private int mTitleColor;

    private String mSubtitle;
    private int mSubtitleColor;

    private String mEndText;
    private int mEndIcon;
    // 居中
    private boolean isCenter;

    public ZTopBar(Context context) {
        this(context, null);
    }

    public ZTopBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ZTopBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    /**
     * 初始化
     *
     * @param context
     * @param attrs
     */
    private void init(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.zjc_widget_top_bar, this);

        mIconBtn = findViewById(R.id.zjc_top_bar_icon);
        mTitleView = findViewById(R.id.zjc_top_bar_title_tv);
        mSubtitleView = findViewById(R.id.zjc_top_bar_subtitle_tv);
        mEndContainer = findViewById(R.id.zjc_top_bar_end_container);
        mEndBtn = findViewById(R.id.zjc_top_bar_end_btn);
        mEndIconBtn = findViewById(R.id.zjc_top_bar_end_icon);

        mTitleColor = ZColor.byRes(R.color.zjc_title);
        mSubtitleColor = ZColor.byRes(R.color.zjc_subtitle);

        // 获取控件的属性值
        handleAttrs(context, attrs);

        if (mIcon == 0) {
            mIconBtn.setVisibility(GONE);
        } else {
            mIconBtn.setVisibility(VISIBLE);
            mIconBtn.setImageResource(mIcon);
        }

        if (!ZStr.isEmpty(mTitle)) {
            mTitleView.setText(mTitle);
        }

        if (!ZStr.isEmpty(mSubtitle)) {
            mSubtitleView.setVisibility(VISIBLE);
            mSubtitleView.setText(mSubtitle);
        } else {
            mSubtitleView.setVisibility(GONE);
        }

        if (!ZStr.isEmpty(mEndText)) {
            mEndBtn.setVisibility(VISIBLE);
            mEndBtn.setText(mEndText);
        } else {
            mEndBtn.setVisibility(GONE);
        }

        if (mEndIcon == 0) {
            mEndIconBtn.setVisibility(GONE);
        } else {
            mEndIconBtn.setVisibility(VISIBLE);
            mEndIconBtn.setImageResource(mEndIcon);
        }

        setupText();
    }

    /**
     * 获取资源属性
     *
     * @param context
     * @param attrs
     */
    private void handleAttrs(Context context, AttributeSet attrs) {
        if (attrs == null) {
            return;
        }
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ZTopBar);
        // 获取自定义属性值，如果没有设置就是默认值
        mIcon = array.getResourceId(R.styleable.ZTopBar_zjc_icon, mIcon);
        mTitle = array.getString(R.styleable.ZTopBar_zjc_title);
        mSubtitle = array.getString(R.styleable.ZTopBar_zjc_subtitle);
        mEndText = array.getString(R.styleable.ZTopBar_zjc_end_btn);
        mEndIcon = array.getResourceId(R.styleable.ZTopBar_zjc_end_icon, mEndIcon);
        mTitleColor = array.getColor(R.styleable.ZTopBar_zjc_title_color, mTitleColor);
        mSubtitleColor = array.getColor(R.styleable.ZTopBar_zjc_subtitle_color, mSubtitleColor);
        isCenter = array.getBoolean(R.styleable.ZTopBar_zjc_is_center, isCenter);

        // 回收资源
        array.recycle();
    }

    /**
     * 装载控件颜色
     */
    private void setupText() {
        mTitleView.setTextColor(mTitleColor);
        mSubtitleView.setTextColor(mSubtitleColor);
        if (isCenter) {
            if (!mEndContainer.isShown() && !mEndBtn.isShown()) {
                mEndContainer.setVisibility(VISIBLE);
                mEndContainer.getLayoutParams().width = ZDimen.dp2px(48);
            }
            mTitleView.setGravity(Gravity.CENTER);
            mSubtitleView.setGravity(Gravity.CENTER);
        }else{
            mTitleView.setGravity(Gravity.START);
            mSubtitleView.setGravity(Gravity.START);
        }
    }

    /**
     * 设置图标
     */
    public void setIcon(int resId) {
        mIcon = resId;
        if (mIcon == 0) {
            mIconBtn.setVisibility(GONE);
        } else {
            mIconBtn.setVisibility(VISIBLE);
            mIconBtn.setImageResource(mIcon);
        }
    }


    /**
     * 是否居中
     */
    public void setCenter(boolean center) {
        isCenter = center;
        setupText();
    }

    /**
     * 设置标题
     */
    public void setTitle(int resId) {
        mTitle = ZStr.byRes(resId);
        mTitleView.setText(mTitle);
    }

    /**
     * 设置标题
     */
    public void setTitle(String title) {
        mTitle = title;
        if (!ZStr.isEmpty(mTitle)) {
            mTitleView.setText(mTitle);
        }
    }

    /**
     * 设置标题颜色
     */
    public void setTitleColor(int resId) {
        if (resId != 0) {
            mTitleColor = ZColor.byRes(resId);
            setupText();
        }
    }

    /**
     * 设置标题颜色
     */
    public void setSubTitleColor(int resId) {
        if (resId != 0) {
            mSubtitleColor = ZColor.byRes(resId);
            setupText();
        }
    }

    /**
     * 设置标题样式
     */
    public void setTitleStyle(int resId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mTitleView.setTextAppearance(resId);
        } else {
            mTitleView.setTextAppearance(getContext(), resId);
        }
    }

    /**
     * 设置子标题
     */
    public void setSubtitle(String title) {
        mSubtitle = title;
        if (ZStr.isEmpty(mSubtitle)) {
            mSubtitleView.setVisibility(GONE);
        } else {
            mSubtitleView.setVisibility(VISIBLE);
            mSubtitleView.setText(mSubtitle);
        }
    }

    /**
     * 设置子标题样式
     */
    public void setSubTitleStyle(int resId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mSubtitleView.setTextAppearance(resId);
        } else {
            mSubtitleView.setTextAppearance(getContext(), resId);
        }
    }

    /**
     * 设置添加尾部控件
     */
    public void addEndView(View view) {
        if (view != null) {
            mEndContainer.removeAllViews();
            mEndContainer.addView(view);
            mEndContainer.setVisibility(VISIBLE);
        } else {
            mEndContainer.removeAllViews();
            mEndContainer.setVisibility(GONE);
        }
    }

    /**
     * 设置图标
     */
    public void setEndIcon(int resId) {
        mEndIcon = resId;
        if (mEndIcon == 0) {
            mEndIconBtn.setVisibility(GONE);
        } else {
            mEndIconBtn.setVisibility(VISIBLE);
            mEndIconBtn.setImageResource(mEndIcon);
        }
    }

    /**
     * 设置图标点击监听
     */
    public void setIconListener(OnClickListener listener) {
        mIconBtn.setOnClickListener(listener);
    }

    /**
     * 设置右侧图标点击监听
     */
    public void setEndIconListener(OnClickListener listener) {
        mEndIconBtn.setOnClickListener(listener);
    }

    /**
     * 设置右侧按钮状态
     */
    public void setEndBtnEnable(boolean enable) {
        mEndBtn.setEnabled(enable);
    }

    /**
     * 设置右侧的按钮
     */
    public void setEndBtn(String text) {
        mEndText = text;
        if (ZStr.isEmpty(mEndText)) {
            mEndBtn.setVisibility(GONE);
        } else {
            mEndBtn.setVisibility(VISIBLE);
            mEndBtn.setText(mEndText);
        }
    }

    /**
     * 设置按钮背景样式
     *
     * @param resId 背景资源 id
     */
    public void setEndBtnBackground(int resId) {
        mEndBtn.setBackgroundResource(resId);
    }

    /**
     * 设置按钮文字颜色
     *
     * @param color 颜色值
     */
    public void setEndBtnTextColor(int color) {
        mEndBtn.setTextColor(color);
    }

    /**
     * 设置右侧的按钮点击的监听
     *
     * @param listener 回调接口
     */
    public void setEndBtnListener(OnClickListener listener) {
        setEndBtnListener(mEndText, listener);
    }

    /**
     * 设置消极的按钮点击监听
     *
     * @param str      文本
     * @param listener 回调接口
     */
    public void setEndBtnListener(String str, OnClickListener listener) {
        setEndBtn(str);
        mEndBtn.setOnClickListener(listener);
    }
}
