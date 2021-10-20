package com.android.zjctools.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.zjctools.utils.ZLog;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


/**
 * Created by zjun on 2019/7/6.
 * Fragment的基类，进行简单的封装，ViewPager 结合 Fragment 实现数据懒加载
 */
public abstract class ZFragment extends Fragment {

    protected String className = this.getClass().getSimpleName();

    protected Context mContext;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ZLog.v("onAttach: %s", className);
        mContext = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ZLog.v("onCreate: %s", className);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ZLog.v("onCreateView: %s", className);
        View view = inflater.inflate(layoutId(), container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ZLog.v("onViewCreated: %s", className);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ZLog.v("onActivityCreated: %s", className);

        init();
    }

    @Override
    public void onStart() {
        super.onStart();
        ZLog.v("onStart: %s", className);
    }

    @Override
    public void onResume() {
        super.onResume();
        ZLog.v("onResume: %s", className);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ZLog.v("onActivityResult: %s", className);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        ZLog.v("onHiddenChanged: %s", className);
    }

    @Override
    public void onPause() {
        super.onPause();
        ZLog.v("onPause: %s", className);
    }

    @Override
    public void onStop() {
        super.onStop();
        ZLog.v("onStop: %s", className);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ZLog.v("onDestroyView: %s", className);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        ZLog.v("onDetach: %s", className);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ZLog.v("onDestroy: %s", className);
    }


    /**
     * 加载 Fragment 界面布局
     *
     * @return 返回布局 id
     */
    protected abstract int layoutId();

    /**
     * 初始化 Fragment 界面
     */
    protected abstract void init();

    /**
     * 定义 Fragmnet 回调接口，方便 Activity 与 Fragment 通讯
     */
    public interface FragmentListener {
        void onAction(int action, Object obj);
    }
}
