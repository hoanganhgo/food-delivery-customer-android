package com.hcmus.fit.customer_apps.networks;

import android.util.Log;

import com.hcmus.fit.customer_apps.contants.API;
import com.hcmus.fit.customer_apps.models.UserInfo;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class MySocket {
    private static Socket instance = null;

    public static Socket getInstance() {
        if (instance == null) {
            try {
                instance = IO.socket(API.SERVER_SOCKET);
//                instance = IO.socket("https://87e83c19d91f.ngrok.io");
                instance.connect();
                instance.on("connect", onAuthenticate);
                instance.on("RESPONSE_CHANGE_STATUS_ROOM", statusRoom);
                instance.on("RESPONSE_SHIPPER_CHANGE_COOR", statusRoom);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }

        return instance;
    }

    private static Emitter.Listener onAuthenticate = args -> {
        JSONObject json = new JSONObject();
        Log.d("token", "connect......");
        try {
            json.put("token", UserInfo.getInstance().getToken());
            instance.emit("authenticate", json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    };

    private static Emitter.Listener statusRoom = args -> {
        JSONObject json = (JSONObject) args[0];
        Log.d("socket", json.toString());
        try {
            JSONObject data = json.getJSONObject("data");
            int status = data.getInt("status");

            if (status == 2) {
                UserInfo.getInstance().getOrderManager().getActivity().setStatusWaitShipper();
            } else if (status == 3) {
                UserInfo.getInstance().getOrderManager().getActivity().setStatusShipping();
            } else if (status == 4) {
                UserInfo.getInstance().getOrderManager().getActivity().setStatusArrived();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    };
}
