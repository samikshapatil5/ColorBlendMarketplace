package com.example.visiualzing;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class lastvisual extends AppCompatActivity {

    private ImageView imageView;
    private Bitmap bitmap;
    private int selectedColor;
    private float prevX, prevY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lastvisual);

        imageView = findViewById(R.id.imageView);


        String imgPath = getIntent().getStringExtra("image_path");


        int imgResId = getResources().getIdentifier(imgPath, "drawable", getPackageName());
        bitmap = BitmapFactory.decodeResource(getResources(), imgResId);

        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
        } else {
            Log.e("Bitmap", "Failed to decode bitmap from resource");
        }


        selectedColor = Color.RED;

        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();

                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        prevX = event.getX();
                        prevY = event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        float x = event.getX();
                        float y = event.getY();
                        applyColorToPixel(x, y, prevX, prevY);
                        prevX = x;
                        prevY = y;
                        break;
                }
                return true;
            }
        });
    }


    private void applyColorToPixel(float x, float y, float prevX, float prevY) {
        if (!bitmap.isMutable()) {
            bitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
        }


        int bitmapWidth = bitmap.getWidth();
        int bitmapHeight = bitmap.getHeight();


        float strokeWidth = Math.max(bitmapWidth, bitmapHeight) / 30f;


        float ratioX = (float) bitmapWidth / imageView.getWidth();
        float ratioY = (float) bitmapHeight / imageView.getHeight();
        int bitmapPrevX = (int) (prevX * ratioX);
        int bitmapPrevY = (int) (prevY * ratioY);
        int bitmapX = (int) (x * ratioX);
        int bitmapY = (int) (y * ratioY);

        Paint paint = new Paint();
        paint.setColor(selectedColor);
        paint.setStrokeWidth(strokeWidth);
        paint.setStyle(Paint.Style.STROKE);


        if (bitmapX < bitmapWidth && bitmapY < bitmapHeight && bitmapX >= 0 && bitmapY >= 0) {
            Canvas canvas = new Canvas(bitmap);
            canvas.drawLine(bitmapPrevX, bitmapPrevY, bitmapX, bitmapY, paint);
        }
        imageView.setImageBitmap(bitmap);
    }


    public void selectColor(View view) {

        Drawable background = view.getBackground();


        if (background instanceof ColorDrawable) {

            selectedColor = ((ColorDrawable) background).getColor();
        } else {

            selectedColor = Color.BLUE;
        }
    }
}
