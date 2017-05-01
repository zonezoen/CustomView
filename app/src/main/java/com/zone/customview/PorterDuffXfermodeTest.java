package com.zone.customview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by zone on 2017/4/20.
 */

public class PorterDuffXfermodeTest extends View {

    private Paint mPaint;

    public PorterDuffXfermodeTest(Context context) {
        super(context);
        init();
    }

    private void init() {
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);//关闭硬件加速
//        mPaint = new Paint();
//        mPaint.setAntiAlias(true);
//        mPaint.setColor(Color.RED);
//        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
//        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

    }

    public PorterDuffXfermodeTest(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();

    }

    public PorterDuffXfermodeTest(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public PorterDuffXfermodeTest(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        Bitmap bitmap = Bitmap.createBitmap(200, 200, Bitmap.Config.ARGB_8888);
//
//        Canvas mCanvas = new Canvas(bitmap);
//
        final int sc = canvas.saveLayer(0, 0, getWidth(), getHeight(), null, Canvas.ALL_SAVE_FLAG);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.jyz_qrcord);
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, 200, mPaint);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));


        canvas.drawBitmap(bitmap,getWidth()/2-bitmap.getWidth()/2,getHeight()/2-bitmap.getHeight()/2,mPaint);
//        canvas.drawCircle(getWidth() / 2 + 50, getHeight() / 2, 100, mPaint);
        mPaint.setXfermode(null);
        // 还原画布
        canvas.restoreToCount(sc);

//        drawTargetBitmap(canvas, bitmap);
    }

    private void drawTargetBitmap(Canvas canvas, Bitmap bitmap) {
        final int sc = canvas.saveLayer(0, 0, getWidth(), getHeight(), null, Canvas.ALL_SAVE_FLAG);
        //先绘制dst层
        final float x = getWidth() / 2;
        final float y = getHeight() / 2;
        final float radius = Math.min(getWidth(), getHeight()) / 2;
        canvas.drawCircle(x, y, radius/2, mPaint);
        //设置混合模式
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        //绘制src层
        final float f_x = getWidth() / 2 - bitmap.getWidth() / 2;
        final float f_y = getHeight() / 2 - bitmap.getHeight() / 2;
        canvas.drawBitmap(bitmap, f_x, f_y, mPaint);
        // 还原混合模式
        mPaint.setXfermode(null);
        // 还原画布
        canvas.restoreToCount(sc);
    }
}
