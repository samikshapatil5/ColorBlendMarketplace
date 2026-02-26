package com.example.admin;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class user_auth extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_auth);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.pass);
        loginButton = findViewById(R.id.materialButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });
    }

    private void loginUser() {
        final String username = usernameEditText.getText().toString().trim();
        final String passwordString = passwordEditText.getText().toString().trim();

        if (TextUtils.isEmpty(username)) {
            usernameEditText.setError("Username cannot be empty");
            return;
        }

        // Ensure the password is exactly 6 characters long
        if (passwordString.length() != 6) {
            passwordEditText.setError("Password must be exactly 6 digits long");
            return;
        }

        // Convert the password from string to integer
        final int password = Integer.parseInt(passwordString);

        DatabaseReference adminAuthRef = mDatabase.child("admin_auth");
        adminAuthRef.child(username).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Long storedPasswordLong = dataSnapshot.getValue(Long.class);
                    if (storedPasswordLong != null) {
                        int storedPassword = storedPasswordLong.intValue();
                        if (storedPassword == password) {
                            Toast.makeText(user_auth.this, "Login successful", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(user_auth.this, MainAcivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(user_auth.this, "Incorrect password", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(user_auth.this, "Password is null", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(user_auth.this, "Username not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(user_auth.this, "Database error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
