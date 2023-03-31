package com.android.zjctools

import com.android.zjctools.base.ZBItemDelegate
import com.android.zjctools.bean.FunctionBean
import com.android.zjctools.databinding.ItemFunctionBinding


class FunctionDelegate(listener: BItemListener<FunctionBean>) : ZBItemDelegate<FunctionBean, ItemFunctionBinding>(listener=listener) {

    override fun layoutId(): Int {
        return R.layout.item_function
    }

    override fun onBindView(holder: BItemHolder<ItemFunctionBinding>, item: FunctionBean) {
        holder.binding.item = item
        holder.binding.executePendingBindings()
        holder.binding.svFunction.setTitle(item.title)
    }
}