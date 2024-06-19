package com.project.smartfrigde.model;

public class FoodItem {
    private String food_item_name;
    private String unit;
    private String expired_date;

    public FoodItem() {
    }

    public FoodItem(String food_item_name, String unit, String expired_date) {
        this.food_item_name = food_item_name;
        this.unit = unit;
        this.expired_date = expired_date;
    }

    public String getFood_item_name() {
        return food_item_name;
    }

    public void setFood_item_name(String food_item_name) {
        this.food_item_name = food_item_name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getExpired_date() {
        return expired_date;
    }

    public void setExpired_date(String expired_date) {
        this.expired_date = expired_date;
    }
}
