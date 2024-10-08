package com.project.smartfrigde.data.remote.api.retrofit;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.project.smartfrigde.data.remote.api.FoodAPIService;
import com.project.smartfrigde.data.remote.api.FoodItemAPIService;
import com.project.smartfrigde.utils.TokenManager;
import com.project.smartfrigde.utils.Validation;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FoodItemClient {
    private static final String FOOD_URL = "/api/food/item/";
    private static final Gson GSON = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();

    private static final HttpLoggingInterceptor HTTP_LOGGING_INTERCEPTOR =
            new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
    private static final Interceptor INTERCEPTOR = new Interceptor() {
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
    private static final OkHttpClient CLIENT = new OkHttpClient.Builder()
            .readTimeout(300, TimeUnit.SECONDS)
            .connectTimeout(300, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .addInterceptor(INTERCEPTOR)
            .addInterceptor(HTTP_LOGGING_INTERCEPTOR)
            .build();
    private static final Retrofit RETROFIT = new Retrofit.Builder()
            .baseUrl(Validation.WEB_SERVICE_URL+FOOD_URL)
            .addConverterFactory(GsonConverterFactory.create(GSON))
            .addCallAdapterFactory(RxJava3CallAdapterFactory.createAsync())
            .client(CLIENT)
            .build();

    public static FoodItemAPIService getFoodItemAPIService() {
        return RETROFIT.create(FoodItemAPIService.class);
    }
}
