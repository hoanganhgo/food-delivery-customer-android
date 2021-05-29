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
import com.hcmus.fit.customer_apps.activities.SearchActivity;
import com.hcmus.fit.customer_apps.adapters.BannerAdapter;
import com.hcmus.fit.customer_apps.adapters.RestaurantAdapter;
import com.hcmus.fit.customer_apps.adapters.RestaurantVAdapter;
import com.hcmus.fit.customer_apps.models.AddressModel;
import com.hcmus.fit.customer_apps.models.Banner;
import com.hcmus.fit.customer_apps.models.Restaurant;
import com.hcmus.fit.customer_apps.models.UserInfo;
import com.hcmus.fit.customer_apps.networks.RestaurantNetwork;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import static android.content.Context.LOCATION_SERVICE;

public class HomeFragment extends Fragment implements LocationListener {
    private final int REQUEST_LOCATION_PERMISSION = 1;
    //permission
    private static final int MY_PERMISSIONS_REQUEST_COARSE_LOCATION = 124;
    private static final int MY_PERMISSIONS_REQUEST_FINE_LOCATION = 123;

    private List<Banner> bannerList = new ArrayList<>();
    private List<Restaurant> restaurantList = new ArrayList<>();
    private List<Restaurant> recommendList = new ArrayList<>();

    private Button btnLocationBar;
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

        btnLocationBar = root.findViewById(R.id.btn_location_bar);
        btnSearchBar = root.findViewById(R.id.btn_search_bar);
        lnRecent = root.findViewById(R.id.ln_recent);
        lvBanner = root.findViewById(R.id.lv_banner);
        lvRestaurant = root.findViewById(R.id.lv_big_sale);
        lvRecommend = root.findViewById(R.id.lv_recommend);
        lvRecent = root.findViewById(R.id.lv_recent);

        btnLocationBar.setOnClickListener(v -> {
            checkPermission();
            Location location = getCurrentLocation();

            if (location == null) {
                return;
            }

            Geocoder geocoder;
            List<Address> addresses;
            geocoder = new Geocoder(getContext(), Locale.getDefault());

            try {
                addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

                String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                String city = addresses.get(0).getLocality();
                String state = addresses.get(0).getAdminArea();
                String country = addresses.get(0).getCountryName();
                String postalCode = addresses.get(0).getPostalCode();
                String knownName = addresses.get(0).getFeatureName();
                Log.d("location", address);
                AddressModel addressModel = new AddressModel(address, "");
                UserInfo.getInstance().addAddressCurrent(addressModel);
                btnLocationBar.setText(UserInfo.getInstance().getAddressCurrent().getFullAddress());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

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