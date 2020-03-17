package com.android.zjctools.widget.tabview;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.design.widget.TabLayout;
import android.util.AttributeSet;
import android.view.View;

import com.android.zjctools.utils.ZColor;
import com.android.zjctools.utils.ZDimen;
import com.android.zjcutils.R;

import java.util.ArrayList;
import java.util.List;

public class ZTabView extends TabLayout {


    private List<ZTabBean>mTabBeans=new ArrayList<>();
    private List<ZTabItemView> mCustomTab = new ArrayList<>();
    private int textSize, pointSize;
    private int iconMarginTop,textMarginTop,textMarginBottom;
    private int pointMarginLeft,pointMarginTop;
    private int selectColorId, unSelectColorId;
    private int iconWidth,iconHeight,pointWidth,pointHeight;
    boolean isWrapSize; //icon 自适应大小


    public ZTabView(Context context) {
        super(context);
    }

    public ZTabView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ZTabView);
        textSize= (int) typedArray.getDimension(R.styleable.ZTabView_zjc_tab_text_size, ZDimen.sp2px(12));
        pointSize=typedArray.getDimensionPixelSize(R.styleable.ZTabView_zjc_tab_point_text_size, ZDimen.sp2px(8));
        iconMarginTop=typedArray.getDimensionPixelSize(R.styleable.ZTabView_zjc_tab_image_top, 5);
        textMarginTop=typedArray.getDimensionPixelSize(R.styleable.ZTabView_zjc_tab_text_top, 3);
        textMarginBottom=typedArray.getDimensionPixelSize(R.styleable.ZTabView_zjc_tab_text_bottom,3);
        pointMarginLeft=typedArray.getDimensionPixelSize(R.styleable.ZTabView_zjc_tab_point_left,5);
        pointMarginTop=typedArray.getDimensionPixelSize(R.styleable.ZTabView_zjc_tab_point_top,2);
        selectColorId=typedArray.getResourceId(R.styleable.ZTabView_zjc_tab_select_color, R.color.zjcBlue);
        unSelectColorId=typedArray.getResourceId(R.styleable.ZTabView_zjc_tab_unSelect_color, R.color.zjc_Gray54);
        isWrapSize=typedArray.getBoolean(R.styleable.ZTabView_zjc_tab_icon_wrap,true);
        iconWidth=typedArray.getDimensionPixelSize(R.styleable.ZTabView_zjc_tab_icon_width, ZDimen.dp2px(25));
        iconHeight=typedArray.getDimensionPixelSize(R.styleable.ZTabView_zjc_tab_icon_height, ZDimen.dp2px(25));
        pointWidth=typedArray.getDimensionPixelSize(R.styleable.ZTabView_zjc_tab_point_width, ZDimen.dp2px(10));
        pointHeight=typedArray.getDimensionPixelSize(R.styleable.ZTabView_zjc_tab_point_height, ZDimen.dp2px(10));
        typedArray.recycle();
    }


    public void setTabBeans(List<ZTabBean>tabBeans){
        mTabBeans.clear();
        mTabBeans.addAll(tabBeans);
        addListener();
    }


    private  void addListener(){
        addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mCustomTab.get(tab.getPosition()).setSelect(true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                mCustomTab.get(tab.getPosition()).setSelect(false);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        for (int i = 0; i < getTabCount(); i++) {
            TabLayout.Tab tab = getTabAt(i);
            if (tab != null) {
                tab.setCustomView(createTab(i));
            }
        }
    }

    private View createTab(int index){
        ZTabItemView zTabItemView=new ZTabItemView(getContext());
        if(!isWrapSize){//icon 大小非自适应
            zTabItemView.setSImageSize(iconWidth,iconHeight);
        }
        zTabItemView.setTabData(mTabBeans.get(index),selectColorId,unSelectColorId);
        zTabItemView.setTextSize(textSize);
        if(index==0){
            zTabItemView.setSelect(true);
        }
        zTabItemView.setPointSize(pointWidth,pointHeight,pointSize);
        zTabItemView.setPointSpace(pointMarginLeft,pointMarginTop);
        zTabItemView.setViewsSpace(iconMarginTop,textMarginTop,textMarginBottom);
        mCustomTab.add(zTabItemView);
        return  zTabItemView;
    }

    public  void  setPointCounts(int index,int counts){
        if(mCustomTab.size()>index){
            ZTabItemView zTabItemView=  mCustomTab.get(index);
            zTabItemView.setPointCount(counts);
        }
    }

    public  void  setPointVisible(int index,boolean isShow){
        if(mCustomTab.size()>index){
            ZTabItemView zTabItemView=  mCustomTab.get(index);
            zTabItemView.setPointVisible(isShow);
        }

    }
}
