package com.hcmus.fit.customer_apps;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.hcmus.fit.customer_apps.contants.Constant;
import com.hcmus.fit.customer_apps.networks.MySocket;
import com.hcmus.fit.customer_apps.networks.SignInNetwork;
import com.hcmus.fit.customer_apps.utils.NotifyUtil;

public class MainActivity extends AppCompatActivity {
    private long pressBackTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_bill, R.id.navigation_order,
                R.id.navigation_notifications, R.id.navigation_setting)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navView, navController);

        SignInNetwork.getUserInfo(this);
        MySocket.getInstance();
        NotifyUtil.init(this);
    }

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - pressBackTime > Constant.TIME_EXIT) {
            Toast.makeText(this, getResources().getString(R.string.notify_exit), Toast.LENGTH_SHORT).show();
            pressBackTime = System.currentTimeMillis();
        } else {
            finishAffinity();
        }
    }
}