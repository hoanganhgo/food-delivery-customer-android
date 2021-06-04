package com.hcmus.fit.customer_apps.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DishModel {
    private String id;
    private String name;
    private String avatar;
    private int price;
    private int totalOrder;
    private String foodCategoryId;
    private List<OptionModel> optionList;

    public DishModel(String id, String name, String avatar, int price) {
        this.id = id;
        this.name = name;
        this.avatar = avatar;
        this.price = price;
    }

    public DishModel(String id, String name, String avatar, int price, int totalOrder,
                     String foodCategoryId, List<OptionModel> optionList) {
        this.id = id;
        this.name = name;
        this.avatar = avatar;
        this.price = price;
        this.totalOrder = totalOrder;
        this.foodCategoryId = foodCategoryId;
        this.optionList = optionList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getPrice() {
        return price;
    }

    public int getPriceTotal() {
        int total = price;

        for (OptionModel optionModel : this.optionList) {
            for (ItemModel itemModel : optionModel.getItemList()) {
                if (itemModel.isSelected()) {
                    total += itemModel.getPrice();
                }
            }
        }

        return total;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getTotalOrder() {
        return totalOrder;
    }

    public void setTotalOrder(int totalOrder) {
        this.totalOrder = totalOrder;
    }

    public String getFoodCategoryId() {
        return foodCategoryId;
    }

    public void setFoodCategoryId(String foodCategoryId) {
        this.foodCategoryId = foodCategoryId;
    }

    public List<OptionModel> getOptionList() {
        return optionList;
    }

    public void setOptionListWithJson(JSONArray jsonArray) throws JSONException {
        this.optionList = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            OptionModel optionModel = new OptionModel();
            JSONObject optionJson = jsonArray.getJSONObject(i);
            optionModel.setOptionWithJson(optionJson);
            this.optionList.add(optionModel);
        }
    }

    public DishModel clone() {
        List<OptionModel> options = new ArrayList<>();

        for (OptionModel optionModel : this.optionList) {
            options.add(optionModel.clone());
        }

        return new DishModel(this.id, this.name, this.avatar, this.price, this.totalOrder,
                this.foodCategoryId, options);
    }
}
