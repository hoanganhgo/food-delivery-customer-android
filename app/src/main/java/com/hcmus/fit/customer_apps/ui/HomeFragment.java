package com.hcmus.fit.customer_apps.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hcmus.fit.customer_apps.R;
import com.hcmus.fit.customer_apps.adapters.BannerAdapter;
import com.hcmus.fit.customer_apps.adapters.RestaurantAdapter;
import com.hcmus.fit.customer_apps.adapters.RestaurantVAdapter;
import com.hcmus.fit.customer_apps.models.Banner;
import com.hcmus.fit.customer_apps.models.Restaurant;

import java.util.LinkedList;
import java.util.List;

public class HomeFragment extends Fragment {
    private List<Banner> bannerList = new LinkedList<>();
    private List<Restaurant> restaurantList = new LinkedList<>();
    private List<Restaurant> recommendList = new LinkedList<>();

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

        lnRecent = root.findViewById(R.id.ln_recent);
        revAdapter = new RestaurantVAdapter(getContext(), recommendList);

        lvBanner = root.findViewById(R.id.lv_banner);
        LinearLayoutManager bnLayoutManager = new LinearLayoutManager(getContext().getApplicationContext());
        bnLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        lvBanner.setLayoutManager(bnLayoutManager);
        lvBanner.setItemAnimator(new DefaultItemAnimator());
        lvBanner.setAdapter(bnAdapter);
        genDataBanners();

        lvRestaurant = root.findViewById(R.id.lv_big_sale);
        LinearLayoutManager resLayoutManager = new LinearLayoutManager(getContext().getApplicationContext());
        resLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        lvRestaurant.setLayoutManager(resLayoutManager);
        lvRestaurant.setItemAnimator(new DefaultItemAnimator());
        lvRestaurant.setAdapter(resAdapter);
        genDataRestaurant();

        lvRecent = root.findViewById(R.id.lv_recent);
        lvRecent.setAdapter(revAdapter);

        lvRecommend = root.findViewById(R.id.lv_recommend);
        LinearLayoutManager reLayoutManager = new LinearLayoutManager(getContext().getApplicationContext());
        reLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        lvRecommend.setLayoutManager(reLayoutManager);
        lvRecommend.setItemAnimator(new DefaultItemAnimator());
        lvRecommend.setAdapter(reAdapter);
        genDataRecommend();
        
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

    private void genDataRestaurant() {
        Restaurant restaurant1 = new Restaurant();
        Restaurant restaurant2 = new Restaurant();
        Restaurant restaurant3 = new Restaurant();
        Restaurant restaurant4 = new Restaurant();
        restaurantList.add(restaurant1);
        restaurantList.add(restaurant2);
        restaurantList.add(restaurant3);
        restaurantList.add(restaurant4);

        resAdapter.notifyDataSetChanged();
    }

    private void genDataRecommend() {
        Restaurant restaurant1 = new Restaurant();
        Restaurant restaurant2 = new Restaurant();
        Restaurant restaurant3 = new Restaurant();
        Restaurant restaurant4 = new Restaurant();
        recommendList.add(restaurant1);
        recommendList.add(restaurant2);
        recommendList.add(restaurant3);
        recommendList.add(restaurant4);

        reAdapter.notifyDataSetChanged();
        revAdapter.notifyDataSetChanged();
    }
}