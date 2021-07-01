package com.android.zjctools.tab;

import com.android.zjctools.R;
import com.android.zjctools.adapter.ZFragmentPagerAdapter;
import com.android.zjctools.base.ZBActivity;
import com.android.zjctools.widget.tabview.ZTabBean;
import com.android.zjctools.widget.tabview.ZTabItemView;
import com.android.zjctools.widget.tabview.ZTabView;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

public class TabviewActivity extends ZBActivity {

    ZTabView tabLayout;
    ViewPager viewPager;
    private List<Fragment> mFragmentList;
    ZFragmentPagerAdapter mAdapter;
    private List<ZTabItemView> mCustomTab = new ArrayList<>();
    @Override
    protected int layoutId() {
        return R.layout.activity_tabview;
    }

    @Override
    protected void initUI() {
        viewPager=findViewById(R.id.main_view_pager);
//        tabLayout=findViewById(R.id.tabs);
    }

    @Override
    protected void initData() {
        mFragmentList=new ArrayList<>();
        mFragmentList.add(new TabFragment());
        mFragmentList.add(new TabFragment());
        mFragmentList.add(new TabFragment());
        mAdapter = new ZFragmentPagerAdapter(getSupportFragmentManager(), mFragmentList);
        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(mAdapter);
//        tabLayout.setupWithViewPager(viewPager);
        List<ZTabBean>tabBeans=new ArrayList<>();
        tabBeans.add(new ZTabBean(R.drawable.ic_friend_select,R.drawable.ic_friend_normal,"朋友"));
        tabBeans.add(new ZTabBean(R.drawable.ic_msg_select,R.drawable.ic_msg_normal,"消息"));
        tabBeans.add(new ZTabBean(R.drawable.ic_menu_select,R.drawable.ic_menu_normal,"我的"));
        //tabLayout.setTabBeans(tabBeans);
//        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                mCustomTab.get(tab.getPosition()).setSelect(true);
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//                mCustomTab.get(tab.getPosition()).setSelect(false);
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });
//        for (int i = 0; i < tabLayout.getTabCount(); i++) {
//            TabLayout.Tab tab = tabLayout.getTabAt(i);
//            if (tab != null) {
//                tab.setCustomView(createTab(i));
//            }
//        }
    }


//    private  View createTab(int index){
//        ZTabItemView zTabItemView=new ZTabItemView(this);
//        zTabItemView.setSImageSize(25,25);
//        zTabItemView.setTabData(R.drawable.ic_friend_select,R.drawable.ic_friend_normal,R.color.z_blue_5c,R.color.zjcBlack,"我是"+index);
//        if(index==0){
//            zTabItemView.setSelect(true);
//        }
//        zTabItemView.setPointSize(2,12,12);
//        zTabItemView.setSpace(4,2,0);
//        mCustomTab.add(zTabItemView);
//        return  zTabItemView;
//    }
}
