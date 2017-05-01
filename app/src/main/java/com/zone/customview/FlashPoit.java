package com.zone.customview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by zone on 2017/4/12.
 */

public class FlashPoit extends View {

    private Paint mPaint;
    private boolean mIsAnimatorPlaying = false;
    private Paint mPaint2;
    private Point movePoit;
    private Path mPath;

    public FlashPoit(Context context) {
        super(context);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

        mPaint2 = new Paint();
        mPaint2.setColor(Color.YELLOW);
        mPaint2.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint2.setAntiAlias(true);

        movePoit = new Point(getWidth() / 2, getHeight() / 2);
        mPath = new Path();

    }

    public FlashPoit(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();

    }

    public FlashPoit(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public FlashPoit(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

            case MotionEvent.ACTION_MOVE:
                movePoit.x = (int) event.getX();
                movePoit.y = (int) event.getY();
                invalidate();
                break;
            case MotionEvent.ACTION_UP:

                break;

        }

        return true;

    }

    @Override
    protected void onDraw(final Canvas canvas) {
        super.onDraw(canvas);
        mPath.reset();
//        mPath.moveTo(getWidth() / 2 - 100, getHeight() / 2);
//        mPath.quadTo(movePoit.x, movePoit.y, getWidth() / 2 + 100, getHeight() / 2);
//        mPath.close();
//        canvas.drawPath(mPath, mPaint2);

        canvas.drawCircle(getWidth()/2+50,getHeight()/2,100, mPaint2);
        canvas.drawCircle(getWidth() / 2, getHeight() / 2+200, 100, mPaint);

        if (!mIsAnimatorPlaying) {
            mIsAnimatorPlaying = true;
            ValueAnimator animator = ValueAnimator.ofInt(0, 500);
            animator.setDuration(2000);
            animator.setRepeatCount(ValueAnimator.INFINITE);
            animator.setRepeatMode(ValueAnimator.REVERSE);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    int alpha = (int) animation.getAnimatedValue();
                    mPaint.setAlpha(alpha);
                    mPaint2.setAlpha(alpha);
                    invalidate();
                    Log.d("zoneLog", String.valueOf(alpha));

                }
            });
            animator.start();

            Log.d("zoneLog", "开始");
        } else {
//            Log.d("zoneLog", "重绘");
        }
    }
}
