package com.project.smartfrigde.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.Calendar;

public class CalculateCaloriesScheduler extends BroadcastReceiver {

    private static final int REQUEST_CODE = 12345;

    @Override
    public void onReceive(Context context, Intent intent) {
        // Thực hiện tính toán lượng calories từ SharedPreferences
        calculateCalories(context);
    }

    public static void schedule(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, CalculateCaloriesScheduler.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Lên lịch để chạy vào 22 giờ tối
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 22);
        calendar.set(Calendar.MINUTE, 0);

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, pendingIntent);
    }

    private static void calculateCalories(Context context) {
        // Đoạn mã để tính toán lượng calories từ SharedPreferences
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        int totalConsumedWeight = prefs.getInt("totalConsumedWeight", 0);
        int eggsTaken = prefs.getInt("eggsTaken", 0);

        // Thực hiện tính toán lượng calories ở đây
        // Ví dụ:
        int calories = totalConsumedWeight * 10 + eggsTaken * 50;

        // Lưu kết quả vào SharedPreferences hoặc làm bất kỳ xử lý nào khác cần thiết
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("calories", calories);
        editor.apply();
    }
}
