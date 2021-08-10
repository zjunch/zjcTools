package com.android.zjctools.ninepicture;

import android.content.Context;
import android.media.Image;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.android.zjctools.R;
import com.android.zjctools.base.AppItemBinder;
import com.android.zjctools.bean.NineMediaBean;
import com.android.zjctools.bean.NinePictureBean;
import com.android.zjctools.glide.ZIMGLoader;
import com.android.zjctools.model.ZMedia;
import com.android.zjctools.pick.ZImgLoaderListener;
import com.android.zjctools.widget.nineimages.ZNineMediaView;
import com.android.zjctools.widget.nineimages.ZNinePicturesView;

public class NineMediaBinder extends AppItemBinder<NineMediaBean> {

    public NineMediaBinder(Context context) {
        super(context);
        this.mContext = context;
    }

    @Override
    protected int loadItemLayoutId() {
        return R.layout.item_nine_media;
    }

    @Override
    protected void onBindView(AppHolder holder, NineMediaBean item) {
        ZNineMediaView zNineMediaView = holder.getView(R.id.nineMedias);
        zNineMediaView.setMediaLoadListener(new ZNineMediaView.MediaLoadListener() {
            @Override
            public void onImgLoad(ViewGroup itemView, ZMedia media, int index, int viewWidth, int viewHeight) {
                ImageView ivMediaCover= (ImageView) itemView.getChildAt(0);

//
//                ImageView ivMediaCover=itemView.findViewById(R.id.ivMediaCover);
//                RelativeLayout.LayoutParams lp= (RelativeLayout.LayoutParams) ivMediaCover.getLayoutParams();
//                lp.height=viewHeight;
//                lp.width=viewWidth;
//                ivMediaCover.setLayoutParams(lp);
//                ivMediaCover.requestLayout();
//                ivMediaCover.postInvalidate();
////                ImageView ivMediaIcon=itemView.findViewById(R.id.ivMediaIcon);
                ZImgLoaderListener.Options options = new ZImgLoaderListener.Options(media.mediaUrl);
                ZIMGLoader.load(mContext, options, ivMediaCover);
//                if(media.mediaType==0){
//                    ivMediaIcon.setVisibility(View.GONE);
//                }else{
//                    ivMediaIcon.setVisibility(View.VISIBLE);
//                }
            }
        });
        zNineMediaView.setMedias(item.medias);
    }
}
