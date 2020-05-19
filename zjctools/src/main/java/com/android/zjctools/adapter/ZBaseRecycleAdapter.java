package com.android.zjctools.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;


public abstract  class ZBaseRecycleAdapter<T>extends RecyclerView.Adapter<ZRecycleViewHolder>  {

    private List<T> datas;
    public OnItemClickListenr onItemClickListenr;
    public   long clickCurrentTime;
    public ZBaseRecycleAdapter(List<T> datas) {
        this.datas = datas;
    }

    @Override
    public ZRecycleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ZRecycleViewHolder(LayoutInflater.from(parent.getContext()).inflate(getLayoutId(viewType),parent,false));
    }

    @Override
    public void onBindViewHolder(ZRecycleViewHolder holder, final  int position) {
        int ViewType=  getItemViewType(position);
        holder.getItemView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onItemClickListenr!=null){
                    if(System.currentTimeMillis()-clickCurrentTime>600){
                        onItemClickListenr.OnClickItem(position);
                        clickCurrentTime= System.currentTimeMillis();
                    }
                }
            }
        });
        initViews(holder,ViewType);
        setUI(position);
    }
    @Override
    public int getItemCount() {
        return (datas==null)? 0:datas.size();
    }


    protected abstract  void initViews(ZRecycleViewHolder holder, int viewType);
    protected abstract  int getLayoutId(int ViewType);
    protected abstract  void setUI(int position);
    public  void setOnItemClickListenr(OnItemClickListenr onItemClickListenr){
        this.onItemClickListenr=onItemClickListenr;

    }
    public  interface  OnItemClickListenr{
        void OnClickItem(int position);
    }
}