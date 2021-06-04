package com.hcmus.fit.customer_apps.networks;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.hcmus.fit.customer_apps.activities.CartActivity;
import com.hcmus.fit.customer_apps.activities.MerchantActivity;
import com.hcmus.fit.customer_apps.adapters.DishAdapter;
import com.hcmus.fit.customer_apps.contants.API;
import com.hcmus.fit.customer_apps.models.Cart;
import com.hcmus.fit.customer_apps.models.DishModel;
import com.hcmus.fit.customer_apps.models.OrderModel;
import com.hcmus.fit.customer_apps.models.UserInfo;
import com.hcmus.fit.customer_apps.utils.QueryUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
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
                                dishModel.setOptionListWithJson( dishJson.getJSONArray("Options"));
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

    public static void order(CartActivity context, Cart cart) {
        RequestQueue queue = Volley.newRequestQueue(context);

        StringRequest req = new StringRequest(Request.Method.POST, API.ORDER,
                response -> {
                    Log.d("order", response.toString());
                    try {
                        JSONObject json = new JSONObject(response);
                        int errorCode = json.getInt("errorCode");
                        if (errorCode == 0) {
                            JSONObject data = json.getJSONObject("data");
                            String id = data.getString("id");
                            OrderModel orderModel = new OrderModel(id);
                            orderModel.setAddress(data.getString("Address"));
                            orderModel.setRestaurantId(data.getString("Restaurant"));
                            orderModel.setRestaurantPhone(data.getString("Phone"));
                            orderModel.setShipFee(data.getInt("ShippingFee"));
                            orderModel.setSubTotal(data.getInt("Subtotal"));
                            orderModel.setTotal(data.getInt("Total"));
                            UserInfo.getInstance().getOrderManager().setOrderModel(orderModel);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> Log.d("order", error.getMessage()))
        {

            @Override
            public byte[] getBody() throws AuthFailureError {
                JSONObject json = null;
                try {
                    json = new JSONObject();
                    UserInfo userInfo = UserInfo.getInstance();
                    JSONArray foodArray = cart.createFoodArrayJson();

                    json.put("foods", foodArray);
                    json.put("subtotal", cart.getTotal());
                    json.put("shippingfee", 10000);


                    json.put("address", userInfo.getAddressCurrent().getFullAddress());
                    json.put("phone", userInfo.getPhoneNumber());
                    json.put("longitude", userInfo.getAddressCurrent().getLongitude());
                    json.put("latitude", userInfo.getAddressCurrent().getLatitude());
                    json.put("method", 0);
                    Log.d("order-send", json.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return json.toString().getBytes(StandardCharsets.UTF_8);
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }



            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + UserInfo.getInstance().getToken());
                return headers;
            }
        };

        queue.add(req);
    }

}
