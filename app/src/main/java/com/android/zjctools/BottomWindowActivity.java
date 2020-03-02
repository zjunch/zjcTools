package com.android.zjctools;

import android.widget.TextView;

import com.android.zjctools.base.ZBActivity;
import com.android.zjctools.utils.DialogUtils;

import java.util.ArrayList;
import java.util.List;

public class BottomWindowActivity extends ZBActivity {
        TextView tvShowBottomWindow;
        List<String> strings=new ArrayList<>();

    @Override
    protected int layoutId() {
        return R.layout.activity_main2;
    }

    @Override
    protected void initUI() {
        tvShowBottomWindow=findViewById(R.id.tvShowBottomWindow);
    }

    @Override
    protected void getValues() {
        super.getValues();
        for (int i = 0; i <3 ; i++) {
            strings.add("第"+i+"个");
        }
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        super.initListener();
        tvShowBottomWindow.setOnClickListener(v -> {
            DialogUtils.showBottomWindow(mActivity, strings, position -> {

            });
        });
    }
}
