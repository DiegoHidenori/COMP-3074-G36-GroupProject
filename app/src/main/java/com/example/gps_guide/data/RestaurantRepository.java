package com.example.gps_guide.data;

import java.util.ArrayList;
import java.util.List;

import com.example.gps_guide.Restaurant;

public class RestaurantRepository {

    private final List<Restaurant> restaurantList = new ArrayList<>();
    private static RestaurantRepository restaurantRepository;

    private RestaurantRepository() {
        restaurantList.add(new Restaurant(
                "The Fancy Fork", "123 Main St", "123-456-7890",
                "Fine dining experience.", "Testing, test", 0, 43.6532, -79.3832
        ));
        restaurantList.add(new Restaurant(
                "Burger Haven", "456 Elm St", "987-654-3210",
                "Best burgers in town.", "Lala", 0, 43.7000, -79.4000
        ));
        restaurantList.add(new Restaurant(
                "Sushi World", "789 Maple Ave", "555-789-1234",
                "Authentic Japanese sushi.", "Sushi, Japanese", 0, 43.6617, -79.3950
        ));
    }

    public static synchronized RestaurantRepository getInstance() {
        if (restaurantRepository == null) {
            restaurantRepository = new RestaurantRepository();
        }
        return restaurantRepository;
    }

    public List<Restaurant> getRestaurants() {
        return new ArrayList<>(restaurantList);
    }

    public void addRestaurant(Restaurant restaurant) {
        restaurantList.add(restaurant);
    }

    public void updateRestaurant(int position, Restaurant updatedRestaurant) {
        if (position >= 0 && position < restaurantList.size()) {
            restaurantList.set(position, updatedRestaurant);
        }
    }

    public void deleteRestaurant(int position) {
        if (position >= 0 && position < restaurantList.size()) {
            restaurantList.remove(position);
        }
    }
}