package com.android.zjctools.ninepicture;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.zjctools.R;
import com.android.zjctools.base.ZBActivity;
import com.android.zjctools.bean.NinePictureBean;
import com.android.zjctools.utils.ZColor;
import com.android.zjctools.utils.ZDimen;
import com.android.zjctools.widget.ZItemDecoration;


import java.util.ArrayList;
import java.util.List;

import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;


public class NinePictureActivity extends ZBActivity {

    RecyclerView recyclerView;
    List<NinePictureBean> nineBeans = new ArrayList<>();

    @Override
    protected int layoutId() {
        return R.layout.activity_glide_test_cach;
    }

    @Override
    protected void initUI() {
        recyclerView = findViewById(R.id.recycleView);
    }

    @Override
    protected void initData() {
        for (int i = 0; i < 12; i++) {
            List<String> urls = new ArrayList<>();
            NinePictureBean bean = new NinePictureBean();
            for (int j = 0; j < i + 1; j++) {
                urls.add("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=1208538952,1443328523&fm=26&gp=0.jpg");
            }
            bean.urls = urls;
            nineBeans.add(bean);
        }
        MultiTypeAdapter adapter = new MultiTypeAdapter();
        adapter.register(NinePictureBean.class, new NinePictureBinder(mActivity));
        Items items = new Items();
        items.addAll(nineBeans);
        adapter.setItems(items);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(ZItemDecoration.createVertical(mActivity, ZColor.byRes(R.color.zjc_black_12), ZDimen.dp2px(10)));
        recyclerView.setAdapter(adapter);
    }
}
