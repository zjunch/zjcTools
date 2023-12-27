package com.android.zjctools.ninepicture;



import com.android.zjctools.R;
import com.android.zjctools.base.ZBActivity;
import com.android.zjctools.bean.NinePictureBean;
import com.android.zjctools.utils.ZColor;
import com.android.zjctools.utils.ZDimen;
import com.android.zjctools.widget.ZItemDecoration;
import com.drakeet.multitype.MultiTypeAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class NinePictureActivity extends ZBActivity {

    RecyclerView recyclerView;
    List<NinePictureBean> nineBeans = new ArrayList<>();

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
            List<String> urls = new ArrayList<>();
            NinePictureBean bean = new NinePictureBean();
            for (int j = 0; j < i + 1; j++) {
                urls.add("http://up.deskcity.org/pic_source/2f/f4/42/2ff442798331f6cc6005098766304e39.jpg");
            }
            bean.setUrls(urls);
            nineBeans.add(bean);
        }
        MultiTypeAdapter adapter = new MultiTypeAdapter();
        adapter.register(NinePictureBean.class, new NinePictureDelegate());
        adapter.setItems(nineBeans);
        recyclerView.addItemDecoration(ZItemDecoration.Companion.createVertical(mActivity, ZColor.INSTANCE.byRes(R.color.z_black_12), ZDimen.INSTANCE.dp2px(10)));
        recyclerView.setAdapter(adapter);
    }
}
