package com.example.gps_guide;

import android.content.Intent;

import java.io.Serializable;

public class Restaurant implements Serializable {

    private String name;
    private String address;
    private String phone;
    private String description;
    private String tags;
    private float rating;
    private double latitude;
    private double longitude;

    public Restaurant(String name, String address, String phone, String description, String tags, float rating, double latitude, double longitude) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.description = description;
        this.tags = tags;
        this.rating = rating;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    // Method to convert this object to an Intent
    public Intent toIntent(Intent intent) {
        intent.putExtra("restaurantName", this.name);
        intent.putExtra("restaurantAddress", this.address);
        intent.putExtra("restaurantPhone", this.phone);
        intent.putExtra("restaurantDescription", this.description);
        intent.putExtra("restaurantTags", this.tags);
        intent.putExtra("restaurantRating", this.rating);
        intent.putExtra("restaurantLatitude", this.latitude);
        intent.putExtra("restaurantLongitude", this.longitude);
        return intent;
    }

    // Method to create a Restaurant object from an Intent
    public static Restaurant fromIntent(Intent intent) {
        return new Restaurant(
                intent.getStringExtra("restaurantName"),
                intent.getStringExtra("restaurantAddress"),
                intent.getStringExtra("restaurantPhone"),
                intent.getStringExtra("restaurantDescription"),
                intent.getStringExtra("restaurantTags"),
                intent.getFloatExtra("restaurantRating", 0),
                intent.getDoubleExtra("restaurantLatitude", 0.0),
                intent.getDoubleExtra("restaurantLongitude", 0.0)
        );
    }
}
