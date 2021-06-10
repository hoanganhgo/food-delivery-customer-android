package com.hcmus.fit.customer_apps.networks;

import android.util.Log;

import com.hcmus.fit.customer_apps.contants.API;
import com.hcmus.fit.customer_apps.contants.EventConstant;
import com.hcmus.fit.customer_apps.models.OrderManager;
import com.hcmus.fit.customer_apps.models.ShipperModel;
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
                instance.on(EventConstant.CONNECT, onAuthenticate);
                instance.on(EventConstant.RESPONSE_CHANGE_STATUS_ROOM, statusRoom);
                instance.on(EventConstant.RESPONSE_SHIPPER_CHANGE_COOR, statusRoom);
                instance.on(EventConstant.RESPONSE_UPDATE_SHIPPER, listenUpdateShipper);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }

        return instance;
    }

    private static final Emitter.Listener onAuthenticate = args -> {
        JSONObject json = new JSONObject();
        Log.d("token", "connect......");
        try {
            json.put("token", UserInfo.getInstance().getToken());
            instance.emit("authenticate", json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    };

    private static final Emitter.Listener statusRoom = args -> {
        JSONObject json = (JSONObject) args[0];
        Log.d("socket", json.toString());
        try {
            JSONObject data = json.getJSONObject("data");
            int status = data.getInt("status");

            if (status == 3) {
                UserInfo.getInstance().getOrderManager().setStatusWaitingDish();
            } else if (status == 4) {
                UserInfo.getInstance().getOrderManager().setStatusShipping();
            } else if (status == 5) {
                UserInfo.getInstance().getOrderManager().setStatusArrived();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    };

    private static final Emitter.Listener listenUpdateShipper = args -> {
        JSONObject json = (JSONObject) args[0];
        Log.d("socket-shipper", json.toString());

        try {
            JSONObject data = json.getJSONObject("data");
            JSONObject shipperJson = data.getJSONObject("infoShipper");
            String id = shipperJson.getString("_id");
            String fullName = shipperJson.getString("FullName");
            String avatar = shipperJson.getString("Avatar");
            String phone = shipperJson.getString("Phone");
            ShipperModel shipperModel = new ShipperModel(id, fullName, avatar, phone);
            UserInfo.getInstance().getOrderManager().updateShipperInfo(shipperModel);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    };
}
