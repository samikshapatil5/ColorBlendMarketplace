package com.example.colorblendmarketplace;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class MainActivity1 extends AppCompatActivity {
    // Declare variables for CardViews
    private CardView cardViewInspire, cardViewVisualize, cardViewPainter, cardViewAdmin, cardViewOrder;
    private FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);

        // Initialize CardViews
        cardViewInspire = findViewById(R.id.inspire);
        cardViewVisualize = findViewById(R.id.visiual);
        cardViewPainter = findViewById(R.id.painter);
        cardViewAdmin = findViewById(R.id.admin);
        cardViewOrder = findViewById(R.id.order);

        fAuth = FirebaseAuth.getInstance();

        // Set up the ImageSlider
        ImageSlider imageSlider = findViewById(R.id.imageSlider);
        ArrayList<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel(R.drawable.pic1, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.pic2, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.pic3, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.pic4, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.pic5, ScaleTypes.FIT));
        imageSlider.setImageList(slideModels, ScaleTypes.FIT);

        // Set up the toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Load GIFs using Glide
        loadGif(R.id.imageView, R.drawable.palette);
        loadGif(R.id.imageView2, R.drawable.roller);
        loadGif(R.id.imageView3, R.drawable.painter);

        // Set onClickListeners for CardViews
        cardViewPainter.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity1.this, com.example.painter.DisplayDataActivity.class);
            startActivity(intent);
        });

        cardViewAdmin.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity1.this, com.example.admin.user_auth.class);
            startActivity(intent);
        });

        cardViewVisualize.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity1.this, com.example.visiualzing.MainActivity.class);
            startActivity(intent);
        });

        cardViewOrder.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity1.this, com.example.ordermanage.MainActivity.class);
            startActivity(intent);
        });

        cardViewInspire.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity1.this, com.example.interiordesign.MainActivity.class);
            startActivity(intent);
        });
    }

    // Utility function to load GIFs using Glide
    private void loadGif(int imageViewId, int gifResourceId) {
        ImageView gifImageView = findViewById(imageViewId);
        Glide.with(this)
                .asGif()
                .load(gifResourceId)
                .into(gifImageView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.log) {
            // Sign out the user and redirect to login activity
            fAuth.signOut();
            startActivity(new Intent(MainActivity1.this, Login.class));
            finish();  // Finish the current activity
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
