package com.project.smartfrigde.data.remote.api;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.project.smartfrigde.data.dto.request.BmiRequest;
import com.project.smartfrigde.data.dto.response.ResponseObject;
import com.project.smartfrigde.model.Bmi;
import com.project.smartfrigde.utils.TokenManager;
import com.project.smartfrigde.utils.Validation;

import java.io.IOException;
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
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface BmiAPIService {
    String USER_URL = "/api/bmi/";
    Gson GSON = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
    HttpLoggingInterceptor HTTP_LOGGING_INTERCEPTOR = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
    Interceptor INTERCEPTOR = new Interceptor() {
        @NonNull
        @Override
        public Response intercept(@NonNull Chain chain) throws IOException {
            TokenManager tokenManager = new TokenManager();
            String token;
            Request original = chain.request();
            token = tokenManager.getAccessToken();
            Request request = original.newBuilder()
                    .header("Authorization", "Bearer " + token)
                    .method(original.method(), original.body())
                    .build();
            return chain.proceed(request);
        }
    };
    OkHttpClient.Builder BUILDER = new OkHttpClient.Builder()
            .readTimeout(300, TimeUnit.SECONDS)
            .connectTimeout(300, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .addInterceptor(INTERCEPTOR)
            .addInterceptor(HTTP_LOGGING_INTERCEPTOR);
    BmiAPIService BMI_API_SERVICE = new Retrofit.Builder()
            .baseUrl(Validation.WEB_SERVICE_URL+USER_URL)
            .addConverterFactory(GsonConverterFactory.create(GSON))
            .addCallAdapterFactory(RxJava3CallAdapterFactory.createAsync())
            .client(BUILDER.build())
            .build()
            .create(BmiAPIService.class);
    @POST("add")
    Observable<ResponseObject> saveBmi(@Body BmiRequest bmi);
    @GET("get")
    Observable<ResponseObject> getBmiByUser_ID(@Query("user_id") Long user_id);
}
