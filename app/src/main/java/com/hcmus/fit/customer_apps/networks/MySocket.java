package com.hcmus.fit.customer_apps.networks;

import android.util.Log;

import com.hcmus.fit.customer_apps.contants.API;
import com.hcmus.fit.customer_apps.contants.Constant;
import com.hcmus.fit.customer_apps.models.ChatBox;
import com.hcmus.fit.customer_apps.models.ChatModel;
import com.hcmus.fit.customer_apps.models.NotifyManager;
import com.hcmus.fit.customer_apps.models.NotifyModel;
import com.hcmus.fit.customer_apps.models.OrderManager;
import com.hcmus.fit.customer_apps.models.OrderModel;
import com.hcmus.fit.customer_apps.models.ShipperModel;
import com.hcmus.fit.customer_apps.models.UserInfo;
import com.hcmus.fit.customer_apps.utils.NotifyUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

import static com.hcmus.fit.customer_apps.contants.EventConstant.*;

public class MySocket {
    private static Socket instance = null;

    public static Socket getInstance() {
        if (instance == null) {
            try {
                instance = IO.socket(API.SERVER_SOCKET);
//                instance = IO.socket("https://87e83c19d91f.ngrok.io");
                instance.connect();
                instance.on(CONNECT, onAuthenticate);
                instance.on(RESPONSE_CHANGE_STATUS_ROOM, statusRoom);
                instance.on(RESPONSE_SHIPPER_CHANGE_COOR, shipperMove);
                instance.on(RESPONSE_UPDATE_SHIPPER, listenUpdateShipper);
                instance.on(RESPONSE_CHAT, listenChat);
                instance.on(RESPONSE_NOTIFICATION, listenNotification);

            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }

        return instance;
    }

    private static final Emitter.Listener onAuthenticate = args -> {
        JSONObject json = new JSONObject();
        Log.d("token", UserInfo.getInstance().getToken());
        try {
            json.put("token", UserInfo.getInstance().getToken());
            instance.emit("authenticate", json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    };

    private static final Emitter.Listener statusRoom = args -> {
        JSONObject json = (JSONObject) args[0];
        Log.d("socket-status", json.toString());
        try {
            JSONObject data = json.getJSONObject("data");
            int status = data.getInt("status");
            String orderId = data.getString("orderID");
            OrderManager.getInstance().setStatusOrder(orderId, status);

            if (status == 1) {
                UserInfo.getInstance().getCart().paymentSuccess(orderId);
            } else if (status == 3) {
                OrderManager.getInstance().setStatusWaitingDish(orderId);
            } else if (status == 4) {
                OrderManager.getInstance().setStatusShipping(orderId);
            } else if (status == 5) {
                OrderManager.getInstance().setStatusArrived(orderId);
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
            String orderId = data.getString("orderID");
            JSONObject shipperJson = data.getJSONObject("infoShipper");
            String id = shipperJson.getString("_id");
            String fullName = shipperJson.getString("FullName");
            String avatar = shipperJson.getString("Avatar");
            String phone = shipperJson.getString("Phone");
            ShipperModel shipperModel = new ShipperModel(id, fullName, avatar, phone);
            OrderManager.getInstance().updateShipperInfo(orderId, shipperModel);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    };


    private static final Emitter.Listener shipperMove = args -> {
        JSONObject json = (JSONObject) args[0];
        Log.d("socket-shipper", json.toString());
        try {
            JSONObject data = json.getJSONObject("data");
            String orderId = data.getString("orderID");
            JSONObject coorJson = data.getJSONObject("coor");
            double latitude = coorJson.getDouble("lat");
            double longitude = coorJson.getDouble("lng");

            OrderModel orderModel = OrderManager.getInstance().getOrderModel(orderId);
            if (orderModel == null) {
                return;
            }

            ShipperModel shipper = orderModel.getShipper();
            if (shipper != null) {
                shipper.setLocation(latitude, longitude);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    };

    public static void sendMessage(String roomId, String message) {
        JSONObject json = new JSONObject();
        Log.d("socket-send_chat", ">> Send message >>");
        try {
            json.put("roomID", roomId);
            json.put("message", message);
            instance.emit(REQUEST_CHAT, json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private static final Emitter.Listener listenChat = args -> {
        JSONObject json = (JSONObject) args[0];
        Log.d("socket-receive-chat", json.toString());
        try {
            JSONObject data = json.getJSONObject("data");
            String role = data.getString("roleSender");

            if (Constant.MY_ROLE.equals(role)) {
                return;
            }

            String roomId = data.getString("roomID");
            String message = data.getString("message");
            JSONObject senderJson = data.getJSONObject("shipper");
            String senderId = senderJson.getString("_id");

            ShipperModel shipper = OrderManager.getInstance().getShipper(senderId);
            if (shipper == null) {
                return;
            }

            ChatBox chatBox = shipper.getChatBox();

            if (chatBox == null) {
                String senderName = senderJson.getString("FullName");
                String senderAvatar = senderJson.getString("Avatar");

                chatBox = new ChatBox();
                chatBox.setId(roomId);
                chatBox.setUserName(senderName);
                chatBox.setAvatar(senderAvatar);
                chatBox.setLastMessage(message);

                ChatModel chatModel = new ChatModel();
                chatModel.setMyself(false);
                chatModel.setContent(message);

                chatBox.addMessage(chatModel);
                shipper.setChatBox(chatBox);
            } else {
                chatBox.setLastMessage(message);

                ChatModel chatModel = new ChatModel();
                chatModel.setMyself(false);
                chatModel.setContent(message);

                chatBox.addMessage(chatModel);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    };

    private static final Emitter.Listener listenNotification = args -> {
        JSONObject json = (JSONObject) args[0];
        Log.d("socket-notification", json.toString());

        try {
            JSONObject data = json.getJSONObject("data");
            String id = data.getString("_id");
            String title = data.getString("Title");
            String content = data.getString("Subtitle");
            String avatar = data.getString("Thumbnail");

            NotifyModel notifyModel = new NotifyModel(id, title, content, avatar);
            NotifyManager.getInstance().addNotifyModel(notifyModel);

            NotifyUtil.call(title, content);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    };
}
