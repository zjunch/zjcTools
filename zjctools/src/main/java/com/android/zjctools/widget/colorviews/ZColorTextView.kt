package com.android.zjctools.widget.colorviews

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import com.android.zjctools.utils.ZColor.byRes
import com.android.zjctools.utils.ZDimen.dp2px
import com.android.zjcutils.R

class ZColorTextView @JvmOverloads constructor(context: Context?, attrs: AttributeSet? = null) :
    View(context, attrs) {
    var mWidth = 0
    var mStart = 9 //起始时间
    var mEnd = 21 //结束时间
    private var mEachSpace = 0
    private var mPaint: Paint? = null
    private fun init() {
        mPaint = Paint()
        mPaint!!.textSize = dp2px(12).toFloat()
        mPaint!!.isAntiAlias = true
        mPaint!!.color = byRes(R.color.zBlack)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        if (mWidth == 0) {
            mWidth = w
            mEachSpace = mWidth / (mEnd - mStart)
        }
    }

    fun setStartAndEnd(start: Int, end: Int) {
        mStart = start
        mEnd = end
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (mEnd == 0 || mWidth == 0) {
            return
        }
        for (i in 0..mEnd - mStart) {
            val rect = getTextBounds((i + mStart).toString(), mPaint)
            val textHeight = rect.height()
            val textWidth = rect.width()
            if (i == 0) {
                canvas.drawText(
                    (i + mStart).toString(), (i * mEachSpace).toFloat(), textHeight.toFloat(),
                    mPaint!!
                )
            } else if (i + mStart == mEnd) {
                canvas.drawText(
                    (i + mStart).toString(),
                    (i * mEachSpace - textWidth).toFloat(),
                    textHeight.toFloat(),
                    mPaint!!
                )
            } else {
                canvas.drawText(
                    (i + mStart).toString(),
                    (i * mEachSpace - textWidth / 2).toFloat(),
                    textHeight.toFloat(),
                    mPaint!!
                )
            }
        }
    }

    private fun getTextBounds(str: String, paint: Paint?): Rect {
        val rect = Rect()
        paint!!.getTextBounds(str, 0, str.length, rect)
        return rect
    }

    init {
        init()
    }
}