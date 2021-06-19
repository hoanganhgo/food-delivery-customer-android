package com.hcmus.fit.customer_apps.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.util.Log;

import com.hcmus.fit.customer_apps.models.AddressModel;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.Calendar;
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

    public static Bitmap roundedCornerBitmap(Bitmap bitmap, float roundPx) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }

    public static Calendar parseCalendar(String s) {
        String[] arr = s.split("T");

        String[] dates = arr[0].split("-");
        int year = Integer.parseInt(dates[0]);
        int month = Integer.parseInt(dates[1]);
        int day = Integer.parseInt(dates[2]);

        String[] times = arr[1].split(":");
        int hour = Integer.parseInt(times[0]);
        int minute = Integer.parseInt(times[1]);

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day, hour, minute);
        return calendar;
    }

    public static String getDateString(Calendar calendar) {
        return calendar.get(Calendar.DAY_OF_MONTH) + "/" + calendar.get(Calendar.MONTH)
                + "/" + calendar.get(Calendar.YEAR);
    }

    public static String getTimeString(Calendar calendar) {
        return calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE);
    }

    /*
    return meters..
     */
    public static double calculateDistance(AddressModel address1, AddressModel address2) {
        Location location1 = new Location("pointA");
        location1.setLatitude(address1.getLatitude());
        location1.setLongitude(address1.getLongitude());

        Location location2 = new Location("pointB");
        location2.setLatitude(address2.getLatitude());
        location2.setLongitude(address2.getLongitude());

        return location1.distanceTo(location2);
    }
}
