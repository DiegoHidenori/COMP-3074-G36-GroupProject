package com.example.gps_guide;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gps_guide.data.RestaurantRepository;

import java.util.ArrayList;
import java.util.List;

public class RestaurantListActivity extends AppCompatActivity {


    // Request codes for the intents used with the other files.
    private static final int DETAILS_REQUEST_CODE = 2; // Request code for details activity
    private static final int ADD_REQUEST_CODE = 1; // Request code for adding a restaurant

    private RestaurantAdapter adapter;
    private RestaurantRepository restaurantRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_list);

        ListView listView = findViewById(R.id.restaurant_list_view);
        Button addRestaurantBtn = findViewById(R.id.add_restaurant_btn);
        Button goAboutBtn = findViewById(R.id.go_to_about_btn);

        restaurantRepository = RestaurantRepository.getInstance();
        List<Restaurant> restaurants = restaurantRepository.getRestaurants();

        adapter = new RestaurantAdapter(this, restaurants);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {

            Restaurant selectedRestaurant = (Restaurant) parent.getItemAtPosition(position);

            Intent intent = new Intent(RestaurantListActivity.this,
                    RestaurantDetailsActivity.class);
            intent.putExtra("restaurantName", selectedRestaurant.getName());
            intent.putExtra("restaurantAddress", selectedRestaurant.getAddress());
            intent.putExtra("restaurantPhone", selectedRestaurant.getPhone());
            intent.putExtra("restaurantDescription", selectedRestaurant.getDescription());
            intent.putExtra("restaurantTags", selectedRestaurant.getTags());
            intent.putExtra("restaurantRating", selectedRestaurant.getRating());
            intent.putExtra("restaurantPosition", position);
            intent.putExtra("restaurantLatitude", selectedRestaurant.getLatitude());
            intent.putExtra("restaurantLongitude", selectedRestaurant.getLongitude());
            startActivityForResult(intent, DETAILS_REQUEST_CODE);

        });

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

            Restaurant newRestaurant = new Restaurant(
                data.getStringExtra("restaurantName"),
                data.getStringExtra("restaurantAddress"),
                data.getStringExtra("restaurantPhone"),
                data.getStringExtra("restaurantDescription"),
                data.getStringExtra("restaurantTags"),
                data.getFloatExtra("restaurantRating", 0),
                data.getDoubleExtra("restaurantLatitude", 0),
                data.getDoubleExtra("restaurantLongitude", 0)
            );
            restaurantRepository.addRestaurant(newRestaurant);
            adapter.add(newRestaurant);

        } else if (requestCode == DETAILS_REQUEST_CODE && resultCode == RESULT_OK && data != null) {

            int deletePos = data.getIntExtra("deletePosition", -1);
            if (deletePos != -1) {

                restaurantRepository.deleteRestaurant(deletePos);
                adapter.remove(adapter.getItem(deletePos));
                adapter.notifyDataSetChanged();

            } else {

                int position = data.getIntExtra("restaurantPosition", -1);
                Restaurant restaurant = adapter.getItem(position);

                if (position != -1 && restaurant != null) {

                    restaurant.setName(data.getStringExtra("restaurantName"));
                    restaurant.setAddress(data.getStringExtra("restaurantAddress"));
                    restaurant.setPhone(data.getStringExtra("restaurantPhone"));
                    restaurant.setDescription(data.getStringExtra("restaurantDescription"));
                    restaurant.setTags(data.getStringExtra("restaurantTags"));
                    restaurant.setRating(data.getFloatExtra("restaurantRating", 0));
                    restaurant.setLatitude(data.getDoubleExtra("restaurantLatitude", 0));
                    restaurant.setLongitude(data.getDoubleExtra("restaurantLongitude", 0));

                    restaurantRepository.updateRestaurant(position, restaurant);
                    adapter.notifyDataSetChanged();

                }
            }
        }
    }

}
