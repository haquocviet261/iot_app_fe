package com.project.smartfrigde.service;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.gson.Gson;
import com.project.smartfrigde.R;
import com.project.smartfrigde.model.FoodConsum;
import com.project.smartfrigde.model.User;
import com.project.smartfrigde.utils.UserSecurePreferencesManager;
import com.project.smartfrigde.utils.Validation;
import com.project.smartfrigde.view.DetailDeviceActivity;
import com.project.smartfrigde.view.fragment.MealFragment;
import com.project.smartfrigde.viewmodel.WebSocketViewModel;

import java.util.NoSuchElementException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import ua.naiksoftware.stomp.dto.StompMessage;

public class WebSocketService extends Service {
    private static final String PREF_NAME = "MyPreferences";
    private static final Gson gson = new Gson();
    private static final String CHANNEL_ID = "Channel_ID";
    private static final String CHANNEL_NAME = "Channel_Name";
    private static final String TAG = "WebSocketService";
    private boolean isSaveFoodComsump = true;
    private boolean isShowFoodExpired = true;

    private User user = UserSecurePreferencesManager.getUser();
    WebSocketViewModel webSocketViewModel;
    private SharedPreferences prefs;

    public WebSocketService() {
        webSocketViewModel = new WebSocketViewModel();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new LocalBinder();
    }

    @SuppressLint("CheckResult")
    @Override
    public void onCreate() {
        super.onCreate();
        if (!areNotificationsEnabled()) {
            redirectToNotificationSettings();
        }
        createNotificationChannel();
        prefs = this.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        connectWebSocket();
        webSocketViewModel.getstompClient().lifecycle().subscribe(lifecycleEvent -> {
            switch (lifecycleEvent.getType()) {
                case OPENED:
                    Log.d("WS", "Stomp connection opened");
                    subscribeToTopics();
                    break;

                case ERROR:
                    Log.e("WS", "Error", lifecycleEvent.getException());
                    break;

                case CLOSED:
                    Log.d("WS", "Stomp connection closed", lifecycleEvent.getException());
                    webSocketViewModel.reconnect();
                    break;
                case FAILED_SERVER_HEARTBEAT:
                    Log.d("STOMP", "Stomp failed server heartbeat");
                    break;
            }
        }, throwable -> {
            Log.e("STOMP", "Error in lifecycle", throwable);
        });
        createNotificationChannel();
    }

    private void connectWebSocket() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(Validation.WEB_SOCKET_URL).build();
        WebSocketListener listener = new WebSocketListener();
        client.newWebSocket(request, listener);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (webSocketViewModel.getLifecycleDisposable() != null && !webSocketViewModel.getLifecycleDisposable().isDisposed()) {
            webSocketViewModel.getLifecycleDisposable().dispose();
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
        public void onMessage(@NonNull okhttp3.WebSocket webSocket, @NonNull String text) {
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
        webSocketViewModel.getstompClient().topic("/topic/expiration")
                .subscribe(
                        this::pushNotificationForFoodExpired,
                        throwable -> Log.e(TAG, "Error subscribing to STOMP topic", throwable)
                );
        webSocketViewModel.getstompClient().topic("/user/" + user.getUser_id() + "/topic/expiration")
                .subscribe(
                        this::pushNotificationForFoodExpired,
                        throwable -> Log.e(TAG, "Error subscribing to STOMP topic", throwable)
                );
        webSocketViewModel.getstompClient().topic("/topic/lunch")
                .subscribe(
                        this::pushNotificationForLunch,
                        throwable -> Log.e(TAG, "Error subscribing to STOMP topic", throwable)
                );
        webSocketViewModel.getstompClient().topic("/topic/dinner")
                .subscribe(
                        this::pushNotificationForDinner,
                        throwable -> Log.e(TAG, "Error subscribing to STOMP topic", throwable)
                );
    }

    private void saveFoodConsumed(String payload) {
        if (isSaveFoodComsump) {
            Log.d("WebSocket Message", payload);
            FoodConsum foodConsum = gson.fromJson(payload, FoodConsum.class);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString(Validation.KEY_FOOD_CONSUMED, gson.toJson(foodConsum));
            editor.apply();
            isSaveFoodComsump = false;
        }
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_HIGH
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            if (manager != null) {
                manager.createNotificationChannel(channel);
            }
        }
    }

    private boolean areNotificationsEnabled() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager manager = getSystemService(NotificationManager.class);
            return manager != null && manager.areNotificationsEnabled();
        } else {
            return NotificationManagerCompat.from(this).areNotificationsEnabled();
        }
    }

    private void pushNotificationForFoodExpired(StompMessage stompMessage) {
        Intent intent = new Intent(this, DetailDeviceActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Smart Fridge")
                .setContentText(stompMessage.getPayload())
                .setSmallIcon(R.drawable.notifications)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build();

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            redirectToNotificationSettings();
        }
        notificationManager.notify(1, notification);
    }
    private void pushNotificationForLunch(StompMessage stompMessage) {
        Intent intent = new Intent(this, MealFragment.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Smart Fridge")
                .setContentText(stompMessage.getPayload())
                .setSmallIcon(R.drawable.notifications)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build();

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            redirectToNotificationSettings();
        }
        notificationManager.notify(3, notification);
    }
    private void pushNotificationForDinner(StompMessage stompMessage) {
        Intent intent = new Intent(this, MealFragment.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Smart Fridge")
                .setContentText(stompMessage.getPayload())
                .setSmallIcon(R.drawable.notifications)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build();

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            redirectToNotificationSettings();

        }
        notificationManager.notify(2, notification);
    }

    private void redirectToNotificationSettings() {
        Intent intent = new Intent();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            intent.setAction(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
            intent.putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName());
        } else {
            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.setData(Uri.parse("package:" + getPackageName()));
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
