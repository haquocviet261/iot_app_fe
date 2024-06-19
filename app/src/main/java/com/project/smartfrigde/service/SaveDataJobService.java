package com.project.smartfrigde.service;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

public class SaveDataJobService extends JobService {

    private static final String TAG = "SaveDataJobService";

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.d(TAG, "Job started");

        // Lưu trữ dữ liệu từ WebSocket vào SharedPreferences
        saveWebSocketDataToSharedPreferences(getApplicationContext());

        jobFinished(params, false);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.d(TAG, "Job stopped");
        return true;
    }

    private void saveWebSocketDataToSharedPreferences(Context context) {
        int totalConsumedWeight = 231;
        int eggsTaken = 1;

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("totalConsumedWeight", totalConsumedWeight);
        editor.putInt("eggsTaken", eggsTaken);
        editor.apply();

        Log.d(TAG, "Saved data to SharedPreferences");
    }
}
