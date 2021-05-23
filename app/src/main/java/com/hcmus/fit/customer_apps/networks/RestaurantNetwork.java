package com.hcmus.fit.customer_apps.networks;

import android.content.Context;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.hcmus.fit.customer_apps.activities.MerchantActivity;
import com.hcmus.fit.customer_apps.adapters.RestaurantAdapter;
import com.hcmus.fit.customer_apps.adapters.RestaurantVAdapter;
import com.hcmus.fit.customer_apps.contants.API;
import com.hcmus.fit.customer_apps.models.Restaurant;
import com.hcmus.fit.customer_apps.utils.AppUtil;
import com.hcmus.fit.customer_apps.utils.QueryUtil;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RestaurantNetwork {
    public static void getRestaurants(Context context, RestaurantAdapter adapter) {
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest req = new StringRequest(Request.Method.GET, API.GET_RESTAURANTS,
                response -> {
                    Log.d("restaurant", response.toString());
                    try {
                        JSONObject json = new JSONObject(response);
                        JSONArray listRestaurant = json.getJSONArray("data");
                        for (int i = 0; i < listRestaurant.length(); i++) {
                            JSONObject restaurantJson = listRestaurant.getJSONObject(i);
                            String id = restaurantJson.getString("id");
                            String name = restaurantJson.getString("Name");
                            String avatar = restaurantJson.getString("Avatar");
                            String fullAddress = restaurantJson.getString("FullAddress");
                            String hours = AppUtil.parseMerchantHours( restaurantJson.getJSONArray("OpenHours"));
                            boolean isOpening = restaurantJson.getBoolean("IsOpening");
                            Restaurant restaurant = new Restaurant(id, avatar, name, fullAddress, hours, isOpening);
                            adapter.restaurantList.add(restaurant);
                        }

                        adapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> Log.d("restaurant", error.getMessage()));

        queue.add(req);
    }

    public static void getRestaurantsRecent(Context context, RestaurantVAdapter adapter) {
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest req = new StringRequest(Request.Method.GET, API.GET_RESTAURANTS,
                response -> {
                    Log.d("restaurant", response.toString());
                    try {
                        JSONObject json = new JSONObject(response);
                        JSONArray listRestaurant = json.getJSONArray("data");
                        int len = Math.min(listRestaurant.length(), 4);

                        for (int i = 0; i < len; i++) {
                            JSONObject restaurantJson = listRestaurant.getJSONObject(i);
                            String id = restaurantJson.getString("id");
                            String name = restaurantJson.getString("Name");
                            String avatar = restaurantJson.getString("Avatar");
                            String fullAddress = restaurantJson.getString("FullAddress");
                            String hours = AppUtil.parseMerchantHours( restaurantJson.getJSONArray("OpenHours"));
                            boolean isOpening = restaurantJson.getBoolean("IsOpening");
                            Restaurant restaurant = new Restaurant(id, avatar, name, fullAddress, hours, isOpening);
                            adapter.restaurantList.add(restaurant);
                        }

                        adapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> Log.d("restaurant", error.getMessage()));

        queue.add(req);
    }

    public static void getRestaurantDetail(MerchantActivity context, Restaurant model) {
        RequestQueue queue = Volley.newRequestQueue(context);

        Map<String, String> params = new HashMap<>();
        params.put("restaurantID", model.getId());
        String query = QueryUtil.createQuery(API.GET_RESTAURANT_DETAIL, params);

        StringRequest req = new StringRequest(Request.Method.GET, query,
                response -> {
                    Log.d("restaurant detail", response.toString());
                    try {
                        JSONObject json = new JSONObject(response);
                        JSONObject restaurantJson = json.getJSONObject("data");
                        model.setName(restaurantJson.getString("Name"));
                        model.setAvatar(restaurantJson.getString("Avatar"));
                        model.setFullAddress(restaurantJson.getString("FullAddress"));
                        String hours = AppUtil.parseMerchantHours( restaurantJson.getJSONArray("OpenHours"));
                        model.setHours(hours);

                        context.tvName.setText(model.getName());
                        Picasso.with(context).load(model.getAvatar()).into(context.ivAvatar);
                        context.tvAddress.setText(model.getFullAddress());
                        context.tvHours.setText(model.getHours());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> Log.d("restaurant detail", error.getMessage()));

        queue.add(req);
    }

    public static void searchRestaurant(Context context, RestaurantVAdapter adapter, String keyWord) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String query = API.GET_RESTAURANTS + "?keyword=" + keyWord;
        StringRequest req = new StringRequest(Request.Method.GET, query,
                response -> {
                    Log.d("restaurant", response.toString());
                    try {
                        JSONObject json = new JSONObject(response);
                        JSONArray listRestaurant = json.getJSONArray("data");
                        for (int i = 0; i < listRestaurant.length(); i++) {
                            JSONObject restaurantJson = listRestaurant.getJSONObject(i);
                            String id = restaurantJson.getString("id");
                            String name = restaurantJson.getString("Name");
                            String avatar = restaurantJson.getString("Avatar");
                            String fullAddress = restaurantJson.getString("FullAddress");
                            String hours = AppUtil.parseMerchantHours( restaurantJson.getJSONArray("OpenHours"));
                            boolean isOpening = restaurantJson.getBoolean("IsOpening");
                            Restaurant restaurant = new Restaurant(id, avatar, name, fullAddress, hours, isOpening);
                            adapter.restaurantList.add(restaurant);
                        }

                        adapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> Log.d("restaurant", error.getMessage()));

        queue.add(req);
    }

}
