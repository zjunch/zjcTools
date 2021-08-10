package com.android.zjctools.ninepicture;


import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.zjctools.R;
import com.android.zjctools.base.ZBActivity;
import com.android.zjctools.bean.NineMediaBean;
import com.android.zjctools.bean.NinePictureBean;
import com.android.zjctools.model.ZMedia;
import com.android.zjctools.utils.ZColor;
import com.android.zjctools.utils.ZDimen;
import com.android.zjctools.widget.ZItemDecoration;

import java.util.ArrayList;
import java.util.List;

import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;


public class NineMediaActivity extends ZBActivity {

    RecyclerView recyclerView;
    List<NineMediaBean> nineBeans = new ArrayList<>();

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
            List<ZMedia> medias = new ArrayList<>();
            NineMediaBean bean = new NineMediaBean();
            for (int j = 0; j < i + 1; j++) {
                medias.add(new ZMedia(j%2,"https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=1208538952,1443328523&fm=26&gp=0.jpg"));
            }
            bean.medias = medias;
            nineBeans.add(bean);
        }
        MultiTypeAdapter adapter = new MultiTypeAdapter();
        adapter.register(NineMediaBean.class, new NineMediaBinder(mActivity));
        Items items = new Items();
        items.addAll(nineBeans);
        adapter.setItems(items);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(ZItemDecoration.createVertical(mActivity, ZColor.byRes(R.color.z_black_12), ZDimen.dp2px(10)));
        recyclerView.setAdapter(adapter);
    }
}
