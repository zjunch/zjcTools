package com.android.zjctools.pick.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.android.zjctools.pick.ZImgLoaderListener;
import com.android.zjctools.pick.ZPicker;
import com.android.zjctools.pick.bean.ZPictureBean;

import java.util.List;

import androidx.viewpager.widget.PagerAdapter;

/**
 * Create by zjun on 2019/05/16 21:51
 *
 * 图片预览适配器
 */
public class ZPreviewPageAdapter extends PagerAdapter {

    private List<ZPictureBean> mDataList;
    private Activity mActivity;
    public OnPreviewClickListener listener;

    public ZPreviewPageAdapter(Activity activity, List<ZPictureBean> images) {
        this.mActivity = activity;
        this.mDataList = images;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = ZPicker.getInstance().getPictureLoader().createView(mActivity);

        ZPictureBean bean = mDataList.get(position);
        ZImgLoaderListener.Options options = new ZImgLoaderListener.Options(bean.path);
        ZPicker.getInstance().getPictureLoader().load(mActivity, options, imageView);

        //photoView.setOnPhotoTapListener((view, x, y) -> {
        //    if (listener != null) {
        //        listener.onPreviewClick(view, x, y);
        //    }
        //});
        imageView.setOnClickListener(v -> {
            listener.onPreviewClick(v);
        }); container.addView(imageView);
        return imageView;
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    /**
     * 设置预览点击监听接口
     */
    public void setPreviewClickListener(OnPreviewClickListener listener) {
        this.listener = listener;
    }

    /**
     * 定义预览点击监听接口
     */
    public interface OnPreviewClickListener {
        void onPreviewClick(View view);
    }
}
