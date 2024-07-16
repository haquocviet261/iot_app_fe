package com.project.smartfrigde.viewmodel;

import android.annotation.SuppressLint;

import androidx.databinding.ObservableField;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.project.smartfrigde.utils.TokenManager;
import com.project.smartfrigde.utils.Validation;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;
import ua.naiksoftware.stomp.Stomp;
import ua.naiksoftware.stomp.StompClient;
import ua.naiksoftware.stomp.dto.StompHeader;
import ua.naiksoftware.stomp.dto.StompMessage;

public class WebSocketViewModel extends ViewModel {
    private StompClient stompClient;
    private io.reactivex.disposables.Disposable lifecycleDisposable;
    private ObservableField<Boolean> is_connected_websocket = new ObservableField<>(false);
    private static final Gson gson = new Gson();
    @SuppressLint("CheckResult")
    public WebSocketViewModel() {
        stompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, "ws://192.168.11.3:9999/ws");
        TokenManager tokenManager = new TokenManager();
        List<StompHeader> headers = new ArrayList<>();
        headers.add(new StompHeader("X-Authorization", "Bearer " + tokenManager.getAccessToken()));
        stompClient.connect(headers);
    }
    public void reconnect() {
        TokenManager tokenManager = new TokenManager();
        if (stompClient != null && !stompClient.isConnected()) {
            List<StompHeader> headers = new ArrayList<>();
            headers.add(new StompHeader("X-Authorization", "Bearer " + tokenManager.getAccessToken()));
            stompClient.connect(headers);
        }
    }
    public StompClient getstompClient() {
        return stompClient;
    }
    public Flowable<StompMessage> subscribeToTopic(String topic) {
        return Flowable.defer(() -> stompClient.topic(topic));
    }

    public Disposable getLifecycleDisposable() {
        return lifecycleDisposable;
    }

    public void setLifecycleDisposable(Disposable lifecycleDisposable) {
        this.lifecycleDisposable = lifecycleDisposable;
    }

    public ObservableField<Boolean> getIs_connected_websocket() {
        return is_connected_websocket;
    }

    public void setIs_connected_websocket(ObservableField<Boolean> is_connected_websocket) {
        this.is_connected_websocket = is_connected_websocket;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (lifecycleDisposable != null) {
            lifecycleDisposable.dispose();
        }
    }
}
