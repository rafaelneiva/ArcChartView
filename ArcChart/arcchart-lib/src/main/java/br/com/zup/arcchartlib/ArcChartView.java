package br.com.zup.arcchartlib;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

/**
 * Created by rafaelneiva on 23/11/16.
 */

public class ArcChartView extends View {

    // The comments here is used to draw a progress indicator

    private RectF mCircleRect;
    private Paint mProgressPaint;
    private Paint mBgPaint;
//    private Paint mDotPaint;
    private float mCirclePadding = 100;
    private int progress;

    private int mProgressColor;
    private final int DEFAULT_COLOR = Color.RED;

    private int mBgColor;
    private final int DEFAULT_BG_COLOR = Color.GRAY;

    private float mStrokeWidth;
    private final int DEFAULT_STROKE_WIDTH = 30;

    public void setProgress(int progress) {
        this.progress = (int) (progress * 1.8);
    }

    public void startAnimation(int endProgress) {
        ObjectAnimator anim = ObjectAnimator.ofInt(this, "progress", 0, endProgress);
        anim.setDuration(1000);
        anim.setInterpolator(new AccelerateDecelerateInterpolator());
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                invalidate();
            }
        });
        anim.start();
    }

    public ArcChartView(Context context) {
        super(context);
        init();
    }

    public ArcChartView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ArcChartView, 0, 0);

        mProgressColor = typedArray.getColor(R.styleable.ArcChartView_acv_progress_color, DEFAULT_COLOR);
        mBgColor = typedArray.getColor(R.styleable.ArcChartView_acv_bg_color, DEFAULT_BG_COLOR);
        mStrokeWidth = dpToPx(typedArray.getDimension(R.styleable.ArcChartView_acv_stroke_width, DEFAULT_STROKE_WIDTH));
        setProgress(typedArray.getInt(R.styleable.ArcChartView_acv_progress, 50));

        typedArray.recycle();

        init();
    }

    private void init() {

        // bg
        mBgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBgPaint.setColor(mBgColor);
        mBgPaint.setStyle(Paint.Style.STROKE);
        mBgPaint.setStrokeWidth(mStrokeWidth);
        mBgPaint.setStrokeCap(Paint.Cap.ROUND);

        // progressDot paint
//        mDotPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
//        mDotPaint.setColor(mProgressColor);
//        mDotPaint.setStyle(Paint.Style.FILL);
//        mDotPaint.setStrokeWidth(mStrokeWidth);
//        mDotPaint.setStrokeCap(Paint.Cap.ROUND);

        // progress
        mProgressPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mProgressPaint.setColor(mProgressColor);
        mProgressPaint.setStyle(Paint.Style.STROKE);
        mProgressPaint.setStrokeWidth(mStrokeWidth - (mStrokeWidth / 20));
        mProgressPaint.setStrokeCap(Paint.Cap.ROUND);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawChart(canvas);
    }

    private void drawChart(Canvas canvas) {
//        float radius = (mCircleRect.width() / 2);
//        float cx = mCircleRect.centerX();
//        float cy = mCircleRect.centerY();
//        float angle = (float) Math.toRadians(progress + 90);

        // flip canvas horizontally
        canvas.scale(-1, 1, getWidth() / 2, getHeight() / 2);

        canvas.drawArc(mCircleRect, 0, 180, false, mBgPaint);
        canvas.drawArc(mCircleRect, 0, progress, false, mProgressPaint);

//        float xPos = (float) (cx + radius * Math.sin(angle));
//        float yPos = (float) (cy - radius * Math.cos(angle));

//        canvas.drawCircle(xPos, yPos, 1f, mProgressPaint);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mCircleRect = new RectF(0 + mCirclePadding, 0 + mCirclePadding, getWidth() - mCirclePadding, getHeight() - mCirclePadding);
    }

    public float dpToPx(float dp) {
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        return (float) Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }
}
