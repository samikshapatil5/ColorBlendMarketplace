package com.example.colorblendmarketplace;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private static final int DELAY_MILLIS = 10000; // Declare as a constant
    private FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Firebase Auth
        fAuth = FirebaseAuth.getInstance();

        // Initialize VideoView
        VideoView videoView = findViewById(R.id.videoView);

        // Set video path (res/raw/stock.mp4)
        String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.stock;
        videoView.setVideoURI(Uri.parse(videoPath));

        // Start video playback
        videoView.start();

        // Check if user is authenticated and proceed accordingly
        if (fAuth.getCurrentUser() != null) {
            // User is already authenticated, redirect to MainActivity1
            new Handler().postDelayed(() -> {
                startActivity(new Intent(MainActivity.this, MainActivity1.class));
                finish(); // Finish MainActivity to prevent returning to it
            }, DELAY_MILLIS);
        } else {
            // If not authenticated, proceed to Login Activity after delay
            new Handler().postDelayed(() -> {
                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);
                finish(); // Finish MainActivity to prevent returning to it
            }, DELAY_MILLIS);
        }

        // Initialize ImageView and load GIF using Glide
        ImageView gifImageView = findViewById(R.id.imageView);
        Glide.with(this)
                .asGif()
                .load(R.drawable.a1)
                .into(gifImageView);
    }
}
