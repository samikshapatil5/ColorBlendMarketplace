package com.example.ordermanage;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class personal_info extends AppCompatActivity {

    private EditText etName, etEmail, etPhone, etDate, etQuantity, etProduct;
    private EditText streetAddress, streetAddressLine2, city, postalCode;
    private EditText etShadeCode, etShadeName, etSize,etprice; // New EditTexts for paint color information
    private Button btnSubmit;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);

        // Initialize Firebase Database reference
        mDatabase = FirebaseDatabase.getInstance().getReference("Order");

        // Find all EditTexts and Button by their IDs
        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);
        etDate = findViewById(R.id.etDate);
        etQuantity = findViewById(R.id.etQuantity);
        etProduct = findViewById(R.id.etProduct);
        streetAddress = findViewById(R.id.streetAddress);
        streetAddressLine2 = findViewById(R.id.streetAddressLine2);
        city = findViewById(R.id.city);
        postalCode = findViewById(R.id.postalCode);

        // New EditTexts for paint color information
        etShadeCode = findViewById(R.id.etShadeCode);
        etShadeName = findViewById(R.id.etShadeName);
        etSize = findViewById(R.id.etSize);
        etprice = findViewById(R.id.etprize);
        btnSubmit = findViewById(R.id.btnSubmit);

        // Receive intent data
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            // Extract data from extras
            String shadeName = extras.getString("shade_name");
            String shadeCode = extras.getString("shade_code");
            int quantity = extras.getInt("quantity");
            String size = extras.getString("size");
            String price = extras.getString("price");

            // Set the data to corresponding EditTexts
            etShadeName.setText(shadeName);
            etShadeCode.setText(shadeCode);
            etQuantity.setText(String.valueOf(quantity));
            etSize.setText(size);
            etprice.setText(price);
        }

        // Set up click listener for submit button
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get user input from EditTexts
                String name = etName.getText().toString();
                String email = etEmail.getText().toString();
                String phone = etPhone.getText().toString();
                String date = etDate.getText().toString();

                String quantity = etQuantity.getText().toString();
                String product = etProduct.getText().toString();
                String streetAddr = streetAddress.getText().toString();
                String streetAddrLine2 = streetAddressLine2.getText().toString();
                String cityStr = city.getText().toString();
                String postalCodeStr = postalCode.getText().toString();

                // New EditTexts for paint color information
                String shadeCode = etShadeCode.getText().toString();
                String shadeName = etShadeName.getText().toString();
                String size = etSize.getText().toString();
                String Prize = etprice.getText().toString();
                String status = "Ordered";

                // Check if any field is empty
                if (name.isEmpty()) {
                    etName.setError("Name is required");
                    etName.requestFocus();
                    return;
                }

                if (email.isEmpty()) {
                    etEmail.setError("Email is required");
                    etEmail.requestFocus();
                    return;
                }

                if (phone.isEmpty()) {
                    etPhone.setError("Phone number is required");
                    etPhone.requestFocus();
                    return;
                }

                if (date.isEmpty()) {
                    etDate.setError("Delivery date is required");
                    etDate.requestFocus();
                    return;
                }

                if (quantity.isEmpty()) {
                    etQuantity.setError("Quantity is required");
                    etQuantity.requestFocus();
                    return;
                }

                if (product.isEmpty()) {
                    etProduct.setError("Product name is required");
                    etProduct.requestFocus();
                    return;
                }

                if (streetAddr.isEmpty()) {
                    streetAddress.setError("Street address is required");
                    streetAddress.requestFocus();
                    return;
                }

                if (cityStr.isEmpty()) {
                    city.setError("City is required");
                    city.requestFocus();
                    return;
                }

                if (postalCodeStr.isEmpty()) {
                    postalCode.setError("Postal code is required");
                    postalCode.requestFocus();
                    return;
                }

                if (shadeCode.isEmpty()) {
                    etShadeCode.setError("Shade code is required");
                    etShadeCode.requestFocus();
                    return;
                }

                if (shadeName.isEmpty()) {
                    etShadeName.setError("Shade name is required");
                    etShadeName.requestFocus();
                    return;
                }

                if (size.isEmpty()) {
                    etSize.setError("Size is required");
                    etSize.requestFocus();
                    return;
                }

                if (Prize.isEmpty()) {
                    etprice.setError("Price is required");
                    etprice.requestFocus();
                    return;
                }

                // Get the current date
                Date currentDate = new Date();

                // Parse the delivery date
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                Date deliveryDate;
                try {
                    deliveryDate = sdf.parse(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                    etDate.setError("Invalid date format");
                    etDate.requestFocus();
                    return;
                }

                // Check if the delivery date is before the current date or more than two days after
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(currentDate);
                calendar.add(Calendar.DAY_OF_YEAR, 2); // Add two days to the current date
                Date minDate = calendar.getTime();

                if (deliveryDate.before(minDate)) {
                    etDate.setError("Delivery date must be at least two days after the current date");
                    etDate.requestFocus();
                    return;
                }


                // Create a map to store user data
                HashMap<String, Object> userData = new HashMap<>();
                userData.put("name", name);
                userData.put("email", email);
                userData.put("phone", phone);
                userData.put("Delivery date", date);
                userData.put("quantity", quantity);
                userData.put("product", product);
                userData.put("streetAddress", streetAddr);
                userData.put("streetAddressLine2", streetAddrLine2);
                userData.put("city", cityStr);
                userData.put("postalCode", postalCodeStr);
                userData.put("shadeCode", shadeCode);
                userData.put("shadeName", shadeName);
                userData.put("size", size);
                userData.put("price", Prize);
                userData.put("Status", status);

                // Add current date to user data
                String currentDateStr = sdf.format(currentDate);
                userData.put("orderDate", currentDateStr);

                // Push data to Firebase Database
                mDatabase.push().setValue(userData)
                        .addOnSuccessListener(unused -> {
                            // Data insertion successful
                            Toast.makeText(personal_info.this, "Data submitted successfully!", Toast.LENGTH_SHORT).show();
                            // Clear form fields (optional)
                            clearForm();
                        })
                        .addOnFailureListener(exception -> {
                            // Data insertion failed
                            Toast.makeText(personal_info.this, "Failed to submit data: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                        });
            }
        });
    }

        private void clearForm() {
        etName.setText("");
        etEmail.setText("");
        etPhone.setText("");
        etDate.setText("");

        etQuantity.setText("");
        etProduct.setText("");
        streetAddress.setText("");
        streetAddressLine2.setText("");
        city.setText("");
        postalCode.setText("");
        etShadeCode.setText("");
        etShadeName.setText("");
        etSize.setText("");
        etprice.setText("");
    }
}
