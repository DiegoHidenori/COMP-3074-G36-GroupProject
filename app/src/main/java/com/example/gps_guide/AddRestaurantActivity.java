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

public class AddRestaurantActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_restaurant);


        // Get the data form the xml file.
        EditText nameEditText = findViewById(R.id.input_name);
        EditText addressEditText = findViewById(R.id.input_address);
        EditText phoneEditText = findViewById(R.id.input_phone);
        EditText descriptionEditText = findViewById(R.id.input_description);
        EditText tagsEditText = findViewById(R.id.input_tags);
        Button saveButton = findViewById(R.id.save_button);

        saveButton.setOnClickListener(v -> {


            // Get user inputs
            String name = nameEditText.getText().toString();
            String address = addressEditText.getText().toString();
            String phone = phoneEditText.getText().toString();
            String description = descriptionEditText.getText().toString();
            String tags = tagsEditText.getText().toString();


            // Intent to pass data back to RestaurantListActivity.
            Intent resultIntent = new Intent();
            resultIntent.putExtra("restaurantName", name);
            resultIntent.putExtra("restaurantAddress", address);
            resultIntent.putExtra("restaurantPhone", phone);
            resultIntent.putExtra("restaurantDescription", description);
            resultIntent.putExtra("restaurantTags", tags);
            setResult(RESULT_OK, resultIntent);
            finish(); // Closes this activity and returns to the list.

        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {

            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;

        });

    }
}