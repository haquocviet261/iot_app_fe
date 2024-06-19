package com.project.smartfrigde.utils;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKey;

import com.google.gson.Gson;
import com.project.smartfrigde.model.User;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class UserSecurePreferencesManager {
    private static final String PREF_NAME = "user_secure_preferences";
    private static final String KEY_USER_DATA = "user_data";

    private static SharedPreferences sharedPreferences;

    public static void init(Context context) {
        try {
            MasterKey masterKey = new MasterKey.Builder(context)
                    .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                    .build();
            sharedPreferences = EncryptedSharedPreferences.create(context, PREF_NAME, masterKey,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );
        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveUser(User user) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(user);
        editor.putString(KEY_USER_DATA, json);
        editor.apply();
    }

    public static User getUser() {
        String json = sharedPreferences.getString(KEY_USER_DATA, null);
        if (json != null) {
            Gson gson = new Gson();
            return gson.fromJson(json, User.class);
        }
        return null;
    }
}
