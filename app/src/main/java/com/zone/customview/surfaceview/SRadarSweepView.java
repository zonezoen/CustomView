package com.zone.customview.surfaceview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.SweepGradient;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.zone.customview.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zone on 2017/5/6.
 */

public class SRadarSweepView extends SurfaceView implements SurfaceHolder.Callback, Runnable {
    private SurfaceHolder holder;//
    private Canvas canvas;//
    private boolean mIsDrawing;//
    private int radius;//圆半径
    private String TAG = "zoneLog";//Log 日志的 tag
    private Matrix matrix;//view 的矩阵参数，用于旋转圆形
    private float sweepAngle;//
    private boolean isStart;//是否开始 valueanimator
    private int value1;//valueanimator 的渐变值
    private int x;//红点的 x 坐标值
    private int y;//红点的 y 坐标值
    private int totalAngle;//总旋转角度
    private Paint redPointPaint;//红点画笔
    private Paint sweepPaint;//圆形画笔，绘制圆角渐变
    private Paint strokeWhitePaint;//描边白色画笔，用于绘制空心圆圈
    private List<SRadarSweepView.MyPoint> pointList;//记录红点的坐标
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                int currentAngle = totalAngle % 360;//计算出一个圆范围内的旋转角度
                int currentRadius = (int) (radius * Math.random()) + 50;//随机取得一个半径
                x = (int) (currentRadius * Math.cos(currentAngle));//通过三角函数，计算出 x y 坐标值
                y = (int) (currentRadius * Math.sin(currentAngle));
                if (currentAngle > 0 && currentAngle < 90) {//计算出各个象限的情况
                    x = Math.abs(x);
                    y = Math.abs(y);
                } else if (currentAngle > 90 && currentAngle < 180) {
                    x = -Math.abs(x);
                    y = Math.abs(y);
                } else if (currentAngle > 180 && currentAngle < 270) {
                    x = -Math.abs(x);
                    y = -Math.abs(y);
                } else if (currentAngle > 270 && currentAngle < 360) {
                    y = -Math.abs(y);
                    x = Math.abs(x);
                } else if (currentAngle == 0 || currentAngle == 360) {
                    y = 0;
                    x = Math.abs(x);

                } else if (currentAngle == 90) {
                    x = 0;
                    y = Math.abs(y);

                } else if (currentAngle == 180) {
                    y = 0;
                    x = -Math.abs(x);
                } else if (currentAngle == 270) {
                    x = 0;
                    y = -Math.abs(y);
                }

                pointList.add(0, new MyPoint(x, y, totalAngle));

                if (pointList.size() > 5) {//超过 5 个数据时，抹掉最后一个数据
                    pointList.remove(pointList.size() - 1);
                }
                handler.sendEmptyMessageDelayed(0, 1000);//发送 message 实现不断循环

            }
        }
    };


    public SRadarSweepView(Context context) {
        super(context);
        init();
    }

    public SRadarSweepView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SRadarSweepView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        holder = getHolder();
        holder.addCallback(this);
        sweepAngle = 8;//旋转角度
        matrix = new Matrix();
        post(runnable);//实现圆形的不断选装
        handler.sendEmptyMessageDelayed(0, 1000);
        isStart = true;
        pointList = new ArrayList<>();
        radius = 300;

        redPointPaint = new Paint();
        redPointPaint.setAntiAlias(true);
        redPointPaint.setColor(Color.RED);
        redPointPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        sweepPaint = new Paint();
        sweepPaint.setAntiAlias(true);
        sweepPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        SweepGradient sweepGradient = new SweepGradient(0, 0, new int[]{0X10000000, Color.WHITE}, null);//角度渐变，由透明变为白色
        sweepPaint.setShader(sweepGradient);//设置 shader

        strokeWhitePaint = new Paint();
        strokeWhitePaint.setAntiAlias(true);
        strokeWhitePaint.setColor(Color.WHITE);
        strokeWhitePaint.setStyle(Paint.Style.STROKE);
        strokeWhitePaint.setStrokeWidth(1);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public SRadarSweepView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mIsDrawing = true;
        new Thread(this).start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mIsDrawing = false;
    }

    @Override
    public void run() {
        while (mIsDrawing) {
            drawCanvas();
        }
    }

    private void drawCanvas() {
        try {
            canvas = holder.lockCanvas();
//            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.start);
//            canvas.drawBitmap(bitmap, 0, 0, null);
            canvas.drawColor(getResources().getColor(R.color.huaweiClockView));//绘制背景颜色
            canvas.save();//在另外一个图层来绘制圆形，否则会影响到后续操作
            canvas.concat(matrix);//获取 view 的矩阵参数
            canvas.translate(getWidth() / 2, getHeight() / 2);//将原点移动至中心
            canvas.drawCircle(0, 0, radius, sweepPaint);//绘制渐变圆
            canvas.drawCircle(0, 0, radius + 80, strokeWhitePaint);//以下是绘制描边圆圈
            canvas.drawCircle(0, 0, radius - 80, strokeWhitePaint);//
            canvas.drawCircle(0, 0, radius - 160, strokeWhitePaint);//
            canvas.drawCircle(0, 0, radius - 240, strokeWhitePaint);//
            canvas.restore();//合并之前的操作，相当于 photoshop 中的图层合并


//            if (isStart) {用于实现小红点的大小缩放效果
//                isStart = false;
//                ValueAnimator animator = ValueAnimator.ofInt(15, 30);
//                animator.setDuration(800);
//                animator.setRepeatCount(ValueAnimator.INFINITE);
//                animator.setRepeatMode(ValueAnimator.REVERSE);
//                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                    @Override
//                    public void onAnimationUpdate(ValueAnimator animation) {
////                    Log.d(TAG, "onAnimationUpdate: " + animation.getAnimatedValue());
//                        value1 = (int) animation.getAnimatedValue();
//                        invalidate();
//
//                    }
//                });
//                animator.start();
//            }
            canvas.translate(getWidth() / 2, getHeight() / 2);
            for (int i = 0; i < pointList.size(); i++) {
                canvas.drawCircle(pointList.get(i).x, pointList.get(i).y, 30, redPointPaint);
//                canvas.drawCircle(pointList.get(i).x, pointList.get(i).y, value1, redPointPaint);
            }
            canvas.restore();

        } catch (Exception e) {
        } finally {
            if (canvas != null) {
                holder.unlockCanvasAndPost(canvas);
            }
        }
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            totalAngle += sweepAngle;//统计总的旋转角度
            matrix.postRotate(sweepAngle, getWidth() / 2, getHeight() / 2);//旋转矩阵
            postInvalidate();//刷新
            postDelayed(runnable, 200);//调用自身，实现不断循环
        }
    };

    class MyPoint {//用于记录小红点的圆心
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
