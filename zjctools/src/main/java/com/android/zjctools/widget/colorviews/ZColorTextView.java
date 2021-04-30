package com.android.zjctools.widget.colorviews;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.android.zjctools.utils.ZColor;
import com.android.zjctools.utils.ZDimen;
import com.android.zjcutils.R;

import androidx.annotation.Nullable;

public class ZColorTextView extends View {
    int mWidth=0;
    int mStart=9;//起始时间
    int mEnd=21;//结束时间
    private int mEachSpace;
    private Paint mPaint;


    public ZColorTextView(Context context) {
        this(context,null);
    }

    public ZColorTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mPaint=new Paint();
        mPaint.setTextSize(ZDimen.dp2px(12));
        mPaint.setAntiAlias(true);
        mPaint.setColor(ZColor.byRes(R.color.zBlack));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if(mWidth==0){
            mWidth=w;
            mEachSpace=mWidth/(mEnd-mStart);
        }
    }
    public void setStartAndEnd( int start,int end){
        this.mStart=start;
        this.mEnd=end;
    }
    

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(mEnd==0||mWidth==0){
            return;
        }
        for (int i = 0; i <mEnd-mStart ; i++) {
           int textHeight= getTextBounds(String.valueOf(i+mStart),mPaint).height();
            canvas.drawText(String.valueOf(i+mStart),i*mEachSpace,textHeight,mPaint);
        }
    }

    private Rect getTextBounds(String str, Paint paint){
        Rect rect=new Rect();
        paint.getTextBounds(str,0,str.length(),rect);
        return rect;
    }

}
