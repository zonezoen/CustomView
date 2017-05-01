package com.zone.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by zone on 2017/4/10.
 */

public class TemptureView extends View {
    private int arcRadius = 100;

    public TemptureView(Context context) {
        super(context);
    }

    public TemptureView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TemptureView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public TemptureView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setAntiAlias(true);
        canvas.save();
        canvas.translate(getWidth() / 2, getHeight() / 2);
        canvas.rotate(-133);
        for (int i = 0; i < 60; i++) {
            canvas.drawLine(0, -arcRadius-5, 0, -arcRadius-20, paint);
            canvas.rotate(4.5f);
        }
        canvas.restore();


        canvas.translate(getWidth() / 2, getHeight() / 2);
        canvas.rotate(135 + 2);
        RectF rectF = new RectF(-arcRadius, -arcRadius, arcRadius, arcRadius);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawArc(rectF,0,265,false,paint);



    }
}
