package com.project.smartfrigde.viewmodel;

import androidx.databinding.ObservableField;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import io.reactivex.rxjava3.disposables.CompositeDisposable;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

public class DashBoardViewModel extends ViewModel {
    private WebSocket webSocket;
    public static final String TEMPURATURE_TAMPLATE = "°C";

    private static final Gson gson = new Gson();
    private final CompositeDisposable disposable = new CompositeDisposable();
    private ObservableField<Double> tempuratureObservable = new ObservableField<>(0.0);
    private ObservableField<Double> humidityObservable = new ObservableField<>(0.0);
    private ObservableField<Boolean> isNotification = new ObservableField<>(false);
    private ObservableField<Boolean> isStatistics = new ObservableField<>(false);
    private ObservableField<Boolean> isDetailUser = new ObservableField<>(false);
    private ObservableField<Boolean> isSetting = new ObservableField<>(false);
    private ObservableField<Boolean> isUser = new ObservableField<>(false);
    private ObservableField<Boolean> isFood = new ObservableField<>(false);

    public ObservableField<Boolean> getIsDetailUser() {
        return isDetailUser;
    }

    public void setIsDetailUser(ObservableField<Boolean> isDetailUser) {
        this.isDetailUser = isDetailUser;
    }

    public ObservableField<Boolean> getIsSetting() {
        return isSetting;
    }

    public void setIsSetting(ObservableField<Boolean> isSetting) {
        this.isSetting = isSetting;
    }

    public ObservableField<Double> getTempuratureObservable() {
        return tempuratureObservable;
    }

    public void setTempuratureObservable(ObservableField<Double> tempurature) {
        this.tempuratureObservable = tempurature;
    }

    public ObservableField<Double> getHumidityObservable() {
        return humidityObservable;
    }

    public void setHumidityObservable(ObservableField<Double> humidity) {
        this.humidityObservable = humidity;
    }

    public ObservableField<Boolean> getIsNotification() {
        return isNotification;
    }

    public void setIsNotification(ObservableField<Boolean> isNotification) {
        this.isNotification = isNotification;
    }

    public ObservableField<Boolean> getIsStatistics() {
        return isStatistics;
    }

    public void setIsStatistics(ObservableField<Boolean> isStatistics) {
        this.isStatistics = isStatistics;
    }

    public ObservableField<Boolean> getIsUser() {
        return isUser;
    }

    public void setIsUser(ObservableField<Boolean> isUser) {
        this.isUser = isUser;
    }

    public ObservableField<Boolean> getIsFood() {
        return isFood;
    }

    public void goToFood(){
        isFood.set(true);
    }
    public void goDetailUser(){
        isDetailUser.set(true);
    }
    public void goToSetting(){
        isSetting.set(true);
    }
    public void goToNotification(){
        isNotification.set(true);
    }

    public void goToUser(){
        isUser.set(true);
    }

    public void goToStatistic(){
        isStatistics.set(true);
    }

    public void setIsFood(ObservableField<Boolean> isFood) {
        this.isFood = isFood;
    }
    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }

    public void connect() {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder().url("ws://192.168.100.12:9999/handle").build();
        webSocket = client.newWebSocket(request, new WebSocketListener() {
            @Override
            public void onOpen(WebSocket webSocket, Response response) {
                System.out.println("Connected to WebSocket");
            }

            @Override
            public void onMessage(WebSocket webSocket, String text) {
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(text);
                    double temperature = jsonObject.getDouble("temperature");
                    double humidity = jsonObject.getDouble("humidity");
                    tempuratureObservable.set(temperature);
                    humidityObservable.set(humidity);
                    System.out.println("Temperature: " + temperature);
                    System.out.println("Humidity: " + humidity);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
            @Override
            public void onClosed(WebSocket webSocket, int code, String reason) {
                System.out.println("WebSocket closed: " + reason);
            }
            @Override
            public void onFailure(WebSocket webSocket, Throwable t, Response response) {
                t.printStackTrace();
            }
        });
    }
}
