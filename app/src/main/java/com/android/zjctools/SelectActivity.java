package com.android.zjctools;

import android.content.Intent;
import android.provider.SyncStateContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.zjctools.app.GlideApp;
import com.android.zjctools.base.AppItemBinder;
import com.android.zjctools.base.ZBActivity;
import com.android.zjctools.base.ZConstant;
import com.android.zjctools.bean.FunctionBean;
import com.android.zjctools.glide.IMManager;
import com.android.zjctools.pick.ZPicker;
import com.android.zjctools.pick.bean.ZPictureBean;
import com.android.zjctools.widget.nineimages.ZNinePicturesView;

import java.util.ArrayList;
import java.util.List;

public class SelectActivity extends ZBActivity {
    TextView tvSelect;
    ZNinePicturesView ninePics;
    List<ZPictureBean> selecteds = new ArrayList<>();

    @Override
    protected int layoutId() {
        return R.layout.activity_select;
    }

    @Override
    protected void initUI() {
        tvSelect = findViewById(R.id.tvSelect);
        ninePics = findViewById(R.id.ninePics);
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void initListener() {
        super.initListener();
        tvSelect.setOnClickListener(v -> {
            IMManager.showMultiPicker(mActivity, 9, selecteds);
        });
    }

    private void refreshPicture(List<ZPictureBean> result) {
        List<String> strings = new ArrayList<>();
        for (int i = 0; i < result.size(); i++) {
            strings.add(result.get(i).path);
        }
        ninePics.setImgLoadUrlListenr(new ZNinePicturesView.ImgLoadUrlListener() {
            @Override
            public void onImgLoad(ImageView imageView, String url, int index, int viewWidth, int viewHeight) {
                GlideApp.with(mActivity)
                        .load(url)
                        .centerCrop()
                        .into(imageView);
            }
        });
        ninePics.setImageUrls(strings);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK || data == null) {
            return;
        }
        if (requestCode == ZConstant.ZJC_PICK_REQUEST_CODE) {//选择图片
            selecteds.clear();
            List<ZPictureBean> result = ZPicker.getInstance().getResultData();
            selecteds.addAll(result);
            refreshPicture(result);
        }
    }


}
