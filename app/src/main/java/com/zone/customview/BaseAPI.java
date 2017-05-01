package com.zone.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by zone on 2017/4/9.
 */

public class BaseAPI extends View {
    public BaseAPI(Context context) {
        super(context);
    }

    public BaseAPI(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseAPI(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public BaseAPI(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setColor(Color.CYAN);
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);

        Path path = new Path();
        path.moveTo(0, 0);
        path.lineTo(100, 0);
        path.lineTo(50, 50);
        path.close();
        canvas.drawPath(path, paint);

        canvas.save();

        paint.setColor(Color.RED);
        canvas.translate(200, 200);
        canvas.drawCircle(0, 0, 100, paint);

        canvas.restore();
//        canvas.save();

        paint.setTextSize(30);
        canvas.drawText("asdf", 100 - (paint.measureText("asdf"))/2, 100, paint);


    }
}
