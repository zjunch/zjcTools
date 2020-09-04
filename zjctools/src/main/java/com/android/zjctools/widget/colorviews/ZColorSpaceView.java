package com.android.zjctools.widget.colorviews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.android.zjcutils.R;

import androidx.annotation.Nullable;

public class ZColorSpaceView extends LinearLayout {
    private final View mContentView;
    ZColorViews mColorViews;
    ZColorTextView mTimeViews;
    public ZColorSpaceView(Context context) {
        this(context,null);
    }

    public ZColorSpaceView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContentView= LayoutInflater.from(context).inflate(R.layout.layout_zcolor_space,this);
        initViews();
    }

    private void initViews() {
        mTimeViews= mContentView.findViewById(R.id.vTimes);
        mColorViews= mContentView.findViewById(R.id.zColorViews);
    }

    public void setColorInfoView(ZColorInfo colorInfo){
        mColorViews.setStartAndEnd(9,21).setItems(colorInfo.ZColorBeans);
        mTimeViews.setStartAndEnd(9,21);
    }
}
