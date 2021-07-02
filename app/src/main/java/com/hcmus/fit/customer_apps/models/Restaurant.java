package com.hcmus.fit.customer_apps.models;

import com.hcmus.fit.customer_apps.utils.AppUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Restaurant {
    private String id;
    private String avatar;
    private String name;
    private AddressModel address = new AddressModel();
    private boolean isOpening;
    private String hours;
    private String sale;
    private double starsNum;
    private int numReview;
    private boolean partner;
    private List<DishModel> dishList = new ArrayList<>();

    public Restaurant() {
    }

    public Restaurant(String id, String avatar, String name, String fullAddress,
                      String hours, boolean isOpening) {
        this.id = id;
        this.avatar = avatar;
        this.name = name;
        this.address.setFullAddress(fullAddress);
        this.hours = hours;
        this.isOpening = isOpening;
    }

    public String getId() {
        return id;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getName() {
        return name;
    }

    public String getFullAddress() {
        return address.getFullAddress();
    }

    public boolean isOpening() {
        return isOpening;
    }

    public String getHours() {
        return hours;
    }

    public String getSale() {
        return sale;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isPartner() {
        return partner;
    }

    public void setPartner(boolean partner) {
        this.partner = partner;
    }

    public double getStarsNum() {
        return starsNum;
    }

    public void setStarsNum(double starsNum) {
        this.starsNum = starsNum;
    }

    public int getNumReview() {
        return numReview;
    }

    public void setNumReview(int numReview) {
        this.numReview = numReview;
    }

    public void setFullAddress(String fullAddress) {
        this.address.setFullAddress(fullAddress);
    }

    public void setLocation(JSONObject locationJson) throws JSONException {
        this.address.setLatitude(locationJson.getDouble("latitude"));
        this.address.setLongitude(locationJson.getDouble("longitude"));
    }

    public double getDistance() {
        AddressModel address = UserInfo.getInstance().getAddressCurrent();
        if (address == null) {
            return 0;
        } else {
            double distance = AppUtil.calculateDistance(address, this.address) / 1000d;
            return Math.round(distance * 10d) / 10d;
        }
    }

    public void setOpening(boolean opening) {
        isOpening = opening;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public void setSale(String sale) {
        this.sale = sale;
    }

    public List<DishModel> getDishList() {
        return dishList;
    }
}
