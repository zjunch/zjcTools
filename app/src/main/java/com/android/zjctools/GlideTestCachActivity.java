package com.android.zjctools;

import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.zjctools.app.GlideApp;
import com.android.zjctools.base.ZBActivity;
import com.android.zjctools.widget.nineimages.NinePicturesView;

import java.util.ArrayList;
import java.util.List;



public class GlideTestCachActivity extends ZBActivity {

    NinePicturesView ninePics;
    List<String>pictures=new ArrayList<>();
    ImageView ivPicture;
    @Override
    protected int layoutId() {
        return R.layout.activity_glide_test_cach;
    }

    @Override
    protected void initUI() {
        ninePics=findViewById(R.id.ninePics);
        ivPicture=findViewById(R.id.ivPicture);
    }

    @Override
    protected void initData() {
        for (int i = 0; i < 12; i++) {
           pictures.add("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=1208538952,1443328523&fm=26&gp=0.jpg");
        }
        ninePics.setImgLoadUrlListenr((imageView, url,index, viewWidth, viewHeight) -> {
            GlideApp.with(mActivity)
                    .load(url)
                    .centerCrop()
                    .into(imageView);
        });
        ninePics.setImageUrls(pictures);
        ninePics.setOnclickItemListenr(new NinePicturesView.OnclickItemListenr() {
            @Override
            public void OnclickItem(View view, int position) {
                Toast.makeText(mActivity,String.valueOf(position),Toast.LENGTH_LONG).show();
            }
        });
    }
}
