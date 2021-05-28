package com.hcmus.fit.customer_apps.models;

public class Address {
    private String fullAddress;
    private String name;
    private float longitude;
    private float latitude;

    public Address(String fullAddress, String name) {
        this.fullAddress = fullAddress;
        this.name = name;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public String getName() {
        return name;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }
}
