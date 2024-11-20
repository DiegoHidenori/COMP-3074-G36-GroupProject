package com.example.gps_guide;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class RestaurantListActivity extends AppCompatActivity {

    private static final int DETAILS_REQUEST_CODE = 2; // Request code for details activity
    private static final int ADD_REQUEST_CODE = 1; // Request code for adding a restaurant

    private RestaurantAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_list);

        ListView listView = findViewById(R.id.restaurant_list_view);
        Button addRestaurantBtn = findViewById(R.id.add_restaurant_btn);


        // Initial list with dummy data.
        List<Restaurant> restaurants = new ArrayList<>();
        restaurants.add(new Restaurant(
                "The Fancy Fork",
                "123 Main St",
                "123-456-7890",
                "Fine dining experience.",
                "Testing, test")
        );
        restaurants.add(new Restaurant(
                "Burger Haven",
                "456 Elm St",
                "987-654-3210",
                "Best burgers in town.",
                "Lala")
        );
        restaurants.add(new Restaurant(
                "Sushi World",
                "789 Maple Ave",
                "555-789-1234",
                "Authentic Japanese sushi.",
                "Sushi, Japanese"
        ));


        // Initializes the adapter and connects it to ListView.
        adapter = new RestaurantAdapter(this, restaurants);
        listView.setAdapter(adapter);


        // When a restaurant is clicked...
        listView.setOnItemClickListener((parent, view, position, id) -> {

            Restaurant selectedRestaurant = (Restaurant) parent.getItemAtPosition(position);

            Intent intent = new Intent(RestaurantListActivity.this,
                    RestaurantDetailsActivity.class);
            intent.putExtra("restaurantName", selectedRestaurant.getName());
            intent.putExtra("restaurantAddress", selectedRestaurant.getAddress());
            intent.putExtra("restaurantPhone", selectedRestaurant.getPhone());
            intent.putExtra("restaurantDescription", selectedRestaurant.getDescription());
            intent.putExtra("restaurantTags", selectedRestaurant.getTags());
            intent.putExtra("restaurantPosition", position);
            startActivityForResult(intent, DETAILS_REQUEST_CODE);

        });

        // When Add Restaurant button is clicked...
        addRestaurantBtn.setOnClickListener(v -> {
            Intent intent = new Intent(RestaurantListActivity.this, AddRestaurantActivity.class);
            startActivityForResult(intent, ADD_REQUEST_CODE); // Request code 1
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            // Retrieve the new restaurant details from the intent
            String name = data.getStringExtra("restaurantName");
            String address = data.getStringExtra("restaurantAddress");
            String phone = data.getStringExtra("restaurantPhone");
            String description = data.getStringExtra("restaurantDescription");
            String tags = data.getStringExtra("restaurantTags");

            // Create a new Restaurant object and add it to the adapter
            Restaurant newRestaurant = new Restaurant(name, address, phone, description, tags);
            adapter.add(newRestaurant); // Update the list in real-time
        }
        else if (requestCode == DETAILS_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            // Get updated restaurant details from the intent
            String updatedName = data.getStringExtra("restaurantName");
            String updatedAddress = data.getStringExtra("restaurantAddress");
            String updatedPhone = data.getStringExtra("restaurantPhone");
            String updatedDescription = data.getStringExtra("restaurantDescription");
            String updatedTags = data.getStringExtra("restaurantTags");
            int position = data.getIntExtra("restaurantPosition", -1);

            if (position != -1) {
                // Update the restaurant in the adapter
                Restaurant updatedRestaurant = new Restaurant(updatedName, updatedAddress, updatedPhone, updatedDescription, updatedTags);
                adapter.remove(adapter.getItem(position)); // Remove the old restaurant
                adapter.insert(updatedRestaurant, position); // Insert the updated restaurant
                adapter.notifyDataSetChanged(); // Refresh the list
            }
        }
    }

}
