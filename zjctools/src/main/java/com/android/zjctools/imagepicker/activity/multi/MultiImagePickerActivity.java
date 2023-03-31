package com.android.zjctools.imagepicker.activity.multi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.android.zjctools.imagepicker.ImagePicker;
import com.android.zjcutils.R;
import com.android.zjctools.imagepicker.activity.PickerActivityManager;
import com.android.zjctools.imagepicker.bean.ImageItem;
import com.android.zjctools.imagepicker.bean.PickerError;
import com.android.zjctools.imagepicker.bean.selectconfig.MultiSelectConfig;
import com.android.zjctools.imagepicker.data.OnImagePickCompleteListener;
import com.android.zjctools.imagepicker.data.OnImagePickCompleteListener2;
import com.android.zjctools.imagepicker.data.PickerActivityCallBack;
import com.android.zjctools.imagepicker.helper.PickerErrorExecutor;
import com.android.zjctools.imagepicker.helper.launcher.PLauncher;
import com.android.zjctools.imagepicker.presenter.IPickerPresenter;
import com.android.zjctools.imagepicker.utils.PViewSizeUtils;

import java.util.ArrayList;

/**
 * Description: 多选页
 * <p>
 * Author: peixing.yang
 * Date: 2019/2/21
 * 使用文档 ：https://github.com/yangpeixing/YImagePicker/wiki/Documentation_3.x
 */
public class MultiImagePickerActivity extends FragmentActivity {
    public static final String INTENT_KEY_SELECT_CONFIG = "MultiSelectConfig";
    public static final String INTENT_KEY_PRESENTER = "IPickerPresenter";
    public static final String INTENT_KEY_CURRENT_INDEX = "currentIndex";
    public static final String INTENT_KEY_CURRENT_IMAGE = "currentImage";

    private MultiImagePickerFragment fragment;
    private MultiSelectConfig selectConfig;
    private IPickerPresenter presenter;

    /**
     * 跳转微信选择器页面
     *
     * @param activity     跳转的activity
     * @param selectConfig 配置项
     * @param presenter    IMultiPickerBindPresenter
     * @param listener     选择回调
     */
    public static void intent(@NonNull Activity activity, @NonNull MultiSelectConfig selectConfig,
                              @NonNull IPickerPresenter presenter, @NonNull OnImagePickCompleteListener listener) {
        if (PViewSizeUtils.onDoubleClick()) {
            return;
        }
        Intent intent = new Intent(activity, MultiImagePickerActivity.class);
        intent.putExtra(MultiImagePickerActivity.INTENT_KEY_SELECT_CONFIG, selectConfig);
        intent.putExtra(MultiImagePickerActivity.INTENT_KEY_PRESENTER, presenter);
        PLauncher.init(activity).startActivityForResult(intent, PickerActivityCallBack.create(listener));
    }

    /**
     * 校验传递数据是否合法
     */
    private boolean isIntentDataFailed() {
        selectConfig = (MultiSelectConfig) getIntent().getSerializableExtra(INTENT_KEY_SELECT_CONFIG);
        presenter = (IPickerPresenter) getIntent().getSerializableExtra(INTENT_KEY_PRESENTER);
        if (presenter == null) {
            PickerErrorExecutor.executeError(this, PickerError.PRESENTER_NOT_FOUND.getCode());
            return true;
        }
        if (selectConfig == null) {
            PickerErrorExecutor.executeError(this, PickerError.SELECT_CONFIG_NOT_FOUND.getCode());
            return true;
        }
        return false;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isIntentDataFailed()) {
            return;
        }
        PickerActivityManager.addActivity(this);
        setContentView(R.layout.picker_activity_fragment_wrapper);
        setFragment();
    }

    /**
     * 填充选择器fragment
     */
    private void setFragment() {
        fragment = ImagePicker.withMulti(presenter)
                .withMultiSelectConfig(selectConfig)
                .pickWithFragment(new OnImagePickCompleteListener2() {
                    @Override
                    public void onPickFailed(PickerError error) {
                        PickerErrorExecutor.executeError(MultiImagePickerActivity.this, error.getCode());
                        PickerActivityManager.clear();
                    }

                    @Override
                    public void onImagePickComplete(ArrayList<ImageItem> items) {
                        ImagePicker.closePickerWithCallback(items);
                    }
                });
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }

    @Override
    public void onBackPressed() {
        if (fragment != null && fragment.onBackPressed()) {
            return;
        }
        super.onBackPressed();
    }
}
