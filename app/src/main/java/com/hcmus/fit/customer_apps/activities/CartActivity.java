package com.hcmus.fit.customer_apps.activities;

import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.hcmus.fit.customer_apps.R;
import com.hcmus.fit.customer_apps.adapters.DishOrderAdapter;
import com.hcmus.fit.customer_apps.models.AddressModel;
import com.hcmus.fit.customer_apps.models.UserInfo;
import com.hcmus.fit.customer_apps.networks.DishNetwork;
import com.hcmus.fit.customer_apps.utils.AppUtil;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import vn.zalopay.sdk.ZaloPaySDK;

public class CartActivity extends AppCompatActivity {

    private ListView lvDishOrder;
    private TextView tvPrice;
    private TextView tvTotal;
    public TextView tvShipFee;
    private Button btnOrder;
    private TextView tvAddress;
    private Button btnEditLocation;
    private Button btnCash;
    private Button btnZaloPay;

    private DishOrderAdapter dishOrderAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        lvDishOrder = findViewById(R.id.lv_dish_order);
        tvPrice = findViewById(R.id.tv_price);
        tvTotal = findViewById(R.id.tv_total);
        tvShipFee = findViewById(R.id.tv_ship_fee);
        tvAddress = findViewById(R.id.tv_address_delivery);
        btnOrder = findViewById(R.id.btn_order);
        btnEditLocation = findViewById(R.id.btn_edit_location);
        btnCash = findViewById(R.id.btn_pay_cash);
        btnZaloPay = findViewById(R.id.btn_pay_zalo);

        UserInfo.getInstance().getCart().setActivity(this);

        if (UserInfo.getInstance().getAddressCurrent() != null) {
            tvAddress.setText(UserInfo.getInstance().getAddressCurrent().getFullAddress());
        }

        btnEditLocation.setOnClickListener(v -> {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setTitle(getResources().getString(R.string.enter_your_location));
            final EditText input = new EditText(this);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            input.setLayoutParams(lp);
            alertDialog.setView(input);

            alertDialog.setPositiveButton(getResources().getString(R.string.confirm),
                    (dialog, which) -> {
                        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                        try {
                            List<Address> addresses = geocoder.getFromLocationName(input.getText().toString(), 1);
                            Address address = addresses.get(0);
                            double latitude = address.getLatitude();
                            double longitude = address.getLongitude();
                            Log.d("location", latitude + ", " + longitude);

                            AddressModel addressModel = new AddressModel(input.getText().toString(), input.getText().toString());
                            addressModel.setLatitude(latitude);
                            addressModel.setLongitude(longitude);

                            UserInfo.getInstance().addAddressCurrent(addressModel);
                            tvAddress.setText(input.getText().toString());

                            DishNetwork.getShipFee(this);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });

            alertDialog.setNegativeButton(getResources().getString(R.string.cancel),
                    (dialog, which) -> dialog.cancel());

            alertDialog.show();
        });

        updateCart();

        if (UserInfo.getInstance().getAddressCurrent() != null) {
            DishNetwork.getShipFee(this);
        }

        dishOrderAdapter = new DishOrderAdapter(this);
        lvDishOrder.setAdapter(dishOrderAdapter);

        btnCash.setOnClickListener(v -> {
            if (UserInfo.getInstance().getCart().getPayment() == 1) {
                UserInfo.getInstance().getCart().setPayment(0);
                btnCash.setBackground(getResources().getDrawable(R.drawable.bg_active_primary));
                btnZaloPay.setBackground(getResources().getDrawable(R.drawable.bg_no_active_primary));
            }
        });

        btnZaloPay.setOnClickListener(v -> {
            if (UserInfo.getInstance().getCart().getPayment() == 0) {
                UserInfo.getInstance().getCart().setPayment(1);
                btnZaloPay.setBackground(getResources().getDrawable(R.drawable.bg_active_primary));
                btnCash.setBackground(getResources().getDrawable(R.drawable.bg_no_active_primary));
            }
        });

        btnOrder.setOnClickListener(v -> {
            UserInfo userInfo = UserInfo.getInstance();
            if (userInfo.getCart().getTotal() == 0) {
                Toast.makeText(this, getResources().getString(R.string.notify_invalid_order), Toast.LENGTH_SHORT).show();
                return;
            }

            if (userInfo.getAddressCurrent() == null) {
                Toast.makeText(this, getResources().getString(R.string.notify_invalid_address), Toast.LENGTH_SHORT).show();
                return;
            }

            if (userInfo.getCart().shipFee == 0) {
                Toast.makeText(this, getResources().getString(R.string.notify_no_fee_ship), Toast.LENGTH_SHORT).show();
                return;
            }

            UserInfo.getInstance().getCart().removeDishOrderEmpty();
            DishNetwork.order(this, UserInfo.getInstance().getCart());
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        UserInfo.getInstance().getCart().removeDishOrderEmpty();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ZaloPaySDK.getInstance().onResult(data);
    }

    public void updateCart() {
        int price = UserInfo.getInstance().getCart().getTotal();
        int shipFee = UserInfo.getInstance().getCart().shipFee;
        tvPrice.setText(AppUtil.convertCurrency(price));
        tvTotal.setText(AppUtil.convertCurrency(price + shipFee));
    }
}
