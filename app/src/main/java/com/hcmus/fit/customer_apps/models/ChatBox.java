package com.hcmus.fit.customer_apps.models;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.hcmus.fit.customer_apps.activities.ChatActivity;
import com.hcmus.fit.customer_apps.networks.MySocket;

import java.net.URL;
import java.util.ArrayList;

public class ChatBox {
    private String id;
    private Bitmap avatar;
    private String userName;
    private String lastMessage;
    private final ArrayList<ChatModel> chatList = new ArrayList<>();
    private ChatActivity activity;

    public ChatBox() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Bitmap getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatarUrl) {
        try {
            URL url = new URL(avatarUrl);
            this.avatar = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public void addMessage(ChatModel chatModel) {
        this.chatList.add(chatModel);

        if (chatModel.isMyself()) {
            MySocket.sendMessage(this.id, chatModel.getContent());
        } else {
            if (activityActive()) {
                this.activity.runOnUiThread(() -> activity.updateChatBox());
            }
        }
    }

    public int getChatListSize() {
        return this.chatList.size();
    }

    public ChatModel getChatIndex(int index) {
        return this.chatList.get(index);
    }

    public void setActivity(ChatActivity activity) {
        this.activity = activity;
    }

    private boolean activityActive() {
        return this.activity != null && !this.activity.isDestroyed();
    }
}
