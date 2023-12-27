package com.android.zjctools.widget.colorviews

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.android.zjcutils.R

class ZColorSpaceView @JvmOverloads constructor(context: Context?, attrs: AttributeSet? = null) :
    LinearLayout(context, attrs) {
   var mContentView: View = LayoutInflater.from(context).inflate(R.layout.layout_zcolor_space, this)
    var mColorViews: ZColorViews? = null
    var mTimeViews: ZColorTextView? = null


    init {
        initViews()
    }


    private fun initViews() {
        mTimeViews = mContentView.findViewById(R.id.vTimes)
        mColorViews = mContentView.findViewById(R.id.zColorViews)
    }


    fun setColorInfoView(colorInfo: ZColorInfo) {
        mColorViews!!.setStartAndEnd(9, 21).setItems(colorInfo.ZColorBeans)
        mTimeViews!!.setStartAndEnd(9, 21)
    }

}