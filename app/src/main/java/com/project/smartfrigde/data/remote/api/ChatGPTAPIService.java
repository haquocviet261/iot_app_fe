package com.project.smartfrigde.data.remote.api;

import com.project.smartfrigde.data.dto.request.ChatGPTRequest;
import com.project.smartfrigde.data.dto.response.ChatGPTResponse;
import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ChatGPTAPIService {
    @POST("v1/chat/completions")
    Observable<ChatGPTResponse> getChatCompletion(@Body ChatGPTRequest request);
}
