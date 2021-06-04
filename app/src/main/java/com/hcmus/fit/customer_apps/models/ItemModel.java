package com.hcmus.fit.customer_apps.models;

import org.json.JSONException;
import org.json.JSONObject;

public class ItemModel {
    private int id;
    private int maxQuantity;
    private int price;
    private String name;
    private boolean isSelected = false;

    public ItemModel() {
    }

    public ItemModel(int id, int maxQuantity, int price, String name) {
        this.id = id;
        this.maxQuantity = maxQuantity;
        this.price = price;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public int getMaxQuantity() {
        return maxQuantity;
    }

    public int getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public void setItemWithJson(JSONObject json) throws JSONException {
        this.id = json.getInt("id");
        this.maxQuantity = json.getInt("MaxQuantity");
        this.price = json.getInt("OriginalPrice");
        this.name = json.getString("Name");
    }

    public ItemModel clone() {
        return new ItemModel(this.id, this.maxQuantity, this.price, this.name);
    }

    public JSONObject createJson() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("id", this.id);
        json.put("price", this.price);
        json.put("quantity", 1);
        return json;
    }
}
