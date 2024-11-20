package com.example.gps_guide;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class EditRestaurantActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_restaurant);

        // Get references to input fields and button
        EditText nameEditText = findViewById(R.id.input_name);
        EditText addressEditText = findViewById(R.id.input_address);
        EditText phoneEditText = findViewById(R.id.input_phone);
        EditText descriptionEditText = findViewById(R.id.input_description);
        EditText tagsEditText = findViewById(R.id.input_tags);
        Button saveButton = findViewById(R.id.save_button);

        // Retrieve current restaurant details from the intent
        Intent intent = getIntent();
        String currentName = intent.getStringExtra("restaurantName");
        String currentAddress = intent.getStringExtra("restaurantAddress");
        String currentPhone = intent.getStringExtra("restaurantPhone");
        String currentDescription = intent.getStringExtra("restaurantDescription");
        String currentTags = intent.getStringExtra("restaurantTags");

        // Populate fields with current details
        nameEditText.setText(currentName);
        addressEditText.setText(currentAddress);
        phoneEditText.setText(currentPhone);
        descriptionEditText.setText(currentDescription);
        tagsEditText.setText(currentTags);

        saveButton.setOnClickListener(v -> {

            // Get updated details from the input fields
            String updatedName = nameEditText.getText().toString();
            String updatedAddress = addressEditText.getText().toString();
            String updatedPhone = phoneEditText.getText().toString();
            String updatedDescription = descriptionEditText.getText().toString();
            String updatedTags = tagsEditText.getText().toString();

            // Pass updated details back to RestaurantDetailActivity
            Intent resultIntent = new Intent();
            resultIntent.putExtra("restaurantName", updatedName);
            resultIntent.putExtra("restaurantAddress", updatedAddress);
            resultIntent.putExtra("restaurantPhone", updatedPhone);
            resultIntent.putExtra("restaurantDescription", updatedDescription);
            resultIntent.putExtra("restaurantTags", updatedTags);
            setResult(RESULT_OK, resultIntent);
            finish(); // Close EditRestaurantActivity and return
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

    }
}