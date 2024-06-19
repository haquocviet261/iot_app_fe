package com.project.smartfrigde.data.remote.api;

import com.project.smartfrigde.data.dto.response.ResponseObject;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;

public interface FoodAPIService {
    @GET("all")
    Observable<ResponseObject> getAllFood();
}
