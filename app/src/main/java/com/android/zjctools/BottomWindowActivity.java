package com.android.zjctools;

import android.view.View;
import android.widget.TextView;

import com.android.zjctools.base.ZBActivity;
import com.android.zjctools.utils.ZDialogUtils;
import com.android.zjctools.widget.dialog.ZPDialog;

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
//            ZDialogUtils.showBottomWindow(mActivity, strings, position -> {
//
//            });
//            ZPDialog dialog=ZDialogUtils.showAlertDialog(mActivity, "水水水水", new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                }
//            });

//            ZPDialog dialog=ZDialogUtils.showSelectDialog(mActivity, "水水水水","就看见看见" ,new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                }
//            });
        });
    }
}
