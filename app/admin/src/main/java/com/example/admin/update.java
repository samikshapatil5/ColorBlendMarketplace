package com.example.admin;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class update extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private EditText mEditName, mEditEmail, mEditPhone, mEditOrder, mEditDelivery,
            mEditQuantity, mEditProduct, edShade_code,edShade_name,edSize,mEditStreetAddressLine1, mEditStreetAddressLine2,
            mEditCity, mEditPostalCode;
    AppCompatSpinner spinner;
    String[] statusItems = {"Ordered", "On Delivery", "Delivered","Cancelled"};
    private TextView Name,Email,Phone,Order,Delivery,Quantity,Product,Shade_code,Shade_name,Size,status,StreetLine1,StreetLine2,City,PostalCode;
    private Button mBtnUpdate;
    private String key;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        // Initialize Firebase Database reference
        mDatabase = FirebaseDatabase.getInstance().getReference("Order");

        // Initialize EditTexts and Button
        mEditName = findViewById(R.id.editName);
        mEditEmail = findViewById(R.id.editEmail);
        mEditPhone = findViewById(R.id.editPhone);
        mEditOrder= findViewById(R.id.editOrder);
        mEditDelivery= findViewById(R.id.editDelivery);
        mEditQuantity = findViewById(R.id.editQuantity);
        mEditProduct = findViewById(R.id.editProduct);
        edShade_code=findViewById(R.id.edShade_Code);
        edShade_name=findViewById(R.id.edShade_Name);
        edSize=findViewById(R.id.edSize);


        mEditStreetAddressLine1 = findViewById(R.id.editStreetAddressLine1);
        mEditStreetAddressLine2 = findViewById(R.id.editStreetAddressLine2);
        mEditCity = findViewById(R.id.editCity);
        mEditPostalCode = findViewById(R.id.editPostalCode);
        mBtnUpdate = findViewById(R.id.Update);
         spinner = findViewById(R.id.editstatus);


        Name = findViewById(R.id.Name);
        Email = findViewById(R.id.Email);
        Phone = findViewById(R.id.Phone);
        Order = findViewById(R.id.Order);
        Delivery = findViewById(R.id.Delivery);
        Quantity = findViewById(R.id.Quantity);
        Product = findViewById(R.id.Product);
        Shade_code=findViewById(R.id.Shade_Code);
        Shade_name=findViewById(R.id.Shade_Name);
        Size=findViewById(R.id.Size);

        StreetLine1 = findViewById(R.id.StreetAddressLine1);
        StreetLine2 = findViewById(R.id.StreetAddressLine2);
        City = findViewById(R.id.City);
        PostalCode = findViewById(R.id.PostalCode);
        status=findViewById(R.id.tstatus);

          //spnnier value
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, statusItems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
      spinner.setAdapter(adapter);


        // Get the key passed from the MainActivity
        key = getIntent().getStringExtra("key");

        // Retrieve user information for the given key
        retrieveUserData(key);

        // Set onClick listener for the "Update" button
        mBtnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUserData(key);
            }
        });
    }

    private void retrieveUserData(String key) {
        // Retrieve user data from Firebase and populate EditText fields
        mDatabase.child(key).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    DataSnapshot snapshot = task.getResult();
                    if (snapshot.exists()) {
                        // Retrieve data from snapshot
                        String name = snapshot.child("name").getValue(String.class);
                        String email = snapshot.child("email").getValue(String.class);
                        String phone = snapshot.child("phone").getValue(String.class);
                        String orderDate1 = snapshot.child("orderDate").getValue(String.class);
                        String DeliveryDate1 = snapshot.child("Delivery date").getValue(String.class);
                        String quantity = snapshot.child("quantity").getValue(String.class);
                        String product = snapshot.child("product").getValue(String.class);
                        String streetAddressLine1 =snapshot.child("streetAddress").getValue(String.class);
                        String streetAddressLine2 = snapshot.child("streetAddressLine2").getValue(String.class);
                        String city = snapshot.child("city").getValue(String.class);
                        String postalCode = snapshot.child("postalCode").getValue(String.class);
                        String shade_code=snapshot.child("shadeCode").getValue(String.class);
                        String Shade_Name=snapshot.child("shadeName").getValue(String.class);
                        String size=snapshot.child("size").getValue(String.class);
                        String status1=snapshot.child("Status").getValue(String.class);
                        // Populate EditText fields with retrieved data
                        Name.setText("Name:    "+name);
                        Email.setText("Email:   "+email);
                        Phone.setText("Phone:    "+phone);
                        Order.setText("Order Date:    "+orderDate1);
                        Delivery.setText("Delivery Date:    "+DeliveryDate1);
                        Quantity.setText("Quantity:    "+quantity);
                        Product.setText("Product:    "+product);

                        Shade_name.setText("Shade Name:    "+Shade_Name);
                        Shade_code.setText("Shade Code:    "+shade_code);
                        Size.setText("size:    "+size);
                        StreetLine1.setText("Street Address :    "+streetAddressLine1);
                        StreetLine2.setText("Street Address Line 2:    "+streetAddressLine2);
                        City.setText("City:    "+city);
                        PostalCode.setText("PostalCode:    "+postalCode);
                        status.setText("Status:    "+status1);
                    } else {
                        Toast.makeText(update.this, "No such data exists", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(update.this, "Failed to retrieve data", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void updateUserData(String key) {

        // Update user data in Firebase based on EditText fields


        String name = mEditName.getText().toString().trim();
        String email = mEditEmail.getText().toString().trim();
        String phone = mEditPhone.getText().toString().trim();
        String order1 = mEditOrder.getText().toString().trim();
        String Delivery1 = mEditDelivery.getText().toString().trim();
        String quantity = mEditQuantity.getText().toString().trim();
        String product = mEditProduct.getText().toString().trim();
        String shade_code = edShade_code.getText().toString().trim();
        String shade_name= edShade_name.getText().toString().trim();
        String size = edSize.getText().toString().trim();


        String streetAddressLine1 = mEditStreetAddressLine1.getText().toString().trim();
        String streetAddressLine2 = mEditStreetAddressLine2.getText().toString().trim();
        String city = mEditCity.getText().toString().trim();
        String postalCode = mEditPostalCode.getText().toString().trim();
        String status2 = spinner.getSelectedItem().toString();


        // Update data in Firebase only if the corresponding EditText field is not empty
        if (!name.isEmpty()) {
            mDatabase.child(key).child("name").setValue(name);
        }
        if (!email.isEmpty()) {
            mDatabase.child(key).child("email").setValue(email);
        }
        if (!phone.isEmpty()) {
            mDatabase.child(key).child("phone").setValue(phone);
        }
        if (!order1.isEmpty()) {
            mDatabase.child(key).child("orderDate").setValue(order1);
        }
        if (!Delivery1.isEmpty()) {
            mDatabase.child(key).child("Delivery date").setValue(Delivery1);
        }
        if (!quantity.isEmpty()) {
            mDatabase.child(key).child("quantity").setValue(quantity);
        }
        if (!product.isEmpty()) {
            mDatabase.child(key).child("product").setValue(product);
        }
        if (!shade_code.isEmpty()) {
            mDatabase.child(key).child("shadeCode").setValue(shade_code);
        }
        if (!shade_name.isEmpty()) {
            mDatabase.child(key).child("shadeName").setValue(shade_name);
        }
        if (!size.isEmpty()) {
            mDatabase.child(key).child("size").setValue(size);
        }

        if (!streetAddressLine1.isEmpty()) {
            mDatabase.child(key).child("streetAddressLine1").setValue(streetAddressLine1);
        }
        if (!streetAddressLine2.isEmpty()) {
            mDatabase.child(key).child("streetAddressLine2").setValue(streetAddressLine2);
        }
        if (!city.isEmpty()) {
            mDatabase.child(key).child("city").setValue(city);
        }
        if (!postalCode.isEmpty()) {
            mDatabase.child(key).child("postalCode").setValue(postalCode);

        }
        if (!status2.isEmpty()) {
            mDatabase.child(key).child("Status").setValue(status2);

        }
        // Show success message
        Toast.makeText(update.this, "Data updated successfully", Toast.LENGTH_SHORT).show();

        // Retrieve updated data from Firebase and display in TextViews
        retrieveUserData(key);
    }
}
