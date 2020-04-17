package com.android.zjctools.ninepicture;

import android.content.Context;
import android.widget.ImageView;

import com.android.zjctools.R;
import com.android.zjctools.base.AppItemBinder;
import com.android.zjctools.bean.NinePictureBean;
import com.android.zjctools.glide.ZIMGLoader;
import com.android.zjctools.pick.ILoaderListener;
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
                ILoaderListener.Options options = new ILoaderListener.Options(url);
                ZIMGLoader.load(mContext, options, imageView);
            }
        });
        NineView.setImageUrls(item.urls);
    }
}
