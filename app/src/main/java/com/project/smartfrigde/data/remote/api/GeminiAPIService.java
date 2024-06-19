package com.project.smartfrigde.data.remote.api;

import com.project.smartfrigde.data.dto.response.GeminiResponse;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface GeminiAPIService {
    @POST("v1/documents:analyzeEntities")
    Observable<GeminiResponse> analyzeEntities(@Body com.project.smartfrigde.data.remote.dto.request.GeminiRequest request);
}
