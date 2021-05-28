package com.hcmus.fit.customer_apps.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Cart {
    private final List<DishOrder> dishList = new ArrayList<>();

    public Cart() {

    }

    public void addDish(DishModel dishModel) {
        for (DishOrder dishOrder : dishList) {
            if (dishOrder.dishModel.getId().equals(dishModel.getId())) {
                dishOrder.num++;
                return;
            }
        }

        DishOrder dishOrder = new DishOrder(dishModel, 1);
        this.dishList.add(dishOrder);
    }

    public int getNumDish() {
        return this.dishList.size();
    }

    public int getTotal() {
        int total = 0;

        for (DishOrder dishOrder : dishList) {
            total += dishOrder.dishModel.getPrice() * dishOrder.num;
        }

        return total;
    }

    public DishModel getDishModel(int index) {
        return this.dishList.get(index).dishModel;
    }

    public int getNumberByIndex(int index) {
        return this.dishList.get(index).num;
    }

    public int increaseNumber(int index) {
        this.dishList.get(index).num++;
        return this.dishList.get(index).num;
    }

    public int decreaseNumber(int index) {
        if (this.dishList.get(index).num > 0) {
            this.dishList.get(index).num--;
        }
        return this.dishList.get(index).num;
    }

    public void removeDishOrderEmpty() {
        Iterator<DishOrder> it = this.dishList.iterator();

        while (it.hasNext()) {
            DishOrder dishOrder = it.next();
            if (dishOrder.num == 0) {
                it.remove();
            }
        }

    }

    public JSONArray createFoodArrayJson() throws JSONException {
        JSONArray foodArray = new JSONArray();

        for (DishOrder dishOrder : this.dishList) {
            JSONObject dishJson = new JSONObject();
            dishJson.put("id", dishOrder.dishModel.getId());
            dishJson.put("price", dishOrder.dishModel.getPrice());
            dishJson.put("quantity", dishOrder.num);
            foodArray.put(dishJson);
        }

        return foodArray;
    }

    public void clear() {
        this.dishList.clear();
    }
}
