package com.hcmus.fit.customer_apps.models;

public class DishOrder {
    public DishModel dishModel;
    public int num = 0;

    public DishOrder(DishModel dishModel, int num) {
        this.dishModel = dishModel;
        this.num = num;
    }

    public int getTotalPrice() {
        return this.dishModel.getPrice() * this.num;
    }
}
