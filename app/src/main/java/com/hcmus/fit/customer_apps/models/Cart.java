package com.hcmus.fit.customer_apps.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Cart {
    public DishModel dishSelect = null;
    public int numSelect = 1;
    private final List<DishOrder> dishList = new ArrayList<>();

    public Cart() {

    }

    public void addDish(DishModel dishModel, int number) {
        for (DishOrder dishOrder : dishList) {
            if (compare2Dish(dishOrder.dishModel, dishModel)) {
                dishOrder.num += number;
                return;
            }
        }

        DishOrder dishOrder = new DishOrder(dishModel, number);
        this.dishList.add(dishOrder);
    }

    public boolean compare2Dish(DishModel dish1, DishModel dish2) {
        if (!dish1.getId().equals( dish2.getId())) {
            return false;
        }

        for (int i = 0; i < dish1.getOptionList().size(); i++) {
            OptionModel optionModel = dish1.getOptionList().get(i);

            for (int j = 0; j < optionModel.getItemList().size(); j++) {
                try {
                    ItemModel itemModel1 = optionModel.getItemList().get(j);
                    ItemModel itemModel2 = dish2.getOptionList().get(i).getItemList().get(j);
                    if (itemModel1.isSelected() != itemModel2.isSelected()) {
                        return false;
                    }
                } catch (IndexOutOfBoundsException e) {
                    e.printStackTrace();
                    return false;
                }
            }
        }

        return true;
    }

    public int getNumDish() {
        int num = 0;

        for (DishOrder dishOrder : dishList) {
            num += dishOrder.num;
        }

        return num;
    }

    public int getDishListSize() {
        return this.dishList.size();
    }

    public int getTotal() {
        int total = 0;

        for (DishOrder dishOrder : dishList) {
            total += (dishOrder.dishModel.getPrice() + getTotalOption(dishOrder.dishModel)) * dishOrder.num;
        }

        return total;
    }

    private int getTotalOption(DishModel dishModel) {
        int total = 0;

        for (OptionModel optionModel : dishModel.getOptionList()) {
            for (ItemModel itemModel : optionModel.getItemList()) {
                if (itemModel.isSelected()) {
                    total += itemModel.getPrice();
                }
            }
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

            JSONArray arrayOption = new JSONArray();

            for (OptionModel optionModel : dishOrder.dishModel.getOptionList()) {
                if (optionModel.countItemSelected() > 0) {
                    arrayOption.put(optionModel.createJson());
                }
            }

            dishJson.put("options", arrayOption);
            foodArray.put(dishJson);
        }

        return foodArray;
    }

    public void clear() {
        this.dishList.clear();
    }
}
