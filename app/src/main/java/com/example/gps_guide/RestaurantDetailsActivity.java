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

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class RestaurantDetailsActivity extends AppCompatActivity {


    // Request codes for startActivityForResult methods.
    private static final int EDIT_REQUEST_CODE = 2;


    // The global variables to store the restaurant data.
    private TextView nameTextView, addressTextView,
            phoneTextView, descriptionTextView, tagsTextView;
    private RatingBar ratingBar;
    private float currentRating;


    // Map functionality
    private MapView mapView;
    private GoogleMap googleMap;
    private double latitude;
    private double longitude;

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
        int position = intent.getIntExtra("restaurantPosition", -1);
        currentRating = intent.getFloatExtra("restaurantRating", 0);
        latitude = intent.getDoubleExtra("restaurantLatitude", 0.0);
        longitude = intent.getDoubleExtra("restaurantLongitude", 0.0);


        // Initialize MapView
        mapView = findViewById(R.id.map_view);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(googleMap -> {
            this.googleMap = googleMap;
            MapsInitializer.initialize(this);


            // Marker for the location
            LatLng restaurantLocation = new LatLng(latitude, longitude);
            googleMap.addMarker(new MarkerOptions().position(restaurantLocation).title(name));
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(restaurantLocation, 15));

        });


        // Set the texts from the RestaurantDetailsActivity
        nameTextView = findViewById(R.id.detail_name);
        addressTextView = findViewById(R.id.detail_address);
        phoneTextView = findViewById(R.id.detail_phone);
        descriptionTextView = findViewById(R.id.detail_description);
        tagsTextView = findViewById(R.id.detail_tags);
        ratingBar = findViewById(R.id.rating_bar);

        nameTextView.setText(name);
        addressTextView.setText(address);
        phoneTextView.setText(phone);
        descriptionTextView.setText(description);
        tagsTextView.setText(tags);
        ratingBar.setRating(currentRating);

        ratingBar.setOnRatingBarChangeListener((ratingBar, rating, fromUser) -> {

            if (fromUser) {

                currentRating = rating;

            }

        });


        // Button to go back to Restaurant list
        Button btn = findViewById(R.id.go_to_list_btn);

        btn.setOnClickListener(view -> {


            // To pass rating to the RestaurantListActivity.
            Intent resultIntent = new Intent();
            resultIntent.putExtra("restaurantName", nameTextView.getText().toString());
            resultIntent.putExtra("restaurantAddress", addressTextView.getText().toString());
            resultIntent.putExtra("restaurantPhone", phoneTextView.getText().toString());
            resultIntent.putExtra("restaurantDescription", descriptionTextView.getText().toString());
            resultIntent.putExtra("restaurantTags", tagsTextView.getText().toString());
            resultIntent.putExtra("restaurantRating", ratingBar.getRating());
            resultIntent.putExtra("restaurantPosition",
                    getIntent().getIntExtra("restaurantPosition", -1));
            setResult(RESULT_OK, resultIntent);


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


        Button deleteBtn = findViewById(R.id.delete_button);
        deleteBtn.setOnClickListener(view -> {


            // Pass position of the restaurant to know which to delete in the list.
            Intent resultIntent = new Intent();
            resultIntent.putExtra("deletePosition", position);
            setResult(RESULT_OK, resultIntent);
            finish();

        });


        // Button for directions functionality.
        Button directionsButton = findViewById(R.id.directions_btn);

        directionsButton.setOnClickListener(v -> {

            // Get the latitude and longitude of the restaurant
            double latitude = getIntent().getDoubleExtra("restaurantLatitude", 0.0);
            double longitude = getIntent().getDoubleExtra("restaurantLongitude", 0.0);

            // Create the Google Maps navigation URI
            String uri = String.format("google.navigation:q=%f,%f", latitude, longitude);

            // Create an Intent to open Google Maps
            Intent directionsIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
            directionsIntent.setPackage("com.google.android.apps.maps");

            // Verify Google Maps is installed before starting the activity
            if (directionsIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(directionsIntent);
            } else {
                Toast.makeText(this, "Google Maps is not installed", Toast.LENGTH_SHORT).show();
            }

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