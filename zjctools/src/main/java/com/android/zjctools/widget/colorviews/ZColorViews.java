package com.android.zjctools.widget.colorviews;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.android.zjctools.utils.ZColor;
import com.android.zjctools.utils.ZToast;
import com.android.zjcutils.R;

import java.util.List;

public class ZColorViews extends View {
    List<ZColorBean> mZColorBeans;
    int mWidth = 0;//总宽度
    int mHeight = 0;//总宽度
    int mStart = 9;//起始时间
    int mEnd = 21;//结束时间
    int mEachSpace = 0; //每一块的长度
    Paint mPaint;

    public ZColorViews(Context context) {
        super(context);
    }

    public ZColorViews(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
         mPaint=new Paint();
         mPaint.setAntiAlias(true);
         mPaint.setStyle(Paint.Style.FILL);
         mPaint.setColor(ZColor.byRes(R.color.zjcBlue));
    }

    public ZColorViews setStartAndEnd(int start,int end) {
        this.mStart = start;
        this.mEnd = end;
        return this;
    }


    public void setItems(List<ZColorBean> ZColorBeans) {
        this.mZColorBeans = ZColorBeans;
        if (mEachSpace == 0) {
            mEachSpace = mWidth / (mEnd - mStart);
        }
        postInvalidate();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (mWidth == 0) {
            mWidth = MeasureSpec.getSize(widthMeasureSpec);
            mHeight = MeasureSpec.getSize(heightMeasureSpec);
            mEachSpace = mWidth / (mEnd - mStart);//这里没用padding直接减
        }

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mZColorBeans == null || mZColorBeans.size() == 0) {
            ZToast.create().showNormal("请设置Items");
            return;
        }
        for (int i = 0; i <mZColorBeans.size() ; i++) {
            ZColorBean zColorBean = mZColorBeans.get(i);
            int startX=mEachSpace*(zColorBean.start-mStart);//起始未知
            int endX=startX+mEachSpace*(zColorBean.end- zColorBean.start);
            RectF rectF=new RectF(startX,0,endX,mHeight);
            mPaint.setColor(ZColor.byRes(zColorBean.colorId));
            canvas.drawRect(rectF,mPaint);
        }

    }



    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (mWidth == 0) {
            mWidth = w;
            mHeight = h;
        }
    }
}
