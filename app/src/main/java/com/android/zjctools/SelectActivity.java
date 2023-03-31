package com.android.zjctools;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.zjctools.base.ZBActivity;
import com.android.zjctools.base.ZConstant;
import com.android.zjctools.imagepicker.bean.ImageItem;
import com.android.zjctools.widget.nineimages.ZNinePicturesView;


import java.util.ArrayList;
import java.util.List;

public class SelectActivity extends ZBActivity {
    TextView tvSelect,tvSelectSingle;
    ZNinePicturesView ninePics;
    List<ImageItem> selecteds = new ArrayList<>();
    int selectType=-1;

    @Override
    public int layoutId() {
        return R.layout.activity_select;
    }

    @Override
    public void initUI() {
        tvSelect = findViewById(R.id.tvSelect);
        tvSelectSingle=findViewById(R.id.tvSelectSingle);
        ninePics = findViewById(R.id.ninePics);
    }

    @Override
    public void initData() {
    }



}
