package com.android.zjctools.base;

import android.os.Bundle;


import com.android.zjctools.utils.ZLog;

import androidx.appcompat.app.AppCompatActivity;

public class ZjcActivity extends AppCompatActivity {

    protected String className = this.getClass().getSimpleName();

    protected ZjcActivity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        ZjcApp.putActivity(mActivity);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        ZLog.v("%s onRestart", className);
    }

    @Override
    protected void onStart() {
        super.onStart();
        ZLog.v("%s onStart", className);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ZLog.v("%s onResume", className);
    }

    @Override
    protected void onPause() {
        super.onPause();
        ZLog.v("%s onPause", className);
    }

    @Override
    protected void onStop() {
        super.onStop();
        ZLog.v("%s onStop", className);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ZLog.v("%s onDestroy", className);
        ZjcApp.removeActivity(mActivity);
        mActivity = null;
    }

    /**
     * 自定义 Activity 结束方法
     */
    protected void onFinish() {
        ZLog.v("%s onFinish", className);
        finish();
    }
}