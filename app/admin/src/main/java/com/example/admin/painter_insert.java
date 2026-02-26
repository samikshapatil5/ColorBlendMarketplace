package com.example.admin;

import android.os.Bundle;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

public class painter_insert extends AppCompatActivity {

    EditText name, qual, phno,Pid;
    Button insert, upload;
    RecyclerView rc;
    DatabaseReference dbRef;
    FirebaseStorage storage;
    StorageReference storageRef;
    List<Uri> selectedImages = new ArrayList<>();
    ImageAdapter imageAdapter;

    private static final int PICK_IMAGES_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_painter_insert);

        name = findViewById(R.id.et1);
        qual = findViewById(R.id.et2);
        phno = findViewById(R.id.et3);
        Pid=   findViewById(R.id.et4);
        insert = findViewById(R.id.Insertbtn);
        upload = findViewById(R.id.upload);
        rc = findViewById(R.id.recyclerView);

        dbRef = FirebaseDatabase.getInstance().getReference("painter"); // Initialize Realtime Database reference
        storage = FirebaseStorage.getInstance(); // Initialize Firebase Storage
        storageRef = storage.getReference(); // Create a storage reference

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rc.setLayoutManager(layoutManager);
        imageAdapter = new ImageAdapter(selectedImages);
        rc.setAdapter(imageAdapter);

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadData();
            }
        });
    }

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGES_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGES_REQUEST && resultCode == RESULT_OK && data != null) {
            if (data.getClipData() != null) {
                int count = data.getClipData().getItemCount();
                for (int i = 0; i < count; i++) {
                    Uri imageUri = data.getClipData().getItemAt(i).getUri();
                    selectedImages.add(imageUri);
                }
            } else if (data.getData() != null) {
                Uri imageUri = data.getData();
                selectedImages.add(imageUri);
            }
            imageAdapter.notifyDataSetChanged();
        }
    }

    private void uploadData() {
        String nameText = name.getText().toString();
        String qualText = qual.getText().toString();
        String phnoText = phno.getText().toString();
        String id=Pid.getText().toString();

        if (selectedImages.isEmpty()) {
            Toast.makeText(this, "Please select at least one image.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Use CountDownLatch to synchronize image uploads
        CountDownLatch latch = new CountDownLatch(selectedImages.size());

        List<String> imageUrls = new ArrayList<>();

        for (Uri imageUri : selectedImages) {
            StorageReference imageRef = storageRef.child("images/" + imageUri.getLastPathSegment());
            UploadTask uploadTask = imageRef.putFile(imageUri);

            uploadTask.continueWithTask(task -> {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }
                return imageRef.getDownloadUrl();
            }).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    imageUrls.add(downloadUri.toString());
                } else {
                    Toast.makeText(painter_insert.this, "Error uploading image: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
                latch.countDown(); // Decrease the latch count
                if (latch.getCount() == 0) {
                    // All uploads complete, insert data
                    insertData(id,nameText, qualText, phnoText, imageUrls);
                }
            });
        }
    }

    private void insertData(String id1,String nameText, String qualText, String phnoText, List<String> imageUrls) {
        String key = dbRef.push().getKey(); // Generate a unique key for the entry
        Map<String, Object> userData = new HashMap<>();
        userData.put("name", nameText);
        userData.put("qualification", qualText);
        userData.put("phoneNumber", phnoText);
        userData.put("imageUrls", imageUrls);
        userData.put("PID",id1);

        dbRef.child(key).setValue(userData)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(painter_insert.this, "New Entry inserted", Toast.LENGTH_LONG).show();
                    name.setText("");
                    Pid.setText("");
                    qual.setText("");
                    phno.setText("");
                    selectedImages.clear();
                    imageAdapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(painter_insert.this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                });
    }
}
