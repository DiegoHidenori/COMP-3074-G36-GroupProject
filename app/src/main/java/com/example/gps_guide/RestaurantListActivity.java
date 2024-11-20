package com.example.gps_guide;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class RestaurantListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_list);

        ListView listView = findViewById(R.id.restaurant_list_view);

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

        RestaurantAdapter adapter = new RestaurantAdapter(this, restaurants);
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
            startActivity(intent);
        });
    }
}
