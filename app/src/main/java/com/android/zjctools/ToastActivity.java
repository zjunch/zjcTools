package com.android.zjctools;

import android.widget.TextView;

import com.android.zjctools.base.ZBActivity;
import com.android.zjctools.utils.ZToast;


public class ToastActivity extends ZBActivity {

    TextView tvNormal,tvCenter,tvBottomSuccess,tvCenterSuccess,tvBottomError,tvCenterError;

    @Override
    protected int layoutId() {
        return R.layout.activity_toast;
    }

    @Override
    protected void initUI() {
        tvNormal=findViewById(R.id.tvNormal);
        tvCenter=findViewById(R.id.tvCenter);
        tvBottomSuccess=findViewById(R.id.tvBottomSuccess);
        tvCenterSuccess=findViewById(R.id.tvCenterSuccess);
        tvBottomError=findViewById(R.id.tvBottomError);
        tvCenterError=findViewById(R.id.tvCenterError);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        super.initListener();
        tvNormal.setOnClickListener(v -> {
            ZToast.create().showNormal("正常的");
        });
        tvCenter.setOnClickListener(v -> {
            ZToast.create().showCenter("中间的");
        });
        tvBottomSuccess.setOnClickListener(v -> {
            ZToast.create().showSuccessBottom("底部成功");
        });
        tvCenterSuccess.setOnClickListener(v -> {
            ZToast.create().showSuccessCenter("中间成功");
        });
        tvBottomError.setOnClickListener(v -> {
            ZToast.create().showErrorBottom("底部失败");
        });
        tvCenterError.setOnClickListener(v -> {
            ZToast.create().showErrorCenter("中间失败");
        });
    }
}
