package com.android.zjctools;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.zjctools.base.ZBActivity;
import com.android.zjctools.base.ZConstant;
import com.android.zjctools.glide.ZIMGLoader;
import com.android.zjctools.glide.ZIMManager;
import com.android.zjctools.pick.ZImgLoaderListener;
import com.android.zjctools.pick.ZPicker;
import com.android.zjctools.pick.bean.ZPictureBean;
import com.android.zjctools.utils.ZToast;
import com.android.zjctools.utils.bitmap.ZCompressUtils;
import com.android.zjctools.widget.nineimages.ZNinePicturesView;


import java.util.ArrayList;
import java.util.List;

public class SelectActivity extends ZBActivity {
    TextView tvSelect,tvSelectSingle;
    ZNinePicturesView ninePics;
    List<ZPictureBean> selecteds = new ArrayList<>();
    int selectType=-1;

    @Override
    protected int layoutId() {
        return R.layout.activity_select;
    }

    @Override
    protected void initUI() {
        tvSelect = findViewById(R.id.tvSelect);
        tvSelectSingle=findViewById(R.id.tvSelectSingle);
        ninePics = findViewById(R.id.ninePics);
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void initListener() {
        super.initListener();
        tvSelect.setOnClickListener(v -> {
            if(selectType==1){
                selecteds.clear();
            }
            selectType=0;
            ZIMManager.showMultiPicker(mActivity, 9, selecteds);
        });
        tvSelectSingle.setOnClickListener(v -> {
            if(selectType==0){
                selecteds.clear();
            }
            selectType=1;
            ZIMManager.showSinglePicker(mActivity);
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
                ZImgLoaderListener.Options options = new ZImgLoaderListener.Options(url);
                ZIMGLoader.load(mActivity, options, imageView);
            }
        });
        ninePics.setImageUrls(strings);
        ninePics.setOnclickItemListenr((view, position) -> {
            Router.goDisplayMulti(mActivity, position, strings);
        });
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
            ZCompressUtils zCompressUtils=new ZCompressUtils();
            zCompressUtils.compressPictures(selecteds, new ZCompressUtils.CompressImageListener() {
                @Override
                public void onComplete() {
                    ZToast.create().showSuccessBottom("压缩完成");
                }

                @Override
                public void onError(String errorMsg) {
                    ZToast.create().showErrorBottom(errorMsg);
                }
            });
        }
    }


}
