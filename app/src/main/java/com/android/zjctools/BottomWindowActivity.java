package com.android.zjctools;

import android.view.View;
import android.widget.TextView;

import com.android.zjctools.base.ZBActivity;


import java.util.ArrayList;
import java.util.List;

public class BottomWindowActivity extends ZBActivity {
        TextView tvShowBottomWindow;
        List<String> strings=new ArrayList<>();

    @Override
    public int layoutId() {
        return R.layout.activity_main2;
    }

    @Override
    public void initUI() {
        tvShowBottomWindow=findViewById(R.id.tvShowBottomWindow);
    }


    @Override
    public void initData() {

    }
}
