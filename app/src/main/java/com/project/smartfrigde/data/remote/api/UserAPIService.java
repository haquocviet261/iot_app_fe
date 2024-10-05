package com.project.smartfrigde.data.remote.api;


import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.project.smartfrigde.data.dto.request.ChangePasswordRequest;
import com.project.smartfrigde.data.dto.request.EditDTO;
import com.project.smartfrigde.data.dto.request.LoginRequest;
import com.project.smartfrigde.data.dto.request.RegisterRequest;
import com.project.smartfrigde.data.dto.response.ResponseObject;
import com.project.smartfrigde.model.User;
import com.project.smartfrigde.utils.TokenManager;
import com.project.smartfrigde.utils.Validation;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import io.reactivex.rxjava3.core.Observable;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UserAPIService {

    @GET("/logout")
    Observable<ResponseObject> logout();

    @PATCH("/change-password")
    Observable<ResponseObject> changePassword(@Body ChangePasswordRequest request);

    @GET("/")
    Observable<List<User>> getAllUsers();

    @POST("/forgot-password")
    Observable<ResponseObject> forgotPassword(@Body String email);

    @GET("verify-account")
    Observable<String> verifyAccount(@Query("email") String email,
                               @Query("jwt") String jwt);

    @PUT("set-password")
    Observable<String> setPassword(@Body String email,
                             @Body String newPassword);

    @GET("{user_id}")
    Observable<ResponseObject> showProfile(@Path("user_id") Long user_id);

    @POST("edit_user")
    Observable<ResponseObject> editProfile(@Body EditDTO editDTO);

    @POST("register")
    Observable<ResponseObject> register(@Body RegisterRequest request);

    @POST("authenticate")
    Observable<ResponseObject> login(@Body LoginRequest request);
    @GET("find")
    Observable<ResponseObject> findbyUserName(@Query("user_name") String user_name);

    @GET("oauth2/google")
    Observable<ResponseObject> sendToken();
    @GET("posts")
    Observable<ResponseObject> getListUsers();

}
