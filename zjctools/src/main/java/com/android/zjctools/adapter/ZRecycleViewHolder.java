package com.android.zjctools.adapter;

import android.util.SparseArray;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;


public class ZRecycleViewHolder extends RecyclerView.ViewHolder {
    private SparseArray<View> mViews;
    View mContentView;
    public ZRecycleViewHolder(View itemView) {
        super(itemView);
        mViews=new SparseArray<>();
        mContentView=itemView;
    }

    public  <T extends View>  T getView(int viewId) {
        View view = mViews.get(viewId);
        if(view==null){
            view=mContentView.findViewById(viewId);
            mViews.append(viewId,view);
        }
        return (T) view;
    }
    public View getItemView(){
        return mContentView;
    }

}