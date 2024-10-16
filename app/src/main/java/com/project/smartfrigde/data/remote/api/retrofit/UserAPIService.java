package com.project.smartfrigde.data.remote.api.retrofit;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.project.smartfrigde.data.remote.api.FoodAPIService;
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

public class UserAPIService {
    private static final Gson GSON = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
    private static HttpLoggingInterceptor HTTP_LOGGING_INTERCEPTOR = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
    private static Interceptor INTERCEPTOR = new Interceptor() {
        @NonNull
        @Override
        public Response intercept(@NonNull Chain chain) throws IOException {
//           // TokenManager tokenManager = new TokenManager();
//            String token;
//            Request original = chain.request();
//          //  token = tokenManager.getAccessToken();
//            String path = original.url().encodedPath();
//            if (path.contains("api/user/authenticate")) {
//                return chain.proceed(original);
//            }
//            if (path.contains("oauth2/google")) {
//                token = tokenManager.getAccessOauth2Token();
//            }
//            Request request = original.newBuilder()
//                    .header("Authorization", "Bearer " + token)
//                    .method(original.method(), original.body())
//                    .build();
            Request original = chain.request();
            return chain.proceed(original);
        }
    };
    private static final OkHttpClient CLIENT = new OkHttpClient.Builder()
            .readTimeout(300, TimeUnit.SECONDS)
            .connectTimeout(300, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .addInterceptor(INTERCEPTOR)
            .addInterceptor(HTTP_LOGGING_INTERCEPTOR)
            .build();
    com.project.smartfrigde.data.remote.api.UserAPIService USER_API_SERVICE = new Retrofit.Builder()
            .baseUrl(Validation.WEB_SERVICE_URL)
            .addConverterFactory(GsonConverterFactory.create(GSON))
            .addCallAdapterFactory(RxJava3CallAdapterFactory.createAsync())
            .client(CLIENT)
            .build()
            .create(com.project.smartfrigde.data.remote.api.UserAPIService.class);
    private static final Retrofit RETROFIT = new Retrofit.Builder()
            .baseUrl(Validation.WEB_SERVICE_URL)
            .addConverterFactory(GsonConverterFactory.create(GSON))
            .addCallAdapterFactory(RxJava3CallAdapterFactory.createAsync())
            .client(CLIENT)
            .build();
    public static com.project.smartfrigde.data.remote.api.UserAPIService getUserAPIService() {
        return RETROFIT.create(com.project.smartfrigde.data.remote.api.UserAPIService.class);
    }
}
