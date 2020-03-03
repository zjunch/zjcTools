package com.android.zjctools.pick.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcelable;
import android.util.DisplayMetrics;


import com.android.zjctools.base.ZConstant;
import com.android.zjctools.pick.ZPicker;
import com.android.zjctools.pick.bean.ZPictureBean;
import com.android.zjctools.utils.ZColor;
import com.android.zjctools.utils.bitmap.ZBitmap;
import com.android.zjctools.widget.ZCropView;
import com.android.zjcutils.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by lzan13 on 2019/05/16 20:37
 *
 * 修剪图片
 */
public class ZPickCropActivity extends ZPickBaseActivity implements ZCropView.OnBitmapSaveCompleteListener {

    private ZCropView mCropView;
    private Bitmap mBitmap;
    private boolean mIsSaveRectangle;
    private int mCropOutWidth;
    private int mCropOutHeight;
    private List<ZPictureBean> mPictureBeans;

    @Override
    protected int layoutId() {
        return R.layout.zjc_activity_pick_crop;
    }

    @Override
    protected void initUI() {
        super.initUI();
        mCropView = findViewById(R.id.zjc_pick_crop_iv);
        mCropView.setOnBitmapSaveCompleteListener(this);
        getTopBar().setIconListener(v -> {
            setResult(RESULT_CANCELED);
            onFinish();
        });
        getTopBar().setEndBtnTextColor(ZColor.byRes(R.color.zjcWhite));
        getTopBar().setEndBtnListener(v -> mCropView.saveBitmapToFile(ZPicker.getInstance()
            .getCropCacheFolder(), mCropOutWidth, mCropOutHeight, mIsSaveRectangle));
    }

    @Override
    protected void initData() {
        mCropView.setFocusStyle(ZPicker.getInstance().getCropStyle());
        mCropView.setFocusWidth(ZPicker.getInstance().getCropFocusWidth());
        mCropView.setFocusHeight(ZPicker.getInstance().getCropFocusHeight());

        mCropOutWidth = ZPicker.getInstance().getCropOutWidth();
        mCropOutHeight = ZPicker.getInstance().getCropOutHeight();
        mIsSaveRectangle = ZPicker.getInstance().isSaveRectangle();
        mPictureBeans = ZPicker.getInstance().getSelectedPictures();

        String imagePath = mPictureBeans.get(0).path;
        // 缩放图片
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imagePath, options);
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        options.inSampleSize = calculateInSampleSize(options, displayMetrics.widthPixels, displayMetrics.heightPixels);
        options.inJustDecodeBounds = false;
        mBitmap = BitmapFactory.decodeFile(imagePath, options);
        // 设置默认旋转角度
        mCropView.setImageBitmap(mCropView.rotate(mBitmap, ZBitmap.getBitmapDegree(imagePath)));
    }

    public int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        int width = options.outWidth;
        int height = options.outHeight;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            if (width > height) {
                inSampleSize = width / reqWidth;
            } else {
                inSampleSize = height / reqHeight;
            }
        }
        return inSampleSize;
    }

    @Override
    public void onBitmapSaveSuccess(ZPictureBean bean) {
        // 裁剪后替换掉返回数据的内容，但是不要改变全局中的选中数据
        mPictureBeans.remove(0);
        mPictureBeans.add(bean);

        // 单选不需要裁剪，返回数据
        Intent intent = new Intent();
        intent.putParcelableArrayListExtra(ZConstant.KEY_PICK_RESULT_PICTURES, (ArrayList<? extends Parcelable>) mPictureBeans);
        setResult(RESULT_OK, intent);
        onFinish();
    }

    @Override
    public void onBitmapSaveError(ZPictureBean bean) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCropView.setOnBitmapSaveCompleteListener(null);
        if (null != mBitmap && !mBitmap.isRecycled()) {
            mBitmap.recycle();
            mBitmap = null;
        }
    }
}
