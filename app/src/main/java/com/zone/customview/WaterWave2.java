package com.zone.customview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by zone on 2017/4/20.
 */

public class WaterWave2 extends View {

    private int mWaveHeight;
    private Paint mPaint;
    private Path mPath;
    private int mWL;
    private String TAG = "zoneLog";
    private int mFu;
    private int mOffset;
    private boolean mMIsAnimatorPlaying;
    private Paint mTextPaint;
    private String mText;

    public WaterWave2(Context context) {
        super(context);
        init();
    }


    public WaterWave2(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public WaterWave2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public WaterWave2(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.parseColor("#5DCEC6"));
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mFu = 100;

        mTextPaint = new Paint();
        mTextPaint.setColor(Color.WHITE);
        mTextPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(75);

        mOffset = 0;
        mWaveHeight = 0;

        mPath = new Path();
    }

    @Override
    protected void onDraw(final Canvas canvas) {
        super.onDraw(canvas);
//        canvas.drawPoint();
//        canvas.drawLine();
//
//        RectF rectF = new RectF()
//        canvas.drawRect();
//        canvas.drawVertices();
//        canvas.drawArc();
//        canvas.drawCircle();
//        canvas.drawText();
//        canvas.drawOval();
//        canvas.drawRoundRect();
//
//
//        mPaint.setAntiAlias();
//        mPaint.setColor();
//        mPaint.setARGB();
//        mPaint.setAlpha();
//        mPaint.setTextSize();
//        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
//        mPaint.setStrokeWidth();


        canvas.drawColor(Color.WHITE);
        mWL = getWidth();
        mPath.reset();
        final int sc = canvas.saveLayer(0, 0, getWidth(), getHeight(), null, Canvas.ALL_SAVE_FLAG);
        mPaint.setColor(Color.WHITE);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.qq3);
        canvas.drawBitmap(bitmap, getWidth() / 2 - bitmap.getWidth() / 2, getHeight() / 2 - bitmap.getHeight() / 2, mPaint);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        mPaint.setColor(Color.parseColor("#5DCEC6"));



        mPath.moveTo(mWL + mOffset, getHeight() - mWaveHeight);
        mPath.lineTo(mWL + mOffset, getHeight());
        mPath.lineTo(-mWL + mOffset, getHeight());
        mPath.lineTo(-mWL + mOffset, getHeight() - mWaveHeight);

//mPath.moveTo(mWL, getHeight()/2);
//mPath.lineTo(mWL, getHeight());
//mPath.lineTo(-mWL, getHeight());
//mPath.lineTo(-mWL, getHeight()/2);


        mPath.quadTo((-mWL * 3 / 4) + mOffset, getHeight() - mWaveHeight + mFu, (-mWL / 2) + mOffset, getHeight() - mWaveHeight); //画出第一段波纹的第一条曲线
        mPath.quadTo((-mWL / 4) + mOffset,     getHeight() - mWaveHeight - mFu, 0 + mOffset, getHeight() - mWaveHeight); //画出第一段波纹的第二条曲线
        mPath.quadTo((mWL / 4) + mOffset,      getHeight() - mWaveHeight + mFu, (mWL / 2) + mOffset, getHeight() - mWaveHeight); //画出第二段波纹的第一条曲线
        mPath.quadTo((mWL * 3 / 4) + mOffset,  getHeight() - mWaveHeight - mFu, mWL + mOffset, getHeight() - mWaveHeight);  //画出第二段波纹的第二条曲线
//        Log.d(TAG, "onDraw:  原值：" + mWaveHeight + "  增值： " + mOffset + "  幅度： " + mFu + "  总和：" + mWaveHeight + mOffset + mFu);
        mPath.close();
        canvas.drawPath(mPath, mPaint);
        if (!mMIsAnimatorPlaying) {
            mMIsAnimatorPlaying = true;
            ValueAnimator animator = ValueAnimator.ofInt(0, mWL);
            animator.setDuration(1000);
            animator.setRepeatCount(ValueAnimator.INFINITE);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    int value = (int) animation.getAnimatedValue();
                    mOffset = value;
                    postInvalidate();
                }
            });
            animator.start();
            ValueAnimator animatorHeight = ValueAnimator.ofInt(0, getHeight() + mFu);
            animatorHeight.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    mWaveHeight = (int) animation.getAnimatedValue();
                    mText = String.valueOf((int) ((double) mWaveHeight / (double) (getHeight() + mFu) * 100)) + "%";
                    if (mWaveHeight == getHeight() + mFu) {
                        animation.cancel();
                    }
                    if (mWaveHeight > getHeight() / 2) {
                        mTextPaint.setColor(Color.WHITE);
                    } else {
                        mTextPaint.setColor(Color.parseColor("#5DCEC6"));
                    }
                    Log.d("zoneLog", "   centerY:  " + mWaveHeight + "   height: " + getHeight() + "     " + String.valueOf((int) ((double) mWaveHeight / (double) (getHeight() + mFu) * 100)));
                    postInvalidate();
                }
            });
            animatorHeight.setDuration(10000);
            animatorHeight.start();
        }
        canvas.drawText(mText, getWidth() / 2 - mTextPaint.measureText(mText) / 2, getHeight() / 2, mTextPaint);
        mPaint.setXfermode(null);
        canvas.restoreToCount(sc);// 还原画布
    }
}
