package com.zone.customview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by zone on 2017/4/22.
 */

public class ReflectionView extends View {

    private Paint mPaint;
    private Bitmap mSrcBitmap;
    private Bitmap mRefBitmap;

    public ReflectionView(Context context) {
        super(context);
        init();
    }

    private void init() {
        mSrcBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.qq2);

        Matrix matrix = new Matrix();
        matrix.setScale(1F, -1F);
//        matrix.setRotate(90);
        mRefBitmap = Bitmap.createBitmap(mSrcBitmap, 0, 0, mSrcBitmap.getWidth(), mSrcBitmap.getWidth(), matrix, true);

        mPaint = new Paint();
//        mPaint.setAntiAlias(true);
//        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
//        mPaint.setColor(Color.RED);
        mPaint.setShader(new LinearGradient(0, mSrcBitmap.getHeight(), 0, mSrcBitmap.getHeight() + mSrcBitmap.getHeight() / 2, 0XDD000000, 0X10000000, Shader.TileMode.CLAMP));

    }

    public ReflectionView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();

    }

    public ReflectionView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ReflectionView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.BLACK);
        canvas.drawBitmap(mSrcBitmap, 0, 0, null);
        canvas.drawBitmap(mRefBitmap, 0, mSrcBitmap.getHeight(), null);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        canvas.drawRect(0, mSrcBitmap.getHeight(), mRefBitmap.getWidth(), mSrcBitmap.getHeight() * 2, mPaint);
        mPaint.setXfermode(null);
    }
}
