package com.android.zjctools;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.zjctools.base.AppItemBinder;
import com.android.zjctools.bean.FunctionBean;
import com.android.zjctools.widget.ZSettingView;

public class FunctionBinder extends AppItemBinder<FunctionBean> {

    public FunctionBinder(Context context) {
        super(context);
        this.mContext = context;
    }

    @NonNull
    @Override
    protected AppHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View view = inflater.inflate(loadItemLayoutId(), parent, false);
        return new AppHolder(view);
    }

    @Override
    protected int loadItemLayoutId() {
        return R.layout.item_function;
    }

    @Override
    protected void onBindView(AppHolder holder, FunctionBean item) {
        ZSettingView settingView= holder.getView(R.id.sv_set_title);
        settingView.setTitle(item.title);
    }
}
