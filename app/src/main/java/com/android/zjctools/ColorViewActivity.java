package com.android.zjctools;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;
import com.android.zjctools.base.ZBActivity;
import com.android.zjctools.widget.colorviews.ZColorInfo;
import com.android.zjctools.tab.ColorViewBinder;
import com.android.zjctools.utils.ZColor;
import com.android.zjctools.utils.ZDimen;
import com.android.zjctools.widget.ZItemDecoration;
import com.android.zjctools.widget.colorviews.ZColorBean;

import java.util.ArrayList;
import java.util.List;

public class ColorViewActivity extends ZBActivity {
    RecyclerView recycleView;

    Items items=new Items();
    MultiTypeAdapter mAdapter=new MultiTypeAdapter();
    @Override
    protected int layoutId() {
        return R.layout.activity_color_view;
    }

    @Override
    protected void initUI() {
        recycleView=findViewById(R.id.recycleView);
    }

    private List<ZColorBean> getColorBeans(int pos){
        List<ZColorBean> ZColorBeans =new ArrayList<>();
        if(pos%2==0){
            ZColorBeans.add(new ZColorBean(10,12,R.color.z_red_87));
            ZColorBeans.add(new ZColorBean(13,14,R.color.z_red_87));
            ZColorBeans.add(new ZColorBean(17,21,R.color.z_red_87));
        }else  if(pos%3==0){
            ZColorBeans.add(new ZColorBean(9,10,R.color.z_green_38));
            ZColorBeans.add(new ZColorBean(12,14,R.color.z_green_38));
            ZColorBeans.add(new ZColorBean(18,20,R.color.z_green_38));
        }else{
            ZColorBeans.add(new ZColorBean(9,10,R.color.z_blue_3a));
            ZColorBeans.add(new ZColorBean(12,14,R.color.z_blue_3a));
            ZColorBeans.add(new ZColorBean(18,20,R.color.z_blue_3a));
        }
        return ZColorBeans;
    }

    @Override
    protected void initData() {
        items.clear();
        for (int i = 1; i <52 ; i++) {
            ZColorInfo ZColorInfo =  new ZColorInfo();
            ZColorInfo.title=String.valueOf(i);
            ZColorInfo.ZColorBeans =getColorBeans(i);
            items.add(ZColorInfo);
        }
        LinearLayoutManager manager= new LinearLayoutManager(this);
        recycleView.setLayoutManager(manager);
        recycleView.addItemDecoration(ZItemDecoration.createVertical(mActivity, ZColor.byRes(R.color.app_divide), ZDimen.dp2px(1)));
        ColorViewBinder  mBinder=new ColorViewBinder(mActivity);
        mAdapter.register(ZColorInfo.class,mBinder);
        mAdapter.setItems(items);
        recycleView.setAdapter(mAdapter);
    }
}
