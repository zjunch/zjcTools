package com.android.zjctools;

import android.provider.SyncStateContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import com.android.zjctools.base.ZBActivity;
import com.android.zjctools.bean.FunctionBean;
import com.android.zjctools.glide.IMManager;

import java.util.ArrayList;

public class SelectActivity extends ZBActivity {
    TextView tvSelect;
    @Override
    protected int layoutId() {
        return R.layout.activity_select;
    }

    @Override
    protected void initUI() {
        tvSelect=findViewById(R.id.tvSelect);
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void initListener() {
        super.initListener();
        tvSelect.setOnClickListener(v -> {
            IMManager.showMultiPicker(mActivity,10, new ArrayList<>());
        });
    }
}
