package com.hcmus.fit.customer_apps.activities;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.hcmus.fit.customer_apps.R;
import com.hcmus.fit.customer_apps.adapters.DishOrderAdapter;
import com.hcmus.fit.customer_apps.models.UserInfo;
import com.hcmus.fit.customer_apps.utils.AppUtil;

public class CartActivity extends AppCompatActivity {

    private ListView lvDishOrder;
    private TextView tvPrice;
    private TextView tvTotal;

    private DishOrderAdapter dishOrderAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        lvDishOrder = findViewById(R.id.lv_dish_order);
        tvPrice = findViewById(R.id.tv_price);
        tvTotal = findViewById(R.id.tv_total);

        updateCart();

        dishOrderAdapter = new DishOrderAdapter(this);
        lvDishOrder.setAdapter(dishOrderAdapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        UserInfo.getInstance().getCart().removeDishOrderEmpty();
    }

    public void updateCart() {
        int price = UserInfo.getInstance().getCart().getTotal();
        tvPrice.setText(AppUtil.convertCurrency(price));
        tvTotal.setText(AppUtil.convertCurrency(price + 5000));
    }
}
