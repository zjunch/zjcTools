package com.android.zjctools.imagepicker.data;

import androidx.annotation.Nullable;

import com.android.zjctools.imagepicker.bean.ImageItem;

public interface ICameraExecutor {

    void takePhoto();

    void takeVideo();

    void onTakePhotoResult(@Nullable ImageItem imageItem);
}
