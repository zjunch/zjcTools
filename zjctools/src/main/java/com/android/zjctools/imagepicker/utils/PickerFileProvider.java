package com.android.zjctools.imagepicker.utils;

import android.app.Activity;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;

import java.io.File;

/**
 * Time: 2019/7/24 15:43
 * Author:ypx
 * Description:
 */
public class PickerFileProvider extends FileProvider {

    public static Uri getUriForFile(@NonNull Activity context,
                                    @NonNull File file) {
        Uri uri;
        if (android.os.Build.VERSION.SDK_INT < 24) {
            uri = Uri.fromFile(file);
        } else {
            uri = getUriForFile(context, context.getApplication().getPackageName()
                    + ".picker.fileprovider", file);
        }

        return uri;
    }
}
