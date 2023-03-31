package com.android.zjctools;


import android.Manifest;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aigestudio.wheelpicker.WheelPicker;
import com.android.zjctools.appupdate.ZAppVersionInfo;
import com.android.zjctools.appupdate.ZUpdateManager;
import com.android.zjctools.base.ZBActivity;
import com.android.zjctools.base.ZBItemDelegate;
import com.android.zjctools.bean.FunctionBean;
import com.android.zjctools.permission.ZPermission;
import com.android.zjctools.permission.ZPermissionBean;
import com.android.zjctools.utils.ZToast;
import com.android.zjctools.widget.ZAreaPicker;
import com.drakeet.multitype.MultiTypeAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;


public class MainActivity extends ZBActivity {
    RecyclerView recycleView;
    FunctionDelegate mBinder;
    List<FunctionBean>  items=new ArrayList<>();
    MultiTypeAdapter mAdapter=new MultiTypeAdapter();

    @Override
    public int layoutId() {
        return R.layout.activity_main;
    }


    public void getValues() {

        items.clear();
        items.add(new FunctionBean("底部弹出window",0));
        items.add(new FunctionBean("九宫格",1));
        items.add(new FunctionBean("toast",2));
        items.add(new FunctionBean("选择图片",3));
        items.add(new FunctionBean("测试file",4));
        items.add(new FunctionBean("app更新",5));
        items.add(new FunctionBean("色块",6));
        items.add(new FunctionBean("media_九宫格",7));
        items.add(new FunctionBean("性别",8));
        items.add(new FunctionBean("地区",9));
    }

    @Override
    public void initUI() {
        super.initUI();
        getValues();
        recycleView=findViewById(R.id.recycleView);
        LinearLayoutManager manager= new LinearLayoutManager(this);
        recycleView.setLayoutManager(manager);
        initDelegate();
        mAdapter.register(FunctionBean.class,mBinder);
        mAdapter.setItems(items);
        recycleView.setAdapter(mAdapter);
    }

    @Override
    public void setStatusBar(boolean isDarkTextStatusBar) {
        super.setStatusBar(true);
    }

    @Override
    public void initData() {
    }


    private void initDelegate() {
        mBinder=new FunctionDelegate(new ZBItemDelegate.BItemListener<FunctionBean>() {
            @Override
            public void onClick(@NotNull View itemView, FunctionBean item, int position) {
                if(item.type==0){
                    Router.goMainBottomWindow(mActivity);
                }else if(item.type==1){
                    Router.goTestGlideCach(mActivity);
                }else if(item.type==2){

                }else if(item.type==3){
                    getPermission();
//                Router.goSelectPictures(mActivity);
                }else if(item.type==4){
                    Router.goFile(mActivity);
                }else if(item.type==5){
                    ZAppVersionInfo appUpdateBean=new ZAppVersionInfo();
                    appUpdateBean.setApkUrl("https://cos.pgyer.com/d5b7e0983fe9243a463a404be5eaeaf7.apk?sign=cbc0a0c3b726ba0345fd10dfe1cb1eb4&t=1680226628&response-content-disposition=attachment%3Bfilename%3D%E8%BD%A6%E5%90%AF%E5%AD%A6%E8%8B%91_1.0.0.apk");
                    appUpdateBean.setAppLogoIcon(R.drawable.ic_logo);
                    List<String> contents=new ArrayList<>();
                    contents.add("系统完善，优化用户体验");
                    contents.add("系统完善，优化用户体验1");
                    contents.add("系统完善，优化用户体验11");
                    appUpdateBean.setUpdateContents(contents);
                    ZUpdateManager.INSTANCE.startAppUpdate(mActivity,appUpdateBean);

                }else if(item.type==6){
                    Router.goColorView(mActivity);
                }else if(item.type==7){
                    Router.goNineMedia(mActivity);
                }else if(item.type==8){
                    TextView  pickerTitleTV=findViewById(R.id.pickerTitleTV);
                    TextView  pickerCancelTV=findViewById(R.id.pickerCancelTV);
                    TextView pickerConfirmTV=findViewById(R.id.pickerConfirmTV);
                    LinearLayout pickerMaskLL=findViewById(R.id.pickerMaskLL);
                    WheelPicker pickerGenderView=findViewById(R.id.pickerGenderView);
                    List<String> str=new ArrayList<>();
                    str.add("男");
                    str.add("女");
                    str.add("保密");
                    pickerGenderView.setData(str);

                    pickerTitleTV.setText("选择性别");
                    pickerMaskLL.setVisibility(View.VISIBLE);
                    pickerGenderView.setVisibility(View.VISIBLE);
                    pickerCancelTV.setOnClickListener(v ->pickerMaskLL.setVisibility (View.GONE)   );
                    pickerMaskLL.setOnClickListener(v -> pickerMaskLL.setVisibility (View.GONE)  );
                    pickerConfirmTV.setOnClickListener(v -> Toast.makeText(mActivity, str.get(pickerGenderView.getCurrentItemPosition()), Toast.LENGTH_SHORT).show());
                }else if(item.type==9){
                    TextView  pickerTitleTV=findViewById(R.id.pickerTitleTV);
                    TextView  pickerCancelTV=findViewById(R.id.pickerCancelTV);
                    LinearLayout pickerMaskLL=findViewById(R.id.pickerMaskLL);
                    ZAreaPicker pickerAreaView=findViewById(R.id.pickerAreaView);
                    TextView pickerConfirmTV=findViewById(R.id.pickerConfirmTV);
                    pickerTitleTV.setText("选择地区");
                    pickerMaskLL.setVisibility(View.VISIBLE);
                    pickerAreaView.setVisibility(View.VISIBLE);
                    pickerCancelTV.setOnClickListener(v ->pickerMaskLL.setVisibility (View.GONE)   );
                    pickerMaskLL.setOnClickListener(v -> pickerMaskLL.setVisibility (View.GONE)  );
                    pickerConfirmTV.setOnClickListener(v -> Toast.makeText(mActivity,pickerAreaView.getProvince()+pickerAreaView.getCity()+pickerAreaView.getArea(), Toast.LENGTH_SHORT).show());
                }
            }
        });
    }

    private void  getPermission(){
        List<ZPermissionBean> listPermission=new ArrayList();
        listPermission.add(new ZPermissionBean(Manifest.permission.CAMERA));
        listPermission.add(new ZPermissionBean(Manifest.permission.WRITE_EXTERNAL_STORAGE));

        ZPermission.getInstance(this).
                setEnableAgain(true).
                setEnableRejectDialog(false).setEnableSettingDialog(true)
                .setPermissionList(listPermission).requestPermission(new ZPermission.PCallback() {
            @Override
            public void onReject() {
                ZToast.INSTANCE.showNormal("onReject");
            }

            @Override
            public void onComplete() {
                ZToast.INSTANCE.showNormal("onComplete");
                Router.goSelectPictures(mActivity);
            }
        });


    }

}
