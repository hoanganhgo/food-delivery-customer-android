package com.hcmus.fit.customer_apps.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.hcmus.fit.customer_apps.R;
import com.hcmus.fit.customer_apps.adapters.RestaurantVAdapter;
import com.hcmus.fit.customer_apps.models.Restaurant;
import com.hcmus.fit.customer_apps.networks.RestaurantNetwork;

import java.util.ArrayList;
import java.util.List;

public class MerchantListActivity extends AppCompatActivity {

    public List<Restaurant> restaurants = new ArrayList<>();

    private TextView tvTitle;
    public ListView lvMerchant;

    public RestaurantVAdapter rvAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_merchant_list);
        lvMerchant = findViewById(R.id.lv_merchant);
        tvTitle = findViewById(R.id.tv_title);

        rvAdapter = new RestaurantVAdapter(this, restaurants);
        lvMerchant.setAdapter(rvAdapter);

        lvMerchant.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(this, MerchantActivity.class);
            intent.putExtra("id", restaurants.get(position).getId());
            startActivity(intent);
        });

        Intent keyWordIntent = getIntent();
        int title = keyWordIntent.getIntExtra("title", R.string.big_sale);
        tvTitle.setText(title);

        String keyword = keyWordIntent.getStringExtra("keyword");
        RestaurantNetwork.searchRestaurant(getApplicationContext(), rvAdapter, keyword, 1);
    }
}
