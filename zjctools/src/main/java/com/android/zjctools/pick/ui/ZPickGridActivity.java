package com.android.zjctools.pick.ui;

import android.content.Intent;
import android.os.Parcelable;

import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;


import com.android.zjctools.base.ZConstant;
import com.android.zjctools.permission.ZPermission;
import com.android.zjctools.pick.ZPickScanPicture;
import com.android.zjctools.pick.ZPicker;
import com.android.zjctools.pick.adapter.ZFolderAdapter;
import com.android.zjctools.pick.adapter.ZPictureAdapter;
import com.android.zjctools.pick.bean.ZFolderBean;
import com.android.zjctools.pick.bean.ZPictureBean;
import com.android.zjctools.pick.widget.FolderPopupWindow;
import com.android.zjctools.utils.ZNavBarUtil;
import com.android.zjctools.utils.ZColor;
import com.android.zjctools.utils.ZDimen;
import com.android.zjctools.utils.ZStr;
import com.android.zjctools.utils.ZToast;
import com.android.zjctools.widget.ZItemDecoration;
import com.android.zjcutils.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Crete by zjun on 2019/12/14
 * <p>
 * 图片选择界面
 */
public class ZPickGridActivity extends ZPickBaseActivity {

    // 图片文件夹适配器
    private ZFolderAdapter mFolderAdapter;
    // 展示图片文件夹列表的 PopupWindow
    private FolderPopupWindow mFolderPopupWindow;
    // 图片文件夹数据集合
    private List<ZFolderBean> mFolderBeans;

    // 是否显示相机
    private boolean isShowCamera = false;
    // 默认不是直接调取相机
    private boolean isDirectCamera = false;
    // 是否选中原图
    private boolean isOrigin = false;

    // 底部栏
    private View mBottomBar;
    private View mBottomSpaceView;
    // 文件夹切换按钮
    private View mChangeDirView;
    // 显示当前文件夹
    private TextView mCurrDirView;
    // 预览按钮
    private TextView mPreviewBtn;

    // 使用 RecyclerView 展示图片
    private RecyclerView mRecyclerView;
    private ZPictureAdapter mPictureAdapter;
    ZPickScanPicture mZPickScanPicture;
    // 图片扫描回调接口
    private ZPickScanPicture.OnScanPictureListener mScanPictureListener;
    private ZPicker.OnSelectedPictureListener mSelectedPictureListener;

    private boolean isNeedClearCamera;//是否删除拍照后的图片

    @Override
    protected int layoutId() {
        return R.layout.z_activity_pick_grid;
    }

    /**
     * 初始化
     */
    @Override
    protected void initUI() {
        super.initUI();
        mRecyclerView = findViewById(R.id.z_pick_grid_recycler_view);
        if(ZPicker.getInstance().getColorResIg()!=0){
            mRecyclerView.setBackgroundColor(ZColor.byRes(ZPicker.getInstance().getColorResIg()));
        }
        mBottomBar = findViewById(R.id.z_pick_grid_bottom_bar_rl);
        mBottomSpaceView = findViewById(R.id.z_pick_grid_bottom_space);
        mChangeDirView = findViewById(R.id.z_pick_grid_choose_folder_rl);
        mCurrDirView = findViewById(R.id.z_pick_grid_choose_folder_tv);
        mPreviewBtn = findViewById(R.id.z_pick_grid_preview_btn);

        mPreviewBtn.setOnClickListener(viewListener);
        mChangeDirView.setOnClickListener(viewListener);
        getTopBar().setIconListener(v -> onFinish());
        getTopBar().setBackIconColor(ZColor.byRes(R.color.zBlack));
        if (ZPicker.getInstance().isMultiMode()) {
            getTopBar().setEndBtnListener(v -> {
                Intent intent = new Intent();
                List<ZPictureBean> result = ZPicker.getInstance().getSelectedPictures();
                intent.putParcelableArrayListExtra(ZConstant.KEY_PICK_RESULT_PICTURES, (ArrayList<? extends Parcelable>) result);
                setResult(RESULT_OK, intent);
                onFinish();
            });
            mPreviewBtn.setVisibility(View.VISIBLE);
        } else {
            getTopBar().setEndBtn("");
            mPreviewBtn.setVisibility(View.GONE);
        }

        initPictureRecyclerView();

        initNavBarListener();
    }

    @Override
    protected void initData() {
        // 每次打开都重置选择器
        //ZPicker.getInstance().reset();

        isShowCamera = ZPicker.getInstance().isShowCamera();
        List<ZPictureBean> pictures = (List<ZPictureBean>) getIntent().getSerializableExtra(ZConstant.Z_KEY_PICK_PICTURES);
        ZPicker.getInstance().setSelectedPictures(pictures);

        mFolderAdapter = new ZFolderAdapter(mActivity, null);

        refreshBtnStatus();
        // 初始化扫描图片， 要先扫描图片
        initScanPicture();
        // 初始化选择图片监听
        initSelectPictureListener();
    }

    /**
     * 初始化图片列表
     */
    private void initPictureRecyclerView() {
        int spanSize =ZPicker.getInstance().getSpanSiZe();
        int spaceDp=5;//上下左右距离,默认5dp
        if(spanSize!=4){
            spaceDp=10;
        }
        mPictureAdapter = new ZPictureAdapter(mActivity, null);
        mRecyclerView.setLayoutManager(new GridLayoutManager(mActivity, spanSize));
        mRecyclerView.setPadding(ZDimen.dp2px(spaceDp),0,0,0);
        mRecyclerView.addItemDecoration(ZItemDecoration.createVertical(mActivity, ZColor.byRes(R.color.zTransparent), ZDimen.dp2px(spaceDp)));
        mRecyclerView.addItemDecoration(ZItemDecoration.createHorizontal(mActivity, ZColor.byRes(R.color.zTransparent), ZDimen.dp2px(spaceDp)));
        mRecyclerView.setAdapter(mPictureAdapter);
        mPictureAdapter.setClickListener((int position, Object object) -> {
            onPictureClick(position);
        });
    }

    /**
     * 初始化底部导航栏变化监听
     */
    private void initNavBarListener() {
        ZNavBarUtil.with(this).setListener(new ZNavBarUtil.OnNavBarChangeListener() {
            @Override
            public void onShow(int orientation, int height) {
                mBottomSpaceView.setVisibility(View.VISIBLE);
                ViewGroup.LayoutParams layoutParams = mBottomSpaceView.getLayoutParams();
                if (layoutParams.height == 0) {
                    layoutParams.height = ZDimen.getNavigationBarHeight();
                    mBottomSpaceView.requestLayout();
                }
            }

            @Override
            public void onHide(int orientation) {
                mBottomSpaceView.setVisibility(View.GONE);
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
     * 扫描图片
     */
    private void initScanPicture() {
        mScanPictureListener = new ZPickScanPicture.OnScanPictureListener() {
            @Override
            public void onLoadComplete(List<ZFolderBean> VMFolderBeans) {
                mFolderBeans = new ArrayList<ZFolderBean>();
                mFolderBeans.clear();
                if (VMFolderBeans != null) {
                    mFolderBeans.addAll(VMFolderBeans);
                }
                if (mFolderBeans.size() == 0) {
                    mPictureAdapter.refresh(null);
                } else {
                    mPictureAdapter.refresh(mFolderBeans.get(0).pictures);
                }
                mFolderAdapter.refreshData(mFolderBeans);
            }
        };
        // 检查权限
        if (!ZPermission.getInstance(mActivity).checkStorage()) {
            ZPermission.getInstance(mActivity).requestStorage(new ZPermission.PCallback() {
                @Override
                public void onReject() {
//                    VMToast.make(mActivity, "读写手机存储 权限被拒绝，无法使用此功能").error();
                    ZToast.create().showErrorBottom("读写手机存储 权限被拒绝，无法使用此功能");
                }

                @Override
                public void onComplete() {
                    mZPickScanPicture=  new ZPickScanPicture(mActivity, null, mScanPictureListener);
                }
            });
        } else {
            mZPickScanPicture= new ZPickScanPicture(mActivity, null, mScanPictureListener);
        }
    }

    /**
     * 初始化选择图片监听
     */
    private void initSelectPictureListener() {
        mSelectedPictureListener = (position, bean, isAdd) -> {
            refreshBtnStatus();
            //这里默认是有相机的默认图片，所以位置要加1
            mPictureAdapter.notifyItemChanged(position+1, 1);
        };
        ZPicker.getInstance().addOnSelectedPictureListener(mSelectedPictureListener);
    }

    /**
     * 刷新确认以及预览按钮
     */
    private void refreshBtnStatus() {
        int selectCount = ZPicker.getInstance().getSelectPictureCount();
        int selectLimit = ZPicker.getInstance().getSelectLimit();
        if (ZPicker.getInstance().isMultiMode()) {
            if (selectCount > 0) {
                getTopBar().setEndBtn(ZStr.byResArgs(R.string.z_pick_complete_select, selectCount, selectLimit));
                getTopBar().getEndBtn().setVisibility(View.VISIBLE);
                getTopBar().setEndBtnTextColor(ZColor.byRes(R.color.app_theme_color));
                mPreviewBtn.setText(ZStr.byResArgs(R.string.z_pick_preview_count, selectCount));
                mPreviewBtn.setEnabled(true);
            } else {
                getTopBar().setEndBtn(ZStr.byRes(R.string.z_pick_complete));
                getTopBar().getEndBtn().setVisibility(View.GONE);
                mPreviewBtn.setEnabled(false);
                mPreviewBtn.setText(getResources().getString(R.string.z_pick_preview));
            }
        }
    }

    /**
     * 点击图片列表
     */
    private void onPictureClick(int position) {
        // 判断第一个是不是相机，如果是，特殊处理
        if (ZPicker.getInstance().isShowCamera() && position == 0) {
            if (ZPicker.getInstance().getSelectPictureCount() >= ZPicker.getInstance().getSelectLimit()) {//图片个数超限
                String toastMsg = ZStr.byResArgs(R.string.z_pick_select_limit, ZPicker.getInstance().getSelectLimit());
                ZToast.create().showErrorBottom(toastMsg);
                return;
            }
            openCamera();
        } else {
            position = isShowCamera ? position - 1 : position;
            if (ZPicker.getInstance().isMultiMode()) {
                Intent intent = new Intent(ZPickGridActivity.this, ZPickPreviewActivity.class);
                intent.putExtra(ZConstant.KEY_PICK_CURRENT_SELECTED_POSITION, position);
                intent.putExtra(ZConstant.KEY_PICK_PREVIEW_ALL, true);

                intent.putExtra(ZConstant.Z_KEY_PICK_IS_ORIGIN, isOrigin);
                startActivityForResult(intent, ZConstant.Z_PICK_REQUEST_CODE_PREVIEW);  //如果是多选，点击图片进入预览界面
            } else {
                ZPicker.getInstance().clearSelectedPictures();
                ZPicker.getInstance().addSelectedPicture(position, ZPicker.getInstance().getCurrentFolderPictures().get(position), true);
                if (ZPicker.getInstance().isCrop()) {
                    Intent intent = new Intent(mActivity, ZPickCropActivity.class);
                    startActivityForResult(intent, ZConstant.Z_PICK_REQUEST_CODE_CROP);  //单选需要裁剪，进入裁剪界面
                } else {
                    Intent intent = new Intent();
                    List<ZPictureBean> result = ZPicker.getInstance().getSelectedPictures();
                    intent.putParcelableArrayListExtra(ZConstant.KEY_PICK_RESULT_PICTURES, (ArrayList<? extends Parcelable>) result);
                    setResult(RESULT_OK, intent);   //单选不需要裁剪，返回数据
                    finish();
                }
            }
        }
    }

    /**
     * 开启相机，先检查是否有相机权限
     */
    private void openCamera() {
        if (!ZPermission.getInstance(mActivity).checkCamera()) {
            ZPermission.getInstance(mActivity).requestCamera(new ZPermission.PCallback() {
                @Override
                public void onReject() {
//                    VMToast.make(mActivity, "访问相机 权限被拒绝，无法使用此功能").error();
                    ZToast.create().showErrorBottom("访问相机 权限被拒绝，无法使用此功能");
                }

                @Override
                public void onComplete() {
                    ZPicker.getInstance().takePicture(mActivity, ZConstant.Z_PICK_REQUEST_CODE_TAKE);
                }
            });
        } else {
            ZPicker.getInstance().takePicture(mActivity, ZConstant.Z_PICK_REQUEST_CODE_TAKE);
        }
    }

    /**
     * 弹出文件夹选择列表
     */
    private void showFolderList() {
        if (mFolderBeans == null) {
            ZToast.create().showErrorBottom("没有更多图片可供选择");
            // VMToast.make(mActivity, "没有更多图片可供选择").error();
            return;
        }
        mFolderPopupWindow = new FolderPopupWindow(mActivity, mFolderAdapter);
        mFolderPopupWindow.setOnItemClickListener((AdapterView<?> adapterView, View view, int position, long l) -> {
            mFolderAdapter.setSelectIndex(position);
            ZPicker.getInstance().setCurrentFolderPosition(position);
            mFolderPopupWindow.dismiss();
            ZFolderBean folderBean = (ZFolderBean) adapterView.getAdapter().getItem(position);
            if (null != folderBean) {
                mPictureAdapter.refresh(folderBean.pictures);
                mCurrDirView.setText(folderBean.name);
            }
        });
        mFolderPopupWindow.setMargin(mBottomBar.getHeight());

        //点击文件夹按钮
        mFolderAdapter.refreshData(mFolderBeans);  //刷新数据
        if (mFolderPopupWindow.isShowing()) {
            mFolderPopupWindow.dismiss();
        } else {
            mFolderPopupWindow.showAtLocation(mBottomBar, Gravity.NO_GRAVITY, 0, 0);
            //默认选择当前选择的上一个，当目录很多时，直接定位到已选中的条目
            int index = mFolderAdapter.getSelectIndex();
            index = index == 0 ? index : index - 1;
            mFolderPopupWindow.setSelection(index);
        }
    }

    /**
     * 界面控件点击事件
     */
    private View.OnClickListener viewListener = v -> {
        if (v.getId() == R.id.z_pick_grid_choose_folder_rl) {
            showFolderList();
        } else if (v.getId() == R.id.z_pick_grid_preview_btn) {
            Intent intent = new Intent(mActivity, ZPickPreviewActivity.class);
            intent.putExtra(ZConstant.KEY_PICK_CURRENT_SELECTED_POSITION, 0);
            intent.putExtra(ZConstant.Z_KEY_PICK_IS_ORIGIN, isOrigin);
            intent.putExtra(ZConstant.KEY_PICK_PREVIEW_ALL, false);
            startActivityForResult(intent, ZConstant.Z_PICK_REQUEST_CODE_PREVIEW);
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && data.getExtras() != null) {
            if (resultCode == ZConstant.Z_PICK_RESULT_CODE_BACK) {
                isOrigin = data.getBooleanExtra(ZConstant.Z_KEY_PICK_IS_ORIGIN, false);
            } else {
                //从拍照界面返回
                //点击 X , 没有选择照片
                if (data.getSerializableExtra(ZConstant.KEY_PICK_RESULT_PICTURES) == null) {
                    //什么都不做，直接返回
                } else {
                    //说明是从裁剪页面过来的数据，直接返回就可以
                    setResult(RESULT_OK, data);
                }
                onFinish();
            }
        } else {
            //如果是裁剪，因为裁剪指定了存储的Uri，所以返回的data一定为null
            if (resultCode == RESULT_OK && requestCode == ZConstant.Z_PICK_REQUEST_CODE_TAKE) {
                /**
                 * 2017-03-21 对机型做旋转处理
                 */

                String path = ZPicker.getInstance().getTakeImageFile().getAbsolutePath();
                ZPictureBean zPictureBean = new ZPictureBean();
                zPictureBean.path = path;
                zPictureBean.isCamera=true;
                ZPicker.getInstance().addCameraImgPath(path);
//              ZPicker.getInstance().clearSelectedPictures();
                //添加到已经选择的图片集合
                ZPicker.getInstance().addSelectedPicture(0, zPictureBean, true);
                mZPickScanPicture.removeListener();  //不需要通过扫描文件获取了

                File parentFile = ZPicker.getInstance().getTakeImageFile().getParentFile();
                // 将图片塞进对应的文件夹
                if(mFolderBeans!=null&&mFolderBeans.size()>0){
                    boolean isHave=false;
                    mFolderBeans.get(0).pictures.add(0,zPictureBean);  //塞进所有图片文件夹
                    mFolderBeans.get(0).cover=zPictureBean;    //更换所有图片封面图
                    for (int i = 0; i <mFolderBeans.size() ; i++) {
                        //找到对应的文件夹
                         if(parentFile!=null&&mFolderBeans.get(i).name!=null&&mFolderBeans.get(i).name.equals(parentFile.getName())){
                            //塞进对应的文件夹
                             mFolderBeans.get(i).pictures.add(0,zPictureBean);
                             //更换对应文件夹的封面
                             mFolderBeans.get(i).cover=zPictureBean;
                            isHave=true;
                            break;
                        }
                    }
                    //没有该文件夹则创建该文件夹
                    if(!isHave&&parentFile!=null){
                        ZFolderBean  folderBean=new ZFolderBean();
                        folderBean.path=parentFile.getAbsolutePath();
                        folderBean.name=parentFile.getName();
                        folderBean.pictures=new ArrayList<>();
                        folderBean.pictures.add(zPictureBean);
                        folderBean.cover=zPictureBean;
                        mFolderBeans.add(folderBean);
                    }

                }
                ArrayList<ZPictureBean> pictures= mFolderBeans.get(ZPicker.getInstance().getCurrentFolderPosition()).pictures;
                mPictureAdapter.refresh(pictures);


               // 发送广播通知图片增加了
                ZPicker.notifyGalleryChange(mActivity, ZPicker.getInstance().getTakeImageFile());
                if (ZPicker.getInstance().isCrop()) {
                    Intent intent = new Intent(mActivity, ZPickCropActivity.class);
                    startActivityForResult(intent, ZConstant.Z_PICK_REQUEST_CODE_CROP);  //单选需要裁剪，进入裁剪界面
                } else {   //单选不需要裁剪，返回数据
//                    Intent intent = new Intent();
//                    List<ZPictureBean> result = ZPicker.getInstance().getSelectedPictures();
//                    intent.putParcelableArrayListExtra(ZConstant.KEY_PICK_RESULT_PICTURES, (ArrayList<? extends Parcelable>) result);
//                    setResult(RESULT_OK, intent);
//                    onFinish();
                }
            } else if (isDirectCamera) {
                onFinish();
            }
        }
    }

    @Override
    protected void onDestroy() {

        if (mSelectedPictureListener != null) {
            ZPicker.getInstance().removeOnSelectedPictureListener(mSelectedPictureListener);
            mSelectedPictureListener = null;
        }
        super.onDestroy();
    }
}