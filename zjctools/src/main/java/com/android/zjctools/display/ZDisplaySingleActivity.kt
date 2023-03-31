package com.android.zjctools.display

import com.android.zjctools.base.ZBActivity
import com.android.zjctools.image.ZImgLoader
import com.android.zjctools.router.ZParams
import com.android.zjctools.router.ZRouter
import com.android.zjcutils.R
import com.github.chrisbanes.photoview.PhotoView

/**
 * 展示单图头像大图界面
 */
class ZDisplaySingleActivity : ZBActivity() {
    var avatarView: PhotoView? = null
    private var avatarUrl: String? = null
    override fun layoutId(): Int {
        return R.layout.z_activity_display_single_image
    }

    override fun initUI() {
        avatarView = findViewById(R.id.img_avatar)
    }

    override fun initData() {
        val params = ZRouter.getParcelable(mActivity) as ZParams
        avatarUrl = params.str0
        avatarView?.let { ZImgLoader.loadCover(it,avatarUrl,isRadius =false) }
    }
}