package com.hcmus.fit.customer_apps.models;

import com.hcmus.fit.customer_apps.activities.OrderStatusActivity;

public class OrderManager {
    private OrderStatusActivity activity;
    private Order order;

    public OrderManager() {
    }

    public OrderStatusActivity getActivity() {
        return activity;
    }

    public void setActivity(OrderStatusActivity activity) {
        this.activity = activity;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
