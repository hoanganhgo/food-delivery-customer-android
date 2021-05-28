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
                        JSONObject data = json.getJSONObject("data");
                        String userId = data.getString("user");

                        UserInfo.getInstance().setId(userId);
                        Log.d("user", UserInfo.getInstance().getId());

                        Intent intent = new Intent(context, PhoneLoginActivity.class);
                        context.startActivity(intent);
                    } catch (JSONException e) {
                        try {
                            JSONObject json = new JSONObject(response);
                            JSONObject data = json.getJSONObject("data");
                            String token = data.getString("token");

                            UserInfo.getInstance().setToken(token);
                            Log.d("token", UserInfo.getInstance().getToken());

                            Intent intent = new Intent(context, MainActivity.class);
                            context.startActivity(intent);
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
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

    public static void verifyPhoneNumber(Context context, String userId, String phoneNumber) {
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest req = new StringRequest(Request.Method.POST, API.SIGN_IN_WITH_PHONE_NUMBER,
                response -> {
                    Log.d("phone_number", response);
                    try {
                        JSONObject json = new JSONObject(response);
                        String token = json.getJSONObject("data").getString("token");
                        UserInfo.getInstance().setToken(token);
                        Log.d("user", UserInfo.getInstance().getToken());

                        Intent intent = new Intent(context, MainActivity.class);
                        context.startActivity(intent);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> Log.d("phone_number", error.getMessage()))
        {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("userID", userId);
                params.put("phone", phoneNumber);
                return params;
            }
        };

        queue.add(req);
    }

    public static void sendOTP(Context context, String userId, String phoneNumber) {
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest req = new StringRequest(Request.Method.POST, API.SEND_OTP,
                response -> {
                    Log.d("OTP", response);
                    JSONObject json = null;
                    try {
                        json = new JSONObject(response);
                        int error = json.getInt("errorCode");

                        if (error == 0) {
                            Intent intent = new Intent(context, OTPLoginActivity.class);
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

    public static void authVerify(Context context, String userId, String OTP) {
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest req = new StringRequest(Request.Method.POST, API.AUTH_VERIFY,
                response -> {
                    Log.d("Verify", response);
                    JSONObject json = null;
                    try {
                        json = new JSONObject(response);
                        int error = json.getInt("errorCode");

                        if (error == 0) {
                            JSONObject data = json.getJSONObject("data");
                            String token = data.getString("token");
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

}
