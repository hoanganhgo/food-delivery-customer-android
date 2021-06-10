package com.hcmus.fit.customer_apps.models;

public class ShipperModel {
    private String id;
    private String fullName;
    private String avatar;
    private String phone;

    public ShipperModel(String id, String fullName, String avatar, String phone) {
        this.id = id;
        this.fullName = fullName;
        this.avatar = avatar;
        this.phone = phone;
    }

    public String getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getPhone() {
        return phone;
    }
}
