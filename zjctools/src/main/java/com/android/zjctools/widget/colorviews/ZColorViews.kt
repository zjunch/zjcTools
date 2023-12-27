package com.android.zjctools.widget.colorviews

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import com.android.zjctools.utils.ZColor.byRes
import com.android.zjctools.utils.ZToast.showNormal
import com.android.zjcutils.R

class ZColorViews : View {
    var mZColorBeans: List<ZColorBean>? = null
    var mWidth = 0 //总宽度
    var mHeight = 0 //总宽度
    var mStart = 9 //起始时间
    var mEnd = 21 //结束时间
    var mEachSpace = 0f //每一块的长度
    var mPaint: Paint? = null

    constructor(context: Context?) : super(context) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    private fun init() {
        mPaint = Paint()
        mPaint!!.isAntiAlias = true
        mPaint!!.style = Paint.Style.FILL
        mPaint!!.color = byRes(R.color.zBlue)
    }

    fun setStartAndEnd(start: Int, end: Int): ZColorViews {
        mStart = start
        mEnd = end
        return this
    }

    fun setItems(ZColorBeans: List<ZColorBean>?) {
        mZColorBeans = ZColorBeans
        if (mEachSpace == 0f) {
            mEachSpace = (mWidth / (mEnd - mStart)).toFloat()
        }
        postInvalidate()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        if (mWidth == 0) {
            mWidth = MeasureSpec.getSize(widthMeasureSpec)
            mHeight = MeasureSpec.getSize(heightMeasureSpec)
            mEachSpace = mWidth * 1f / (mEnd - mStart) //这里没用padding直接减
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (mZColorBeans == null || mZColorBeans!!.size == 0) {
            showNormal("请设置Items")
            return
        }
        for (i in mZColorBeans!!.indices) {
            val zColorBean = mZColorBeans!![i]
            val startX = (mEachSpace * (zColorBean.start - mStart)).toInt() //起始未知
            val endX = (startX + mEachSpace * (zColorBean.end - zColorBean.start)).toInt()
            val rectF = RectF(
                startX.toFloat(), 0F,
                endX.toFloat(), mHeight.toFloat()
            )
            mPaint!!.color = byRes(zColorBean.colorId)
            canvas.drawRect(rectF, mPaint!!)
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        if (mWidth == 0) {
            mWidth = w
            mHeight = h
        }
    }
}