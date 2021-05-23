package com.hcmus.fit.customer_apps.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hcmus.fit.customer_apps.R;
import com.hcmus.fit.customer_apps.activities.SearchActivity;
import com.hcmus.fit.customer_apps.adapters.BannerAdapter;
import com.hcmus.fit.customer_apps.adapters.RestaurantAdapter;
import com.hcmus.fit.customer_apps.adapters.RestaurantVAdapter;
import com.hcmus.fit.customer_apps.models.Banner;
import com.hcmus.fit.customer_apps.models.Restaurant;
import com.hcmus.fit.customer_apps.networks.RestaurantNetwork;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class HomeFragment extends Fragment {
    private List<Banner> bannerList = new ArrayList<>();
    private List<Restaurant> restaurantList = new ArrayList<>();
    private List<Restaurant> recommendList = new ArrayList<>();

    private Button btnSearchBar;
    private RecyclerView lvBanner;
    private RecyclerView lvRestaurant;
    private RecyclerView lvRecommend;
    private ListView lvRecent;
    LinearLayout lnRecent;


    private BannerAdapter bnAdapter = new BannerAdapter(bannerList);
    private RestaurantAdapter resAdapter = new RestaurantAdapter(restaurantList);
    private RestaurantAdapter reAdapter = new RestaurantAdapter(recommendList);
    private RestaurantVAdapter revAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        btnSearchBar = root.findViewById(R.id.btn_search_bar);
        lnRecent = root.findViewById(R.id.ln_recent);
        lvBanner = root.findViewById(R.id.lv_banner);
        lvRestaurant = root.findViewById(R.id.lv_big_sale);
        lvRecommend = root.findViewById(R.id.lv_recommend);
        lvRecent = root.findViewById(R.id.lv_recent);

        btnSearchBar.setOnClickListener(v -> {
            Intent intentSearch = new Intent(getContext(), SearchActivity.class);
            startActivity(intentSearch);
        });

        revAdapter = new RestaurantVAdapter(getContext(), recommendList);

        LinearLayoutManager bnLayoutManager = new LinearLayoutManager(getContext().getApplicationContext());
        bnLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        lvBanner.setLayoutManager(bnLayoutManager);
        lvBanner.setItemAnimator(new DefaultItemAnimator());
        lvBanner.setAdapter(bnAdapter);
        genDataBanners();

        LinearLayoutManager resLayoutManager = new LinearLayoutManager(getContext().getApplicationContext());
        resLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        lvRestaurant.setLayoutManager(resLayoutManager);
        lvRestaurant.setItemAnimator(new DefaultItemAnimator());
        lvRestaurant.setAdapter(resAdapter);
        RestaurantNetwork.getRestaurants(getContext(), resAdapter);

        LinearLayoutManager reLayoutManager = new LinearLayoutManager(getContext().getApplicationContext());
        reLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        lvRecommend.setLayoutManager(reLayoutManager);
        lvRecommend.setItemAnimator(new DefaultItemAnimator());
        lvRecommend.setAdapter(reAdapter);
        RestaurantNetwork.getRestaurants(getContext(), reAdapter);

        lvRecent.setAdapter(revAdapter);
        RestaurantNetwork.getRestaurantsRecent(getContext(), revAdapter);
        
        return root;
    }

    private void genDataBanners() {
        Banner banner1 = new Banner("drawable-v24/banner.png", "");
        bannerList.add(banner1);

        Banner banner2 = new Banner("drawable-v24/banner.png", "");
        bannerList.add(banner2);

        Banner banner3 = new Banner("drawable-v24/banner.png", "");
        bannerList.add(banner3);

        Banner banner4 = new Banner("drawable-v24/banner.png", "");
        bannerList.add(banner4);

        bnAdapter.notifyDataSetChanged();
    }
}