package com.hcmus.fit.customer_apps.models;

public class AddressModel {
    private String fullAddress;
    private String name;
    private double longitude;
    private double latitude;

    public AddressModel(String fullAddress, String name) {
        this.fullAddress = fullAddress;
        this.name = name;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public String getName() {
        return name;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
