package com.android.zjctools.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.android.zjctools.utils.ZjcLog;

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
        ZjcLog.v("%s onRestart", className);
    }

    @Override
    protected void onStart() {
        super.onStart();
        ZjcLog.v("%s onStart", className);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ZjcLog.v("%s onResume", className);
    }

    @Override
    protected void onPause() {
        super.onPause();
        ZjcLog.v("%s onPause", className);
    }

    @Override
    protected void onStop() {
        super.onStop();
        ZjcLog.v("%s onStop", className);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ZjcLog.v("%s onDestroy", className);
        ZjcApp.removeActivity(mActivity);
        mActivity = null;
    }

    /**
     * 自定义 Activity 结束方法
     */
    protected void onFinish() {
        ZjcLog.v("%s onFinish", className);
        finish();
    }
}