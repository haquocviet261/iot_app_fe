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
}
