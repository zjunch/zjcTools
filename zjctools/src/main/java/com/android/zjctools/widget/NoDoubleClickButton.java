package com.android.zjctools.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.appcompat.widget.AppCompatButton;

public class NoDoubleClickButton extends AppCompatButton {
    private long clickTimeInterval = 1000;
    private long previousTime;

    public NoDoubleClickButton(Context context) {
        this(context,null);
    }

    public NoDoubleClickButton(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public NoDoubleClickButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * @param event
     *         touch事件
     *
     * @return 是否消耗点击事件
     * true - 消耗点击事件
     * false - 不消耗点击事件
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:

                long currentTime = System.currentTimeMillis();
                if (currentTime - previousTime < clickTimeInterval) {
                    return true;
                }
                previousTime = currentTime;
                break;
        }
        return super.onTouchEvent(event);
    }
}
