package com.example.visiualzing;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.visiual);






        }
    public void passimage(View view) {
        // Change the background color of the ImageView when clicked
        ImageView imageView = (ImageView) view;
        int imageResourceId = getResources().getIdentifier(imageView.getTag().toString(), "drawable", getPackageName());

        // Pass the resource ID to the next activity
        Intent intent = new Intent(MainActivity.this, lastvisual.class);
        intent.putExtra("image_path", "" + imageResourceId);
        startActivity(intent);
    }
    }
