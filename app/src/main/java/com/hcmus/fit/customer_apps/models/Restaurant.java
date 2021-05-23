package com.hcmus.fit.customer_apps.models;

import java.util.ArrayList;
import java.util.List;

public class Restaurant {
    private String id;
    private String avatar;
    private String name;
    private String fullAddress;
    private boolean isOpening;
    private String hours;
    private String sale;
    private List<DishModel> dishList = new ArrayList<>();

    public Restaurant() {
    }

    public Restaurant(String id, String avatar, String name, String fullAddress,
                      String hours, boolean isOpening) {
        this.id = id;
        this.avatar = avatar;
        this.name = name;
        this.fullAddress = fullAddress;
        this.hours = hours;
        this.isOpening = isOpening;
    }

    public String getId() {
        return id;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getName() {
        return name;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public boolean isOpening() {
        return isOpening;
    }

    public String getHours() {
        return hours;
    }

    public String getSale() {
        return sale;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }

    public void setOpening(boolean opening) {
        isOpening = opening;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public void setSale(String sale) {
        this.sale = sale;
    }

    public List<DishModel> getDishList() {
        return dishList;
    }
}
