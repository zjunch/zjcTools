package com.android.zjctools.widget.dialog

import com.android.zjctools.base.ZBItemDelegate
import com.android.zjcutils.R
import com.android.zjcutils.databinding.ZWidgetWindowItemBinding


class ZWindowDelegate(actionListener: BItemActionListener<String>) : ZBItemDelegate<String, ZWidgetWindowItemBinding>(actionListener=actionListener) {

    override fun layoutId(): Int {
        return R.layout.z_widget_window_item
    }

    override fun onBindView(holder: BItemHolder<ZWidgetWindowItemBinding>, item: String) {
        holder.binding.item = item
        holder.binding.executePendingBindings()

        holder.binding.zWindowItemTileTv.text = item
    }
}