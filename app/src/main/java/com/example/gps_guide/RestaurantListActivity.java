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


    // Request codes for the intents used with the other files.
    private static final int DETAILS_REQUEST_CODE = 2; // Request code for details activity
    private static final int ADD_REQUEST_CODE = 1; // Request code for adding a restaurant


    // Instance of the RestaurantAdapter for the display.
    private RestaurantAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_list);

        ListView listView = findViewById(R.id.restaurant_list_view);
        Button addRestaurantBtn = findViewById(R.id.add_restaurant_btn);
        Button goAboutBtn = findViewById(R.id.go_to_about_btn);


        // Initial list with dummy data.
        List<Restaurant> restaurants = new ArrayList<>();
        restaurants.add(new Restaurant(
                "The Fancy Fork",
                "123 Main St",
                "123-456-7890",
                "Fine dining experience.",
                "Testing, test",
                0
        ));
        restaurants.add(new Restaurant(
                "Burger Haven",
                "456 Elm St",
                "987-654-3210",
                "Best burgers in town.",
                "Lala",
                0
        ));
        restaurants.add(new Restaurant(
                "Sushi World",
                "789 Maple Ave",
                "555-789-1234",
                "Authentic Japanese sushi.",
                "Sushi, Japanese",
                0
        ));


        // Initializes the adapter and connects it to ListView in the xml file.
        adapter = new RestaurantAdapter(this, restaurants);
        listView.setAdapter(adapter);


        // When a restaurant is clicked...
        listView.setOnItemClickListener((parent, view, position, id) -> {


            // Get the selected restaurant object
            Restaurant selectedRestaurant = (Restaurant) parent.getItemAtPosition(position);


            // Create an intent that passes data to the details activity.
            Intent intent = new Intent(RestaurantListActivity.this,
                    RestaurantDetailsActivity.class);
            intent.putExtra("restaurantName", selectedRestaurant.getName());
            intent.putExtra("restaurantAddress", selectedRestaurant.getAddress());
            intent.putExtra("restaurantPhone", selectedRestaurant.getPhone());
            intent.putExtra("restaurantDescription", selectedRestaurant.getDescription());
            intent.putExtra("restaurantTags", selectedRestaurant.getTags());
            intent.putExtra("restaurantRating", selectedRestaurant.getRating());
            intent.putExtra("restaurantPosition", position);
            startActivityForResult(intent, DETAILS_REQUEST_CODE);

        });


        // Takes you to the AddRestaurantActivity
        addRestaurantBtn.setOnClickListener(v -> {

            Intent intent = new Intent(RestaurantListActivity.this, AddRestaurantActivity.class);
            startActivityForResult(intent, ADD_REQUEST_CODE); // Request code 1

        });


        goAboutBtn.setOnClickListener(view -> {

            Intent intent = new Intent(RestaurantListActivity.this, AboutActivity.class);
            startActivity(intent);

        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_REQUEST_CODE && resultCode == RESULT_OK && data != null) {


            // Retrieve the new restaurant details from the AddRestaurantActivity.
            String name = data.getStringExtra("restaurantName");
            String address = data.getStringExtra("restaurantAddress");
            String phone = data.getStringExtra("restaurantPhone");
            String description = data.getStringExtra("restaurantDescription");
            String tags = data.getStringExtra("restaurantTags");
            float rating = data.getFloatExtra("restaurantRating", 0);


            // Create a new Restaurant object and add it to the adapter
            Restaurant newRestaurant = new Restaurant(name, address, phone, description,
                    tags, rating);


            // Update the list in real-time
            adapter.add(newRestaurant);

        } else if (requestCode == DETAILS_REQUEST_CODE && resultCode == RESULT_OK && data != null) {


            // Checks if have to delete
            int deletePos = data.getIntExtra("deletePosition", -1);
            if (deletePos != -1) {


                // Delete restaurant from the specified position
                adapter.remove(adapter.getItem(deletePos));
                adapter.notifyDataSetChanged(); // To refresh the list

            } else {

                int position = data.getIntExtra("restaurantPosition", -1);
                // Get updated restaurant details from the RestaurantDetailsActivity.
//                String updatedName = data.getStringExtra("restaurantName");
//                String updatedAddress = data.getStringExtra("restaurantAddress");
//                String updatedPhone = data.getStringExtra("restaurantPhone");
//                String updatedDescription = data.getStringExtra("restaurantDescription");
//                String updatedTags = data.getStringExtra("restaurantTags");
//                int position = data.getIntExtra("restaurantPosition", -1);
//                float updatedRating = data.getFloatExtra("restaurantRating", 0);

                if (position != -1) {


                    // Update the restaurant in the adapter
//                    Restaurant updatedRestaurant = new Restaurant(updatedName, updatedAddress,
//                            updatedPhone, updatedDescription, updatedTags, updatedRating);
//                    adapter.remove(adapter.getItem(position)); // Remove the old restaurant
//                    adapter.insert(updatedRestaurant, position); // Insert the updated restaurant
//                    adapter.notifyDataSetChanged(); // Refresh the list

                    Restaurant restaurant = adapter.getItem(position);

                    if (restaurant != null) {

                        // Update the existing restaurant object
                        restaurant.setName(data.getStringExtra("restaurantName"));
                        restaurant.setAddress(data.getStringExtra("restaurantAddress"));
                        restaurant.setPhone(data.getStringExtra("restaurantPhone"));
                        restaurant.setDescription(data.getStringExtra("restaurantDescription"));
                        restaurant.setTags(data.getStringExtra("restaurantTags"));
                        restaurant.setRating(data.getFloatExtra("restaurantRating", 0));

                        // Notify the adapter that the data has changed
                        adapter.notifyDataSetChanged();

                    }

                }
            }
        }
    }

}
