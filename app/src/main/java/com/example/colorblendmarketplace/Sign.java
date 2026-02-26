package com.example.colorblendmarketplace;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Sign extends AppCompatActivity {
    EditText e1,e2,e3;
    MaterialButton m1;

    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);
        TextView textView = findViewById(R.id.textView3);
        e1=findViewById(R.id.username);
        e2=findViewById(R.id.email);
        e3=findViewById(R.id.pass);
        m1=findViewById(R.id.materialButton);
        fAuth=FirebaseAuth.getInstance();


        SpannableString spannableString = new SpannableString("Already have any account? Login-in");
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {

                Intent browserIntent = new Intent(Sign.this, Login.class);
                startActivity(browserIntent);
            }
        };

        spannableString.setSpan(clickableSpan,26, 34, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(spannableString);
        textView.setMovementMethod(LinkMovementMethod.getInstance());

        //firebase

        m1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=e2.getText().toString().trim();
                String password=e3.getText().toString().trim();
                if(email.isEmpty())
                {
                    e2.setError("Email is Required.");
                    return;
                }
                if(password.isEmpty())
                {
                    e2.setError("Password is Required.");
                    return;
                }
                if(password.length()<6)
                {
                    e2.setError("Password must be grater than or equal to 6");
                }
                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(Sign.this,"User Created",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Sign.this,Login.class));
                            finish();
                        }
                        else
                        {
                            Toast.makeText(Sign.this,"Error ! "+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}