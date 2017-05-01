package com.zone.customview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by zone on 2017/4/9.
 */

public class PentagonView extends View {
    private int dataCount = 5;//多边形维度
    private float radian = (float) (Math.PI * 2 / dataCount);//每个维度的角度
    private float radius;//一条星射线的长度，即是发散的五条线白线
    private int centerX;//中心坐标 Y
    private int centerY;//中心坐标 X
    private String[] titles = {"履约能力", "信用历史", "人脉关系", "行为偏好", "身份特质"};//标题
    private int[] icos = {R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher};//五个维度的图标
    private float[] data = {170, 180, 100, 170, 150};//五个维度的数据值
    private float maxValue = 190;//每个维度的最大值
    private Paint mPaintText;//绘制文字的画笔
    private int radarMargin = 40;//
    private int mAlpha;//白色五边形的透明度
    private Path mPentagonPath;//记录白色五边形的路径
    private Paint mPentagonPaint;//绘制白色五边形的画笔
    private Path mDataPath;//记录红色五边形的路径
    private Paint mDataPaint;//绘制红色五边形的画笔


    public PentagonView(Context context) {
        super(context);
        init();
    }

    public PentagonView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PentagonView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public PentagonView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        mPentagonPaint = new Paint();//初始化白色五边形的画笔
        mPentagonPaint.setAntiAlias(true);//
        mPentagonPaint.setStrokeWidth(5);//
        mPentagonPaint.setColor(Color.WHITE);//
        mPentagonPaint.setStyle(Paint.Style.FILL_AND_STROKE);//

        mDataPaint = new Paint();//初始化红色五边形的画笔
        mDataPaint.setAntiAlias(true);//
        mDataPaint.setStrokeWidth(10);//
        mDataPaint.setColor(Color.RED);//
        mDataPaint.setAlpha(150);//
        mDataPaint.setStyle(Paint.Style.STROKE);//

        mPaintText = new Paint();//初始化文字画笔
        mPaintText.setAntiAlias(true);//
        mPaintText.setTextSize(50);//
        mPaintText.setColor(Color.WHITE);//
        mPaintText.setStyle(Paint.Style.FILL);//

        mPentagonPath = new Path();//初始化白色五边形路径
        mDataPath = new Path();//初始化红色五边形路径
        radius = 80;//星射线的初始值，也是最小的五边形的一条星射线的长度（后期会递增）
        mAlpha = 150;//白色五边形的透明度（后期后递减）
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(getResources().getColor(R.color.colorAccent));
        centerX = getWidth() / 2;
        centerY = getHeight() / 2;
        drawPentagon(canvas);//绘制白色五边形和红色五边形
        drawFiveLine(canvas);//绘制五条星射线
        drawTitle(canvas);//绘制五个标题
        drawIcon(canvas);//绘制五个图标
        drawScore(canvas);//绘制中间的分数
    }

    private void drawPentagon(Canvas canvas) {
        for (int j = 0; j < 3; j++) {//绘制三层白色五边形
            radius += 70;//每一层五边形的星射线增加 70 的长度
            mAlpha -= 30;//每一层五边形的透明度减少 30
            mPentagonPaint.setAlpha(mAlpha);
            for (int i = 0; i < dataCount; i++) {//绘制一层
                if (i == 0) {
                    mPentagonPath.moveTo(getPoint(i).x, getPoint(i).y);
                } else {
                    mPentagonPath.lineTo(getPoint(i).x, getPoint(i).y);
                }
            }
            mPentagonPath.close();
            canvas.drawPath(mPentagonPath, mPentagonPaint);
        }

        for (int i = 0; i < dataCount; i++) {//绘制红色五边形
            float percent = data[i] / maxValue;//数据值与最大值的百分比
            if (i == 0) {
                mDataPath.moveTo(getPoint(i, 0, percent).x, getPoint(i, 0, percent).y);//通过百分比计算出红色顶点的位置
            } else {
                mDataPath.lineTo(getPoint(i, 0, percent).x, getPoint(i, 0, percent).y);
            }
        }
        mDataPath.close();
        canvas.drawPath(mDataPath, mDataPaint);
    }

    private void drawFiveLine(Canvas canvas) {
        mPentagonPaint.setColor(Color.WHITE);//设置颜色为白色
        mPentagonPaint.setStrokeWidth(2);//设置宽度为2
        for (int i = 0; i < dataCount; i++) {
            canvas.drawLine(centerX, centerY, getPoint(i).x, getPoint(i).y, mPentagonPaint);//绘制
        }
    }

    private void drawIcon(Canvas canvas) {
        for (int i = 0; i < dataCount; i++) {
            int x = getPoint(i, radarMargin, 1).x;//获取添加 radarMargin 值之后的 X 坐标的指
            int y = getPoint(i, radarMargin, 1).y;//获取添加 radarMargin 值之后的 Y 坐标的指
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), icos[i]);
            int iconHeight = bitmap.getHeight();
            int iconWidth = bitmap.getWidth();
            int titleWidth = (int) mPaintText.measureText(titles[i]);
            switch (i) {
                case 0:
                    x += (titleWidth - iconWidth) / 2;
                    y -= (iconHeight + getTextHeight(titles[i]));
                    break;
                case 1:
                    x += (titleWidth - iconWidth) / 2;
                    y -= (iconHeight / 2 + getTextHeight(titles[i]));
                    break;
                case 2:
                    x -= titleWidth - (titleWidth - iconWidth) / 2;
                    y -= (iconHeight / 2 + getTextHeight(titles[i]));
                    break;
                case 3:
                    x -= titleWidth - (titleWidth - iconWidth) / 2;
                    y -= (iconHeight + getTextHeight(titles[i]));
                    break;
                case 4:
                    x -= (iconHeight / 2);
                    y -= (iconHeight + getTextHeight(titles[i]));
                    break;
            }
            canvas.drawBitmap(bitmap, x, y, mPaintText);
        }
    }

    private int getTextHeight(String text) {
        Paint.FontMetrics fm = mPaintText.getFontMetrics();
        return (int) Math.ceil(fm.descent - fm.ascent);
    }

    private void drawTitle(Canvas canvas) {
        for (int i = 0; i < dataCount; i++) {
            int x = getPoint(i, radarMargin, 1).x;//获取添加 radarMargin 值之后的 X 坐标的指
            int y = getPoint(i, radarMargin, 1).y;//获取添加 radarMargin 值之后的 Y 坐标的指
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), icos[i]);
            int iconHeight = bitmap.getHeight();
            int titleWidth = (int) mPaintText.measureText(titles[i]);
            switch (i) {
                case 1://说明一下为什么是 iconHeight / 2 ，主要是因为这样会比较好看
                    y += iconHeight / 2;
                    break;
                case 2:
                    x -= titleWidth;
                    y += iconHeight / 2;
                    break;
                case 3:
                    x -= titleWidth;
                    break;
                case 4:
                    x -= titleWidth / 2;
                    break;
            }
            canvas.drawText(titles[i], x, y, mPaintText);
        }
    }

    private void drawScore(Canvas canvas) {
        mPaintText.setColor(getResources().getColor(R.color.colorAccent));
        int score = 0;
        for (int i = 0; i < data.length; i++) {//累加分数值
            score += data[i];
        }
        String str_score = String.valueOf(score);
        Paint.FontMetrics fm = mPaintText.getFontMetrics();//用于计算文字的高度
        canvas.drawText(str_score, centerX - mPaintText.measureText(str_score) / 2, (centerY + (int) Math.ceil(fm.descent - fm.ascent) / 2), mPaintText);
    }

    public Point getPoint(int position) {
        return getPoint(position, 0, 1);
    }

// 右上角的顶点为第一个点，顺时针计算，position 依次是 0，1，2，3，4
// 参数：position：顶点的位置，radarMargin：边距，percent：星射线长度的百分比，用于计算红色五边形的顶点
    public Point getPoint(int position, int radarMargin, float percent) {//以五边形的中心点为坐标原点
        int x = 0;
        int y = 0;
        switch (position) {
            case 0://第一象限，右上角顶点的坐标计算
                x = (int) (centerX + (radius + radarMargin) * Math.sin(radian) * percent);
                y = (int) (centerY - (radius + radarMargin) * Math.cos(radian) * percent);
                break;
            case 1://第四象限，右下角顶点的坐标计算
                x = (int) (centerX + (radius + radarMargin) * Math.sin(radian / 2) * percent);
                y = (int) (centerY + (radius + radarMargin) * Math.cos(radian / 2) * percent);
                break;
            case 2://第三象限，左下角顶点的坐标计算
                x = (int) (centerX - (radius + radarMargin) * Math.sin(radian / 2) * percent);
                y = (int) (centerY + (radius + radarMargin) * Math.cos(radian / 2) * percent);
                break;
            case 3://第二象限，左上角顶点的坐标计算
                x = (int) (centerX - (radius + radarMargin) * Math.sin(radian) * percent);
                y = (int) (centerY - (radius + radarMargin) * Math.cos(radian) * percent);
                break;
            case 4:// Y 轴正方向顶点的计算
                x = centerX;
                y = (int) (centerY - (radius + radarMargin) * percent);
                break;
        }
        return new Point(x, y);
    }
}
