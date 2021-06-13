package com.hcmus.fit.customer_apps.networks;

import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.hcmus.fit.customer_apps.activities.CartActivity;
import com.hcmus.fit.customer_apps.activities.MerchantActivity;
import com.hcmus.fit.customer_apps.activities.OrderStatusActivity;
import com.hcmus.fit.customer_apps.adapters.DishAdapter;
import com.hcmus.fit.customer_apps.contants.API;
import com.hcmus.fit.customer_apps.models.Cart;
import com.hcmus.fit.customer_apps.models.DishModel;
import com.hcmus.fit.customer_apps.models.OrderManager;
import com.hcmus.fit.customer_apps.models.OrderModel;
import com.hcmus.fit.customer_apps.models.UserInfo;
import com.hcmus.fit.customer_apps.utils.AppUtil;
import com.hcmus.fit.customer_apps.utils.QueryUtil;

import org.apache.http.HttpEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
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
                            orderModel.setAddress("url_avatar");
                            orderModel.setRestaurantName("Quan co Tu");
                            orderModel.setRestaurantAddress("123, Ly Thuong Kiet,...");
                            orderModel.setRestaurantId(data.getString("Restaurant"));
                            orderModel.setRestaurantPhone(data.getString("Phone"));
                            orderModel.setShipFee(data.getInt("ShippingFee"));
                            orderModel.setSubTotal(data.getInt("Subtotal"));
                            orderModel.setTotal(data.getInt("Total"));
                            orderModel.setDistance( data.getDouble("Distance"));
                            UserInfo.getInstance().getCart().copyDishList( orderModel.getDishOrders());
                            OrderManager.getInstance().addOrderModel(orderModel);

                            UserInfo.getInstance().getCart().clear();
                            context.onBackPressed();

                            Intent intent = new Intent(context, OrderStatusActivity.class);
                            intent.putExtra("orderId", id);
                            context.startActivity(intent);
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
                    json.put("shippingfee", cart.shipFee);

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

    public static void getShipFee(CartActivity context) {
        RequestQueue queue = Volley.newRequestQueue(context);

        Map<String, String> params = new HashMap<>();
        params.put("merchantId", UserInfo.getInstance().getCart().merchant);
        params.put("addressDelivery", UserInfo.getInstance().getAddressCurrent().getFullAddress());
        String query = QueryUtil.createQuery(API.GET_SHIP_FEE, params);

        StringRequest req = new StringRequest(Request.Method.GET, query,
                response -> {
                    Log.d("feeShip", response.toString());
                    try {
                        JSONObject json = new JSONObject(response);
                        int feeShip = json.getInt("fee");
                        UserInfo.getInstance().getCart().shipFee = feeShip;
                        context.tvShipFee.setText( AppUtil.convertCurrency(feeShip));
                        context.updateCart();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> Log.d("feeShip", error.getMessage()));

        queue.add(req);
    }

    public static void ratingShipper(OrderStatusActivity context, String orderId, String content, int stars) {
        RequestQueue queue = Volley.newRequestQueue(context);

        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

        builder.addTextBody("content", content);
        builder.addTextBody("point", String.valueOf(stars));


        HttpEntity httpEntity = builder.build();

        Map<String, String> params = new HashMap<>();
        params.put("orderId", orderId);
        String query = QueryUtil.createQuery(API.REVIEW_SHIPPER, params);

        StringRequest req = new StringRequest(Request.Method.POST, query,
                response -> {
                    Log.d("review shipper", response.toString());
                    try {
                        JSONObject json = new JSONObject(response);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> Log.d("review shipper", error.getMessage()))
        {

            @Override
            public byte[] getBody() throws AuthFailureError {
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                try {
                    httpEntity.writeTo(bos);
                } catch (IOException e) {
                    VolleyLog.e("IOException writing to ByteArrayOutputStream");
                }
                return bos.toByteArray();
            }

            @Override
            public String getBodyContentType() {
                return httpEntity.getContentType().getValue();
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
