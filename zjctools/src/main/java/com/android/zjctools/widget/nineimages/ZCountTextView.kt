package com.android.zjctools.widget.nineimages

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.android.zjctools.utils.ZDimen
import com.android.zjcutils.R

/**
 * created zjun 2019-12-23
 */
class ZCountTextView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    var mWidth = 0
    var mHeight = 0
    private val coverPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var mCount = 0
    private var mMarginRight = 30 //半透明边框的右边距
    private var mMarginBottom = 30 // 半透明边框的下边距
    private var xPadding = 20 //字题横向的padding
    private var yPadding = 8 //字题竖向的padding
    private val isExactWidth = true //是否是固定半透明的宽度
    private var mExactHalfRectfWidth = 90 //固定半透明背景的宽

    constructor(context: Context?, counts: Int) : this(context, null) {
        mCount = counts
    }

    /**
     * 设置半透明矩形的宽度 ，单位dp
     */
    private fun setExactHalfRectWidth(size: Int) {
        mExactHalfRectfWidth = ZDimen.dp2px(size)
        postInvalidate()
    }

    fun setTextSize(size: Int) {
        coverPaint.textSize = size.toFloat()
    }

    fun setTextColor(color: Int) {
        coverPaint.color = color
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        if (w > 0) {
            mWidth = w
            mHeight = h
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        //canvas.drawColor(getResources().getColor(R.color.color_half));
        val rect = getTextBounds("+$mCount", coverPaint)
        val textWidth = rect.width() //字体的宽度
        val textHeight = rect.height() //字体的高度
        coverPaint.color = resources.getColor(R.color.z_half9) //矩形背景色
        var countTextStartX = mWidth - mMarginRight - textWidth - 2 * xPadding //矩形x起点，
        val countTextStartY = mHeight - mMarginBottom - textHeight - 2 * yPadding //矩形Y起点，
        if (isExactWidth) { // 小矩形宽度为统一具体值，不需要根据padding 测算
            countTextStartX = mWidth - mMarginRight - mExactHalfRectfWidth
        }
        //绘制圆角矩形
        val rectF = RectF(
            countTextStartX.toFloat(),
            countTextStartY.toFloat(),
            (mWidth - mMarginRight).toFloat(),
            (mHeight - mMarginBottom).toFloat()
        )
        canvas.drawRoundRect(
            rectF,
            ((textHeight + 2 * yPadding) / 2).toFloat(),
            ((textHeight + 2 * yPadding) / 2).toFloat(),
            coverPaint
        )

        //绘制个数
        coverPaint.color = resources.getColor(R.color.zWhite)
        if (isExactWidth) { // 小矩形宽度为统一值
            canvas.drawText(
                "+$mCount",
                (countTextStartX + mExactHalfRectfWidth / 2 - textWidth / 2).toFloat(),
                (mHeight - mMarginBottom - yPadding).toFloat(),
                coverPaint
            )
        } else {
            canvas.drawText(
                "+$mCount",
                (countTextStartX + xPadding).toFloat(),
                (mHeight - mMarginBottom - yPadding).toFloat(),
                coverPaint
            )
        }
    }

    private fun getTextBounds(counts: String, paint: Paint): Rect {
        val rect = Rect()
        paint.getTextBounds(counts, 0, counts.length, rect)
        return rect
    }

    init {
        coverPaint.textSize = ZDimen.sp2px(12).toFloat()
        coverPaint.color = Color.WHITE
        mMarginRight = ZDimen.dp2px(7)
        mMarginBottom = mMarginRight
        xPadding = ZDimen.dp2px(5)
        yPadding = ZDimen.dp2px(3)
        mExactHalfRectfWidth = ZDimen.dp2px(30)
    }
}