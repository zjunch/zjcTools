package com.android.zjctools.base;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.drakeet.multitype.ItemViewBinder;

/**
 * Create by lzan13 on 2020-01-05 19:18
 *
 * 封装多类型适配器
 */
public abstract class AppItemBinder<T> extends ItemViewBinder<T, AppItemBinder.AppHolder> {

    // Item 默认点击动作
    public static final int ACTION_NORMAL = 0;
    // 带删除的按钮的点击动作
    public static final int ACTION_DELETE = 1;

    protected Context mContext;

    protected OnItemClickListener<T> mOnItemClickListener;
    protected OnItemLongClickListener<T> mOnItemLongClickListener;

    public AppItemBinder() {
    }

    public AppItemBinder(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    protected AppHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View view = inflater.inflate(loadItemLayoutId(), parent, false);
        return new AppHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull AppHolder holder, @NonNull T item) {
        holder.itemView.setOnClickListener(v -> {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onClick(ACTION_NORMAL, item);
            }
        });
        holder.itemView.setOnLongClickListener(v -> {
            if (mOnItemLongClickListener != null) {
                return mOnItemLongClickListener.onLongClick(item);
            }
            return false;
        });

        onBindView(holder, item);
    }

    /**
     * 获取 Item 布局 uid
     */
    protected abstract int loadItemLayoutId();

    /**
     * 绑定定数
     */
    protected abstract void onBindView(AppHolder holder, T item);

    /**
     * 通用绑定数据类型
     */
    public static class AppItem {
        public int type;

        public AppItem() {
        }

        public AppItem(int type) {
            this.type = type;
        }
    }

    /**
     * ----------------------------------
     * 创建通用的 ViewHolder
     */
    public static class AppHolder extends RecyclerView.ViewHolder {
        private View mItemView;
        private SparseArray<View> mViews = new SparseArray<>();   // 1

        public AppHolder(@NonNull View itemView) {
            super(itemView);
            mItemView = itemView;
        }

        //返回根View
        public View getView() {
            return mItemView;
        }

        /**
         * 根据 View 的 uid 来返回 View 实例
         */
        public <T extends View> T getView(@IdRes int ResId) {
            View view = mViews.get(ResId);
            if (view == null) {
                view = mItemView.findViewById(ResId);
                mViews.put(ResId, view);
            }
            return (T) view;
        }
    }

    /**
     * -------------------------------------------------------------------
     * 点击回调
     */
    /**
     * 设置 Item 点击监听
     */
    public void setOnItemClickListener(OnItemClickListener<T> listener) {
        mOnItemClickListener = listener;
    }

    /**
     * 设置 Item 长按监听
     */
    public void setOnItemLongClickListener(OnItemLongClickListener<T> listener) {
        mOnItemLongClickListener = listener;
    }

    /**
     * 定义 Item 点击监听
     *
     * @param <T>
     */
    public interface OnItemClickListener<T> {
        /**
         * Item 点击事件
         *
         * @param action 动作
         * @param item   实体对象
         */
        void onClick(int action, T item);
    }

    /**
     * 定义 Item 长按监听
     *
     * @param <T>
     */
    public interface OnItemLongClickListener<T> {
        boolean onLongClick(T item);
    }
}
