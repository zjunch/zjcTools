package com.android.zjctools.ninepicture;

import android.content.Context;
import android.widget.ImageView;

import com.android.zjctools.R;
import com.android.zjctools.app.GlideApp;
import com.android.zjctools.base.AppItemBinder;
import com.android.zjctools.bean.NinePictureBean;
import com.android.zjctools.widget.nineimages.ZNinePicturesView;

public class NinePictureBinder extends AppItemBinder<NinePictureBean> {

    public NinePictureBinder(Context context) {
        super(context);
        this.mContext = context;
    }

    @Override
    protected int loadItemLayoutId() {
        return R.layout.item_nine_picture;
    }

    @Override
    protected void onBindView(AppHolder holder, NinePictureBean item) {
        ZNinePicturesView NineView = holder.getView(R.id.ninePics);
        NineView.setImgLoadUrlListenr(new ZNinePicturesView.ImgLoadUrlListener() {
            @Override
            public void onImgLoad(ImageView imageView, String url, int index, int viewWidth, int viewHeight) {
                GlideApp.with(mContext)
                        .load(url)
                        .centerCrop()
                        .into(imageView);
            }
        });
        NineView.setImageUrls(item.urls);
    }
}
