package com.android.zjctools.imagepicker.views.wx;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.zjctools.imagepicker.ImagePicker;
import com.android.zjcutils.R;
import com.android.zjctools.imagepicker.utils.PCornerUtils;
import com.android.zjctools.imagepicker.utils.PStatusBarUtil;
import com.android.zjctools.imagepicker.views.base.SingleCropControllerView;
import com.android.zjctools.imagepicker.widget.cropimage.CropImageView;

public class WXSingleCropControllerView extends SingleCropControllerView {
    private TextView mCompleteBtn;
    private TextView mTitle;

    public WXSingleCropControllerView(Context context) {
        super(context);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.picker_wx_crop_titlebar;
    }

    @Override
    protected void initView(View view) {
        RelativeLayout mTitleBar = view.findViewById(R.id.mTitleBar);
        ImageView mIvBack = view.findViewById(R.id.iv_back);
        mTitle=view.findViewById(R.id.tv_title);
        mCompleteBtn = view.findViewById(R.id.tv_rightBtn);
        mTitleBar.setBackgroundColor(Color.WHITE);
        mCompleteBtn.setBackground(PCornerUtils.cornerDrawable(ImagePicker.getThemeColor(), dp(2)));
        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mCompleteBtn.setText(getContext().getString(R.string.picker_str_title_crop_right));
        mTitle.setText(getContext().getString(R.string.picker_str_title_crop));
    }

    @Override
    public void setStatusBar() {
        PStatusBarUtil.setStatusBar((Activity) getContext(), Color.WHITE, false, true);
    }

    @Override
    public View getCompleteView() {
        return mCompleteBtn;
    }

    @Override
    public void setCropViewParams(CropImageView cropImageView, ViewGroup.MarginLayoutParams params) {
        params.topMargin = dp(50);
        cropImageView.setLayoutParams(params);
    }

}
