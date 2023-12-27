package com.android.zjctools.widget

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import com.android.zjcutils.R

/**
 * 自定义ViewGroup-堆叠头像的点赞Layout
 *
 * 使用方式
 * <com.android.zjctools.widget.PileLayout android:id="@+id/apply_pile_layout" android:layout_width="wrap_content" android:layout_height="wrap_content" android:layout_centerVertical="true" app:PileLayout_pileWidth="16dp"></com.android.zjctools.widget.PileLayout>   此处16dp是重叠宽度
 */
class ZPileLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0) : ViewGroup(context, attrs, defStyleAttr) {


    /**
     * 两个子控件之间的垂直间隙
     */
    private var vertivalSpace: Float

    /**
     * 重叠宽度
     */
    private var pileWidth: Int

    init {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.ZPileLayout)
        vertivalSpace = ta.getDimension(R.styleable.ZPileLayout_zv_pileLayout_verSpace, dp2px(4f))
        pileWidth = ta.getDimension(R.styleable.ZPileLayout_zv_pileLayout_pileWidth, dp2px(10f)).toInt()
        ta.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val widthSpecMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSpecSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightSpecMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSpecSize = MeasureSpec.getSize(heightMeasureSpec)

        //AT_MOST
        var width = 0
        var height = 0
        var rawWidth = 0 //当前行总宽度
        var rawHeight = 0 // 当前行高
        var rowIndex = 0 //当前行位置
        val count = childCount
        for (i in 0 until count) {
            val child = getChildAt(i)
            if (child.visibility == GONE) {
                if (i == count - 1) {
                    //最后一个child
                    height += rawHeight
                    width = Math.max(width, rawWidth)
                }
                continue
            }

            //这里调用measureChildWithMargins 而不是measureChild
            measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, 0)
            val lp = child.layoutParams as MarginLayoutParams
            val childWidth = child.measuredWidth + lp.leftMargin + lp.rightMargin
            val childHeight = child.measuredHeight + lp.topMargin + lp.bottomMargin
            if (rawWidth + childWidth - (if (rowIndex > 0) pileWidth else 0) > widthSpecSize - paddingLeft - paddingRight) {
                //换行
                width = Math.max(width, rawWidth)
                rawWidth = childWidth
                height += (rawHeight + vertivalSpace).toInt()
                rawHeight = childHeight
                rowIndex = 0
            } else {
                rawWidth += childWidth
                if (rowIndex > 0) {
                    rawWidth -= pileWidth.toInt()
                }
                rawHeight = Math.max(rawHeight, childHeight)
            }
            if (i == count - 1) {
                width = Math.max(rawWidth, width)
                height += rawHeight
            }
            rowIndex++
        }
        setMeasuredDimension(
            if (widthSpecMode == MeasureSpec.EXACTLY) widthSpecSize else width + paddingLeft + paddingRight,
            if (heightSpecMode == MeasureSpec.EXACTLY) heightSpecSize else height + paddingTop + paddingBottom
        )
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val viewWidth = r - l
        var leftOffset = paddingLeft
        var topOffset = paddingTop
        var rowMaxHeight = 0
        var rowIndex = 0 //当前行位置
        var childView: View
        var w = 0
        val count = childCount
        while (w < count) {
            childView = getChildAt(w)
            if (childView.visibility == GONE) {
                w++
                continue
            }
            val lp = childView.layoutParams as MarginLayoutParams
            // 如果加上当前子View的宽度后超过了ViewGroup的宽度，就换行
            val occupyWidth = lp.leftMargin + childView.measuredWidth + lp.rightMargin
            if (leftOffset + occupyWidth + paddingRight > viewWidth) {
                leftOffset = paddingLeft // 回到最左边
                topOffset += (rowMaxHeight + vertivalSpace).toInt() // 换行
                rowMaxHeight = 0
                rowIndex = 0
            }
            val left = leftOffset + lp.leftMargin
            val top = topOffset + lp.topMargin
            val right = leftOffset + lp.leftMargin + childView.measuredWidth
            val bottom = topOffset + lp.topMargin + childView.measuredHeight
            childView.layout(left, top, right, bottom)

            // 横向偏移
            leftOffset += occupyWidth
            // 试图更新本行最高View的高度
            val occupyHeight = lp.topMargin + childView.measuredHeight + lp.bottomMargin
            if (rowIndex != count - 1) {
                leftOffset -= pileWidth.toInt()
            }
            rowMaxHeight = Math.max(rowMaxHeight, occupyHeight)
            rowIndex++
            w++
        }
    }

    override fun generateLayoutParams(attrs: AttributeSet): LayoutParams {
        return MarginLayoutParams(context, attrs)
    }

    override fun generateDefaultLayoutParams(): LayoutParams {
        return MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
    }

    override fun generateLayoutParams(p: LayoutParams): LayoutParams {
        return MarginLayoutParams(p)
    }

    fun dp2px(dpValue: Float): Float {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dpValue,
            resources.displayMetrics
        )
    }

}