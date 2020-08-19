package com.android.zjctools.widget.dialog;
import android.app.Activity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.android.zjctools.base.AppItemBinder;
import com.android.zjctools.utils.ZColor;
import com.android.zjctools.utils.ZDimen;
import com.android.zjcutils.R;
import java.util.List;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;


/**
 * created zjun 2019-12-23
 * 弹出框处理类
 */
public class ZPWindow {

    private Activity mActivity;
    private PopupWindow mPWindow;

    private View mView;
    private TextView mTitleTV;

    private MultiTypeAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private Items mItems = new Items();
    private OnWindowListener mListener;

    private Button mCancelBtn;
    AppItemBinder mAppItemBinder;


    public ZPWindow(Activity activity, List<String> list) {
        this(activity,list,R.layout.zjc_widget_window_item);
    }



    public ZPWindow(Activity activity, List<String> list,int itemLayoutId){
        this(activity,list,new ZWindowItemBinder(itemLayoutId));
    }

    public ZPWindow(Activity activity, List<String> list,AppItemBinder appItemBinder) {
        if(activity==null||activity.isFinishing()){
            return;
        }
        mActivity = activity;
        mItems.addAll(list);
        mAppItemBinder=appItemBinder;
        mView = LayoutInflater.from(mActivity).inflate(R.layout.zjc_widget_window_layout, null);
        mTitleTV = mView.findViewById(R.id.window_title_tv);
        mRecyclerView = mView.findViewById(R.id.window_recycler_view);
        mCancelBtn = mView.findViewById(R.id.window_cancel_btn);

        initView();
        initWindow();
    }



    /**
     * 初始化 View 内容
     */
    private void initView() {
        mAdapter = new MultiTypeAdapter();
        mAdapter.register(String.class, mAppItemBinder);
        mAdapter.setItems(mItems);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity, RecyclerView.VERTICAL, false));
        mRecyclerView.setAdapter(mAdapter);
        mAppItemBinder.setOnItemClickListener((action, item) -> {
            if (mListener != null) {
                mListener.onItemClick(mItems.indexOf(item));
            }
            dismiss();
        });
    }

    /**
     * 初始化对话框
     */
    private void initWindow() {
        if (mPWindow == null) {
            mPWindow = new PopupWindow(mActivity);
            mPWindow.setContentView(mView);
            mPWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
            mPWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);

            //设置获取焦点
            mPWindow.setFocusable(true);
            //设置可触摸
            mPWindow.setTouchable(true);
            //设置外部可以点击
            mPWindow.setOutsideTouchable(true);
            //设置空背景，必须加上，可以让外部点击事件被触发
            mPWindow.setBackgroundDrawable(null);

            mView.setOnClickListener(v -> dismiss());
            mCancelBtn.setOnClickListener(v -> dismiss());
        }
    }

    /**
     * 设置对话框标题
     */
    public void setTitle(String title) {
        mTitleTV.setText(title);
        mTitleTV.setVisibility(View.VISIBLE);
    }

    /**
     * 设置对话框提示信息
     *
     * @param messages 提示信息
     */
    public void setMessage(List<String> messages) {
        mItems.addAll(messages);
        mAdapter.notifyDataSetChanged();
    }


    public void setCancelEnable(boolean enable) {
        mCancelBtn.setVisibility(enable?View.VISIBLE:View.GONE);
    }

    public void setCancelColor(int  colorId) {
        mCancelBtn.setTextColor(ZColor.byRes(colorId));
    }

    public void setCancelText(int  colorId,int size) {
        mCancelBtn.setTextSize(ZDimen.sp2px(size));
        mCancelBtn.setTextColor(ZColor.byRes(colorId));
    }

    public Button getTextCancel() {
        return mCancelBtn;
    }


    /**
     * 设置弹窗点击监听
     */
    public void setWindowListener(OnWindowListener listener) {
        mListener = listener;
    }

    public void setCancelClick(View.OnClickListener listener) {
        mCancelBtn.setVisibility(View.VISIBLE);
        mCancelBtn.setOnClickListener(listener);
    }

    public void setCancelClick(int id, View.OnClickListener listener) {
        mCancelBtn.setVisibility(View.VISIBLE);
        mCancelBtn.setText(id);
        mCancelBtn.setOnClickListener(listener);
    }

    public void show() {
        if (mPWindow != null && !mPWindow.isShowing()) {
            mPWindow.showAtLocation(mActivity.getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
        }
    }

    public void dismiss() {
        if (mPWindow != null && mPWindow.isShowing()) {
            mPWindow.dismiss();
            mPWindow = null;
        }
    }

    /**
     * 定义弹窗点击监听
     */
    public interface OnWindowListener {
        void onItemClick(int position);
    }
}
