package com.example.ordermanage;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public class discrption extends AppCompatActivity {
     MaterialButton m1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discrption);
        m1=findViewById(R.id.button2);
        // Get the intent that started this activity
        Intent intent = getIntent();

        // Retrieve data from the intent
        String text = intent.getStringExtra("text");
        String text2=intent.getStringExtra("text1");
        int color = intent.getIntExtra("color", 0);

        // Set the title text
        TextView titleTextView = findViewById(R.id.titleTextView);
        titleTextView.setText(text);

        TextView subtitle=findViewById(R.id.subtitleTextView);
        subtitle.setText(text2);
        // Set the background color of colorView
        View colorView = findViewById(R.id.colorView);
        colorView.setBackground(new ColorDrawable(color));

        m1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(discrption.this, quntity.class);
                intent1.putExtra("text",text);
                intent1.putExtra("text1",text2);
                intent1.putExtra("color",color);
                startActivity(intent1);

            }
        });
    }

}
