package com.hcmus.fit.customer_apps.models;

import com.hcmus.fit.customer_apps.activities.OrderStatusActivity;

public class OrderManager {
    private OrderStatusActivity activity;
    private OrderModel orderModel;

    public OrderManager() {
    }

    public OrderStatusActivity getActivity() {
        return activity;
    }

    public void setActivity(OrderStatusActivity activity) {
        this.activity = activity;
    }

    public OrderModel getOrderModel() {
        return orderModel;
    }

    public void setOrderModel(OrderModel orderModel) {
        this.orderModel = orderModel;
    }

    public void setStatusWaitingDish() {
        if (activity != null) {
            activity.runOnUiThread(() -> activity.setStatusWaitingDish());
        }
    }

    public void setStatusShipping() {
        if (activity != null) {
            activity.runOnUiThread(() -> activity.setStatusShipping());
        }
    }

    public void setStatusArrived() {
        if (activity != null) {
            activity.runOnUiThread(() -> activity.setStatusArrived());
        }
    }
}
