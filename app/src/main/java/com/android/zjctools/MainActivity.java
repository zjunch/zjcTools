package com.android.zjctools;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.android.zjctools.base.ZBActivity;
import com.android.zjctools.bean.FunctionBean;
import com.android.zjctools.permission.ZPermission;
import com.android.zjctools.utils.ZDimen;
import com.android.zjctools.utils.ZColor;
import com.android.zjctools.utils.ZLog;
import com.android.zjctools.widget.ItemDecoration;

import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

public class MainActivity extends ZBActivity {
    RecyclerView recycleView;
    FunctionBinder mBinder;

    Items items=new Items();
    MultiTypeAdapter  mAdapter=new MultiTypeAdapter();


    @Override
    protected int layoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void getValues() {
        super.getValues();
        items.clear();
        for (int i = 0; i <1 ; i++) {
            items.add(new FunctionBean("底部弹出window",0));
            items.add(new FunctionBean("九宫格",1));
        }
    }

    @Override
    protected void initUI() {
        recycleView=findViewById(R.id.recycleView);
        LinearLayoutManager manager= new LinearLayoutManager(this);
        recycleView.setLayoutManager(manager);
        recycleView.addItemDecoration(ItemDecoration.createVertical(mActivity, ZColor.byRes(R.color.app_divide), ZDimen.dp2px(1)));
        mBinder=new FunctionBinder(mActivity);
        mAdapter.register(FunctionBean.class,mBinder);
        mAdapter.setItems(items);
        recycleView.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {
        getPermission();
    }

    @Override
    protected void initListener() {
        super.initListener();
        mBinder.setOnItemClickListener((action, item) -> {
            if(item.type==0){
                Router.goMainBottomWindow(mActivity);
            }else if(item.type==1){
                Router.goTestGlideCach(mActivity);
            }
        });
    }

    private void  getPermission(){
        ZPermission.getInstance(this).requestCamera(new ZPermission.PCallback() {
            @Override
            public void onReject() {
                ZLog.e("onReject");
            }

            @Override
            public void onComplete() {
                ZLog.e("onComplete");

            }
        });
        findViewById(R.id.tvSave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Router.goMainBottomWindow(mActivity);
            }
        });
    }

}
