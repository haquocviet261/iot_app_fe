package com.project.smartfrigde.data.remote.api.retrofit;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.project.smartfrigde.data.remote.api.GeminiAPIService;

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

public class GeminiClient {
    private static final String GEMINI_API_URL = "https://language.googleapis.com/";
    private static GeminiAPIService geminiAPIService;

    public static GeminiAPIService getGeminiAPIService(String apiKey) {
        if (geminiAPIService == null) {
            synchronized (GeminiClient.class) {
                if (geminiAPIService == null) {
                    Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();

                    HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
                    loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

                    Interceptor apiKeyInterceptor = new Interceptor() {
                        @NonNull
                        @Override
                        public Response intercept(@NonNull Chain chain) throws IOException {
                            Request original = chain.request();
                            Request request = original.newBuilder()
                                    .header("Authorization", "Bearer " + apiKey)
                                    .method(original.method(), original.body())
                                    .build();
                            return chain.proceed(request);
                        }
                    };

                    OkHttpClient okHttpClient = new OkHttpClient.Builder()
                            .readTimeout(120, TimeUnit.SECONDS)
                            .connectTimeout(120, TimeUnit.SECONDS)
                            .retryOnConnectionFailure(true)
                            .addInterceptor(apiKeyInterceptor)
                            .addInterceptor(loggingInterceptor)
                            .build();

                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(GEMINI_API_URL)
                            .addConverterFactory(GsonConverterFactory.create(gson))
                            .addCallAdapterFactory(RxJava3CallAdapterFactory.createAsync())
                            .client(okHttpClient)
                            .build();

                    geminiAPIService = retrofit.create(GeminiAPIService.class);
                }
            }
        }
        return geminiAPIService;
    }
}
