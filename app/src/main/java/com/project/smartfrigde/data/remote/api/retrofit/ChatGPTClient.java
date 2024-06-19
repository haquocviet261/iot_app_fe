package com.project.smartfrigde.data.remote.api.retrofit;

import androidx.annotation.NonNull;
        import com.google.gson.Gson;
        import com.google.gson.GsonBuilder;
import com.project.smartfrigde.data.remote.api.ChatGPTAPIService;

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

public class ChatGPTClient {
    private static final String CHAT_GPT_URL = "https://api.openai.com/";

    private static final Gson GSON = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();

    private static final HttpLoggingInterceptor HTTP_LOGGING_INTERCEPTOR =
            new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);

    private static final Interceptor INTERCEPTOR = new Interceptor() {
        @NonNull
        @Override
        public Response intercept(@NonNull Chain chain) throws IOException {
            String token = "sk-proj-dOokjSy3rIPT2oIJqFecT3BlbkFJFKP4Dm5EFz0seelyPd1Y"; // Retrieve token dynamically
            Request original = chain.request();
            Request request = original.newBuilder()
                    .header("Authorization", "Bearer " + token)
                    .method(original.method(), original.body())
                    .build();
            return chain.proceed(request);
        }
    };

    private static final OkHttpClient CLIENT = new OkHttpClient.Builder()
            .readTimeout(120, TimeUnit.SECONDS)
            .connectTimeout(120, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .addInterceptor(INTERCEPTOR)
            .addInterceptor(HTTP_LOGGING_INTERCEPTOR)
            .build();

    private static final Retrofit RETROFIT = new Retrofit.Builder()
            .baseUrl(CHAT_GPT_URL)
            .addConverterFactory(GsonConverterFactory.create(GSON))
            .addCallAdapterFactory(RxJava3CallAdapterFactory.createAsync())
            .client(CLIENT)
            .build();

    public static ChatGPTAPIService getChatGPTApiService() {
        return RETROFIT.create(ChatGPTAPIService.class);
    }
}
