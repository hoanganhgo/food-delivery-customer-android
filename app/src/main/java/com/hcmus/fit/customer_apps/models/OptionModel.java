package com.hcmus.fit.customer_apps.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class OptionModel {
    private int id;
    private int maxSelect;
    private String name;
    private List<ItemModel> itemList;

    public OptionModel() {
    }

    public OptionModel(int id, int maxSelect, String name, List<ItemModel> itemList) {
        this.id = id;
        this.maxSelect = maxSelect;
        this.name = name;
        this.itemList = itemList;
    }

    public int getId() {
        return id;
    }

    public int getMaxSelect() {
        return maxSelect;
    }

    public String getName() {
        return name;
    }

    public List<ItemModel> getItemList() {
        return itemList;
    }

    public int countItemSelected() {
        int count = 0;

        for (ItemModel itemModel : this.itemList) {
            if (itemModel.isSelected()) {
                count++;
            }
        }
        return count;
    }

    public void setOptionWithJson(JSONObject json) throws JSONException {
        this.id = json.getInt("id");
        this.maxSelect = json.getInt("MaxSelect");
        this.name = json.getString("Name");

        this.itemList = new ArrayList<>();
        JSONArray itemArrJson = json.getJSONArray("Items");

        for (int i = 0; i < itemArrJson.length(); i++) {
            ItemModel itemModel = new ItemModel();
            JSONObject itemJson = itemArrJson.getJSONObject(i);
            itemModel.setItemWithJson(itemJson);
            this.itemList.add(itemModel);
        }
    }

    public OptionModel clone() {
        List<ItemModel> items = new ArrayList<>();

        for (ItemModel itemModel : this.itemList) {
            items.add(itemModel.clone());
        }

        return new OptionModel(this.id, this.maxSelect, this.name, items);
    }

    public JSONObject createJson() throws JSONException {
        JSONObject jsonOption = new JSONObject();
        jsonOption.put("id", this.id);

        JSONArray jsonArray = new JSONArray();

        for (ItemModel itemModel : this.itemList) {
            if (itemModel.isSelected()) {
                jsonArray.put(itemModel.createJson());
            }
        }

        jsonOption.put("items", jsonArray);

        return jsonOption;
    }
}
