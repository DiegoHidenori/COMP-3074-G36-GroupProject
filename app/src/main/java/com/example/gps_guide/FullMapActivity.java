package com.example.gps_guide;

import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class FullMapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private MapView mapView;
    private GoogleMap googleMap;
    private double latitude;
    private double longitude;
    private String restaurantName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_full_map);


        // Get data from the intent
        latitude = getIntent().getDoubleExtra("restaurantLatitude", 0.0);
        longitude = getIntent().getDoubleExtra("restaurantLongitude", 0.0);
        restaurantName = getIntent().getStringExtra("restaurantName");

        // Initialize MapView
        mapView = findViewById(R.id.full_map_view);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);


        Button goBackBtn = findViewById(R.id.go_back_btn);

        goBackBtn.setOnClickListener(view -> {

            finish();

        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap map) {
        googleMap = map;
        MapsInitializer.initialize(this);

        // Add a marker at the restaurant's location
        LatLng restaurantLocation = new LatLng(latitude, longitude);
        googleMap.addMarker(new MarkerOptions().position(restaurantLocation).title(restaurantName));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(restaurantLocation, 15));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
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
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
}