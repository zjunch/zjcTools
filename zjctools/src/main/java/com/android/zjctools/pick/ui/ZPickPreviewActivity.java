package com.android.zjctools.pick.ui;

import android.content.Intent;
import android.os.Parcelable;
import android.text.format.Formatter;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.CompoundButton;


import com.android.zjctools.base.ZConstant;
import com.android.zjctools.pick.ZPicker;
import com.android.zjctools.pick.adapter.ZPreviewPageAdapter;
import com.android.zjctools.pick.bean.ZPictureBean;
import com.android.zjctools.utils.ZColor;
import com.android.zjctools.utils.ZDimen;
import com.android.zjctools.utils.ZNavBarUtil;
import com.android.zjctools.utils.ZStr;
import com.android.zjctools.utils.ZToast;
import com.android.zjctools.widget.ZViewPager;
import com.android.zjcutils.R;

import java.util.ArrayList;
import java.util.List;

import androidx.viewpager.widget.ViewPager;

/**
 * Create by zjun on 2019/12/17
 *
 * 图片预览界面
 */
public class ZPickPreviewActivity extends ZPickBaseActivity implements CompoundButton.OnCheckedChangeListener {

    // 预览图片集合
    protected List<ZPictureBean> mPictures;
    // 所以已选择图片
    protected List<ZPictureBean> mSelectedPictures;
    // 当前位置
    protected int mCurrentPosition = 0;
    protected ZViewPager mViewPager;
    // 预览适配器
    protected ZPreviewPageAdapter mAdapter;
    // 是否预览整个文件夹
    protected boolean isPreviewFolder = false;
    // 是否选中原图
    private boolean isOrigin;
    // 是否选中当前图片
    private CheckBox mSelectCB;
    // 原图
    private CheckBox mOriginCB;
    // 确认图片的选择
    private View mBottomBar;
    private View mSpaceView;

    // 图片扫描回调接口
    private ZPicker.OnSelectedPictureListener mSelectedPictureListener;

    @Override
    protected int layoutId() {
        return R.layout.z_activity_pick_preview;
    }

    /**
     * 初始化
     */
    @Override
    protected void initUI() {
        super.initUI();
        getTopBar().setEndBtnTextColor(ZColor.byRes(R.color.zWhite));
        mBottomBar = findViewById(R.id.zjc_preview_bottom_bar);
        mSelectCB = findViewById(R.id.zjc_preview_select_cb);
        mOriginCB = findViewById(R.id.zjc_preview_origin_cb);
        mSpaceView = findViewById(R.id.zjc_preview_bottom_space);
        setDarkTextStatusBar(false); //不需要把状态栏字体变成黑色
    }

    @Override
    protected void setupTopBar() {//重新 状态栏，不需要marginTop 状态栏高度
        mTopBar = findViewById(R.id.zjc_common_top_bar);
        if (mTopBar != null) {
            // 设置状态栏透明主题时，布局整体会上移，所以给头部加上状态栏的 margin 值，保证头部不会被覆盖
            mTopBar.setIconListener(v -> onBackPressed());
        }
    }

    @Override
    protected void initData() {

        mCurrentPosition = getIntent().getIntExtra(ZConstant.KEY_PICK_CURRENT_SELECTED_POSITION, 0);
        isPreviewFolder = getIntent().getBooleanExtra(ZConstant.KEY_PICK_PREVIEW_ALL, false);
        isOrigin = getIntent().getBooleanExtra(ZConstant.ZJC_KEY_PICK_IS_ORIGIN, false);
        mPictures=new ArrayList<>();
        if (isPreviewFolder) {
           // mPictures = ZPicker.getInstance().getCurrentFolderPictures();
            mPictures.addAll(ZPicker.getInstance().getCurrentFolderPictures());
        } else {
           // mPictures = ZPicker.getInstance().getSelectedPictures();
            mPictures.addAll(ZPicker.getInstance().getSelectedPictures());
        }
        mSelectedPictures = ZPicker.getInstance().getSelectedPictures();
        getTopBar().setTitle(ZStr.byResArgs(R.string.zjc_pick_preview_picture_count, mCurrentPosition + 1, mPictures.size()));
        getTopBar().setIconListener(v -> {
            Intent intent = new Intent();
            intent.putExtra(ZConstant.ZJC_KEY_PICK_IS_ORIGIN, isOrigin);
            setResult(ZConstant.ZJC_PICK_RESULT_CODE_BACK, intent);
            onFinish();
        });
        getTopBar().setEndBtnListener(v -> {
            if (ZPicker.getInstance().getSelectedPictures().size() == 0) {
                mSelectCB.setChecked(true);
                ZPictureBean bean = mPictures.get(mCurrentPosition);
                ZPicker.getInstance().addSelectedPicture(mCurrentPosition, bean, mSelectCB.isChecked());
            }
            Intent intent = new Intent();
            List<ZPictureBean> result = ZPicker.getInstance().getSelectedPictures();
            intent.putParcelableArrayListExtra(ZConstant.KEY_PICK_RESULT_PICTURES, (ArrayList<? extends Parcelable>) result);
            setResult(RESULT_OK, intent);
            onFinish();
        });

        mOriginCB.setText(getString(R.string.zjc_pick_origin));
        mOriginCB.setOnCheckedChangeListener(this);
        mOriginCB.setChecked(isOrigin);

        // 初始化当前页面的状态
        ZPictureBean item = mPictures.get(mCurrentPosition);
        mSelectCB.setChecked(ZPicker.getInstance().isSelectPicture(item));

        initViewPager();

        initSelectedPictureListener();

        initNavBarListener();

        //当点击当前选中按钮的时候，需要根据当前的选中状态添加和移除图片
        mSelectCB.setOnClickListener(v -> {
            ZPictureBean VMPictureBean = mPictures.get(mCurrentPosition);
            int selectLimit = ZPicker.getInstance().getSelectLimit();
            if (mSelectCB.isChecked() && mSelectedPictures.size() >= selectLimit) {
                ZToast.create().showErrorBottom(ZStr.byResArgs(R.string.zjc_pick_select_limit, selectLimit));
//                VMToast.make(mActivity, ZStr.byResArgs(R.string.zjc_pick_select_limit, selectLimit)).error();
                mSelectCB.setChecked(false);
            } else {
                ZPicker.getInstance().addSelectedPicture(mCurrentPosition, VMPictureBean, mSelectCB.isChecked());
            }
        });
    }

    /**
     * 初始化底部导航栏变化监听
     */
    private void initNavBarListener() {
        ZNavBarUtil.with(this).setListener(new ZNavBarUtil.OnNavBarChangeListener() {
            @Override
            public void onShow(int orientation, int height) {
                mSpaceView.setVisibility(View.VISIBLE);
                ViewGroup.LayoutParams layoutParams = mSpaceView.getLayoutParams();
                if (layoutParams.height == 0) {
                    layoutParams.height = ZDimen.getNavigationBarHeight();
                    mSpaceView.requestLayout();
                }
            }

            @Override
            public void onHide(int orientation) {
                mSpaceView.setVisibility(View.GONE);
            }
        });
        ZNavBarUtil.with(this, ZNavBarUtil.ORIENTATION_HORIZONTAL).setListener(new ZNavBarUtil.OnNavBarChangeListener() {
            @Override
            public void onShow(int orientation, int height) {
                mTopBar.setPadding(0, 0, height, 0);
                mBottomBar.setPadding(0, 0, height, 0);
            }

            @Override
            public void onHide(int orientation) {
                mTopBar.setPadding(0, 0, 0, 0);
                mBottomBar.setPadding(0, 0, 0, 0);
            }
        });
    }

    /**
     * 初始化预览适配器
     */
    private void initViewPager() {
        mViewPager = findViewById(R.id.zjc_pick_preview_viewpager);
        mAdapter = new ZPreviewPageAdapter(mActivity, mPictures);
        mAdapter.setPreviewClickListener((view) -> onPictureClick());
        mViewPager.setAdapter(mAdapter);
        mViewPager.setCurrentItem(mCurrentPosition, false);

        // ViewPager 滑动的时候，根据外界的数据改变当前的选中状态和当前的图片的位置描述文本
        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                mCurrentPosition = position;
                ZPictureBean item = mPictures.get(mCurrentPosition);
                boolean isSelected = ZPicker.getInstance().isSelectPicture(item);
                mSelectCB.setChecked(isSelected);
                getTopBar().setTitle(ZStr.byResArgs(R.string.zjc_pick_preview_picture_count, mCurrentPosition + 1, mPictures.size()));
            }
        });
    }

    /**
     * 初始化图片选择回调
     * 图片添加成功后，修改当前图片的选中数量
     */
    private void initSelectedPictureListener() {
        mSelectedPictureListener = (position, item, isAdd) -> {
            if (ZPicker.getInstance().getSelectPictureCount() > 0) {
                int selectCount = ZPicker.getInstance().getSelectPictureCount();
                int selectLimit = ZPicker.getInstance().getSelectLimit();
                String complete = ZStr.byResArgs(R.string.zjc_pick_complete_select, selectCount, selectLimit);
                getTopBar().setEndBtn(complete);
            } else {
                getTopBar().setEndBtn(ZStr.byRes(R.string.zjc_pick_complete));
            }

            if (mOriginCB.isChecked()) {
                long size = 0;
                for (ZPictureBean VMPictureBean : mSelectedPictures) {
                    size += VMPictureBean.size;
                }
                String fileSize = Formatter.formatFileSize(mActivity, size);
                mOriginCB.setText(getString(R.string.zjc_pick_origin_size, fileSize));
            }
        };
        ZPicker.getInstance().addOnSelectedPictureListener(mSelectedPictureListener);
        mSelectedPictureListener.onPictureSelected(0, null, false);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra(ZConstant.ZJC_KEY_PICK_IS_ORIGIN, isOrigin);
        setResult(ZConstant.ZJC_PICK_RESULT_CODE_BACK, intent);
        finish();
        super.onBackPressed();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        int id = buttonView.getId();
        if (id == R.id.zjc_preview_origin_cb) {
            if (isChecked) {
                long size = 0;
                for (ZPictureBean item : mSelectedPictures) {
                    size += item.size;
                }
                String fileSize = Formatter.formatFileSize(this, size);
                isOrigin = true;
                mOriginCB.setText(getString(R.string.zjc_pick_origin_size, fileSize));
            } else {
                isOrigin = false;
                mOriginCB.setText(getString(R.string.zjc_pick_origin));
            }
        }
    }

    /**
     * 单击时，隐藏头和尾
     */
    public void onPictureClick() {
        if (mTopBar.getVisibility() == View.VISIBLE) {
            mTopBar.setAnimation(AnimationUtils.loadAnimation(this, R.anim.zjc_fade_out));
            mBottomBar.setAnimation(AnimationUtils.loadAnimation(this, R.anim.zjc_fade_out));
            mTopBar.setVisibility(View.GONE);
            mBottomBar.setVisibility(View.GONE);
        } else {
            mTopBar.setAnimation(AnimationUtils.loadAnimation(this, R.anim.zjc_fade_in));
            mBottomBar.setAnimation(AnimationUtils.loadAnimation(this, R.anim.zjc_fade_in));
            mTopBar.setVisibility(View.VISIBLE);
            mBottomBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onDestroy() {
        ZPicker.getInstance().removeOnSelectedPictureListener(mSelectedPictureListener);
        super.onDestroy();
    }
}
