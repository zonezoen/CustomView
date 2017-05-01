package com.zone.customview;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

public class ColorMatrixActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_matrix);
        ImageView imageView = (ImageView) findViewById(R.id.iv);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        imageView.setImageBitmap(handImage(bitmap,1,2,3));


    }

    private Bitmap handImage(Bitmap bm,float hue,float saturation,float lum) {
        Bitmap bitmap2 = Bitmap.createBitmap(bm.getWidth(), bm.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap2);

        Paint paint = new Paint();
        ColorMatrix hueMatrix = new ColorMatrix();
        hueMatrix.setRotate(0,hue);
        hueMatrix.setRotate(1,hue);
        hueMatrix.setRotate(2,hue);



        ColorMatrix saturationMatrix = new ColorMatrix();
        saturationMatrix.setSaturation(saturation);

        ColorMatrix lumMatrix = new ColorMatrix();
        lumMatrix.setScale(lum,lum,lum,1);

        ColorMatrix postMatrix = new ColorMatrix();
        postMatrix.postConcat(hueMatrix);
        postMatrix.postConcat(saturationMatrix);
        postMatrix.postConcat(lumMatrix);

        paint.setColorFilter(new ColorMatrixColorFilter(postMatrix));
        canvas.drawBitmap(bm, 0, 0, paint);
        Math.sin(30);

        return bitmap2;
    }
}
