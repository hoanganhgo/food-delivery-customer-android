package com.hcmus.fit.customer_apps.networks;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.hcmus.fit.customer_apps.activities.MerchantActivity;
import com.hcmus.fit.customer_apps.adapters.DishAdapter;
import com.hcmus.fit.customer_apps.contants.API;
import com.hcmus.fit.customer_apps.models.DishModel;
import com.hcmus.fit.customer_apps.utils.QueryUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DishNetwork {
    public static void getRestaurantDetail(MerchantActivity context, String id, DishAdapter adapter) {
        RequestQueue queue = Volley.newRequestQueue(context);

        Map<String, String> params = new HashMap<>();
        params.put("restaurantID", id);
        String query = QueryUtil.createQuery(API.GET_MENU_RESTAURANT, params);

        StringRequest req = new StringRequest(Request.Method.GET, query,
                response -> {
                    Log.d("menu", response.toString());
                    try {
                        JSONObject json = new JSONObject(response);
                        JSONArray menuJson = json.getJSONArray("data");

                        for (int i = 0; i < menuJson.length(); i++) {
                            JSONObject category = menuJson.getJSONObject(i);
                            JSONArray foods = category.getJSONArray("Foods");
                            String categoryId = category.getString("id");

                            for (int j = 0; j < foods.length(); j++) {
                                JSONObject dishJson = foods.getJSONObject(j);
                                String dishId = dishJson.getString("id");
                                String name = dishJson.getString("Name");
                                String avatar = dishJson.getString("Avatar");
                                int price = dishJson.getInt("OriginalPrice");
                                int totalOrder = dishJson.getInt("TotalOrder");

                                DishModel dishModel = new DishModel(dishId, name, avatar, price);
                                dishModel.setFoodCategoryId(categoryId);
                                dishModel.setTotalOrder(totalOrder);
                                adapter.dishList.add(dishModel);
                            }
                        }

                        context.tvDishNum.setText(String.valueOf(adapter.dishList.size()));
                        adapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> Log.d("menu", error.getMessage()));

        queue.add(req);
    }

}
