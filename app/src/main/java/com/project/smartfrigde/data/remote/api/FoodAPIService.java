package com.project.smartfrigde.data.remote.api;

import com.project.smartfrigde.data.dto.response.ResponseObject;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface FoodAPIService {
    @GET("all")
    Observable<ResponseObject> getAllFood();
    @POST("update")
    Observable<ResponseObject> updateFood(@Query("food_id") Integer food_id);
}
