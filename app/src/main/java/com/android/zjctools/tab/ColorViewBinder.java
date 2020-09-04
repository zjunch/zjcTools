package com.android.zjctools.tab;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.zjctools.R;
import com.android.zjctools.base.AppItemBinder;
import com.android.zjctools.widget.colorviews.ZColorInfo;
import com.android.zjctools.widget.colorviews.ZColorSpaceView;
import com.android.zjctools.widget.colorviews.ZColorViews;

import androidx.annotation.NonNull;

public class ColorViewBinder extends AppItemBinder<ZColorInfo> {

    public ColorViewBinder(Context context) {
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
        return R.layout.item_color_view;
    }

    @Override
    protected void onBindView(AppHolder holder, ZColorInfo item) {
        TextView tvTitle=holder.getView(R.id.tvTitle);
        tvTitle.setText(item.title);
        ZColorSpaceView zColorSpaceView= holder.getView(R.id.zColorSpaceView);
        zColorSpaceView.setColorInfoView(item);
    }
}
