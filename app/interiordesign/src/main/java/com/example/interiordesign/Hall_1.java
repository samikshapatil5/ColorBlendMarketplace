package com.example.interiordesign;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class Hall_1 extends AppCompatActivity {
    ImageView mimageView;
    TextView mresultTv;
    View mcolorView;
    Bitmap bitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hall1);

        mimageView=(ImageView) findViewById(R.id.image_view);
        mresultTv=findViewById(R.id.resultTv);
        mcolorView=findViewById(R.id.colorView);


        Intent i=getIntent();

        int position=i.getExtras().getInt("id");
        HallImageAdapter imageAdapter=new HallImageAdapter(this);
        mimageView.setImageResource(imageAdapter.imageArray1[position]);

        mimageView.setDrawingCacheEnabled(true);
        mimageView.buildDrawingCache(true);

        mimageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE){
                    bitmap=mimageView.getDrawingCache();
                    int pixel=bitmap.getPixel((int)event.getX(),(int) event.getY());
                    int r= Color.red(pixel);
                    int g=Color.green(pixel);
                    int b=Color.blue(pixel);

                    String hex="#"+ Integer.toHexString(pixel);
                    mcolorView.setBackgroundColor(Color.rgb(r,g,b));
                    mresultTv.setText("RED,GREEN, BLUE Combination"+"\n Red:  "+r+"\n Green:  "+ g +"\n Blue:  "+b+"\nColor Code : "+hex);
                }
                return false;
            }
        });

    }
}