package com.android.zjctools.pick.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.zjctools.pick.ZImgLoaderListener;
import com.android.zjctools.pick.ZPicker;
import com.android.zjctools.pick.bean.ZFolderBean;
import com.android.zjctools.utils.ZDimen;
import com.android.zjcutils.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by zjun on 2019/05/16 21:51
 *
 * 文件夹列表适配器
 */
public class ZFolderAdapter extends BaseAdapter {

    private Activity mActivity;
    private LayoutInflater mInflater;
    private List<ZFolderBean> mFolderBeans;
    private int lastSelected = 0;

    public ZFolderAdapter(Activity activity, List<ZFolderBean> folders) {
        mActivity = activity;
        if (folders != null && folders.size() > 0) {
            mFolderBeans = folders;
        } else {
            mFolderBeans = new ArrayList<>();
        }

        mInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void refreshData(List<ZFolderBean> folders) {
        if (folders != null && folders.size() > 0) {
            mFolderBeans = folders;
        } else {
            mFolderBeans.clear();
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mFolderBeans.size();
    }

    @Override
    public ZFolderBean getItem(int position) {
        return mFolderBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.zjc_pick_folder_list_item, parent, false);
            holder = new ViewHolder(convertView);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ZFolderBean folder = getItem(position);
        holder.folderName.setText(folder.name);
        holder.imageCount.setText(mActivity.getString(R.string.zjc_pick_folder_picture_count, folder.pictures.size()));

        ZImgLoaderListener.Options options = new ZImgLoaderListener.Options(folder.cover.path);
        options.isRadius = true;
        options.radiusSize = ZDimen.dp2px(4);
        ZPicker.getInstance().getPictureLoader().load(mActivity, options, holder.cover); //显示图片

        if (lastSelected == position) {
            holder.mSelectView.setSelected(true);
        } else {
            holder.mSelectView.setSelected(false);
        }

        return convertView;
    }

    public void setSelectIndex(int i) {
        if (lastSelected == i) {
            return;
        }
        lastSelected = i;
        notifyDataSetChanged();
    }

    public int getSelectIndex() {
        return lastSelected;
    }

    private class ViewHolder {
        ImageView cover;
        TextView folderName;
        TextView imageCount;
        ImageView mSelectView;

        public ViewHolder(View view) {
            cover = view.findViewById(R.id.zjc_pick_folder_cover_iv);
            folderName = view.findViewById(R.id.zjc_pick_folder_name_tv);
            imageCount = view.findViewById(R.id.zjc_pick_folder_count_tv);
            mSelectView = view.findViewById(R.id.zjc_pick_folder_select_iv);
            view.setTag(this);
        }
    }
}
