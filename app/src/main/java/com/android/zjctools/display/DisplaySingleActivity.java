package com.android.zjctools.display;

import android.text.TextUtils;
import android.view.View;

import com.android.zjctools.R;
import com.android.zjctools.base.ZBActivity;
import com.android.zjctools.glide.IMGLoader;
import com.android.zjctools.pick.ILoaderListener;
import com.android.zjctools.router.ZParams;
import com.android.zjctools.router.ZRouter;
import com.github.chrisbanes.photoview.PhotoView;


/**
 * 展示单图头像大图界面
 */
public class DisplaySingleActivity extends ZBActivity {


    PhotoView avatarView;

    private String avatarUrl;
    private int sex;

    @Override
    protected int layoutId() {
        return R.layout.activity_display_single_image;
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
            ILoaderListener.Options options = new ILoaderListener.Options(avatarUrl);
            options.isCircle = true;
            IMGLoader.load(mActivity, options, avatarView);
        }
    }



}
