package com.android.zjctools.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.annotation.ColorInt
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

/**
 * RecyclerView列表分割线，用于LinearLayoutManager布局
 */
class ZItemDecoration(context :Context ,  orientation:Int, @ColorInt  color:Int,  space:Int)  : ItemDecoration() {


    var mOrientation = orientation
    var mSpace=space
    private val mRect = Rect(0, 0, 0, 0)
    private val mPaint = Paint()
    init {
        mPaint.color = color
    }
    companion object {
        private const val HORIZONTAL_LIST = LinearLayoutManager.HORIZONTAL
        private const val VERTICAL_LIST = LinearLayoutManager.VERTICAL

        /**
         * 生成竖向排列的Decoration
         *
         * @param color  分割线颜色 例如：Color.parseString("#ff3300")
         * @param height 分割线高度（单位为PX）
         */
        fun createVertical(context: Context, @ColorInt color: Int, height: Int): ZItemDecoration {
            return ZItemDecoration(context, VERTICAL_LIST, color, height)
        }

        /**
         * 生成横向排列的Decoration
         *
         * @param color 分割线颜色 例如：Color.parseString("#ff3300")
         * @param width 分割线宽度（单位为PX）
         */
        fun createHorizontal(context: Context, @ColorInt color: Int, width: Int): ZItemDecoration {
            return ZItemDecoration(context, HORIZONTAL_LIST, color, width)
        }
    }


    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
        if (mOrientation == VERTICAL_LIST) {
            drawVertical(c, parent)
        } else {
            drawHorizontal(c, parent)
        }
    }

    fun drawVertical(c: Canvas, parent: RecyclerView) {
        val left = parent.paddingLeft
        val right = parent.width - parent.paddingRight
        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            val params = child
                .layoutParams as RecyclerView.LayoutParams
            val top = child.bottom + params.bottomMargin
            val bottom = top + mSpace
            mRect[left, top, right] = bottom
            c.drawRect(mRect, mPaint)
        }
    }

    fun drawHorizontal(c: Canvas, parent: RecyclerView) {
        val top = parent.paddingTop
        val bottom = parent.height - parent.paddingBottom
        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            val params = child
                .layoutParams as RecyclerView.LayoutParams
            val left = child.right + params.rightMargin
            val right = left + mSpace
            mRect[left, top, right] = bottom
            c.drawRect(mRect, mPaint)
        }
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        if (mOrientation == VERTICAL_LIST) {
            outRect[0, 0, 0] = mSpace
        } else {
            outRect[0, 0, mSpace] = 0
        }
    }




}