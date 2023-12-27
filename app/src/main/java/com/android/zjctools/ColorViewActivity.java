package com.android.zjctools;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.zjctools.base.ZBActivity;
import com.android.zjctools.widget.colorviews.ZColorInfo;
import com.android.zjctools.utils.ZColor;
import com.android.zjctools.utils.ZDimen;
import com.android.zjctools.widget.ZItemDecoration;
import com.android.zjctools.widget.colorviews.ZColorBean;
import com.drakeet.multitype.MultiTypeAdapter;

import java.util.ArrayList;
import java.util.List;

public class ColorViewActivity extends ZBActivity {
    RecyclerView recycleView;


    MultiTypeAdapter mAdapter=new MultiTypeAdapter();
    @Override
    public int layoutId() {
        return R.layout.activity_color_view;
    }

    @Override
    public void initUI() {
        super.initUI();
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
    public void initData() {
        List <ZColorInfo> items=new ArrayList();
        items.clear();
        for (int i = 1; i <52 ; i++) {
            ZColorInfo ZColorInfo =  new ZColorInfo();
            ZColorInfo.setTitle("第"+i+"条");
            ZColorInfo.setZColorBeans(getColorBeans(i));
            items.add(ZColorInfo);
        }
        LinearLayoutManager manager= new LinearLayoutManager(this);
        recycleView.setLayoutManager(manager);
        recycleView.addItemDecoration(ZItemDecoration.Companion.createVertical(mActivity, ZColor.INSTANCE.byRes(R.color.app_divide), ZDimen.INSTANCE.dp2px(1)));
        mAdapter.register(ZColorInfo.class, new ColorViewDelegate());
        mAdapter.setItems(items);
        recycleView.setAdapter(mAdapter);
    }
}
