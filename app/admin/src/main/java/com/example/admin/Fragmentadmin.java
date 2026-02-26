package com.example.admin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Fragmentadmin extends Fragment {

    private LinearLayout containerLayout;
    private DatabaseReference mDatabase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragmentadmin, container, false);

        containerLayout = view.findViewById(R.id.containerLayout);

        // Initialize Firebase Database reference
        mDatabase = FirebaseDatabase.getInstance().getReference("Order");

        // Retrieve and display users' data
        retrieveUsersData();

        return view;
    }

    private void retrieveUsersData() {
        Query query = mDatabase;

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                containerLayout.removeAllViews(); // Clear existing views

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Create a LinearLayout for each user's data with border styling
                    LinearLayout userDataLayout = new LinearLayout(getContext());
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);
                    layoutParams.setMargins(20, 20, 20, 20); // Add margin between user data layouts
                    userDataLayout.setLayoutParams(layoutParams);
                    userDataLayout.setOrientation(LinearLayout.VERTICAL);
                    userDataLayout.setBackgroundResource(R.drawable.border); // Border style


                    addUserField(userDataLayout, "Name", snapshot.child("name").getValue(String.class));
                    addUserField(userDataLayout, "Email", snapshot.child("email").getValue(String.class));
                    addUserField(userDataLayout, "Phone", snapshot.child("phone").getValue(String.class));
                    addUserField(userDataLayout, " Order Date", snapshot.child("orderDate").getValue(String.class));
                  addUserField(userDataLayout,"Delivery date",snapshot.child("Delivery date").getValue(String.class));
                    String deliveryDate = snapshot.child("Delivery Date").getValue(String.class);
                    Log.d("DeliveryDateDebug", "Delivery Date: " + deliveryDate);
                    addUserField(userDataLayout, "Quantity", snapshot.child("quantity").getValue(String.class));
                    addUserField(userDataLayout, "Product", snapshot.child("product").getValue(String.class));

                    addUserField(userDataLayout, "Shade Name", snapshot.child("shadeName").getValue(String.class));
                    addUserField(userDataLayout, "Shade Code", snapshot.child("shadeCode").getValue(String.class));
                    addUserField(userDataLayout, "Size", snapshot.child("size").getValue(String.class));
                    addUserField(userDataLayout, "Price", snapshot.child("price").getValue(String.class));
                    addUserField(userDataLayout, "Street Address", snapshot.child("streetAddress").getValue(String.class));
                    addUserField(userDataLayout, "Street Address Line 2", snapshot.child("streetAddressLine2").getValue(String.class));
                    addUserField(userDataLayout, "City", snapshot.child("city").getValue(String.class));
                    addUserField(userDataLayout, "Postal Code", snapshot.child("postalCode").getValue(String.class));
                    addUserField(userDataLayout, "Status", snapshot.child("Status").getValue(String.class));
                    // Add "Update" and "Delete" buttons
                    addUpdateAndDeleteButtons(userDataLayout, snapshot.getKey());

                    // Add the user data layout to the container layout
                    containerLayout.addView(userDataLayout);

                    // Add space between user information blocks
                    addSpaceBetweenUserBlocks(containerLayout);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle database error
            }
        });
    }

    // Helper method to add space between user information blocks
    private void addSpaceBetweenUserBlocks(LinearLayout parentLayout) {
        View spaceView = new View(getContext());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                32); // Height of the space between user information blocks
        spaceView.setLayoutParams(layoutParams);
        parentLayout.addView(spaceView);
    }


    private void addUserField(LinearLayout parentLayout, String label, String value) {
        TextView textView = new TextView(getContext());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(30, 12, 16, 12); // Add margin around TextView
        textView.setLayoutParams(layoutParams);
        textView.setText(label + ": " + value);
        textView.setTextSize(18);
        parentLayout.addView(textView);
    }

    // Helper method to add "Update" and "Delete" buttons to LinearLayout
    private void addUpdateAndDeleteButtons(LinearLayout parentLayout, final String key) {
        // Create a horizontal LinearLayout to hold both buttons
        LinearLayout buttonLayout = new LinearLayout(getContext());
        buttonLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        buttonLayout.setOrientation(LinearLayout.HORIZONTAL);
        buttonLayout.setGravity(Gravity.CENTER_HORIZONTAL); // Center buttons horizontally

        // Add "Update" button
        Button updateButton = new Button(getContext());
        LinearLayout.LayoutParams updateParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        updateParams.setMargins(30, 20, 16, 20); // Add margin around button
        updateButton.setLayoutParams(updateParams);
        updateButton.setText("Update");
        buttonLayout.addView(updateButton);

        // Update data when the "Update" button is clicked
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), update.class);
                intent.putExtra("key", key);
                startActivity(intent);
            }
        });

        // Add "Delete" button
        Button deleteButton = new Button(getContext());
        LinearLayout.LayoutParams deleteParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        deleteParams.setMargins(30, 12, 16, 12); // Add margin around button
        deleteButton.setLayoutParams(deleteParams);
        deleteButton.setText("Delete");
        buttonLayout.addView(deleteButton);

        // Delete data when the "Delete" button is clicked
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Delete data using the key
                deleteData(key);
            }
        });

        // Add the button layout to the parent layout
        parentLayout.addView(buttonLayout);
    }

    private void deleteData(String key) {
        mDatabase.child(key).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getContext(), "Data deleted successfully", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Failed to delete data", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
