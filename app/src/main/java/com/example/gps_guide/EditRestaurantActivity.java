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

public class EditRestaurantActivity extends AppCompatActivity {

    private int position;
    private Restaurant restaurant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_restaurant);

        EditText nameEditText = findViewById(R.id.input_name);
        EditText addressEditText = findViewById(R.id.input_address);
        EditText phoneEditText = findViewById(R.id.input_phone);
        EditText descriptionEditText = findViewById(R.id.input_description);
        EditText tagsEditText = findViewById(R.id.input_tags);
        Button saveButton = findViewById(R.id.save_button);
        Button cancelButton = findViewById(R.id.cancel_button);

        Intent intent = getIntent();
        restaurant = Restaurant.fromIntent(intent);
        position = intent.getIntExtra("restaurantPosition", -1);

        if (restaurant != null) {
            nameEditText.setText(restaurant.getName());
            addressEditText.setText(restaurant.getAddress());
            phoneEditText.setText(restaurant.getPhone());
            descriptionEditText.setText(restaurant.getDescription());
            tagsEditText.setText(restaurant.getTags());
        }

        saveButton.setOnClickListener(v -> {

            String updatedName = nameEditText.getText().toString().trim();
            String updatedAddress = addressEditText.getText().toString().trim();
            String updatedPhone = phoneEditText.getText().toString().trim();
            String updatedDescription = descriptionEditText.getText().toString().trim();
            String updatedTags = tagsEditText.getText().toString().trim();

            if (updatedName.isEmpty() || updatedAddress.isEmpty()) {
                Toast.makeText(this, "Name and Address are required", Toast.LENGTH_SHORT).show();
                return;
            }

            if (position != -1 && restaurant != null) {
                restaurant.setName(updatedName);
                restaurant.setAddress(updatedAddress);
                restaurant.setPhone(updatedPhone);
                restaurant.setDescription(updatedDescription);
                restaurant.setTags(updatedTags);

                RestaurantRepository.getInstance().updateRestaurant(position, restaurant);

                Intent resultIntent = new Intent();
                restaurant.toIntent(resultIntent);
                resultIntent.putExtra("restaurantPosition", position);
                setResult(RESULT_OK, resultIntent);
                Toast.makeText(this, "Restaurant updated", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Error: Invalid position", Toast.LENGTH_SHORT).show();
            }

        });

        cancelButton.setOnClickListener(v -> {
            finish();
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {

            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;

        });

    }
}