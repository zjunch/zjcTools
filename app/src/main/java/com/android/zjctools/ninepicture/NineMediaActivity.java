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
import com.drakeet.multitype.MultiTypeAdapter;

import java.util.ArrayList;
import java.util.List;



public class NineMediaActivity extends ZBActivity {

    RecyclerView recyclerView;
    List<NineMediaBean> nineBeans = new ArrayList<>();

    @Override
    public int layoutId() {
        return R.layout.activity_glide_test_cach;
    }

    @Override
    public void initUI() {
        super.initUI();
        recyclerView = findViewById(R.id.recycleView);
    }

    @Override
    public void initData() {
        for (int i = 0; i < 12; i++) {
            List<ZMedia> medias = new ArrayList<>();
            NineMediaBean bean = new NineMediaBean();
            for (int j = 0; j < i + 1; j++) {
                medias.add(new ZMedia(j%2==0?1:2,"http://up.deskcity.org/pic_source/2f/f4/42/2ff442798331f6cc6005098766304e39.jpg"));
            }
            bean.setMedias(medias);
            nineBeans.add(bean);
        }
        MultiTypeAdapter adapter = new MultiTypeAdapter();
        adapter.register(NineMediaBean.class, new NineMediaDelegate());
        adapter.setItems(nineBeans);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(ZItemDecoration.Companion.createVertical(mActivity, ZColor.INSTANCE.byRes(R.color.z_black_12), ZDimen.INSTANCE.dp2px(10)));
        recyclerView.setAdapter(adapter);

    }
}
