package com.project.smartfrigde.data.remote.api;

import com.project.smartfrigde.data.dto.request.FoodItemRequest;
import com.project.smartfrigde.data.dto.response.ResponseObject;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface FoodItemAPIService {
    @GET("find")
    Observable<ResponseObject> getFoodItemByDeviceItemID(@Query("device_item_id") Long device_item_id);
    @DELETE("delete")
    Observable<ResponseObject> deleteFoodFoodItemByID(@Query("device_item_id") Long device_item_id);
    @POST("add")
    Observable<ResponseObject> addFoodItem(@Body FoodItemRequest foodItemRequest);
}
