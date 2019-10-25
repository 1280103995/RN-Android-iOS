package com.rn.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.TypedValue;

import androidx.appcompat.widget.AppCompatTextView;

import com.rn.R;

public class CirCleBarView extends AppCompatTextView {

    //中心圆的颜色
    private int mCircleColor;
    private int mRingColor;
    private int mRingBgColor;
    private int mRingWidth;
    //画笔
    private Paint mPaint;
    private TextPaint mTextPaint;
    //进度
    private int progress = 100;

    //进度条类型
    private ProgressType mProgressType = ProgressType.COUNT_BACK;

    //进度倒计时时间
    private long timeMillis = 5000;

    //进度条通知。
    private OnProgressListener listener;

    public CirCleBarView(Context context) {
        this(context, null);
    }

    public CirCleBarView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CirCleBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context, attrs);
        initPaint();
    }

    private void initialize(Context context, AttributeSet attributeSet) {
        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.CirCleBarView);
        //内圆背景色
        mCircleColor = typedArray.getColor(R.styleable.CirCleBarView_circleColor, Color.WHITE);
        //外圆环属性
        mRingWidth = (int)typedArray.getDimension(R.styleable.CirCleBarView_ringWidth, TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics()));//默认外圆环宽度2dp

        mRingColor = typedArray.getColor(R.styleable.CirCleBarView_ringColor, Color.BLACK);
        mRingBgColor = typedArray.getColor(R.styleable.CirCleBarView_ringBgColor, Color.GRAY);

        typedArray.recycle();
    }

    private void initPaint(){
        mPaint = new Paint();
        mPaint.setAntiAlias(true); // 抗锯齿
        mPaint.setDither(true); // 防抖动

        mTextPaint = getPaint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setDither(true);
        mTextPaint.setColor(getCurrentTextColor());
        mTextPaint.setTextAlign(Paint.Align.CENTER);
    }

    /**
     * 设置进度条值
     */
    public void setProgress(int progress) {
        this.progress = validateProgress(progress);
        invalidate();
    }


    private int validateProgress(int progress) {
        if (progress > 100)
            progress = 100;
        else if (progress < 0)
            progress = 0;
        return progress;
    }

    /**
     * 获取进度值
     */
    public int getProgress() {
        return progress;
    }

    /**
     * 设置倒计时时间
     */
    public void setTimeMillis(long timeMillis) {
        this.timeMillis = timeMillis;
        invalidate();
    }

    /**
     * 获取倒计时时间
     */
    public long getTimeMillis() {
        return this.timeMillis;
    }

    /**
     * 设置进度条类型  是0-100 还是100_0
     */
    public void setProgressType(ProgressType progressType) {
        this.mProgressType = progressType;
        resetProgress();
        invalidate();
    }

    private void resetProgress() {
        switch (mProgressType) {
            case COUNT:
                progress = 0;
                break;
            case COUNT_BACK:
                progress = 100;
                break;
        }
    }

    /**
     * 获取进度条类型
     */
    public ProgressType getProgressType() {
        return mProgressType;
    }

    /**
     * 设置进度监听
     */
    public void setProgressListener(OnProgressListener listener) {
        this.listener = listener;
    }

    public void start() {
        stop();
        post(progressChangeTask);
    }

    /**
     * 开始旋转倒计时
     */
    public void reStart() {
        resetProgress();
        start();
    }

    public void stop() {
        removeCallbacks(progressChangeTask);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int center = this.getWidth() / 2;
        int radius = center - mRingWidth / 2;

        //画中心圆
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(mCircleColor);
        canvas.drawCircle(center, center, radius, mPaint);

        //画外圆圈底色
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mRingWidth);
        mPaint.setColor(mRingBgColor);
        canvas.drawCircle(center, center, radius, mPaint);

        //画外圆圈进度条
        mPaint.setColor(mRingColor);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mRingWidth);
        // mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setAntiAlias(true);
        RectF oval = new RectF(center - radius, center - radius, center + radius, center + radius);
        canvas.drawArc(oval, -90, -360.0f * progress / 100, false, mPaint);

        //画文字
        mTextPaint.setStrokeWidth(0);// 注意此处一定要重新设置宽度为0,否则绘制的文字会重叠
        Rect bounds = new Rect(); // 文字边框
        String text = getText().toString();
        mTextPaint.getTextBounds(text, 0, text.length(), bounds); // 获得绘制文字的边界矩形
        Paint.FontMetricsInt fontMetrics = mTextPaint.getFontMetricsInt(); // 获取绘制Text时的四条线
        int baseline = center + (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom; // 计算文字的基线,方法见http://blog.csdn.net/harvic880925/article/details/50423762
        canvas.drawText(text, center, baseline, mTextPaint); // 绘制表示进度的文字
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measureWidth = MeasureSpec.getSize(widthMeasureSpec) + mRingWidth * 2;
        int measureHeight = MeasureSpec.getSize(heightMeasureSpec) + mRingWidth * 2;
        setMeasuredDimension(Math.min(measureWidth, measureHeight), Math.min(measureWidth, measureHeight));
    }

    private Runnable progressChangeTask = new Runnable() {
        @Override
        public void run() {
            removeCallbacks(this);
            switch (mProgressType) {
                //判断是顺数进度条还是倒数进度条
                case COUNT:
                    progress += 1;
                    break;
                case COUNT_BACK:
                    progress -= 1;
                    break;
            }
            if (progress >= 0 && progress <= 100) {
                if (listener != null)
                    listener.onProgress(mProgressType, progress);
                invalidate();
                postDelayed(progressChangeTask, timeMillis / 100);
            } else
                progress = validateProgress(progress);
        }
    };

    public enum ProgressType {
        /**
         * 顺数进度条，从0-100；
         */
        COUNT,

        /**
         * 倒数进度条，从100-0；
         */
        COUNT_BACK
    }

    public interface OnProgressListener {
        void onProgress(ProgressType type, int progress);
    }
}
