package com.android.zjctools.display;

import android.text.TextUtils;

import com.android.zjctools.base.ZBActivity;
import com.android.zjctools.glide.ZIMGLoader;
import com.android.zjctools.pick.ZImgLoaderListener;
import com.android.zjctools.router.ZParams;
import com.android.zjctools.router.ZRouter;
import com.android.zjcutils.R;
import com.github.chrisbanes.photoview.PhotoView;


/**
 * 展示单图头像大图界面
 */
public class ZDisplaySingleActivity extends ZBActivity {


    PhotoView avatarView;

    private String avatarUrl;
    private int sex;

    @Override
    protected int layoutId() {
        return R.layout.zjc_activity_display_single_image;
    }

    @Override
    protected void initUI() {
        avatarView=findViewById(R.id.img_avatar);
    }

    @Override
    protected void initData() {
        ZParams params = (ZParams) ZRouter.getParcelable(mActivity);
        sex = params.what;
        avatarUrl = params.str0;
        if (!TextUtils.isEmpty(avatarUrl)) {
            ZImgLoaderListener.Options options = new ZImgLoaderListener.Options(avatarUrl);
            options.isCircle = true;
            ZIMGLoader.load(mActivity, options, avatarView);
        }
    }



}
