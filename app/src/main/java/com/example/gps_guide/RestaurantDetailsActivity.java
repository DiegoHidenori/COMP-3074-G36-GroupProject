package com.example.gps_guide;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.gps_guide.data.RestaurantRepository;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class RestaurantDetailsActivity extends AppCompatActivity {

    private static final int EDIT_REQUEST_CODE = 2;

    private TextView nameTextView, addressTextView, phoneTextView, descriptionTextView, tagsTextView;
    private RatingBar ratingBar;
    private float currentRating;
    private MapView mapView;
    private GoogleMap googleMap;
    private Restaurant restaurant;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_restaurant_details);

        Intent intent = getIntent();
        restaurant = Restaurant.fromIntent(intent);
        position = intent.getIntExtra("restaurantPosition", -1);

        nameTextView = findViewById(R.id.detail_name);
        addressTextView = findViewById(R.id.detail_address);
        phoneTextView = findViewById(R.id.detail_phone);
        descriptionTextView = findViewById(R.id.detail_description);
        tagsTextView = findViewById(R.id.detail_tags);
        ratingBar = findViewById(R.id.rating_bar);
        mapView = findViewById(R.id.map_view);

        populateFields();

        ratingBar.setOnRatingBarChangeListener((ratingBar, rating, fromUser) -> {
            if (fromUser) {
                currentRating = rating;
                restaurant.setRating(rating);
                if (position != -1) {
                    RestaurantRepository.getInstance().updateRestaurant(position, restaurant);
                }
            }
        });

        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(googleMap -> {
            this.googleMap = googleMap;
            MapsInitializer.initialize(this);
            LatLng restaurantLocation = new LatLng(restaurant.getLatitude(), restaurant.getLongitude());
            googleMap.addMarker(new MarkerOptions().position(restaurantLocation).title(restaurant.getName()));
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(restaurantLocation, 15));
        });

        Button goToListButton = findViewById(R.id.go_to_list_btn);
        goToListButton.setOnClickListener(view -> {
            Intent resultIntent = new Intent();
            restaurant.toIntent(resultIntent);
            resultIntent.putExtra("restaurantPosition", position);
            setResult(RESULT_OK, resultIntent);
            finish();
        });

        Button editButton = findViewById(R.id.edit_button);
        editButton.setOnClickListener(view -> {
            Intent editIntent = new Intent(RestaurantDetailsActivity.this, EditRestaurantActivity.class);
            restaurant.toIntent(editIntent);
            editIntent.putExtra("restaurantPosition", position);
            startActivityForResult(editIntent, EDIT_REQUEST_CODE);
        });

        Button deleteButton = findViewById(R.id.delete_button);
        deleteButton.setOnClickListener(view -> {
            if (position != -1) {
                RestaurantRepository.getInstance().deleteRestaurant(position);
                Toast.makeText(this, "Restaurant deleted", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
                finish();
            }
        });

        Button fullMapButton = findViewById(R.id.view_full_map_btn);
        fullMapButton.setOnClickListener(v -> {

            Intent mapIntent = new Intent(RestaurantDetailsActivity.this, FullMapActivity.class);
            mapIntent.putExtra("restaurantLatitude", restaurant.getLatitude());
            mapIntent.putExtra("restaurantLongitude", restaurant.getLongitude());
            mapIntent.putExtra("restaurantName", restaurant.getName());
            startActivity(mapIntent);

        });

        Button directionsButton = findViewById(R.id.directions_btn);
        directionsButton.setOnClickListener(v -> {

            String uri = String.format("google.navigation:q=%f,%f", restaurant.getLatitude(), restaurant.getLongitude());
            Intent directionsIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
            directionsIntent.setPackage("com.google.android.apps.maps");

            if (directionsIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(directionsIntent);
            } else {
                Toast.makeText(this, "Google Maps is not installed",
                        Toast.LENGTH_SHORT).show();
            }

        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {

            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;

        });
    }

    private void populateFields() {
        nameTextView.setText(restaurant.getName());
        addressTextView.setText(restaurant.getAddress());
        phoneTextView.setText(restaurant.getPhone());
        descriptionTextView.setText(restaurant.getDescription());
        tagsTextView.setText(restaurant.getTags());
        ratingBar.setRating(restaurant.getRating());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == EDIT_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            restaurant = Restaurant.fromIntent(data);
            populateFields();
            if (position != -1) {
                RestaurantRepository.getInstance().updateRestaurant(position, restaurant);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
}