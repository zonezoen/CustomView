package com.zone.customview;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by zone on 2017/4/8.
 */

public class LayerTest extends View {
    public LayerTest(Context context) {
        super(context);
    }

    public LayerTest(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LayerTest(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public LayerTest(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);
        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        canvas.drawCircle(100, 100, 50, paint);

        canvas.saveLayerAlpha(0, 0, 400, 400, 125,Canvas.ALL_SAVE_FLAG );

        paint.setColor(Color.RED);
        canvas.drawCircle(150, 150, 50, paint);
//        canvas.restore();

        ColorMatrix matrix = new ColorMatrix();
        matrix.setScale(1,1,1,1);

//        canvas.drawText();


    }
}
