package com.android.zjctools.base

import android.content.Context
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.drakeet.multitype.ItemViewDelegate


/**
 * Create by zjun on 2023/03/15 17:20
 * 描述：MultiType 适配器 Item 的代理基类类
 */
abstract class ZBItemDelegate<T, VDB : ViewDataBinding> : ItemViewDelegate<T, ZBItemDelegate.BItemHolder<VDB>> {
    protected lateinit var mContext: Context

    // 将事件回调给 View 层监听接口
    protected var mItemListener: BItemListener<T>? = null
    protected var mItemActionListener: BItemActionListener<T>? = null
    protected var mItemLongListener: BItemLongListener<T>? = null
    protected lateinit var mEvent: MotionEvent
    companion object{
        val ATION_NORMAL=0;  //正常
        val ATION_DELETE=1;   //删除
        val ATION_UPDATE=2;    //修改

        val ATION_SHARE=3;    //分享
        val ATION_LIKE=4;    //点赞
        val ATION_COLLECT=5;    //收藏

        val ATION_CONFIRM=6;    //确定
        val ATION_CANCEL=7;    //取消

    }


    constructor() : super()

    constructor(listener: BItemListener<T>? = null,actionListener: BItemActionListener<T>? = null,longListener: BItemLongListener<T>? = null) : super() {
        mItemListener = listener
        mItemActionListener=actionListener
        mItemLongListener = longListener
    }

    override fun onBindViewHolder(holder: BItemHolder<VDB>, item: T) {
        holder.itemView.setOnClickListener {
            mItemListener?.onClick(it, item, getPosition(holder))
        }
        holder.itemView.setOnTouchListener { v, event ->
            mEvent = event
            false
        }
        holder.itemView.setOnLongClickListener {
            mItemLongListener?.onLongClick(it, mEvent, item, getPosition(holder)) ?: false
        }
        onBindView(holder, item)
    }

    override fun onCreateViewHolder(context: Context, parent: ViewGroup): BItemHolder<VDB> {
        mContext = context
        var binding: VDB = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            layoutId(),
            parent,
            false
        );
        return BItemHolder(binding)
    }

    /**
     * 获取 Item 布局 uid
     */
    protected abstract fun layoutId(): Int

    /**
     * 绑定定数
     */
    protected abstract fun onBindView(holder: BItemHolder<VDB>, item: T)

    /**
     * ----------------------------------
     * 定义通用的 ViewHolder
     */
    class BItemHolder<B : ViewDataBinding>(val binding: B) : RecyclerView.ViewHolder(binding.root)

    /**
     * 回调给 View 层监听接口
     */
    interface BItemActionListener<T> {
        fun onClick(v: View, data: T, position: Int,action:Int=ATION_NORMAL)
    }

    /**
     * 回调给 View 层监听接口
     */
    interface BItemListener<T> {
        fun onClick(v: View, data: T, position: Int)
    }



    interface BItemLongListener<T> {
        fun onLongClick(v: View, event: MotionEvent, data: T, position: Int): Boolean
    }


}


