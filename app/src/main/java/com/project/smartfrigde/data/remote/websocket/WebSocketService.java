package com.project.smartfrigde.data.remote.websocket;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.google.gson.Gson;
import com.project.smartfrigde.R;
import com.project.smartfrigde.view.DashboardActivity;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

public class WebSocketService extends Service {
    private WebSocket webSocket;

    @Override
    public void onCreate() {
        super.onCreate();
        startWebSocketConnection();
    }

    private void startWebSocketConnection() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("ws://haquocviet261.click:9999/handle").build();
        webSocket = client.newWebSocket(request, new WebSocketListener() {
            @Override
            public void onOpen(WebSocket webSocket, Response response) {
                // Handle connection opened
            }

            @Override
            public void onMessage(WebSocket webSocket, String text) {
                // Handle incoming messages
            }

            @Override
            public void onClosing(WebSocket webSocket, int code, String reason) {
                // Handle connection closing
            }

            @Override
            public void onClosed(WebSocket webSocket, int code, String reason) {
                // Handle connection closed
            }

            @Override
            public void onFailure(WebSocket webSocket, Throwable t, Response response) {
                // Handle connection failure
            }
        });
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (webSocket != null) {
            webSocket.close(1000, "Service destroyed");
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
