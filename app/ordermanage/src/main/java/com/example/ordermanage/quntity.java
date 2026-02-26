package com.example.ordermanage;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public class quntity extends AppCompatActivity {

    Button b1, b2, b4;
    MaterialButton materialButton, materialButton1, materialButton2, materialButton3;
    TextView t1, t2, t3, price;
    int quantity = 1;
    String buttonText;
    View colorview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quntity);

        b1 = findViewById(R.id.minus);
        b2 = findViewById(R.id.plus);
        b4 = findViewById(R.id.button4);
        t1 = findViewById(R.id.textView7);
        t2 = findViewById(R.id.titleTextView);
        t3 = findViewById(R.id.subtitleTextView);
        price = findViewById(R.id.price);
        colorview = findViewById(R.id.colorView);
        materialButton = findViewById(R.id.materialButton);
        materialButton1 = findViewById(R.id.materialButton1);
        materialButton2 = findViewById(R.id.materialButton2);
        materialButton3 = findViewById(R.id.materialButton3);

        Intent intent1 = getIntent();

        // Retrieve data from the intent
        String text = intent1.getStringExtra("text");
        String text2 = intent1.getStringExtra("text1");
        int color = intent1.getIntExtra("color", 0);

        t2.setText(text);
        t3.setText(text2);
        colorview.setBackground(new ColorDrawable(color));

        // Set onClickListeners for buttons
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantity--;
                if (quantity < 1) {
                    quantity = 1; // Ensure quantity is not less than 1
                }
                t1.setText(String.valueOf(quantity));
                calculateAndDisplayPrice();
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantity++;
                t1.setText(String.valueOf(quantity));
                calculateAndDisplayPrice();
            }
        });

        // Set onClickListeners for material buttons
        materialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonText = materialButton.getText().toString();
                calculateAndDisplayPrice();
            }
        });
        materialButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonText = materialButton1.getText().toString();
                calculateAndDisplayPrice();
            }
        });
        materialButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonText = materialButton2.getText().toString();
                calculateAndDisplayPrice();
            }
        });
        materialButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonText = materialButton3.getText().toString();
                calculateAndDisplayPrice();
            }
        });
        // Set onClickListener for Next button
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(quntity.this, personal_info.class);

                // Pass the data as extras to the next activity
                intent3.putExtra("shade_name", t2.getText().toString()); // Assuming t2 is the TextView for shade name
                intent3.putExtra("shade_code", t3.getText().toString()); // Assuming t3 is the TextView for shade code
                intent3.putExtra("quantity", quantity); // Pass the quantity
                String size = buttonText;
                intent3.putExtra("size", size); // Pass the size, modify accordingly
                intent3.putExtra("price", price.getText().toString()); // Pass the calculated price

                startActivity(intent3);
            }
        });

        // Calculate and display initial price
        calculateAndDisplayPrice();
    }

    private void calculateAndDisplayPrice() {
        int basePrice = 0;

        // Check if buttonText is null
        if(buttonText == null) {
            // Handle the case where buttonText is null
            // Set a default base price or any other logic based on your requirement
        } else {
            // Determine base price based on the selected button
            switch (buttonText) {
                case "1 LITRE":
                    basePrice = 250;
                    break;
                case "4 LITRE":
                    basePrice = 450;
                    break;
                case "10 LITRE":
                    basePrice = 650;
                    break;
                case "20 LITRE":
                    basePrice = 820;
                    break;
                default:
                    break;
            }
        }

        // Calculate total price
        int totalPrice = basePrice * quantity;

        // Display the total price
        price.setText(String.valueOf(totalPrice));
    }
}
