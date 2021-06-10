package com.hcmus.fit.customer_apps.networks;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.hcmus.fit.customer_apps.MainActivity;
import com.hcmus.fit.customer_apps.activities.OTPLoginActivity;
import com.hcmus.fit.customer_apps.activities.PhoneLoginActivity;
import com.hcmus.fit.customer_apps.contants.API;
import com.hcmus.fit.customer_apps.models.UserInfo;
import com.hcmus.fit.customer_apps.utils.JWTUtils;
import com.hcmus.fit.customer_apps.utils.QueryUtil;
import com.hcmus.fit.customer_apps.utils.StorageUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignInNetwork {
    public static GoogleSignInClient googleSignInClient;

    public static void sendGGIdTokenToServer(Context context, GoogleSignInAccount account) {
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest req = new StringRequest(Request.Method.POST, API.SIGN_IN_WITH_GOOGLE,
                response -> {
                    Log.d("google", response.toString());
                    try {
                        JSONObject json = new JSONObject(response);
                        int errorCode = json.getInt("errorCode");

                        if (errorCode == 8) {
                            JSONObject data = json.getJSONObject("data");
                            String userId = data.getString("user");

                            UserInfo.getInstance().setId(userId);
                            Log.d("user", UserInfo.getInstance().getId());

                            Intent intent = new Intent(context, PhoneLoginActivity.class);
                            intent.putExtra("userId", userId);
                            context.startActivity(intent);
                        } else if (errorCode == 0) {
                            JSONObject data = json.getJSONObject("data");
                            String token = data.getString("token");

                            StorageUtil.putString(context, StorageUtil.TOKEN_KEY, token);
                            UserInfo.getInstance().setToken(token);
                            Log.d("token", UserInfo.getInstance().getToken());

                            Intent intent = new Intent(context, MainActivity.class);
                            context.startActivity(intent);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> Log.d("google", error.getMessage()))
        {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("idToken", account.getIdToken());

                return params;
            }
        };

        queue.add(req);
    }

    public static void sendGGOTP(Context context, String userId, String phoneNumber) {
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest req = new StringRequest(Request.Method.POST, API.SEND_GG_OTP,
                response -> {
                    Log.d("sendGGOTP: ", response);
                    JSONObject json = null;
                    try {
                        json = new JSONObject(response);
                        int error = json.getInt("errorCode");

                        if (error == 0) {
                            Intent intent = new Intent(context, OTPLoginActivity.class);
                            intent.putExtra("method", "google");
                            context.startActivity(intent);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                },
                error -> Log.d("OTP", error.getMessage()))
        {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user", userId);
                params.put("phone", phoneNumber);
                return params;
            }
        };

        queue.add(req);
    }

    public static void sendPhoneOTP(Context context, String phoneNumber) {
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest req = new StringRequest(Request.Method.POST, API.SEND_PHONE_OTP,
                response -> {
                    Log.d("sendPhoneOTP", response);
                    JSONObject json = null;
                    try {
                        json = new JSONObject(response);
                        int error = json.getInt("errorCode");

                        if (error == 0) {
                            Intent intent = new Intent(context, OTPLoginActivity.class);
                            intent.putExtra("method", "phone");
                            context.startActivity(intent);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                },
                error -> Log.d("OTP", error.getMessage()))
        {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("phone", phoneNumber);
                return params;
            }
        };

        queue.add(req);
    }

    public static void authGGVerify(Context context, String userId, String OTP) {
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest req = new StringRequest(Request.Method.POST, API.AUTH_GG_VERIFY,
                response -> {
                    Log.d("Verify", response);
                    JSONObject json = null;
                    try {
                        json = new JSONObject(response);
                        int error = json.getInt("errorCode");

                        if (error == 0) {
                            JSONObject data = json.getJSONObject("data");
                            String token = data.getString("token");

                            StorageUtil.putString(context, StorageUtil.TOKEN_KEY, token);
                            UserInfo.getInstance().setToken(token);
                            Intent intent = new Intent(context, MainActivity.class);
                            context.startActivity(intent);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                },
                error -> Log.d("Verify", error.getMessage()))
        {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user", userId);
                params.put("otp", OTP);
                return params;
            }
        };

        queue.add(req);
    }

    public static void authPhoneVerify(Context context, String phoneNumber, String OTP) {
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest req = new StringRequest(Request.Method.POST, API.AUTH_PHONE_VERIFY,
                response -> {
                    Log.d("Verify", response);
                    JSONObject json = null;
                    try {
                        json = new JSONObject(response);
                        int error = json.getInt("errorCode");

                        if (error == 0) {
                            JSONObject data = json.getJSONObject("data");
                            String token = data.getString("token");

                            StorageUtil.putString(context, StorageUtil.TOKEN_KEY, token);
                            UserInfo.getInstance().setToken(token);
                            Intent intent = new Intent(context, MainActivity.class);
                            context.startActivity(intent);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                },
                error -> Log.d("Verify", error.getMessage()))
        {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("phone", phoneNumber);
                params.put("otp", OTP);
                return params;
            }
        };

        queue.add(req);
    }

    public static void getUserInfo(Context context) {
        RequestQueue queue = Volley.newRequestQueue(context);

        Map<String, String> params = new HashMap<>();
        String userId = JWTUtils.getUserIdFromToken(UserInfo.getInstance().getToken());
        params.put("id", userId);
        String query = QueryUtil.createQuery(API.GET_USER_INFO, params);

        StringRequest req = new StringRequest(Request.Method.GET, query,
                response -> {
                    Log.d("userInfo", response);
                    JSONObject json = null;
                    try {
                        json = new JSONObject(response);
                        int error = json.getInt("errorCode");

                        if (error == 0) {
                            JSONObject data = json.getJSONObject("data");
                            JSONObject user = data.getJSONObject("user");
                            String id = user.getString("id");
                            String phone = user.getString("Phone");
                            UserInfo.getInstance().setId(id);
                            UserInfo.getInstance().setPhoneNumber(phone);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                },
                error -> Log.d("userInfo", error.getMessage()))
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/json; charset=UTF-8");
                params.put("Authorization", "Bearer " + UserInfo.getInstance().getToken());
                return params;
            }
        };

        queue.add(req);
    }

}
