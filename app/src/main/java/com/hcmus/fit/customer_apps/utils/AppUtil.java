package com.hcmus.fit.customer_apps.utils;

import org.json.JSONArray;
import org.json.JSONException;

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
}
