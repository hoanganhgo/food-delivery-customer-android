package com.hcmus.fit.customer_apps.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.hcmus.fit.customer_apps.R;
import com.hcmus.fit.customer_apps.adapters.RestaurantVAdapter;
import com.hcmus.fit.customer_apps.models.Restaurant;
import com.hcmus.fit.customer_apps.networks.RestaurantNetwork;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    public List<Restaurant> restaurants = new ArrayList<>();

    public EditText edtSearch;
    public ListView lvSearch;

    public RestaurantVAdapter rvAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        lvSearch = findViewById(R.id.lv_search_merchant);
        edtSearch = findViewById(R.id.edt_search);

        rvAdapter = new RestaurantVAdapter(this, restaurants);
        lvSearch.setAdapter(rvAdapter);

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                restaurants.clear();
                RestaurantNetwork.searchRestaurant(getApplicationContext(), rvAdapter, s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        lvSearch.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(this, MerchantActivity.class);
            intent.putExtra("id", restaurants.get(position).getId());
            startActivity(intent);
        });
    }
}
