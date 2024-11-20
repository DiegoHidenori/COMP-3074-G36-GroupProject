package com.example.gps_guide;


public class Restaurant {

    private String name;
    private String address;
    private String phone;
    private String description;
    private String tags;

    public Restaurant() {}

    public Restaurant(String name, String address, String phone, String description, String tags) {

        this.name = name;
        this.address = address;
        this.phone = phone;
        this.description = description;
        this.tags = tags;

    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }
}
