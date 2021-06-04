package com.hcmus.fit.customer_apps.utils;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.util.Log;

import com.hcmus.fit.customer_apps.models.AddressModel;
import com.hcmus.fit.customer_apps.models.UserInfo;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class AppUtil {
    public static String parseMerchantHours(JSONArray hoursArray) throws JSONException {
        return hoursArray.getString(0) + " - " + hoursArray.getString(1);
    }

    public static String convertCurrency(int money) {
        StringBuilder s = new StringBuilder("đ");

        while (money / 1000 > 0) {
            int mod = money % 1000;

            if (mod == 0) {
                s.insert(0, "000");
            } else if (mod < 10) {
                s.insert(0, mod);
                s.insert(0,"00");
            } else if (mod < 100) {
                s.insert(0, mod);
                s.insert(0, "0");
            } else {
                s.insert(0, mod);
            }

            money = money / 1000;

            s.insert(0, ",");
        }

        s.insert(0, money);

        return s.toString();
    }

    public static String convertDishOption(String name, int price) {
        return name
                + " (" + AppUtil.convertCurrency(price) + ")";
    }

    public static String getAddressByLocation(Context context, double latitude, double longitude) {
        String address = null;
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());

        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);

            address = addresses.get(0).getAddressLine(0);
            Log.d("location", address);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return address;
    }
}
