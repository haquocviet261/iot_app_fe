package com.project.smartfrigde.data.dto.request;

import java.util.Date;

public class FoodItemRequest {
    private String food_name;
    private Date add_date;
    private int quantity;
    private Date expiration_date;
    private Long device_item_id;
    private Long food_id;

    public FoodItemRequest() {
    }

    public FoodItemRequest(String food_name, Date add_date, int quantity, Date expiration_date, Long device_item_id, Long food_id) {
        this.food_name = food_name;
        this.add_date = add_date;
        this.quantity = quantity;
        this.expiration_date = expiration_date;
        this.device_item_id = device_item_id;
        this.food_id = food_id;
    }

    public String getFood_name() {
        return food_name;
    }

    public void setFood_name(String food_name) {
        this.food_name = food_name;
    }

    public Date getAdd_date() {
        return add_date;
    }

    public void setAdd_date(Date add_date) {
        this.add_date = add_date;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Date getExpiration_date() {
        return expiration_date;
    }

    public void setExpiration_date(Date expiration_date) {
        this.expiration_date = expiration_date;
    }

    public Long getDevice_item_id() {
        return device_item_id;
    }

    public void setDevice_item_id(Long device_item_id) {
        this.device_item_id = device_item_id;
    }

    public Long getFood_id() {
        return food_id;
    }

    public void setFood_id(Long food_id) {
        this.food_id = food_id;
    }
}
