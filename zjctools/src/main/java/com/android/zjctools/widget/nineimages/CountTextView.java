package com.android.zjctools.widget.nineimages;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.android.zjctools.utils.ZDimen;
import com.android.zjcutils.R;

/**
 * created zjun 2019-12-23
 */

public class CountTextView extends View {
    int mWidth, mHeight;
    private Paint coverPaint;
    private  int mCount;
    private  int mMarginRight=30;   //半透明边框的右边距
    private  int  mMarginBottom=30;// 半透明边框的下边距
    private  int xPadding=20;  //字题横向的padding
    private  int yPadding=8;//字题竖向的padding



    private   boolean isExactWidth=true;  //是否是固定半透明的宽度
    private   int    mExactHalfRectfWidth=90;  //固定半透明背景的宽
    public CountTextView(Context context, int counts) {
        this(context,null);
        this.mCount=counts;
    }


    /**
     * 设置半透明矩形的宽度 ，单位dp
     */
    private  void setmExactHalfRectfWidth(int   size){
        mExactHalfRectfWidth=ZDimen.dp2px(size);
        postInvalidate();

    }
    public CountTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        coverPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        int viewCountTextSize= (int) ZDimen.sp2px(12);
        coverPaint.setTextSize(viewCountTextSize);
        coverPaint.setColor(Color.WHITE);
        mMarginBottom=mMarginRight=ZDimen.dp2px(7);
        xPadding= ZDimen.dp2px(5);
        yPadding= ZDimen.dp2px(3);
        mExactHalfRectfWidth=ZDimen.dp2px(30);
    }

    public void  setTextSize(int size){
        coverPaint.setTextSize(size);
    }

    public void  setTextColor(int color){
        coverPaint.setColor(color);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if(w>0){
            mWidth=w;
            mHeight=h;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //canvas.drawColor(getResources().getColor(R.color.color_half));
        Rect rect=  getTextBounds("+"+mCount,coverPaint);
        int textWidth= rect.width();//字体的宽度
        int textHeight= rect.height();//字体的高度
        coverPaint.setColor(getResources().getColor(R.color.zjc_half9));  //矩形背景色
        int countTextStartX=mWidth-mMarginRight-textWidth-2*xPadding;     //矩形x起点，
        int countTextStartY=mHeight-mMarginBottom-textHeight-2*yPadding;//矩形Y起点，

        if(isExactWidth){// 小矩形宽度为统一具体值，不需要根据padding 测算
            countTextStartX=mWidth-mMarginRight-mExactHalfRectfWidth;
        }
        //绘制圆角矩形
        RectF rectF =new RectF(countTextStartX,countTextStartY,mWidth-mMarginRight,mHeight-mMarginBottom);
        canvas.drawRoundRect(rectF,(textHeight+2*yPadding)/2,(textHeight+2*yPadding)/2,coverPaint);

        //绘制个数
        coverPaint.setColor(getResources().getColor(R.color.zjcWhite));
        if(isExactWidth){// 小矩形宽度为统一值
            canvas.drawText("+"+mCount,countTextStartX+mExactHalfRectfWidth/2-textWidth/2,mHeight-mMarginBottom-yPadding,coverPaint);
        }else{
            canvas.drawText("+"+mCount,countTextStartX+xPadding,mHeight-mMarginBottom-yPadding,coverPaint);
        }

    }

    private Rect getTextBounds(String counts, Paint paint){
        Rect rect=new Rect();
        paint.getTextBounds(counts,0,counts.length(),rect);
        return rect;
    }

}
