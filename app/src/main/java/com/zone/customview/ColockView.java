package com.zone.customview;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by zone on 2017/4/8.
 */

public class ColockView extends View {
    private Paint mPaintCircle;

    public ColockView(Context context) {
        super(context);

    }

    public ColockView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ColockView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ColockView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaintCircle = new Paint();
        mPaintCircle.setStyle(Paint.Style.STROKE);
        mPaintCircle.setAntiAlias(true);
        mPaintCircle.setStrokeWidth(5);
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, getWidth() / 2, mPaintCircle);

        Paint paintDegree = new Paint();

        for (int i = 0; i < 24; i++) {
            if (i == 0 || i == 6 || i == 12 || i == 18) {
                paintDegree.setStrokeWidth(5);
                paintDegree.setTextSize(30);
                canvas.drawLine(getWidth() / 2, getHeight() / 2 - getWidth() / 2, getWidth() / 2, getHeight() / 2 - getWidth() / 2 + 60, paintDegree);
                String degree = String.valueOf(i / 2);
                canvas.drawText(degree, getWidth() / 2 - paintDegree.measureText(degree) / 2, getHeight() / 2 - getWidth() / 2 + 90, paintDegree);
            } else {
                paintDegree.setStrokeWidth(3);
                paintDegree.setTextSize(24);
                canvas.drawLine(getWidth() / 2, getHeight() / 2 - getWidth() / 2, getWidth() / 2, getHeight() / 2 - getWidth() / 2 + 30, paintDegree);
                String degree = String.valueOf(i);
                canvas.drawText(degree, getWidth() / 2 - paintDegree.measureText(degree) / 2, getHeight() / 2 - getWidth() / 2 + 60, paintDegree);
//                paintDegree.measureText(degree)  测量文字的宽度 Return the width of the text.
            }
            canvas.rotate(15, getWidth() / 2, getHeight() / 2);
            canvas.save();

            canvas.restore();
        }

        canvas.translate(getWidth() / 2, getHeight() / 2);
        Paint paintHour = new Paint();
        paintHour.setStrokeWidth(20);
        Paint paintMimute = new Paint();
        paintMimute.setStrokeWidth(10);

        canvas.drawLine(0,0,100,100,paintHour);
        canvas.drawLine(0,0,100,200,paintMimute);

    }
}
