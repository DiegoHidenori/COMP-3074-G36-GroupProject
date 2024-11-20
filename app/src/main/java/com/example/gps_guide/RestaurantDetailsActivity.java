package com.example.gps_guide;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class RestaurantDetailsActivity extends AppCompatActivity {


    // Request codes for startActivityForResult methods.
    private static final int EDIT_REQUEST_CODE = 2;

    private TextView nameTextView, addressTextView,
            phoneTextView, descriptionTextView, tagsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_restaurant_details);


        // Retrieve the passed data from the Intent
        Intent intent = getIntent();
        String name = intent.getStringExtra("restaurantName");
        String address = intent.getStringExtra("restaurantAddress");
        String phone = intent.getStringExtra("restaurantPhone");
        String description = intent.getStringExtra("restaurantDescription");
        String tags = intent.getStringExtra("restaurantTags");


        // Set the texts from the RestaurantDetailsActivity
        nameTextView = findViewById(R.id.detail_name);
        addressTextView = findViewById(R.id.detail_address);
        phoneTextView = findViewById(R.id.detail_phone);
        descriptionTextView = findViewById(R.id.detail_description);
        tagsTextView = findViewById(R.id.detail_tags);

        nameTextView.setText(name);
        addressTextView.setText(address);
        phoneTextView.setText(phone);
        descriptionTextView.setText(description);
        tagsTextView.setText(tags);


        // Button to go back to Restaurant list
        Button btn = findViewById(R.id.go_to_list_btn);

        btn.setOnClickListener(view -> {


            // finish() method ends the current activity and removes it from the stack. It then
            // goes to the next activity on top of the stack (Android manages stack of activities).
            finish();

        });


        // Button for editing the restaurant.
        Button editBtn = findViewById(R.id.edit_button);

        editBtn.setOnClickListener(v -> {


            // Launches the EditRestaurantActivity and passes the details with an Intent.
            Intent editIntent = new Intent(RestaurantDetailsActivity.this,
                    EditRestaurantActivity.class);


            // Intent to pass restaurant details to the EditRestaurantActivity.
            editIntent.putExtra("restaurantName", nameTextView.getText().toString());
            editIntent.putExtra("restaurantAddress", addressTextView.getText().toString());
            editIntent.putExtra("restaurantPhone", phoneTextView.getText().toString());
            editIntent.putExtra("restaurantDescription",
                    descriptionTextView.getText().toString());
            editIntent.putExtra("restaurantTags", tagsTextView.getText().toString());
            startActivityForResult(editIntent, EDIT_REQUEST_CODE);

        });


        // Handles edge-to-edge window insets?
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {

            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;

        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == EDIT_REQUEST_CODE && resultCode == RESULT_OK && data != null) {


            // Updates the data with the updated details.
            String updatedName = data.getStringExtra("restaurantName");
            String updatedAddress = data.getStringExtra("restaurantAddress");
            String updatedPhone = data.getStringExtra("restaurantPhone");
            String updatedDescription = data.getStringExtra("restaurantDescription");
            String updatedTags = data.getStringExtra("restaurantTags");


            // Updates the restaurant with the new details
            nameTextView.setText(updatedName);
            addressTextView.setText(updatedAddress);
            phoneTextView.setText(updatedPhone);
            descriptionTextView.setText(updatedDescription);
            tagsTextView.setText(updatedTags);


            // Pass updated details back to the RestaurantListActivity.
            Intent resultIntent = new Intent();
            resultIntent.putExtra("restaurantName", updatedName);
            resultIntent.putExtra("restaurantAddress", updatedAddress);
            resultIntent.putExtra("restaurantPhone", updatedPhone);
            resultIntent.putExtra("restaurantDescription", updatedDescription);
            resultIntent.putExtra("restaurantTags", updatedTags);
            resultIntent.putExtra("restaurantPosition", getIntent()
                    .getIntExtra("restaurantPosition", -1)); // Pass position
            setResult(RESULT_OK, resultIntent);

        }
    }

}