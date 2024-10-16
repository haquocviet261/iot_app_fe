package com.project.smartfrigde.viewmodel;

import androidx.databinding.ObservableField;
import androidx.lifecycle.ViewModel;

import com.google.ai.client.generativeai.java.GenerativeModelFutures;
import com.google.ai.client.generativeai.type.Content;
import com.google.ai.client.generativeai.type.GenerateContentResponse;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.gson.Gson;
import com.project.smartfrigde.data.dto.request.ChatGPTRequest;
import com.project.smartfrigde.data.dto.response.ChatGPTResponse;
import com.project.smartfrigde.data.dto.response.GeminiResponse;
import com.project.smartfrigde.data.remote.api.GeminiAPIService;
import com.project.smartfrigde.data.remote.api.retrofit.ChatGPTClient;
import com.project.smartfrigde.data.remote.api.retrofit.GeminiClient;
import com.project.smartfrigde.utils.Validation;

import java.util.Arrays;
import java.util.concurrent.Executor;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FoodRecommendViewModel extends ViewModel {
    private static final Gson gson = new Gson();
    private final CompositeDisposable disposable = new CompositeDisposable();
    private ObservableField<Boolean> is_loaded_data = new ObservableField<>(false);
    private ObservableField<String> repply_message = new ObservableField<>();
    private ObservableField<String> is_loadded_message = new ObservableField<>();
    private GeminiAPIService geminiAPIService;

    public FoodRecommendViewModel() {
        geminiAPIService = GeminiClient.getGeminiAPIService(Validation.APIKEY);
    }

    public CompositeDisposable getDisposable() {
        return disposable;
    }

    public ObservableField<Boolean> getIs_loaded_data() {
        return is_loaded_data;
    }

    public void setIs_loaded_data(ObservableField<Boolean> is_loaded_data) {
        this.is_loaded_data = is_loaded_data;
    }

    public ObservableField<String> getRepply_message() {
        return repply_message;
    }

    public void setRepply_message(ObservableField<String> repply_message) {
        this.repply_message = repply_message;
    }

    public ObservableField<String> getIs_loadded_message() {
        return is_loadded_message;
    }

    public void setIs_loadded_message(ObservableField<String> is_loadded_message) {
        this.is_loadded_message = is_loadded_message;
    }

    public GeminiAPIService getGeminiAPIService() {
        return geminiAPIService;
    }

    public void setGeminiAPIService(GeminiAPIService geminiAPIService) {
        this.geminiAPIService = geminiAPIService;
    }

    public void analyzeText(String text) {
        com.project.smartfrigde.data.remote.dto.request.GeminiRequest.Document document = new com.project.smartfrigde.data.remote.dto.request.GeminiRequest.Document("PLAIN_TEXT", text);
        com.project.smartfrigde.data.remote.dto.request.GeminiRequest request = new com.project.smartfrigde.data.remote.dto.request.GeminiRequest(document, "UTF8");
        geminiAPIService.analyzeEntities(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GeminiResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull GeminiResponse geminiResponse) {
                        StringBuilder responseText = new StringBuilder();
                        for (GeminiResponse.Entity entity : geminiResponse.getEntities()) {
                            responseText.append(entity.getName()).append(", ");
                        }
                        repply_message.set(responseText.toString());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        is_loaded_data.set(false);
                    }

                    @Override
                    public void onComplete() {
                        is_loaded_data.set(true);
                    }
                });
    }

    public void callModel(Executor executor, GenerativeModelFutures model, Content content){

        ListenableFuture<GenerateContentResponse> response = model.generateContent(content);
        Futures.addCallback(response, new FutureCallback<GenerateContentResponse>() {
                    @Override
                    public void onSuccess(GenerateContentResponse result) {
                        String resultText = result.getText();
                        repply_message.set(resultText);

                        is_loaded_data.set(true);
                    }
                    @Override
                    public void onFailure(Throwable t) {
                        t.printStackTrace();
                        is_loaded_data.set(false);
                    }
                }, executor
        );
    }
    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}
