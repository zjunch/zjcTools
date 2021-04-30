package com.android.zjctools.widget;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.OvershootInterpolator;

import com.android.zjcutils.R;

import androidx.annotation.ColorRes;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

/**
 * 圆形进度条控件
 * Author: zjun
 * Time: 2019-12-07
 */

public class ZCircleProgressView extends View {

    private Paint mBackPaint, mProgPaint;   // 绘制画笔
    private RectF mRectF;       // 绘制区域
    private int[] mColorArray;  // 圆环渐变色
    private int mProgress;      // 圆环进度(0-100)
    private int mStartAngle=0; //底色的起始角度
    private int mSweepAngle=360;//底色的跨度
    private int mProgressStartAngle=275;//进度条起始角度

    public ZCircleProgressView(Context context) {
        this(context, null);
    }

    public ZCircleProgressView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ZCircleProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        @SuppressLint("Recycle")
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ZCircleProgressView);

        // 初始化背景圆环画笔
        mBackPaint = new Paint();
        mBackPaint.setStyle(Paint.Style.STROKE);    // 只描边，不填充
        mBackPaint.setStrokeCap(Paint.Cap.ROUND);   // 设置圆角
        mBackPaint.setAntiAlias(true);              // 设置抗锯齿
        mBackPaint.setDither(true);                 // 设置抖动
        mBackPaint.setStrokeWidth(typedArray.getDimension(R.styleable.ZCircleProgressView_zv_backWidth, 5));
        mBackPaint.setColor(typedArray.getColor(R.styleable.ZCircleProgressView_zv_backColor, Color.LTGRAY));

        // 初始化进度圆环画笔
        mProgPaint = new Paint();
        mProgPaint.setStyle(Paint.Style.STROKE);    // 只描边，不填充
        mProgPaint.setStrokeCap(Paint.Cap.ROUND);   // 设置圆角
        mProgPaint.setAntiAlias(true);              // 设置抗锯齿
        mProgPaint.setDither(true);                 // 设置抖动
        mProgPaint.setStrokeWidth(typedArray.getDimension(R.styleable.ZCircleProgressView_zv_progWidth, 10));
        mProgPaint.setColor(typedArray.getColor(R.styleable.ZCircleProgressView_zv_progColor, Color.BLUE));

        // 初始化进度圆环渐变色
        int startColor = typedArray.getColor(R.styleable.ZCircleProgressView_zv_progStartColor, -1);
        int firstColor = typedArray.getColor(R.styleable.ZCircleProgressView_zv_progFirstColor, -1);
        if (startColor != -1 && firstColor != -1) mColorArray = new int[]{startColor, firstColor};
        else mColorArray = null;

        // 初始化进度
        mProgress = typedArray.getInteger(R.styleable.ZCircleProgressView_zv_progress, 0);

        mStartAngle = typedArray.getInteger(R.styleable.ZCircleProgressView_zv_startAngle, 0);
        mSweepAngle = typedArray.getInteger(R.styleable.ZCircleProgressView_zv_sweepAngle, 360);
        mProgressStartAngle = typedArray.getInteger(R.styleable.ZCircleProgressView_zv_progressStartAngle, 275);
        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int viewWide = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
        int viewHigh = getMeasuredHeight() - getPaddingTop() - getPaddingBottom();
        int mRectLength = (int) ((viewWide > viewHigh ? viewHigh : viewWide) - (mBackPaint.getStrokeWidth() > mProgPaint.getStrokeWidth() ? mBackPaint.getStrokeWidth() : mProgPaint.getStrokeWidth()));
        int mRectL = getPaddingLeft() + (viewWide - mRectLength) / 2;
        int mRectT = getPaddingTop() + (viewHigh - mRectLength) / 2;
        mRectF = new RectF(mRectL, mRectT, mRectL + mRectLength, mRectT + mRectLength);

        // 设置进度圆环渐变色
        if (mColorArray != null && mColorArray.length > 1)
            mProgPaint.setShader(new LinearGradient(0, 0, 0, getMeasuredWidth(), mColorArray, null, Shader.TileMode.MIRROR));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawArc(mRectF, mStartAngle, mSweepAngle, false, mBackPaint);
        canvas.drawArc(mRectF, mProgressStartAngle, mSweepAngle * mProgress / 100, false, mProgPaint);
    }

    // ---------------------------------------------------------------------------------------------

    /**
     * 获取当前进度
     *
     * @return 当前进度（0-100）
     */
    public int getProgress() {
        return mProgress;
    }

    /**
     * 设置当前进度
     *
     * @param progress 当前进度（0-100）
     */
    public void setProgress(int progress) {
        this.mProgress = progress;
        invalidate();
    }

    /**
     * 设置当前进度，并展示进度动画。如果动画时间小于等于0，则不展示动画
     *
     * @param progress 当前进度（0-100）
     * @param animTime 动画时间（毫秒）
     */
    public void setProgress(int progress, long animTime) {
        if (animTime <= 0) setProgress(progress);
        else {
            ValueAnimator animator = ValueAnimator.ofInt(mProgress, progress);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    mProgress = (int) animation.getAnimatedValue();
                    invalidate();
                }
            });
            animator.setInterpolator(new OvershootInterpolator());
            animator.setDuration(animTime);
            animator.start();
        }
    }

    /**
     * 设置背景圆环宽度
     *
     * @param width 背景圆环宽度
     */
    public void setBackWidth(int width) {
        mBackPaint.setStrokeWidth(width);
        invalidate();
    }

    /**
     * 设置背景圆环颜色
     *
     * @param color 背景圆环颜色
     */
    public void setBackColor(@ColorRes int color) {
        mBackPaint.setColor(ContextCompat.getColor(getContext(), color));
        invalidate();
    }

    /**
     * 设置进度圆环宽度
     *
     * @param width 进度圆环宽度
     */
    public void setProgWidth(int width) {
        mProgPaint.setStrokeWidth(width);
        invalidate();
    }

    /**
     * 设置进度圆环颜色
     *
     * @param color 景圆环颜色
     */
    public void setProgColor(@ColorRes int color) {
        mProgPaint.setColor(ContextCompat.getColor(getContext(), color));
        mProgPaint.setShader(null);
        invalidate();
    }

    /**
     * 设置进度圆环颜色(支持渐变色)
     *
     * @param startColor 进度圆环开始颜色
     * @param firstColor 进度圆环结束颜色
     */
    public void setProgColor(@ColorRes int startColor, @ColorRes int firstColor) {
        mColorArray = new int[]{ContextCompat.getColor(getContext(), startColor), ContextCompat.getColor(getContext(), firstColor)};
        mProgPaint.setShader(new LinearGradient(0, 0, 0, getMeasuredWidth(), mColorArray, null, Shader.TileMode.MIRROR));
        invalidate();
    }

    /**
     * 设置进度圆环颜色(支持渐变色)
     *
     * @param colorArray 渐变色集合
     */
    public void setProgColor(@ColorRes int[] colorArray) {
        if (colorArray == null || colorArray.length < 2) return;
        mColorArray = new int[colorArray.length];
        for (int index = 0; index < colorArray.length; index++)
            mColorArray[index] = ContextCompat.getColor(getContext(), colorArray[index]);
        mProgPaint.setShader(new LinearGradient(0, 0, 0, getMeasuredWidth(), mColorArray, null, Shader.TileMode.MIRROR));
        invalidate();
    }
}
