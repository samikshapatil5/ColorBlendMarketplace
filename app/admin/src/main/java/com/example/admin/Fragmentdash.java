package com.example.admin;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Fragmentdash extends Fragment {
    int orderedCount = 0;
    int cancelledCount = 0;
    int deliveredCount = 0;
    int onDeliveryCount = 0;
    DatabaseReference ordersRef;
    TextView orderedTextView, cancelledTextView, deliveredTextView, onDeliveryTextView,t5;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragmentdash, container, false);
        orderedTextView = view.findViewById(R.id.Ordered);
        cancelledTextView = view.findViewById(R.id.cancelled);
        deliveredTextView = view.findViewById(R.id.Delivered);
        onDeliveryTextView = view.findViewById(R.id.Delivery);
        t5=view.findViewById(R.id.Total);

        ordersRef = FirebaseDatabase.getInstance().getReference().child("Order");

        ordersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot orderSnapshot : dataSnapshot.getChildren()) {
                    // Get the status of the current order
                    String status = orderSnapshot.child("Status").getValue(String.class);

                    // Increment the corresponding counter based on the status
                    if (status != null) {
                        switch (status) {
                            case "Ordered":
                                orderedCount++;
                                break;
                            case "Cancelled":
                                cancelledCount++;
                                break;
                            case "On Delivery":
                                deliveredCount++;
                                break;
                            case "Delivered":
                                onDeliveryCount++;
                                break;
                            default:
                                // Handle unrecognized status
                                break;
                        }
                    }
                }
                int total=orderedCount+cancelledCount+onDeliveryCount+deliveredCount;
                orderedTextView.setText(String.valueOf(orderedCount));
                cancelledTextView.setText(String.valueOf(cancelledCount));
                onDeliveryTextView.setText(String.valueOf(onDeliveryCount));
                deliveredTextView.setText(String.valueOf(deliveredCount));
                t5.setText(String.valueOf(total));


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Failed to Fetch data", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
