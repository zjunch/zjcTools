# zjcTools常用工具库，
包含用基类，activity,fragment,app
toast,bitmap，file,date，router
图片选择，权限申请

简单view，设置类型view（ZSettingView），九宫格图片（ZNinePicturesView），点赞头像堆叠（ZPileLayout），recycleview分割线（ZItemDecoration）等


引用方式：


一  、项目gradle ：allprojects {
    repositories {
        maven { url 'https://dl.bintray.com/zjun/maven' }
    }
}

二 、module: implementation 'com.zjun:zjcTools:0.0.7'


三 、初始化工具  ZTools.init(this);


简单的使用说明：


1.沉浸式使用： 已经在基类ZBActivity 实现，只需继承即可

2.动态权限申请：

    //单个权限
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


    //多个权限
    
    //        list.add(new ZPermissionBean(Manifest.permission.READ_CONTACTS,"通讯录", "邀请通讯录好友，请允许我们获取访问通讯录权限，否则你     //         将无法邀请通讯录好友"));
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
    
    3.图片选择

    //选择图片   selecteds 为已选图片
     IMManager.showMultiPicker(mActivity, 9, selecteds);   
      
      
     //选择结果返回
       @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK || data == null) {
            return;
        }
        if (requestCode == ZConstant.ZJC_PICK_REQUEST_CODE) {//选择图片
            selecteds.clear();
            List<ZPictureBean> result = ZPicker.getInstance().getResultData();
            selecteds.addAll(result);
        }
    }

    4.常用toast    
       ZToast.create().showNormal("正常的");
       ZToast.create().showCenter("中间的");
       ZToast.create().showSuccessBottom("底部成功");
       ZToast.create().showSuccessCenter("中间成功");
       ZToast.create().showSuccessCenter("中间成功");
       ZToast.create().showErrorBottom("底部失败");
       ZToast.create().showErrorCenter("中间失败");
       
    5.九宫格图片ZNinePicturesView
     布局中使用实例
    <com.android.zjctools.widget.nineimages.ZNinePicturesView
            android:id="@+id/ninePics"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            app:zjc_nine_full_One="true"
            app:zjc_nine_max_counts="9"
            app:zjc_nine_average2_enable="true"
            app:zjc_nine_show_un_counts="true"
            app:zjc_nine_spacex="4"
            app:zjc_nine_spacey="4"/>
        
       布局中使用实例
     <declare-styleable name="ZNinePicturesView">
        <attr name="zjc_nine_spacex" format="integer"/><!--x 间距-->
        <attr name="zjc_nine_spacey" format="integer"/><!--y 间距-->
        <attr name="zjc_nine_average2_enable" format="boolean"/><!--两张图张图一行两个-->
        <attr name="zjc_nine_average4_enable" format="boolean"/><!--四张图一行两个-->
        <attr name="zjc_nine_12_Enable" format="boolean"/><!--三张图 左边一个右边两个-->
        <attr name="zjc_nine_max_counts" format="integer"/><!--最多显示多少张-->
        <attr name="zjc_nine_full_One" format="boolean"/><!--一张图的时候，一行一张- 默认16/9->
        <attr name="zjc_nine_show_un_counts" format="boolean"/><!--是否有未展示的图片个数的文字显示-->
        <attr name="zjc_nine_un_counts_textColor" format="color" /><!--未读图片个数的文字颜色-->
        <attr name="zjc_nine_un_counts_textSzie" format="integer"/><!--未读图片个数的文字大小-->
    </declare-styleable>
    
  6.点赞头衔堆叠ZPileLayout
      
      布局使用
      <com.android.zjctools.widget.PileLayout
      android:id="@+id/apply_pile_layout"
      android:layout_width="wrap_content"
      android:layout_height="wrap_content"
      android:layout_centerVertical="true"
      app:zjc_pileLayout_pileWidth="16dp" />   此处16dp是重叠宽度
 
      添加需要堆叠的view
      zileLayout.addView(avtatView);

