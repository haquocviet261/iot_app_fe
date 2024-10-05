package com.project.smartfrigde.data.dto.response;

import java.util.Date;

public class FoodItemResponse {
    private Long food_item_id;
    private String food_name;
    private Date add_date;
    private Date expiration_date;
    private Long food_id;
    private Long device_item_id;

    public FoodItemResponse() {
    }

    public FoodItemResponse(Long food_item_id, String food_name, Date add_date, Date expiration_date, Long food_id, Long device_item_id) {
        this.food_item_id = food_item_id;
        this.food_name = food_name;
        this.add_date = add_date;
        this.expiration_date = expiration_date;
        this.food_id = food_id;
        this.device_item_id = device_item_id;
    }

    public Long getFood_item_id() {
        return food_item_id;
    }

    public void setFood_item_id(Long food_item_id) {
        this.food_item_id = food_item_id;
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

    public Date getExpiration_date() {
        return expiration_date;
    }

    public void setExpiration_date(Date expiration_date) {
        this.expiration_date = expiration_date;
    }

    public Long getFood_id() {
        return food_id;
    }

    public void setFood_id(Long food_id) {
        this.food_id = food_id;
    }

    public Long getDevice_item_id() {
        return device_item_id;
    }

    public void setDevice_item_id(Long device_item_id) {
        this.device_item_id = device_item_id;
    }
}
