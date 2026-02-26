package com.example.ordermanage;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public class firstorderscreen extends AppCompatActivity {
     int NUM_CARDS=112;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firstorderscreen);
        for (int i = 1; i <= NUM_CARDS; i++) {

            int cardViewId = getResources().getIdentifier("c" + i, "id", getPackageName());
            int textviewId=getResources().getIdentifier("t" + i, "id", getPackageName());
            int materialbuttonId=getResources().getIdentifier("m" + i, "id", getPackageName());
            int subtitle=getResources().getIdentifier("tt" + i, "id", getPackageName());
            setupCardClickListener(cardViewId, textviewId, subtitle,materialbuttonId);
        }
        // Repeat the above line for other cardViews...

    }

    private void setupCardClickListener(int cardViewId, int textViewId,int subtitle, int materialButtonId) {
        findViewById(cardViewId).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TextView textView = view.findViewById(textViewId);
                TextView textview1=view.findViewById(subtitle);
                MaterialButton materialButton = view.findViewById(materialButtonId);
                String text = textView.getText().toString();
                String text1 = textview1.getText().toString();


                int color = ((ColorDrawable) materialButton.getBackground()).getColor();



                Intent intent = new Intent(firstorderscreen.this, discrption.class);
                intent.putExtra("text", text);
                intent.putExtra("text1", text1);
                intent.putExtra("color", color);
                startActivity(intent);

            }
        });
    }


}
