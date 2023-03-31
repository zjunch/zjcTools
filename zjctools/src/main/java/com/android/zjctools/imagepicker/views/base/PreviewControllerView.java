package com.android.zjctools.imagepicker.views.base;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.zjcutils.R;
import com.android.zjctools.imagepicker.bean.ImageItem;
import com.android.zjctools.imagepicker.bean.selectconfig.BaseSelectConfig;
import com.android.zjctools.imagepicker.helper.DetailImageLoadHelper;
import com.android.zjctools.imagepicker.presenter.IPickerPresenter;
import com.android.zjctools.imagepicker.utils.PViewSizeUtils;
import com.android.zjctools.imagepicker.views.PickerUiConfig;
import com.android.zjctools.imagepicker.widget.cropimage.CropImageView;

import java.util.ArrayList;

/**
 * Time: 2019/11/13 14:39
 * Author:ypx
 * Description:自定义预览页面
 */
public abstract class PreviewControllerView extends PBaseLayout {

    /**
     * 设置状态栏
     */
    public abstract void setStatusBar();

    /**
     * 初始化数据
     *
     * @param selectConfig 选择配置项
     * @param presenter    presenter
     * @param uiConfig     ui配置类
     * @param selectedList 已选中列表
     */
    public abstract void initData(BaseSelectConfig selectConfig, IPickerPresenter presenter,
                                  PickerUiConfig uiConfig, ArrayList<ImageItem> selectedList);

    /**
     * @return 获取可以点击完成的View
     */
    public abstract View getCompleteView();

    /**
     * 单击图片
     */
    public abstract void singleTap();

    /**
     * 图片切换回调
     *
     * @param position          当前图片索引
     * @param imageItem         当前图片信息
     * @param totalPreviewCount 总预览数
     */
    public abstract void onPageSelected(int position, ImageItem imageItem, int totalPreviewCount);


    public PreviewControllerView(Context context) {
        super(context);
    }

    public PreviewControllerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PreviewControllerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 获取预览的fragment里的布局
     *
     * @param fragment 当前加载的fragment，可以使用以下方式来绑定生命周期
     * <p>
     *         fragment.getLifecycle().addObserver(new ILifeCycleCallBack() {
     *             public void onResume() {}
     *             public void onPause() {}
     *             public void onDestroy() {}
     *         });
     *</p>
     *
     * @param imageItem  当前加载imageitem
     * @param presenter presenter
     * @return 预览的布局
     */
    public View getItemView(Fragment fragment, final ImageItem imageItem, IPickerPresenter presenter) {
        if (imageItem == null) {
            return new View(fragment.getContext());
        }

        RelativeLayout layout = new RelativeLayout(getContext());
        final CropImageView imageView = new CropImageView(getContext());
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        // 启用图片缩放功能
        imageView.setBounceEnable(true);
        imageView.enable();
        imageView.setShowImageRectLine(false);
        imageView.setCanShowTouchLine(false);
        imageView.setMaxScale(7.0f);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        imageView.setLayoutParams(params);
        layout.setLayoutParams(params);
        layout.addView(imageView);

        ImageView mVideoImg = new ImageView(getContext());
        mVideoImg.setImageDrawable(getResources().getDrawable(R.mipmap.picker_icon_video));
        RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(PViewSizeUtils.dp(getContext(), 80), PViewSizeUtils.dp(getContext(), 80));
        mVideoImg.setLayoutParams(params1);
        params1.addRule(RelativeLayout.CENTER_IN_PARENT);
        layout.addView(mVideoImg, params1);

        if (imageItem.isVideo()) {
            mVideoImg.setVisibility(View.VISIBLE);
        } else {
            mVideoImg.setVisibility(View.GONE);
        }

        imageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageItem.isVideo()) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    intent.setDataAndType(imageItem.getUri(), "video/*");
                    getContext().startActivity(intent);
                    return;
                }
                singleTap();
            }
        });
        DetailImageLoadHelper.displayDetailImage(false, imageView, presenter, imageItem);
        return layout;
    }
}
