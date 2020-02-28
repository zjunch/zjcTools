package com.android.zjctools;
import android.view.View;
import com.android.zjctools.base.ZBActivity;
import com.android.zjctools.permission.ZPermission;
import com.android.zjctools.utils.ZjcLog;

public class MainActivity extends ZBActivity {

    @Override
    protected int layoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initUI() {
    }

    @Override
    protected void initData() {
        getPermission();
    }


    private void  getPermission(){
        ZPermission.getInstance(this).requestCamera(new ZPermission.PCallback() {
            @Override
            public void onReject() {
                ZjcLog.e("onReject");
            }

            @Override
            public void onComplete() {
                ZjcLog.e("onComplete");

            }
        });
        findViewById(R.id.tvSave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Router.goMain2(mActivity);
            }
        });
    }
}
