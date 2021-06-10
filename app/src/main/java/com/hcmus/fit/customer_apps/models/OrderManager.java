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

public class OrderManager {
    private OrderStatusActivity activity;
    private OrderModel orderModel;

    public OrderManager() {
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
        if (activity != null && !activity.isDestroyed()) {
            activity.runOnUiThread(() -> activity.setStatusWaitingDish());
        }
    }

    public void setStatusShipping() {
        if (activity != null && !activity.isDestroyed()) {
            activity.runOnUiThread(() -> activity.setStatusShipping());
        }
    }

    public void setStatusArrived() {
        if (activity != null && !activity.isDestroyed()) {
            activity.runOnUiThread(() -> activity.setStatusArrived());
            try {
                Thread.sleep(1000);
                activity.runOnUiThread(this::showRating);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void showRating() {
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

        btnCancel.setOnClickListener(v -> {
            alertDialog.dismiss();
        });

        alertDialog.setView(rateView);
        alertDialog.show();
    }

    public void updateShipperInfo(ShipperModel shipper) {
        orderModel.setShipper(shipper);

        if (activity != null && !activity.isDestroyed()) {
            activity.runOnUiThread(() -> activity.updateShipperInfo(shipper));
        }
    }
}
