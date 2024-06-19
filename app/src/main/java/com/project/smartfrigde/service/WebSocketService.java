package com.project.smartfrigde.service;


import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.databinding.ObservableField;

import com.google.gson.Gson;
import com.project.smartfrigde.R;
import com.project.smartfrigde.model.User;
import com.project.smartfrigde.utils.TokenManager;
import com.project.smartfrigde.utils.UserSecurePreferencesManager;
import com.project.smartfrigde.utils.Validation;
import com.project.smartfrigde.view.DashboardActivity;
import com.project.smartfrigde.view.DetailDeviceActivity;


import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;
import ua.naiksoftware.stomp.StompClient;
import ua.naiksoftware.stomp.dto.StompHeader;
import ua.naiksoftware.stomp.dto.StompMessage;

public class WebSocketService extends Service {
    private static final String PREF_NAME = "MyPreferences";
    private static final String KEY_FOOD_CONSUMMED = "food_consumed";
    private static final Gson gson = new Gson();
    private static final String CHANNEL_ID = "Channel_ID";
    private static final String CHANNEL_NAME = "Channel_Name";
    private static final int NOTIFICATION_ID = 101;
    private static final String TAG = "WebSocketService";
    private StompClient stompClient;
    User user = UserSecurePreferencesManager.getUser();
    private io.reactivex.disposables.Disposable lifecycleDisposable;
    private Context context;
    public WebSocketService(Context context) {
        this.context = context;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new LocalBinder();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        connectWebSocket();
        connectStomp();
    }

    private void connectWebSocket() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(Validation.WEB_SOCKET_URL).build();
        WebSocketListener listener = new WebSocketListener();
        client.newWebSocket(request, listener);
    }

    private void connectStomp() {
        TokenManager tokenManager = new TokenManager();
        List<StompHeader> headers = new ArrayList<>();
        headers.add(new StompHeader("Authorization", "Bearer " + tokenManager.getAccessToken()));
        stompClient.connect(headers);

        lifecycleDisposable = stompClient.lifecycle().subscribe(lifecycleEvent -> {
            switch (lifecycleEvent.getType()) {
                case OPENED:
                    Log.d(TAG, "STOMP connected");
                    subscribeToTopics();
                    break;

                case ERROR:
                    Log.d(TAG, "STOMP error");
                case CLOSED:
                    Log.d(TAG, "STOMP closed");
                    reconnect();
                    break;
            }
        });
    }
    public void reconnect() {
        TokenManager tokenManager = new TokenManager();
        if (stompClient != null && !stompClient.isConnected()) {
            List<StompHeader> headers = new ArrayList<>();
            headers.add(new StompHeader("Authorization", "Bearer " + tokenManager.getAccessToken()));
            stompClient.connect(headers);
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        disconnectWebSocket();
        disconnectStomp();
        lifecycleDisposable.dispose();
    }

    private void disconnectWebSocket() {

    }

    private void disconnectStomp() {
        if (stompClient != null) {
            stompClient.disconnect();
        }
    }

    public class LocalBinder extends Binder {
        public WebSocketService getService() {
            return WebSocketService.this;
        }
    }
    private class WebSocketListener extends okhttp3.WebSocketListener {
        @Override
        public void onOpen(okhttp3.WebSocket webSocket, okhttp3.Response response) {
            Log.d(TAG, "WebSocket connected");
        }

        @Override
        public void onClosed(okhttp3.WebSocket webSocket, int code, String reason) {
            Log.d(TAG, "WebSocket closed: " + reason);
        }
        @Override
        public void onMessage(@NonNull WebSocket webSocket, @NonNull String text) {
            super.onMessage(webSocket, text);
            Log.d(TAG, "Received message: " + text);
            saveFoodConsumed(text);
        }
        @Override
        public void onFailure(okhttp3.WebSocket webSocket, Throwable t, okhttp3.Response response) {
            Log.e(TAG, "WebSocket failure", t);
        }
    }
    @SuppressLint("CheckResult")
    private void subscribeToTopics() {

        stompClient.topic("/user/" + user.getUser_id() + "/topic/expiration")
                .subscribeOn(io.reactivex.schedulers.Schedulers.io())
                .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                .subscribe(
                        this::handleStompMessage
                );
    }
    private void handleStompMessage(StompMessage stompMessage) {
        try {
            String payload = stompMessage.getPayload();
            Log.d("STOMP Message", payload);
            getNotification(payload);
        } catch (NoSuchElementException e) {
            Log.e("WebSocketError", "Error parsing message (NoSuchElementException)", e);
            Log.e("WebSocketError", "Raw message: " + stompMessage.getPayload());
        } catch (Exception e) {
            Log.e("WebSocketError", "Error parsing message", e);
        }
    }
    private void saveFoodConsumed(String payload) {
        Log.d("WebSocket Message", payload);
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        String json = gson.toJson(payload);
        editor.putString(KEY_FOOD_CONSUMMED,json);
        editor.apply();
    }
    private Notification getNotification(String message) {
        Intent intent = new Intent(context, DetailDeviceActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        return new NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentTitle("Smart Fridge")
                .setContentText(message)
                .setSmallIcon(R.drawable.notifications)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build();
    }
}
