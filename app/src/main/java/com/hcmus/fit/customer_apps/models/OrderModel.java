package com.hcmus.fit.customer_apps.models;

import java.util.ArrayList;
import java.util.List;

public class OrderModel {
    private String id;
    private String restaurantId;
    private String restaurantName;
    private String restaurantPhone;
    private String avatarRestaurant;
    private String restaurantAddress;
    private int shipFee;
    private int subTotal;
    private int total;
    private String address;
    private double distance;
    private List<DishOrder> dishOrders = new ArrayList<>();
    private ShipperModel shipper;
    private int status;

    public OrderModel(String id) {
        this.id = id;
        this.status = 0;
    }

    public String getId() {
        return id;
    }

    public List<DishOrder> getDishOrders() {
        return dishOrders;
    }

    public void setDishOrders(List<DishOrder> dishOrders) {
        this.dishOrders = dishOrders;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public void setRestaurantPhone(String restaurantPhone) {
        this.restaurantPhone = restaurantPhone;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getRestaurantAddress() {
        return restaurantAddress;
    }

    public void setRestaurantAddress(String restaurantAddress) {
        this.restaurantAddress = restaurantAddress;
    }

    public void setAvatarRestaurant(String avatarRestaurant) {
        this.avatarRestaurant = avatarRestaurant;
    }

    public void setShipFee(int shipFee) {
        this.shipFee = shipFee;
    }

    public void setSubTotal(int subTotal) {
        this.subTotal = subTotal;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotal() {
        return total;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public ShipperModel getShipper() {
        return shipper;
    }

    public void setShipper(ShipperModel shipper) {
        this.shipper = shipper;
    }
}
