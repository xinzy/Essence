package com.xinzy.essence.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

import com.xinzy.essence.R;
import com.xinzy.essence.util.L;

/**
 * Created by xinzy on 17/1/19.
 */
public class CircleProgress extends View
{
    private static final int DEFAULT_COLOR           = 0xFFFFFFFF;
    private static final int DEFAULT_BACKGROUD_COLOR = 0xFFF0F0F0;
    private static final int DEFAULT_TEXT_COLOR      = 0xFFDDDDDD;

    private RectF                mRectF;
    private Point                mCenter;
    private int                  mRadius;

    private int     color;
    private int     backgroundColor;
    private int     textColor;
    private boolean showProgress;

    private Paint mPaint;
    private int   progress;

    public CircleProgress(Context context)
    {
        this(context, null);
    }

    public CircleProgress(Context context, AttributeSet attrs)
    {
        this(context, attrs, 0);
    }

    public CircleProgress(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CircleProgress(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes)
    {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs)
    {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CircleProgress);
        color = ta.getColor(R.styleable.CircleProgress_color, DEFAULT_COLOR);
        backgroundColor = ta.getColor(R.styleable.CircleProgress_backgroundColor, DEFAULT_BACKGROUD_COLOR);
        textColor = ta.getColor(R.styleable.CircleProgress_textColor, DEFAULT_TEXT_COLOR);
        showProgress = ta.getBoolean(R.styleable.CircleProgress_showProgress, false);
        ta.recycle();

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        mCenter = new Point();
        mRectF = new RectF();
    }

    public void setProgress(int progress)
    {
        if (progress >= 0 && progress <= 100)
        {
            this.progress = progress;
            postInvalidate();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width  = getMeasuredWidth();
        int height = getMeasuredHeight();
        int min    = Math.min(width, height);
        setMeasuredDimension(min, min);

        mRadius = min / 2;
        mCenter.set(mRadius, mRadius);
        mRectF.set(0, 0, min, min);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        canvas.drawColor(0);

        mPaint.setColor(backgroundColor);
        canvas.drawCircle(mCenter.x, mCenter.y, mRadius, mPaint);

        float angle = progress / 100f * 360;
        mPaint.setColor(color);
        canvas.drawArc(mRectF, 0, angle, true, mPaint);

        if (showProgress && progress <= 100)
        {
            mPaint.setColor(textColor);
            mPaint.setTextSize(mRectF.width() / 3);
            mPaint.setTextAlign(Paint.Align.CENTER);
            Paint.FontMetricsInt fontMetricsInt = mPaint.getFontMetricsInt();

            String text     = String.valueOf(progress);
            int    baseline = (int) ((mRectF.bottom + mRectF.top - fontMetricsInt.bottom - fontMetricsInt.top) / 2);
            canvas.drawText(text, mRectF.centerX(), baseline, mPaint);
        }
    }
}
