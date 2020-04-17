package com.android.zjctools.display;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.android.zjctools.glide.ZIMGLoader;
import com.android.zjctools.pick.ILoaderListener;
import com.github.chrisbanes.photoview.PhotoView;

import java.util.List;

import androidx.viewpager.widget.PagerAdapter;

/**
 * 展示图片集合的适配器
 */
public class ZDisplayAdapter extends PagerAdapter {

    private Context context;
    private List<String> imagePaths;
    private IClickListener listener;

    public ZDisplayAdapter(Context context, List<String> list) {
        this.context = context;
        imagePaths = list;
    }

    @Override
    public int getCount() {
        return imagePaths.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        PhotoView photoView = new PhotoView(context);
        photoView.setEnabled(true);
        photoView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onClick();
            }
        });
        ILoaderListener.Options options = new ILoaderListener.Options(imagePaths.get(position));
        ZIMGLoader.load(context, options, photoView);

        container.addView(photoView, 0);
        return photoView;
    }

    /**
     * 设置回调接口
     */
    public void setListener(IClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    public interface IClickListener {
        void onClick();
    }
}
