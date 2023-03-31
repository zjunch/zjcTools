package com.android.zjctools.display

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.android.zjctools.image.ZImgLoader
import com.github.chrisbanes.photoview.PhotoView

/**
 * 展示图片集合的适配器
 */
class ZDisplayAdapter(private val context: Context, private val imagePaths: List<String>) : PagerAdapter() {
    private var listener: IClickListener? = null
    override fun getCount(): Int {
        return imagePaths.size
    }
    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val photoView = PhotoView(context)
        photoView.isEnabled = true
        photoView.setOnClickListener { v: View? ->
            if (listener != null) {
                listener!!.onClick()
            }
        }
        ZImgLoader.loadCover(photoView, imagePaths[position])
        container.addView(photoView, 0)
        return photoView
    }

    /**
     * 设置回调接口
     */
    fun setListener(listener: IClickListener) {
        this.listener = listener
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    interface IClickListener {
        fun onClick()
    }
}