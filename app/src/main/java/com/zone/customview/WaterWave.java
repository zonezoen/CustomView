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

public class WaterWave extends View {

    private int mWaveHeight;//水波纹高度
    private Paint mPaint;//绘制水波纹的画笔
    private Path mPath;//绘制水波纹的路径
    private int mWL;//屏幕宽度
    private String TAG = "zoneLog";//Log 日志的 Tag
    private int mFu;//水波纹的振幅
    private int mOffset;//水波纹移动的偏移值
    private boolean mMIsAnimatorPlaying;//用于判断是否第一次执行 ValueAnimator
    private Paint mTextPaint;//文字画笔
    private String mText;//进度值文字

    public WaterWave(Context context) {
        super(context);
        init();
    }

    public WaterWave(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public WaterWave(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public WaterWave(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        mPaint = new Paint();//用于绘制波浪
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.parseColor("#5DCEC6"));
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mFu = 100;//波浪的振幅
        mTextPaint = new Paint();//用于绘制文字
        mTextPaint.setColor(Color.WHITE);
        mTextPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(75);
        mOffset = 0;//水波纹移动的偏移值
        mWaveHeight = 0;//进度值，也是水波纹的高度
        mPath = new Path();//用于绘制波浪
    }

    @Override
    protected void onDraw(final Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);
        mWL = getWidth();
        mPath.reset();
        final int sc = canvas.saveLayer(0, 0, getWidth(), getHeight(), null, Canvas.ALL_SAVE_FLAG);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.qq3);//获取 bitmap 资源
        canvas.drawBitmap(bitmap, getWidth() / 2 - bitmap.getWidth() / 2, getHeight() / 2 - bitmap.getHeight() / 2, null);//绘制 bitmap
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));//设置特效画笔
        mPaint.setColor(Color.parseColor("#5DCEC6"));

        mPath.moveTo(mWL + mOffset, getHeight() - mWaveHeight);//因为 y 轴正方向是向下的，所以减去水波纹的高度
        mPath.lineTo(mWL + mOffset, getHeight());//绘制右边的线段
        mPath.lineTo(-mWL + mOffset, getHeight());//绘制底部的线段
        mPath.lineTo(-mWL + mOffset, getHeight() - mWaveHeight);//绘制左边的线段

        mPath.quadTo((-mWL * 3 / 4) + mOffset, getHeight() - mWaveHeight + mFu, (-mWL / 2) + mOffset, getHeight() - mWaveHeight); //画出第一段波纹的第一条曲线
        mPath.quadTo((-mWL / 4) + mOffset, getHeight() - mWaveHeight - mFu, 0 + mOffset, getHeight() - mWaveHeight); //画出第一段波纹的第二条曲线
        mPath.quadTo((mWL / 4) + mOffset, getHeight() - mWaveHeight + mFu, (mWL / 2) + mOffset, getHeight() - mWaveHeight); //画出第二段波纹的第一条曲线
        mPath.quadTo((mWL * 3 / 4) + mOffset, getHeight() - mWaveHeight - mFu, mWL + mOffset, getHeight() - mWaveHeight);  //画出第二段波纹的第二条曲线
        mPath.close();//封闭曲线
        canvas.drawPath(mPath, mPaint);//绘制曲线
        if (!mMIsAnimatorPlaying) {//判断是否第一次开始 ValueAnimator
            mMIsAnimatorPlaying = true;
            ValueAnimator animator = ValueAnimator.ofInt(0, mWL);//设置移动范围为一个屏幕宽度
            animator.setDuration(1000);//设置持续时间为1秒
            animator.setRepeatCount(ValueAnimator.INFINITE);//设置为无限循环
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    int value = (int) animation.getAnimatedValue();
                    mOffset = value;//修改偏移量
                    postInvalidate();//刷新界面
                }
            });
            animator.start();
            ValueAnimator animatorHeight = ValueAnimator.ofInt(0, getHeight() + mFu);
            animatorHeight.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    mWaveHeight = (int) animation.getAnimatedValue();
                    mText = String.valueOf((int) ((double) mWaveHeight / (double) (getHeight() + mFu) * 100)) + "%";//计算进度的百分比
                    if (mWaveHeight == getHeight() + mFu) {//当水位高于屏幕高度，停止水波纹移动效果
                        animation.cancel();
                    }
                    if (mWaveHeight > getHeight() / 2) {//当水位高于文字的时候，字体为白色，否则为浅蓝色
                        mTextPaint.setColor(Color.WHITE);
                    } else {
                        mTextPaint.setColor(Color.parseColor("#5DCEC6"));
                    }
                    postInvalidate();
                }
            });
            animatorHeight.setDuration(10000);//持续10秒
            animatorHeight.start();
        }
        canvas.drawText(mText, getWidth() / 2 - mTextPaint.measureText(mText) / 2, getHeight() / 2, mTextPaint);
        mPaint.setXfermode(null);
        canvas.restoreToCount(sc);// 还原画布
    }
}
