package com.android.zjctools.file;

import android.widget.TextView;

import com.android.zjctools.R;
import com.android.zjctools.base.ZBActivity;
import com.android.zjctools.interface_function.ZCallback;
import com.android.zjctools.utils.ZFile;
import com.android.zjctools.utils.ZLog;
import com.android.zjctools.utils.ZToast;

import java.io.File;

public class TestFileActivity extends ZBActivity {
    TextView tvDownLoad;
    @Override
    protected int layoutId() {
        return R.layout.activity_test_file;
    }

    @Override
    protected void initUI() {
        tvDownLoad=findViewById(R.id.tvDownLoad);
    }

    @Override
    protected void initData() {
        String fileUrl="http://cc.cocimg.com/api/uploads/image/20200603/1591174646688470.jpg";
        tvDownLoad.setOnClickListener(v -> {
            ZFile.downloadFileByNetWork(fileUrl, "zjcTools", ".jpg", new ZCallback<File>() {
                @Override
                public void onSuccess(File file) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ZLog.e("下载成功");
                            ZToast.create().showSuccessBottom("下载成功："+file.getAbsolutePath());
                        }
                    });

                }

                @Override
                public void onError(int code, String desc) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ZToast.create().showErrorBottom(desc);
                        }
                    });
                }

                @Override
                public void onProgress(int progress, String desc) {
                    ZLog.e(desc+":"+progress);
                }
            });
        });
    }
}
