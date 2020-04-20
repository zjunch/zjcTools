# zjcTools常用工具库，

最新  com.zjun:zjcTools:0.1.4 

com.zjun:zjcTools:0.1.2     0.1.4 已适配androidX

com.zjun:zjcTools:0.1.1     0.1.3 未适配androidX



包含用基类，activity,fragment,app
工具类：toast,bitmap，file,date，router color string.log..
app更新，图片选择，权限申请
简单view，设置类型view（ZSettingView）
九宫格图片（ZNinePicturesView），
点赞头像堆叠（ZPileLayout），
recycleview分割线（ZItemDecoration）,

引用方式：


一  、项目gradle ：allprojects {
    repositories {
        maven { url 'https://dl.bintray.com/zjun/maven' }
    }
}

二 、module: implementation 'com.zjun:zjcTools:0.1.2'


三 、初始化工具  ZTools.init(this);


东西很简单，都是日常自己常用的东西，列下简单使用说明：防止吃瓜的时候忘了怎么用


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
    
     多选：
     List<ZPictureBean> selecteds = new ArrayList<>();  //selecteds 已经选择过的图片
     ZIMManager.showMultiPicker(mActivity, 9, selecteds);
     
     单选：
     ZIMManager.showSinglePicker(mActivity);
     
     
      @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK || data == null) {
            return;
        }
        if (requestCode == ZConstant.ZJC_PICK_REQUEST_CODE) {//返回图片 集合
            List<ZPictureBean> result = ZPicker.getInstance().getResultData();
        }
    }
    
    //此处为图片压缩，已经做了避免重复压缩
     ZCompressUtils zCompressUtils=new ZCompressUtils();
            zCompressUtils.compressPictures(selecteds, new ZCompressUtils.CompressImageListener() {
                @Override
                public void onComplete() {
                    ZToast.create().showSuccessBottom("压缩完成");
                }

                @Override
                public void onError(String errorMsg) {
                    ZToast.create().showErrorBottom(errorMsg);
                }
            });
    
    //图片预览 photoView 缩放
       ZParams params = new ZParams();
        params.what = position;   //第一个展示得位置
        params.strList = pictureList; //图片的地址集合
        Intent intent = new Intent(context, ZDisplayMultiActivity.class);
        putParams(intent, params);
        context.startActivity(intent);


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
      
 7.ZsettingView(类似于设置菜单,左侧是菜单名字，有侧是描述或者选择箭头)
 
     xml 布局如下
       <com.android.zjctools.widget.ZSettingView
                android:layout_marginTop="10dp"
                android:background="@color/white"
                android:id="@+id/svCompany"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:paddingLeft="12dp"
                android:paddingRight="12dp"
                app:zjc_sv_desc_Hint="请输入"    
                app:zjc_sv_Line_enable="true"
                app:zjc_sv_title_text="单位名称" />   

  8.ZSettingInputView(类似于ZsettingView,左侧是菜单名字，右侧是输入框)
    
          <!--是否显示底部分隔线-->
        <attr name="zjc_siv_Line_enable" format="boolean" />
        <!--标题-->
        <attr name="zjc_siv_title_text" format="string" />
        <!--底部分隔线颜色-->
        <attr name="zjc_siv_line_color" format="color" />
        <!--右侧输入框hint-->
        <attr name="zjc_siv_hint_text" format="string" />
        <!--右侧输入框最多字符-->
        <attr name="zjc_siv_max_counts" format="integer" />
        
   9.ZSearchView（搜索框）
   
         <declare-styleable name="ZSearchView">
        <!--是否显示清除-->
        <attr name="zjc_srv_clear_enable" format="boolean" />
        <!--输入框search-->
        <attr name="zjc_srv_search_hint" format="string" />
        <!--是否有输入功能-->
        <attr name="zjc_srv_search_input_enable" format="boolean" />

        <!--是否显示右侧搜索文字-->
        <attr name="zjc_srv_right_tv_search_enable" format="boolean" />

        <!--右侧搜索icon-->
        <attr name="zjc_srv_right_iv_search_icon" format="reference" />
        <!--是否显示右侧搜索文字-->
        <attr name="zjc_srv_right_iv_search_enable" format="boolean" />
        <!--左侧侧搜索icon-->
        <attr name="zjc_srv_left_iv_search_icon" format="reference" />

      </declare-styleable>
   



