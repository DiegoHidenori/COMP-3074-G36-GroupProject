package com.example.gps_guide;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.gps_guide.data.RestaurantRepository;

public class AddRestaurantActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_restaurant);

        EditText nameEditText = findViewById(R.id.input_name);
        EditText addressEditText = findViewById(R.id.input_address);
        EditText phoneEditText = findViewById(R.id.input_phone);
        EditText descriptionEditText = findViewById(R.id.input_description);
        EditText tagsEditText = findViewById(R.id.input_tags);
        EditText latitudeEditText = findViewById(R.id.input_latitude);
        EditText longitudeEditText = findViewById(R.id.input_longitude);
        Button saveButton = findViewById(R.id.save_button);
        Button backButton = findViewById(R.id.back_button);

        saveButton.setOnClickListener(v -> {

            String name = nameEditText.getText().toString().trim();
            String address = addressEditText.getText().toString().trim();
            String phone = phoneEditText.getText().toString().trim();
            String description = descriptionEditText.getText().toString().trim();
            String tags = tagsEditText.getText().toString().trim();
            String latitudeString = latitudeEditText.getText().toString().trim();
            String longitudeString = longitudeEditText.getText().toString().trim();

            if (name.isEmpty() || address.isEmpty()) {
                Toast.makeText(this, "Name and Address are required", Toast.LENGTH_SHORT).show();
                return;
            }

            double latitude = 0.0;
            double longitude = 0.0;
            try {
                latitude = Double.parseDouble(latitudeString);
                longitude = Double.parseDouble(longitudeString);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Invalid latitude or longitude", Toast.LENGTH_SHORT).show();
                return;
            }

            Restaurant newRestaurant = new Restaurant(
                    name, address, phone, description, tags, 0, latitude, longitude
            );

            RestaurantRepository.getInstance().addRestaurant(newRestaurant);

            Intent resultIntent = new Intent();
            newRestaurant.toIntent(resultIntent);
            setResult(RESULT_OK, resultIntent);
            Toast.makeText(this, "Restaurant added", Toast.LENGTH_SHORT).show();
            finish();

        });

        backButton.setOnClickListener(v -> {
            finish();
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {

            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;

        });

    }
}