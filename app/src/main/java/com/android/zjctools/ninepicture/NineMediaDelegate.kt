package com.android.zjctools.ninepicture

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.android.zjctools.R
import com.android.zjctools.base.ZBItemDelegate
import com.android.zjctools.bean.NineMediaBean
import com.android.zjctools.databinding.ItemNineMediaBinding
import com.android.zjctools.image.ZImgLoader
import com.android.zjctools.model.ZMedia
import com.android.zjctools.utils.ZToastUtils
import com.android.zjctools.widget.nineimages.ZNineMediaView


class NineMediaDelegate : ZBItemDelegate<NineMediaBean, ItemNineMediaBinding>() {

    override fun layoutId(): Int {
        return R.layout.item_nine_media
    }

    override fun onBindView(holder: BItemHolder<ItemNineMediaBinding>, item: NineMediaBean) {
        holder.binding.item = item
        holder.binding.executePendingBindings()


        holder.binding.nineMedias.setMediaLoadListener(object :ZNineMediaView.MediaLoadListener{
            override fun onImgLoad(
                itemView: ViewGroup, media: ZMedia, index: Int, viewWidth: Int, viewHeight: Int) {
                var img= itemView.findViewById<ImageView>(R.id.ivMediaCover)
                var icon= itemView.findViewById<ImageView>(R.id.ivMediaIcon)
                ZImgLoader.loadCover(img,media.mediaUrl)

                icon.visibility=if(media.mediaType==1) View.GONE else View.VISIBLE
            }

        })
        holder.binding.nineMedias.setMedias(item.medias,R.layout.layout_item_media)
        holder.binding.nineMedias.setItemClickListener(object :ZNineMediaView.OnItemClickListener{
            override fun onItemClick(view: ViewGroup, position: Int) {
                ZToastUtils.show(mContext,"$position")
            }

        })
    }
}