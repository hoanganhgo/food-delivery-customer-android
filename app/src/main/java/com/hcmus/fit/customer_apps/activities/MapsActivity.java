package com.hcmus.fit.customer_apps.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.hcmus.fit.customer_apps.R;
import com.hcmus.fit.customer_apps.models.OrderManager;
import com.hcmus.fit.customer_apps.models.ShipperModel;
import com.hcmus.fit.customer_apps.models.UserInfo;

import org.jetbrains.annotations.NotNull;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private String orderId;
    private ShipperModel shipper;
    private GoogleMap ggMap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Intent intent = getIntent();
        this.orderId = intent.getStringExtra("orderId");
        this.shipper = OrderManager.getInstance().getOrderModel(this.orderId).getShipper();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            Log.d("map","map not null");
            mapFragment.getMapAsync(this);
        }

        this.shipper.setMapsActivity(this);
    }

    @Override
    public void onMapReady(@NotNull GoogleMap googleMap) {
        ggMap = googleMap;

        // Shipper location
        LatLng shipperLocation = new LatLng(shipper.getLatitude(), shipper.getLongitude());

        BitmapDescriptor icon = BitmapDescriptorFactory.fromBitmap(shipper.getAvatar());
        MarkerOptions marketShipper = new MarkerOptions().position(shipperLocation).icon(icon);
        ggMap.addMarker(marketShipper);

        // User location
        LatLng userLocation = new LatLng(UserInfo.getInstance().getAddressCurrent().getLatitude(),
                UserInfo.getInstance().getAddressCurrent().getLongitude());
        MarkerOptions marketUser = new MarkerOptions().position(userLocation).title("Your location");
        ggMap.addMarker(marketUser);

        ggMap.moveCamera(CameraUpdateFactory.newLatLngZoom(shipperLocation, 15));
        ggMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
    }

    public void updateLocation() {
        //Shipper location
        LatLng shipperLocation = new LatLng(shipper.getLatitude(), shipper.getLongitude());
        BitmapDescriptor icon = BitmapDescriptorFactory.fromBitmap(shipper.getAvatar());
        MarkerOptions marketShipper = new MarkerOptions().position(shipperLocation).icon(icon);

        // User location
        LatLng userLocation = new LatLng(UserInfo.getInstance().getAddressCurrent().getLatitude(),
                UserInfo.getInstance().getAddressCurrent().getLongitude());
        MarkerOptions marketUser = new MarkerOptions().position(userLocation).title("Your location");

        this.runOnUiThread(() -> {
            ggMap.clear();
            ggMap.addMarker(marketShipper);
            ggMap.addMarker(marketUser);
        });
    }

}