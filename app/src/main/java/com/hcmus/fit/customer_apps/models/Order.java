package com.hcmus.fit.customer_apps.models;

import java.util.List;

public class Order {
    private String id;
    private String restaurantId;
    private String restaurantPhone;
    private int shipFee;
    private int subTotal;
    private int total;
    private String address;
    private List<DishOrder> dishOrders;
    private int status;

    public Order(String id) {
        this.id = id;
        this.status = 0;
    }

    public String getId() {
        return id;
    }

    public List<DishOrder> getDishOrders() {
        return dishOrders;
    }

    public int getStatus() {
        return status;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public void setRestaurantPhone(String restaurantPhone) {
        this.restaurantPhone = restaurantPhone;
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

    public void setAddress(String address) {
        this.address = address;
    }
}
