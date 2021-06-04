package com.hcmus.fit.customer_apps.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.hcmus.fit.customer_apps.R;
import com.hcmus.fit.customer_apps.models.UserInfo;

import de.hdodenhof.circleimageview.CircleImageView;

public class OrderStatusActivity extends AppCompatActivity {

    private ImageView ivOrderStatus;
    private LinearLayout lnWay01;
    private LinearLayout lnWay02;
    private LinearLayout lnWay03;
    private LinearLayout lnWay04;
    private LinearLayout lnShipper;
    private TextView tvProcessOrder;
    private TextView tvWaitingDish;
    private TextView tvShipping;
    private TextView tvArrived;
    private CircleImageView ivShipperAvatar;
    private TextView tvShipperName;
    private ImageButton btnMessenger;
    private ImageButton btnCallShipper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_status);

        ivOrderStatus = findViewById(R.id.iv_order_status);
        lnWay01 = findViewById(R.id.ln_way_01);
        lnWay02 = findViewById(R.id.ln_way_02);
        lnWay03 = findViewById(R.id.ln_way_03);
        lnWay04 = findViewById(R.id.ln_way_04);
        lnShipper = findViewById(R.id.ln_shipper);
        tvProcessOrder = findViewById(R.id.tv_lb_process_order);
        tvWaitingDish = findViewById(R.id.tv_lb_waiting_dish);
        tvShipping = findViewById(R.id.tv_lb_shipping);
        tvArrived = findViewById(R.id.tv_lb_arrived);
        ivShipperAvatar = findViewById(R.id.iv_shipper_avatar);
        tvShipperName = findViewById(R.id.tv_shipper_name);
        btnMessenger = findViewById(R.id.btn_messenger);
        btnCallShipper = findViewById(R.id.btn_call_shipper);

        UserInfo.getInstance().getOrderManager().setActivity(this);
        setStatusProcessOrder();
    }

    public void setStatusProcessOrder() {
        this.ivOrderStatus.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.img_look_for));
        this.lnWay01.setBackgroundColor(Color.BLACK);
        this.tvProcessOrder.setVisibility(View.VISIBLE);
        this.lnShipper.setVisibility(View.INVISIBLE);
    }

    public void setStatusWaitingDish() {
        setStatusProcessOrder();
        this.ivOrderStatus.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.img_cooking));
        this.lnWay02.setBackgroundColor(Color.BLACK);
        this.tvWaitingDish.setVisibility(View.VISIBLE);
        this.lnShipper.setVisibility(View.VISIBLE);
    }

    public void setStatusShipping() {
        setStatusWaitingDish();
        this.ivOrderStatus.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.img_shipper));
        this.lnWay03.setBackgroundColor(Color.BLACK);
        this.tvShipping.setVisibility(View.VISIBLE);
        this.lnShipper.setVisibility(View.VISIBLE);
    }

    public void setStatusArrived() {
        setStatusShipping();
        this.ivOrderStatus.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.img_location));
        this.lnWay04.setBackgroundColor(Color.BLACK);
        this.tvArrived.setVisibility(View.VISIBLE);
        this.lnShipper.setVisibility(View.VISIBLE);
    }
}