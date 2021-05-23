package com.hcmus.fit.customer_apps.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.hcmus.fit.customer_apps.R;
import com.hcmus.fit.customer_apps.adapters.DishAdapter;
import com.hcmus.fit.customer_apps.models.Cart;
import com.hcmus.fit.customer_apps.models.Restaurant;
import com.hcmus.fit.customer_apps.models.UserInfo;
import com.hcmus.fit.customer_apps.networks.DishNetwork;
import com.hcmus.fit.customer_apps.networks.RestaurantNetwork;
import com.hcmus.fit.customer_apps.utils.AppUtil;

public class MerchantActivity extends AppCompatActivity {
    private final Restaurant model = new Restaurant();

    public TextView tvName;
    public ImageView ivAvatar;
    public TextView tvAddress;
    public TextView tvHours;
    public TextView tvDishNum;

    public ListView lvDish;
    public RelativeLayout rlCart;
    public TextView tvOrderNum;
    public TextView tvTotalCart;
    public Button btnCart;

    public DishAdapter dishAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant_detail);
        tvName = findViewById(R.id.tv_restaurant_name);
        ivAvatar = findViewById(R.id.iv_avatar_restaurant);
        tvAddress = findViewById(R.id.tv_restaurant_address);
        tvHours = findViewById(R.id.tv_merchant_hours);
        tvDishNum = findViewById(R.id.tv_num_dish);
        lvDish = findViewById(R.id.lv_dish);
        rlCart = findViewById(R.id.rl_total_cart);
        tvOrderNum = findViewById(R.id.tv_order_num);
        tvTotalCart = findViewById(R.id.tv_total);
        btnCart = findViewById(R.id.btn_cart);

        Intent intent = getIntent();
        model.setId(intent.getStringExtra("id"));
        RestaurantNetwork.getRestaurantDetail(this, model);

        dishAdapter = new DishAdapter(this, model.getDishList());
        lvDish.setAdapter(dishAdapter);
        DishNetwork.getRestaurantDetail(this, model.getId(), dishAdapter);

        btnCart.setOnClickListener(v -> {
            Intent intentCart = new Intent(this, CartActivity.class);
            startActivity(intentCart);
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Cart cart = UserInfo.getInstance().getCart();

        if (cart.getNumDish() == 0) {
            rlCart.setVisibility(View.GONE);
        } else {
            tvOrderNum.setText(String.valueOf(cart.getNumDish()));
            tvTotalCart.setText(AppUtil.convertCurrency(cart.getTotal()));
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        UserInfo.getInstance().getCart().clear();
    }
}
