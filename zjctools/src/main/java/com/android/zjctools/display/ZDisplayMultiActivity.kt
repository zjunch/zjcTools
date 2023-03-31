package com.android.zjctools.display

import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.android.zjctools.base.ZBActivity
import com.android.zjctools.router.ZParams
import com.android.zjctools.router.ZRouter
import com.android.zjcutils.R

/**
 * 展示图片集合类
 */
class ZDisplayMultiActivity : ZBActivity() {
    var viewPager: ZFixedViewPager? = null
    var currentTv: TextView? = null
    var dotLayout: LinearLayout? = null
    private var adapter: ZDisplayAdapter? = null
    private var imagePaths: MutableList<String> = mutableListOf()
    private var selected = 0

    private var dotViews: MutableList<View> = mutableListOf()
    override fun layoutId(): Int =R.layout.z_activity_display_images

    override fun initUI() {
        viewPager = findViewById(R.id.display_view_pager)
        dotLayout = findViewById(R.id.display_dot_layout)
        currentTv = findViewById(R.id.z_current_text)
        setStatusBar(false)
    }

    override fun initData() {
        val params = ZRouter.getParcelable(mActivity) as ZParams
        selected = params.what!!
        imagePaths = params.strList!!
        dotViews =  mutableListOf()
        for (i in imagePaths.indices) {
            var  dotView = View(mActivity)
            dotViews.add(dotView)
            dotView!!.setBackgroundResource(R.color.zWhite)
            if (i == selected) {
                dotView!!.setBackgroundResource(R.color.picture_in)
            }
            val lp = LinearLayout.LayoutParams(0, 0)
            lp.width = 20
            lp.height = 20
            lp.leftMargin = 5
            lp.rightMargin = 5
            dotLayout!!.addView(dotView, lp)
        }
        adapter = ZDisplayAdapter(mActivity, imagePaths)
        viewPager!!.adapter = adapter
        viewPager!!.currentItem = selected
        currentTv?.text = "${selected + 1}/${imagePaths.size}"
        adapter!!.setListener(object :ZDisplayAdapter.IClickListener{
            override fun onClick() {
                finish()
            }

        })
        setPageListener()
    }

    private fun setPageListener() {
        viewPager!!.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrolled(position: Int,
                positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                for (i in dotViews.indices) {
                    dotViews[i]!!.setBackgroundResource(R.color.z_white_87)
                    if (i == position) {
                        currentTv?.text = "${position + 1}/${imagePaths!!.size}"
                        dotViews[position]!!.setBackgroundResource(R.color.picture_in)
                    }
                }
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })
    }
}