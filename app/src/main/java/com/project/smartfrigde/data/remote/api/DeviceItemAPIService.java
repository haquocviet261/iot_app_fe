package com.project.smartfrigde.data.remote.api;

import com.project.smartfrigde.data.dto.request.DeviceItemRequest;
import com.project.smartfrigde.data.dto.response.ResponseObject;
import com.project.smartfrigde.model.Device;
import com.project.smartfrigde.model.DeviceItem;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface DeviceItemAPIService {
    @POST("/add")
    Observable<ResponseObject> addDeviceItem(@Body DeviceItemRequest device);
    @GET("/find")
    Observable<ResponseObject> findDeviceItem(@Query("user_id") Long user_id);

}
