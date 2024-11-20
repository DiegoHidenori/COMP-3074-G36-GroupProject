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

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_restaurant_details);

        // Retrieve the passed data from the intent
        Intent intent = getIntent();
        String name = intent.getStringExtra("restaurantName");
        String address = intent.getStringExtra("restaurantAddress");
        String phone = intent.getStringExtra("restaurantPhone");
        String description = intent.getStringExtra("restaurantDescription");
        String tags = intent.getStringExtra("restaurantTags");

        // Bind data to UI elements
        TextView nameTextView = findViewById(R.id.detail_name);
        TextView addressTextView = findViewById(R.id.detail_address);
        TextView phoneTextView = findViewById(R.id.detail_phone);
        TextView descriptionTextView = findViewById(R.id.detail_description);
        TextView tagsTextView = findViewById(R.id.detail_tags);

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



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

}