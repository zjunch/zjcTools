package com.android.zjctools

import android.widget.TextView
import com.android.zjctools.R
import com.android.zjctools.base.ZBItemDelegate
import com.android.zjctools.databinding.ItemColorViewBinding
import com.android.zjctools.widget.colorviews.ZColorInfo
import com.android.zjctools.widget.colorviews.ZColorSpaceView


class ColorViewDelegate() : ZBItemDelegate<ZColorInfo, ItemColorViewBinding>() {

    override fun layoutId(): Int {
        return R.layout.item_color_view
    }

    override fun onBindView(holder: BItemHolder<ItemColorViewBinding>, item: ZColorInfo) {
        holder.binding.item = item
        holder.binding.executePendingBindings()
        holder.binding.tvTitle.text = item.title
        holder.binding.zColorSpaceView.setColorInfoView(item)
    }
}