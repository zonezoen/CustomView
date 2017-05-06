package com.zone.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zone on 2017/5/2.
 */

public class RadarSweepView extends View {
    private String TAG = "zoneLog";
    private float value;
    private Matrix matrix;
    private float sweepAngle;
    private Canvas mCanvas;
    private int x;
    private int y;
    private int radius;
    private List<MyPoint> pointList;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                x = (int) (Math.random() * 250);
                y = (int) (Math.random() * 250);
                Log.d(TAG, "handleMessage: "+x);
                Log.d(TAG, "handleMessage: "+y);

                pointList.add(new MyPoint(x,y,totalAngle));
                Log.d(TAG, "handleMessage: "+x);
                Log.d(TAG, "handleMessage: "+y);
                handler.sendEmptyMessageDelayed(0, 1200);

            }
        }
    };
    private boolean isStart;
    private int value1;
    private int totalAngle;

    public RadarSweepView(Context context) {
        super(context);
        init();
    }

    public RadarSweepView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RadarSweepView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        matrix = new Matrix();
        post(runnable);
        handler.sendEmptyMessageDelayed(0, 200);
        isStart = true;
        pointList = new ArrayList<>();
        radius = 300;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public RadarSweepView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    @Override
    protected void onDraw(final Canvas canvas) {
        super.onDraw(canvas);
        mCanvas = canvas;
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.start);
//        canvas.drawBitmap(bitmap,0,0,null);
        canvas.drawColor(getResources().getColor(R.color.huaweiClockView));
        final Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        SweepGradient sweepGradient = new SweepGradient(0, 0, new int[]{0X10000000, Color.WHITE}, null);
        paint.setShader(sweepGradient);
        RectF rectF = new RectF(0, 0, getWidth(), getHeight());
        canvas.saveLayerAlpha(rectF, 180, Canvas.FULL_COLOR_LAYER_SAVE_FLAG);
        canvas.save();
        canvas.concat(matrix);
        canvas.translate(getWidth() / 2, getHeight() / 2);
        canvas.drawCircle(0, 0, radius, paint);
        canvas.restore();
        final Paint redPaint = new Paint();
        redPaint.setAntiAlias(true);
        redPaint.setColor(Color.RED);
        redPaint.setStyle(Paint.Style.FILL_AND_STROKE);
//        if (x % 2 == 1) {
//            x = -x;
//        }
//        if (y % 2 == 1) {
//            y = -y;
//        }
//        if (isStart) {
//            isStart = false;
//            ValueAnimator animator = ValueAnimator.ofInt(5, 30);
//            animator.setDuration(800);
//            animator.setRepeatCount(ValueAnimator.INFINITE);
//            animator.setRepeatMode(ValueAnimator.REVERSE);
//            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                @Override
//                public void onAnimationUpdate(ValueAnimator animation) {
////                    Log.d(TAG, "onAnimationUpdate: " + animation.getAnimatedValue());
//                    value1 = (int) animation.getAnimatedValue();
//                    invalidate();
//
//                }
//            });
//            animator.start();
//        }
//        for (int i = 0; i < pointList.size(); i++) {
//            Log.d(TAG, "onDraw: size ==> " + pointList.size() + "  x===>" + pointList.get(i).x);
//            Log.d(TAG, "onDraw: y===>" + pointList.get(i).y);
//            canvas.drawCircle(getWidth() / 2 - x, getHeight() / 2 - y, value1, redPaint);
//        }
        canvas.restore();
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
//            Log.d(TAG, "onDraw: size ==> " + pointList.size());
//            for (int i = 0; i < pointList.size(); i++) {
//                Log.d(TAG, "onDraw: size ==> " + pointList.size() + "  x===>" + pointList.get(i).x);
//                Log.d(TAG, "onDraw: y===>" + pointList.get(i).y);
////            canvas.drawCircle(getWidth() / 2 - x, getHeight() / 2 - y, value1, redPaint);
//            }
            sweepAngle = 15;
            totalAngle += 15;
            Log.d(TAG, "run: " + totalAngle);
            matrix.postRotate(15, getWidth() / 2, getHeight() / 2);
            postInvalidate();
            postDelayed(runnable, 100);
        }
    };

    public MyPoint getMypoint(int x, int y, float angle) {
        return new MyPoint(x, y, angle);
    }

    class MyPoint {
        int x;
        int y;
        float angle;
        public MyPoint(int x, int y, float angle) {
            this.x = x;
            this.y = y;
            this.angle = angle;
        }


    }
}

