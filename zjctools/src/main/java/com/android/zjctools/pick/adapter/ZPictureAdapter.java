package com.android.zjctools.pick.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;


import com.android.zjctools.base.ZAdapter;
import com.android.zjctools.base.ZHolder;
import com.android.zjctools.pick.ZImgLoaderListener;
import com.android.zjctools.pick.ZPicker;
import com.android.zjctools.pick.bean.ZPictureBean;
import com.android.zjctools.utils.ZDimen;
import com.android.zjctools.utils.ZStr;
import com.android.zjctools.utils.ZToast;
import com.android.zjcutils.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

/**
 * Create by zjun on 2019/05/16 21:51
 *
 * 加载相册图片的 RecyclerView 适配器，使用局部刷新解决选中照片出现闪动问题
 */
public class ZPictureAdapter extends ZAdapter<ZPictureBean, ZHolder> {

    // 相机 Item 类型
    private static final int ITEM_TYPE_CAMERA = 0;
    // 正常 Item 类型
    private static final int ITEM_TYPE_NORMAL = 1;

    // 是否显示拍照按钮
    private boolean isShowCamera;

    /**
     * 构造方法
     */
    public ZPictureAdapter(Context context, ArrayList<ZPictureBean> pictures) {
        super(context, pictures);

        int space = ZDimen.dp2px(2);

        isShowCamera = ZPicker.getInstance().isShowCamera();
        if (isShowCamera) {
            mDataList.add(0, new ZPictureBean());
        }
    }

    @Override
    public ZHolder createHolder(@NonNull ViewGroup root, int viewType) {
        if (viewType == ITEM_TYPE_CAMERA) {
            return new CameraViewHolder(mInflater.inflate(R.layout.z_pick_picture_grid_camera_item, root, false));
        }
        return new PictureViewHolder(mInflater.inflate(R.layout.z_pick_picture_grid_item, root, false));
    }

    @Override
    public void bindHolder(@NonNull ZHolder holder, int position) {
        switch (getItemViewType(position)) {
        case ITEM_TYPE_CAMERA:
            ((CameraViewHolder) holder).bindCamera();
            break;
        case ITEM_TYPE_NORMAL:
            ((PictureViewHolder) holder).bind(position);
            break;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ZHolder holder, int position, @NonNull List<Object> payloads) {
        //        super.onBindViewHolder(holder, position, payloads);
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position);
            return;
        }
        if (ZPicker.getInstance().isMultiMode() && position > 0) {
            ZPictureBean bean = getItemData(position);
            ((PictureViewHolder) holder).refreshCheckBox(bean);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (isShowCamera) {
            return position == 0 ? ITEM_TYPE_CAMERA : ITEM_TYPE_NORMAL;
        }
        return ITEM_TYPE_NORMAL;
    }

    @Override
    public void refresh(List<ZPictureBean> list) {
        if (list == null || list.size() == 0) {
            mDataList.clear();
        } else {
            mDataList.clear();
            mDataList.addAll(list);
        }
        if (isShowCamera) {
            mDataList.add(0, new ZPictureBean());
        }
        notifyDataSetChanged();
    }

    /**
     * 展示图片缩略图 Holder
     */
    private class PictureViewHolder extends ZHolder {

        // 图片
        public ImageView mThumbView;
        // 选择框热区
        public View mHotRegionCB;
        // 选择框
        public CheckBox mItemCB;

        public PictureViewHolder(View itemView) {
            super(itemView);
            mThumbView = itemView.findViewById(R.id.zjc_pick_grid_item_thumb_iv);
            mHotRegionCB = itemView.findViewById(R.id.zjc_pick_grid_item_check_layout);
            mItemCB = itemView.findViewById(R.id.zjc_pick_grid_item_cb);
        }

        /**
         * 绑定数据
         *
         * @param position 数据所在位置
         */
        public void bind(final int position) {
            final ZPictureBean bean = getItemData(position);
            // 根据是否多选，显示或隐藏checkbox
            if (ZPicker.getInstance().isMultiMode()) {
                refreshCheckBox(bean);
            } else {
                mHotRegionCB.setVisibility(View.GONE);
                mItemCB.setVisibility(View.GONE);
            }

            mHotRegionCB.setOnClickListener(v -> {
                mItemCB.setChecked(!mItemCB.isChecked());
                List<ZPictureBean> selectedList = ZPicker.getInstance().getSelectedPictures();
                int selectLimit = ZPicker.getInstance().getSelectLimit();
                if (mItemCB.isChecked() && selectedList.size() >= selectLimit) {
                    String toastMsg = ZStr.byResArgs(R.string.zjc_pick_select_limit, selectLimit);
                    ZToast.create().showErrorBottom(toastMsg);
                    mItemCB.setChecked(false);
                } else {
                    ZPicker.getInstance().addSelectedPicture(position, bean, mItemCB.isChecked());
                }
            });
            ZImgLoaderListener.Options options = new ZImgLoaderListener.Options(bean.path);
//            options.isRadius = true;
//            options.radiusSize = ZDimen.dp2px(4);
            ZPicker.getInstance().getPictureLoader().load(mContext, options, mThumbView); //显示图片
        }

        /**
         * 刷新选中状态
         */
        public void refreshCheckBox(ZPictureBean bean) {
            mHotRegionCB.setVisibility(View.VISIBLE);
            mItemCB.setVisibility(View.VISIBLE);
            List<ZPictureBean> selectedList = ZPicker.getInstance().getSelectedPictures();
            boolean checked = selectedList.contains(bean);
            if (checked) {
                mItemCB.setChecked(true);
            } else {
                mItemCB.setChecked(false);
            }
        }
    }

    /**
     * 打开相机 Holder
     */
    private class CameraViewHolder extends ZHolder {

        private View mItemView;

        public CameraViewHolder(View itemView) {
            super(itemView);
            mItemView = itemView;
        }

        public void bindCamera() {
            mItemView.setTag(null);
        }
    }
}
