package com.android.zjctools.ninepicture

import android.widget.ImageView
import com.android.zjctools.R
import com.android.zjctools.base.ZBItemDelegate
import com.android.zjctools.bean.NinePictureBean
import com.android.zjctools.databinding.ItemNinePictureBinding
import com.android.zjctools.image.ZImgLoader
import com.android.zjctools.widget.nineimages.ZNinePicturesView


class NinePictureDelegate() : ZBItemDelegate<NinePictureBean, ItemNinePictureBinding>() {

    override fun layoutId(): Int {
        return R.layout.item_nine_picture
    }

    override fun onBindView(holder: BItemHolder<ItemNinePictureBinding>, item: NinePictureBean) {
        holder.binding.item = item
        holder.binding.executePendingBindings()

        holder.binding.ninePics.setImgLoadUrlListener(object :ZNinePicturesView.ImgLoadUrlListener{
            override fun onImgLoad(
                imageView: ImageView,
                url: String?,
                index: Int,
                viewWidth: Int,
                viewHeight: Int) {
                ZImgLoader.loadCover(imageView,url)
            }

        })
        holder.binding.ninePics.setImageUrls(item.urls)
    }

}