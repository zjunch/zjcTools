package com.android.zjctools.widget

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatButton


class ZUnDoubleClickButton @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0) :
    AppCompatButton(context!!, attrs, defStyleAttr) {
    private val clickTimeInterval: Long = 1000
    private var previousTime: Long = 0

    /**
     * @param event
     * touch事件
     *
     * @return 是否消耗点击事件
     * true - 消耗点击事件
     * false - 不消耗点击事件
     */
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_UP -> {
                val currentTime = System.currentTimeMillis()
                if (currentTime - previousTime < clickTimeInterval) {
                    return true
                }
                previousTime = currentTime
            }
        }
        return super.onTouchEvent(event)
    }
}
