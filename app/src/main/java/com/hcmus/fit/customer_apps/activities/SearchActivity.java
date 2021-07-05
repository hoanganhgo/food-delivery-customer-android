package com.hcmus.fit.customer_apps.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.AbsListView;
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

    private boolean loading = true;
    private int previousTotal = 0;
    private int visibleThreshold = 5;
    private int currentPage = 0;
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
                RestaurantNetwork.searchRestaurant(getApplicationContext(), rvAdapter, s.toString(), 1);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        lvSearch.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(this, MerchantActivity.class);
            intent.putExtra("id", restaurants.get(position).getId());
            intent.putExtra("opening", restaurants.get(position).isOpening());
            startActivity(intent);
        });

        lvSearch.setOnScrollListener(new AbsListView.OnScrollListener(){

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {}

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                if (loading) {
                    if (totalItemCount > previousTotal) {
                        loading = false;
                        previousTotal = totalItemCount;
                        currentPage++;
                    }
                }
                if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
                    RestaurantNetwork.searchRestaurant(getApplicationContext(), rvAdapter,
                            edtSearch.getText().toString(), currentPage);
                    loading = true;
                }
            }
        });
    }
}
