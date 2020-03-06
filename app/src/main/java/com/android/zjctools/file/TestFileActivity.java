package com.android.zjctools.file;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.zjctools.R;
import com.android.zjctools.base.ZBActivity;
import com.android.zjctools.interface_function.ZCallback;
import com.android.zjctools.utils.ZFile;
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
        String fileUrl="https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=1208538952,1443328523&fm=26&gp=0.jpg";
        tvDownLoad.setOnClickListener(v -> {
            ZFile.downloadFileByNetWork(fileUrl, "zjcTools", ".jpg", new ZCallback<File>() {
                @Override
                public void onSuccess(File file) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
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

                }
            });
        });
    }
}
