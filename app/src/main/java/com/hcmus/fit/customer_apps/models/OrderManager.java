package com.hcmus.fit.customer_apps.models;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import androidx.appcompat.app.AlertDialog;

import com.hcmus.fit.customer_apps.R;
import com.hcmus.fit.customer_apps.activities.OrderStatusActivity;
import com.hcmus.fit.customer_apps.adapters.OrderAdapter;
import com.hcmus.fit.customer_apps.networks.DishNetwork;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class OrderManager {
    private static OrderManager instance = null;
    private OrderStatusActivity activity;
    private final List<OrderModel> orderList = new ArrayList<>();
    private OrderAdapter orderAdapter;

    private OrderManager() {
    }

    public static OrderManager getInstance() {
        if (instance == null) {
            instance = new OrderManager();
        }

        return instance;
    }

    public void setActivity(OrderStatusActivity activity) {
        this.activity = activity;
    }

    public void setOrderAdapter(OrderAdapter orderAdapter) {
        this.orderAdapter = orderAdapter;
    }

    public void addOrderModel(OrderModel orderModel) {
        this.orderList.add(orderModel);
    }

    public int getOrderListSize() {
        return this.orderList.size();
    }

    public OrderModel getOrderModel(int index) {
        return this.orderList.get(index);
    }

    public OrderModel getOrderModel(String orderId) {
        for (OrderModel orderModel : this.orderList) {
            if (orderModel.getId().equals(orderId)) {
                return orderModel;
            }
        }

        return null;
    }

    public ShipperModel getShipper(String shipperId) {
        for (OrderModel order : this.orderList) {
            ShipperModel shipper = order.getShipper();

            if (shipper == null) {
                continue;
            }

            if (shipper.getId().equals(shipperId)) {
                return shipper;
            }
        }

        return null;
    }

    public void removeOrderModel(String orderId) {
        Iterator<OrderModel> it = this.orderList.iterator();

        while (it.hasNext()) {
            if (it.next().getId().equals(orderId)) {
                it.remove();
            }
        }
    }

    public void setStatusOrder(String orderId, int status) {
        for (OrderModel orderModel : this.orderList) {
            if (orderModel.getId().equals(orderId)) {
                orderModel.setStatus(status);
            }
        }
    }

    public boolean activityActive(String orderId) {
        return activity != null && !activity.isDestroyed() && orderId.equals(activity.orderId);
    }

    public void setStatusWaitingDish(String orderId) {
        if (activityActive(orderId)) {
            activity.runOnUiThread(() -> activity.setStatusWaitingDish());
        }
    }

    public void setStatusShipping(String orderId) {
        if (activityActive(orderId)) {
            activity.runOnUiThread(() -> activity.setStatusShipping());
        }
    }

    public void setStatusArrived(String orderId) {
        if (activityActive(orderId)) {
            activity.runOnUiThread(() -> activity.setStatusArrived());

            try {
                Thread.sleep(1000);
                activity.runOnUiThread(() -> showRating(orderId));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            removeOrderModel(orderId);
        }
    }

    public void showRating(String orderId) {
        AlertDialog alertDialog = new AlertDialog.Builder(this.activity).create();
        LayoutInflater inflater = LayoutInflater.from(this.activity);
        View rateView = inflater.inflate(R.layout.alert_rating, null);
        RatingBar ratingBar = rateView.findViewById(R.id.rating);
        EditText edtRating = rateView.findViewById(R.id.edt_rating);
        Button btnRating = rateView.findViewById(R.id.btn_rating);
        Button btnCancel = rateView.findViewById(R.id.btn_cancel);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                ratingBar.setRating(rating);
            }
        });

        ratingBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("star", "click start");
            }
        });

        btnRating.setOnClickListener(v -> {
            DishNetwork.ratingShipper(this.activity, orderId, edtRating.getText().toString(), (int) ratingBar.getRating());
            alertDialog.dismiss();
        });

        btnCancel.setOnClickListener(v -> {
            alertDialog.dismiss();
        });

        alertDialog.setView(rateView);
        alertDialog.show();
    }

    public void updateShipperInfo(String orderId, ShipperModel shipper) {
        for (OrderModel orderModel : this.orderList) {
            if (orderModel.getId().equals(orderId)) {
                orderModel.setShipper(shipper);
                break;
            }
        }

        if (activityActive(orderId)) {
            activity.runOnUiThread(() -> activity.updateShipperInfo(shipper));
        }
    }
}
