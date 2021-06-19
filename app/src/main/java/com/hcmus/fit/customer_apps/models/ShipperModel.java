package com.hcmus.fit.customer_apps.models;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.hcmus.fit.customer_apps.activities.MapsActivity;
import com.hcmus.fit.customer_apps.utils.AppUtil;

import java.net.URL;

public class ShipperModel {
    private String id;
    private String fullName;
    private String avatarUrl;
    private Bitmap avatar;
    private String phone;
    private double latitude;
    private double longitude;
    private MapsActivity mapsActivity;
    private ChatBox chatBox;

    public ShipperModel(String id, String fullName, String avatarUrl, String phone) {
        this.id = id;
        this.fullName = fullName;
        this.avatarUrl = avatarUrl;
        this.phone = phone;

        try {
            URL url = new URL(avatarUrl);
            Bitmap bm = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            Bitmap resizedBitmap = Bitmap.createScaledBitmap(bm, 55, 55, false);
            this.avatar = AppUtil.roundedCornerBitmap(resizedBitmap, 50);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public Bitmap getAvatar() {
        return avatar;
    }

    public String getPhone() {
        return phone;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLocation(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;

        if (this.mapsActivity != null && !this.mapsActivity.isDestroyed()) {
            this.mapsActivity.updateLocation();
        }
    }

    public double getLongitude() {
        return longitude;
    }

    public ChatBox getChatBox() {
        return chatBox;
    }

    public void setChatBox(ChatBox chatBox) {
        this.chatBox = chatBox;
    }

    public void setMapsActivity(MapsActivity mapsActivity) {
        this.mapsActivity = mapsActivity;
    }
}
