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
 * Created by zone on 2017/4/24.
 */

public class ClassTableView  extends View {
    public ClassTableView(Context context) {
        super(context);
    }

    public ClassTableView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ClassTableView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ClassTableView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setColor(Color.YELLOW);
        paint.setAlpha(150);
        paint.setAntiAlias(true);
        RectF rectF = new RectF(10,10,150,300);
        canvas.drawRoundRect(rectF,10,10,paint);
    }
}
