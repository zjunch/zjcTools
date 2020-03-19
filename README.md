# zjcTools常用工具库，
包含用基类，activity,fragment,app
工具类：toast,bitmap，file,date，router color string.log..
app更新，图片选择，权限申请
简单view，设置类型view（ZSettingView）
九宫格图片（ZNinePicturesView），
点赞头像堆叠（ZPileLayout），
recycleview分割线（ZItemDecoration）,
自定义tablayou(ZTabview) 
..........

引用方式：


一  、项目gradle ：allprojects {
    repositories {
        maven { url 'https://dl.bintray.com/zjun/maven' }
    }
}

二 、module: implementation 'com.zjun:zjcTools:0.1.1'


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
    
    参照 app里面  SelectActivity,涵盖phptoview 预览缩放，点击返回
    

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
   
  10.ZTabView (首页 tab切换) 
     xml 布局： 可以设置的属性如下 declare-styleable
     <com.android.zjctools.widget.tabview.ZTabView
        android:id="@+id/main_tab_layout"
        android:padding="@dimen/zjc_dimen_0"
        android:layout_width="match_parent"
        android:layout_height="55dp"
        android:background="@color/zjcWhite"
        android:layout_alignParentBottom="true"
        app:zjc_tab_icon_wrap="false"
        app:zjc_tab_text_size="@dimen/zjc_size_12"
        app:tabIndicatorHeight="0dp" />

    <android.support.v4.view.ViewPager
        android:background="@color/zjc_white_87"
        android:id="@+id/main_view_pager"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
       android:layout_above="@id/main_tab_layout"
        android:overScrollMode="never" />
        
        属性说明
    <declare-styleable name="ZTabView">
        <!--菜单icon 宽高自适应-->
        <attr name="zjc_tab_icon_wrap" format="boolean" />
        <!--菜单文字大小-->
        <attr name="zjc_tab_text_size" format="dimension" />
        <!--菜单icon margin top-->
        <attr name="zjc_tab_image_top" format="dimension" />
        <!--菜单文字 margin top-->
        <attr name="zjc_tab_text_top" format="dimension" />
        <attr name="zjc_tab_text_bottom" format="dimension" />
        <!--icon 宽高-->
        <attr name="zjc_tab_icon_width" format="dimension" />
        <attr name="zjc_tab_icon_height" format="dimension" />
        <!--选中颜色-->
        <attr name="zjc_tab_select_color" format="color" />
        <!--未选中颜色-->
        <attr name="zjc_tab_unSelect_color" format="color" />
        <!--菜单小红点 margin top-->
        <attr name="zjc_tab_point_top" format="dimension" />
        <!--菜单小红点 margin left-->
        <attr name="zjc_tab_point_left" format="dimension" />
        <!--菜单小红点 宽度-->
        <attr name="zjc_tab_point_width" format="dimension" />
        <!--菜单小红点 高度-->
        <attr name="zjc_tab_point_height" format="dimension" />
        <!--菜单字体大小 高度-->
        <attr name="zjc_tab_point_text_size" format="dimension" />
    </declare-styleable>

    使用方式
     mAdapter = new ZFragmentPagerAdapter(getSupportFragmentManager(), mFragmentList);
        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(mAdapter);
        tabLayout.setupWithViewPager(viewPager);
        List<ZTabBean>tabBeans=new ArrayList<>();
        tabBeans.add(new ZTabBean(R.drawable.ic_friend_select,R.drawable.ic_friend_normal,"朋友"));
        tabBeans.add(new ZTabBean(R.drawable.ic_msg_select,R.drawable.ic_msg_normal,"消息"));
        tabBeans.add(new ZTabBean(R.drawable.ic_menu_select,R.drawable.ic_menu_normal,"我的"));
        tabLayout.setTabBeans(tabBeans);



