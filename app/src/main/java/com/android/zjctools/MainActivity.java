package com.android.zjctools;


import android.Manifest;

import com.android.zjctools.appupdate.ZAppUpdateBean;
import com.android.zjctools.base.ZBActivity;
import com.android.zjctools.bean.FunctionBean;
import com.android.zjctools.permission.ZPermission;
import com.android.zjctools.permission.ZPermissionBean;
import com.android.zjctools.utils.ZDimen;
import com.android.zjctools.utils.ZColor;
import com.android.zjctools.utils.ZLog;
import com.android.zjctools.utils.ZToast;
import com.android.zjctools.widget.ZItemDecoration;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

public class MainActivity extends ZBActivity {
    RecyclerView recycleView;
    FunctionBinder mBinder;
    Items items=new Items();
    MultiTypeAdapter  mAdapter=new MultiTypeAdapter();


    @Override
    protected int layoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void getValues() {
        super.getValues();
        items.clear();
        items.add(new FunctionBean("底部弹出window",0));
        items.add(new FunctionBean("九宫格",1));
        items.add(new FunctionBean("toast",2));
        items.add(new FunctionBean("选择图片",3));
        items.add(new FunctionBean("测试file",4));
        items.add(new FunctionBean("app更新",5));
        items.add(new FunctionBean("色块",6));
//        items.add(new FunctionBean("tabView",6));
    }

    @Override
    protected void initUI() {
        recycleView=findViewById(R.id.recycleView);
        LinearLayoutManager manager= new LinearLayoutManager(this);
        recycleView.setLayoutManager(manager);
      //  recycleView.addItemDecoration(ZItemDecoration.createVertical(mActivity, ZColor.byRes(R.color.app_divide), ZDimen.dp2px(1)));
        mBinder=new FunctionBinder(mActivity);
        mAdapter.register(FunctionBean.class,mBinder);
        mAdapter.setItems(items);
        recycleView.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {
//        getPermission();
    }

    @Override
    protected void initListener() {
        super.initListener();
        mBinder.setOnItemClickListener((action, item) -> {
            if(item.type==0){
                Router.goMainBottomWindow(mActivity);
            }else if(item.type==1){
                Router.goTestGlideCach(mActivity);
            }else if(item.type==2){
                Router.goToast(mActivity);
            }else if(item.type==3){
                getPermission();
//                Router.goSelectPictures(mActivity);
            }else if(item.type==4){
                Router.goFile(mActivity);
            }else if(item.type==5){
                ZAppUpdateBean appUpdateBean=new ZAppUpdateBean("https://ali-fir-pro-binary.imfir.cn/7685e3ad591dd5402593180f4caf007acb5cffae.apk?auth_key=1585550085-0-0-1d7aadef3968018e4d25dc3d758a9abf",null);
                appUpdateBean.appLogoIcon=R.drawable.ic_logo;
                appUpdateBean.isForce=true;
                List<String> contents=new ArrayList<>();
                contents.add("系统完善，优化用户体验");
                contents.add("系统完善，优化用户体验1");
                contents.add("系统完善，优化用户体验11");
                appUpdateBean.updateContents=contents;
                Router.goAppUpdate(mActivity,appUpdateBean);
            }else if(item.type==6){
                Router.goColorView(mActivity);
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
                ZToast.create().showNormal("onReject");
            }

            @Override
            public void onComplete() {
                ZToast.create().showNormal("onComplete");
                Router.goSelectPictures(mActivity);
            }
        });

//        List<ZPermissionBean> list = new ArrayList<>();
//        list.add(new ZPermissionBean(Manifest.permission.CAMERA,"访问相机", "拍摄照片需要访问相机，请允许我们获取访问相机权限，否则你将无法使用应用"));
//        list.add(new ZPermissionBean(Manifest.permission.SEND_SMS, "短信", "邀请通讯录好友，请允许我们获取访发送短信权限，否则你将无法邀请通讯录好友"));
//        list.add(new ZPermissionBean(Manifest.permission.WRITE_EXTERNAL_STORAGE, "读写手机存储", "发送和保存图片需要读写手机存储，请允许我们访问读写手机存储权限，否则你将无法使用应用"));
//        list.add(new ZPermissionBean(Manifest.permission.READ_CONTACTS,"通讯录", "邀请通讯录好友，请允许我们获取访问通讯录权限，否则你将无法邀请通讯录好友"));
//        list.add(new ZPermissionBean(Manifest.permission.ACCESS_FINE_LOCATION,"定位", "获取你的位置，和好友分享位置信息"));
//        ZPermission.getInstance(mActivity)
////                .setEnableDialog(false)
////                .setPermissionList(list)
////                .requestPermission(new ZPermission.PCallback() {
////                    @Override
////                    public void onReject() {}
////
////                    @Override
////                    public void onComplete() {}
////                });

//        ZPermissionBean bean=    new ZPermissionBean(Manifest.permission.ACCESS_FINE_LOCATION,"定位", "获取你的位置，和好友分享位置信息");
//        ZPermission.getInstance(mActivity)
//                .setPermission(bean)
//                .requestPermission(new ZPermission.PCallback() {
//                    @Override
//                    public void onReject() {}
//
//                    @Override
//                    public void onComplete() {}
//                });

    }

}
