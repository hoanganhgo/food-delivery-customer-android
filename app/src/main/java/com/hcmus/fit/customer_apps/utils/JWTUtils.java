package com.hcmus.fit.customer_apps.utils;

import android.util.Base64;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class JWTUtils {
    public static JSONObject decoded(String JWTEncoded) {
        JSONObject body = null;
        try {
            String[] split = JWTEncoded.split("\\.");
            body = new JSONObject(getJson(split[1]));
        } catch (UnsupportedEncodingException | JSONException e) {
            e.printStackTrace();
        }

        return body;
    }

    private static String getJson(String strEncoded) throws UnsupportedEncodingException {
        byte[] decodedBytes = Base64.decode(strEncoded, Base64.URL_SAFE);
        return new String(decodedBytes, "UTF-8");
    }

    public static String getUserIdFromToken(String token) {
        String id = "";
        try {
            JSONObject body = decoded(token);
            id = body.getString("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return id;
    }
}
