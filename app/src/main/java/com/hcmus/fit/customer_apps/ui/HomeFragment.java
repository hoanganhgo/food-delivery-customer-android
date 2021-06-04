package com.hcmus.fit.customer_apps.ui;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.hcmus.fit.customer_apps.R;
import com.hcmus.fit.customer_apps.activities.MerchantListActivity;
import com.hcmus.fit.customer_apps.activities.SearchActivity;
import com.hcmus.fit.customer_apps.adapters.BannerAdapter;
import com.hcmus.fit.customer_apps.adapters.RestaurantAdapter;
import com.hcmus.fit.customer_apps.adapters.RestaurantVAdapter;
import com.hcmus.fit.customer_apps.models.AddressModel;
import com.hcmus.fit.customer_apps.models.Banner;
import com.hcmus.fit.customer_apps.models.Restaurant;
import com.hcmus.fit.customer_apps.models.UserInfo;
import com.hcmus.fit.customer_apps.networks.RestaurantNetwork;
import com.hcmus.fit.customer_apps.utils.AppUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import static android.content.Context.LOCATION_SERVICE;

public class HomeFragment extends Fragment implements LocationListener {

    //permission
    private static final int MY_PERMISSIONS_REQUEST_COARSE_LOCATION = 124;
    private static final int MY_PERMISSIONS_REQUEST_FINE_LOCATION = 123;

    private List<Banner> bannerList = new ArrayList<>();
    private List<Restaurant> restaurantList = new ArrayList<>();
    private List<Restaurant> recommendList = new ArrayList<>();

    private Button btnLocationBar;
    private Button btnSearchBar;
    private RecyclerView lvBanner;
    private ImageButton btnDishRice;
    private ImageButton btnDishSnack;
    private ImageButton btnDishDrink;
    private ImageButton btnDishSoup;
    private ImageButton btnDishSale;
    private ImageButton btnDishFreeShip;
    private RecyclerView lvRestaurant;
    private RecyclerView lvRecommend;
    private ListView lvRecent;
    private LinearLayout lnRecent;



    private BannerAdapter bnAdapter = new BannerAdapter(bannerList);
    private RestaurantAdapter resAdapter = new RestaurantAdapter(restaurantList);
    private RestaurantAdapter reAdapter = new RestaurantAdapter(recommendList);
    private RestaurantVAdapter revAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        btnLocationBar = root.findViewById(R.id.btn_location_bar);
        btnSearchBar = root.findViewById(R.id.btn_search_bar);
        lnRecent = root.findViewById(R.id.ln_recent);
        lvBanner = root.findViewById(R.id.lv_banner);
        lvRestaurant = root.findViewById(R.id.lv_big_sale);
        lvRecommend = root.findViewById(R.id.lv_recommend);
        lvRecent = root.findViewById(R.id.lv_recent);
        btnDishRice = root.findViewById(R.id.btn_dish_rice);
        btnDishSnack = root.findViewById(R.id.btn_dish_snacks);
        btnDishDrink = root.findViewById(R.id.btn_dish_drink);
        btnDishSoup = root.findViewById(R.id.btn_dish_soup);
        btnDishSale = root.findViewById(R.id.btn_dish_sale);
        btnDishFreeShip = root.findViewById(R.id.btn_dish_free_ship);

        btnDishRice.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), MerchantListActivity.class);
            intent.putExtra("title", R.string.dish_rice);
            intent.putExtra("keyword", "cơm");
            startActivity(intent);
        });

        btnDishSnack.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), MerchantListActivity.class);
            intent.putExtra("title", R.string.dish_snacks);
            intent.putExtra("keyword", "thức ăn nhanh");
            startActivity(intent);
        });

        btnDishDrink.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), MerchantListActivity.class);
            intent.putExtra("title", R.string.dish_drinks);
            intent.putExtra("keyword", "nước uống");
            startActivity(intent);
        });

        btnDishSoup.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), MerchantListActivity.class);
            intent.putExtra("title", R.string.dish_soup);
            intent.putExtra("keyword", "mì");
            startActivity(intent);
        });

        btnDishSale.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), MerchantListActivity.class);
            intent.putExtra("title", R.string.dish_sale);
            intent.putExtra("keyword", "khuyến mãi");
            startActivity(intent);
        });

        btnDishFreeShip.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), MerchantListActivity.class);
            intent.putExtra("title", R.string.dish_free_ship);
            intent.putExtra("keyword", "miễn phí");
            startActivity(intent);
        });

        // Update user location
        checkPermission();
        Location location = getCurrentLocation();

        if (location != null) {
            updateLocation(location);
        }

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

    @Override
    public void onStart() {
        super.onStart();
        if (UserInfo.getInstance().getAddressCurrent() != null) {
            if (!btnLocationBar.getText().toString().equals( UserInfo.getInstance().getAddressCurrent().getFullAddress()))
                btnLocationBar.setText(UserInfo.getInstance().getAddressCurrent().getFullAddress());
        }
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

    private void checkPermission() {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

            } else {

                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_FINE_LOCATION);
            }
        }

        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.ACCESS_COARSE_LOCATION)) {

            } else {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        MY_PERMISSIONS_REQUEST_COARSE_LOCATION);
            }
        }
    }

    private Location getCurrentLocation() {
        //GPS
        LocationManager locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);

        assert locationManager != null;
        if (getContext().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && getContext().checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return null;
        }

        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 0, this);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0, this);

        Criteria criteria = new Criteria();

        return locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
    }

    private void updateLocation(Location location) {
        String address = AppUtil.getAddressByLocation(getContext(),
                location.getLatitude(), location.getLongitude());
        if (address == null) {
            return;
        }

        AddressModel addressModel = new AddressModel(address, "");
        addressModel.setLatitude(location.getLatitude());
        addressModel.setLongitude(location.getLongitude());

        UserInfo.getInstance().addAddressCurrent(addressModel);
        btnLocationBar.setText(UserInfo.getInstance().getAddressCurrent().getFullAddress());
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {

    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {

    }
}