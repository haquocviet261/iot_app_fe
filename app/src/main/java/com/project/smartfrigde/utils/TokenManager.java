package com.project.smartfrigde.utils;

import android.content.SharedPreferences;

public class TokenManager {
    private static final String PREF_NAME = "token_pref";
    private static final String KEY_ACCESS_TOKEN = "access_token";
    private static final String KEY_ACCESS_OAUTH2_TOKEN = "access_oauth2_token";
    private static final String KEY_REFRESH_TOKEN = "refresh_token";
    private static final String KEY_USER_NAME = "user_name";
    private SharedPreferences sharedPreferences;
    public TokenManager() {
        this.sharedPreferences = SecurePreferencesManager.getSharedPreferences();
    }

    public void saveToken(String accessToken, String refreshToken) {
        SharedPreferences.Editor editor = SecurePreferencesManager.getSharedPreferences().edit();
        editor.putString(KEY_ACCESS_TOKEN, accessToken);
        editor.putString(KEY_REFRESH_TOKEN, refreshToken);
        editor.putLong(KEY_USER_NAME,Validation.extractUserID(accessToken));
        editor.apply();
    }
    public void saveOauth2AccessToken(String accessToken){
        SharedPreferences.Editor editor = SecurePreferencesManager.getSharedPreferences().edit();
        editor.putString(KEY_ACCESS_OAUTH2_TOKEN, accessToken);
        editor.apply();
    }

    public String getAccessToken() {
        return SecurePreferencesManager.getSharedPreferences().getString(KEY_ACCESS_TOKEN, null);
    }
    public String getAccessOauth2Token() {
        return SecurePreferencesManager.getSharedPreferences().getString(KEY_ACCESS_OAUTH2_TOKEN, null);
    }

    public String getRefreshToken() {
        return SecurePreferencesManager.getSharedPreferences().getString(KEY_REFRESH_TOKEN, null);
    }

    public void clearToken() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(KEY_ACCESS_TOKEN);
        editor.remove(KEY_REFRESH_TOKEN);
        editor.apply();
    }
}
