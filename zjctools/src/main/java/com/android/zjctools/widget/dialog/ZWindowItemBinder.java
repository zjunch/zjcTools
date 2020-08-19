package com.android.zjctools.widget.dialog;

import android.widget.TextView;

import com.android.zjctools.base.AppItemBinder;
import com.android.zjcutils.R;


/**
 * created zjun 2019-12-23
 * 底部弹出框 Item 展示处理
 */
public class ZWindowItemBinder extends AppItemBinder<String> {

    int mLayoutId;
    public ZWindowItemBinder(int layoutId) {
        this.mLayoutId=layoutId;
    }

    @Override
    protected int loadItemLayoutId() {
        return mLayoutId;
    }

    @Override
    protected void onBindView(AppHolder holder, String item) {
        TextView titleTV = holder.getView(R.id.zjc_window_item_title_tv);
        titleTV.setText(item);
    }
}
