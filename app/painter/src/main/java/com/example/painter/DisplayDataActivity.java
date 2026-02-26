package com.example.painter;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class DisplayDataActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<DataModel> dataModels;
    private ImageAd adapter;
    private DatabaseReference dbRef;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_data);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        dataModels = new ArrayList<>();
        adapter = new ImageAd(this, dataModels);
        recyclerView.setAdapter(adapter);
        dbRef = FirebaseDatabase.getInstance().getReference("painter");

        // Fetch and display inserted data
        fetchInsertedData();
    }

    private void fetchInsertedData() {
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    dataModels.clear();

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String name = snapshot.child("name").getValue(String.class);
                        String qualification = snapshot.child("qualification").getValue(String.class);
                        String phoneNumber = snapshot.child("phoneNumber").getValue(String.class);

                        List<String> imageUrls = snapshot.child("imageUrls").getValue(new GenericTypeIndicator<List<String>>() {});
                        if (imageUrls != null && !imageUrls.isEmpty()) {
                            // Create a DataModel object with text and image data
                            DataModel dataModel = new DataModel(name, qualification, phoneNumber, imageUrls);
                            dataModels.add(dataModel);
                        }
                    }

                    // Update RecyclerView
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
                String errorMessage = "Error fetching data: " + databaseError.getMessage();
                Log.e("FirebaseError", errorMessage); // Log the error message
                Toast.makeText(DisplayDataActivity.this, errorMessage, Toast.LENGTH_LONG).show();
            }
        });
    }
}
