package com.hcmus.fit.customer_apps.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.hcmus.fit.customer_apps.R;
import com.hcmus.fit.customer_apps.models.UserInfo;

public class OrderStatusActivity extends AppCompatActivity {

    private LinearLayout lnWay01;
    private LinearLayout lnWay02;
    private LinearLayout lnWay03;
    private LinearLayout lnWay04;
    private TextView tvWaiting;
    private TextView tvWaitShipper;
    private TextView tvShipping;
    private TextView tvArrived;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_status);

        UserInfo.getInstance().getOrderManager().setActivity(this);
    }

    public void setStatusWaiting() {
        this.lnWay01.setBackgroundColor(Color.BLACK);
        this.tvWaiting.setVisibility(View.VISIBLE);
    }

    public void setStatusWaitShipper() {
        setStatusWaiting();
        this.lnWay02.setBackgroundColor(Color.BLACK);
        this.tvWaitShipper.setVisibility(View.VISIBLE);
    }

    public void setStatusShipping() {
        setStatusWaitShipper();
        this.lnWay03.setBackgroundColor(Color.BLACK);
        this.tvShipping.setVisibility(View.VISIBLE);
    }

    public void setStatusArrived() {
        setStatusShipping();
        this.lnWay04.setBackgroundColor(Color.BLACK);
        this.tvShipping.setVisibility(View.VISIBLE);
    }
}
