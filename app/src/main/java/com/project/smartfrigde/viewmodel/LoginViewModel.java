package com.project.smartfrigde.viewmodel;


import android.content.SharedPreferences;
import android.view.View;

import androidx.credentials.Credential;
import androidx.credentials.CustomCredential;
import androidx.credentials.GetCredentialResponse;
import androidx.credentials.exceptions.GetCredentialException;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableField;
import androidx.lifecycle.ViewModel;

import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.project.smartfrigde.data.remote.api.UserAPIService;
import com.project.smartfrigde.data.dto.response.ResponseObject;
import com.project.smartfrigde.data.dto.response.TokenResponse;
import com.project.smartfrigde.data.remote.api.retrofit.DeviceItemClient;
import com.project.smartfrigde.model.DeviceItem;
import com.project.smartfrigde.model.Food;
import com.project.smartfrigde.model.User;
import com.project.smartfrigde.utils.TokenManager;
import com.project.smartfrigde.utils.UserSecurePreferencesManager;
import com.project.smartfrigde.utils.Validation;

import java.lang.reflect.Type;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class LoginViewModel extends ViewModel {
    private static final Gson gson = new Gson();
    private ObservableField<String> user_name = new ObservableField<>();
    private ObservableField<String> password = new ObservableField<>();
     ObservableField<Integer> messageVisibility = new ObservableField<>(View.GONE);
    private ObservableField<Boolean> click_forgot_password = new ObservableField<>(false);

    private ObservableField<Boolean> click_oauth2 = new ObservableField<>(false);
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private ObservableField<TokenResponse> tokenLiveData = new ObservableField<>();
    private ObservableField<User> userLiveData = new ObservableField<>();
    private ObservableField<String> errorMessageLiveData = new ObservableField<>();
    private  ObservableField<Boolean> isLoadingLiveData = new ObservableField<>(false);
    public ObservableArrayList<DeviceItem> list_device_item = new ObservableArrayList<>();
    public ObservableField<Boolean> is_loadded_data = new ObservableField<>(false);

    private TokenResponse tokenResponse;
    private final TokenManager tokenManager  = new TokenManager();
    public void setErrorMessageLiveData(ObservableField<String> errorMessageLiveData) {
        this.errorMessageLiveData = errorMessageLiveData;
    }
    private  SharedPreferences sharedPreferences;
    public LoginViewModel(SharedPreferences sharedPreferences) {
        this.sharedPreferences= sharedPreferences;
    }
    public ObservableField<Integer> getMessageVisibility() {
        return messageVisibility;
    }

    public void setMessageVisibility(ObservableField<Integer> messageVisibility) {
        this.messageVisibility = messageVisibility;
    }

    public ObservableField<Boolean> getClick_forgot_password() {
        return click_forgot_password;
    }

    public void setClick_forgot_password(ObservableField<Boolean> click_forgot_password) {
        this.click_forgot_password = click_forgot_password;
    }
    public ObservableField<Boolean> getClick_oauth2() {
        return click_oauth2;
    }

    public void setClick_oauth2(ObservableField<Boolean> click_oauth2) {
        this.click_oauth2 = click_oauth2;

    }


    public void setIsLoadingLiveData(ObservableField<Boolean> isLoadingLiveData) {
        this.isLoadingLiveData = isLoadingLiveData;
    }
    public ObservableField<TokenResponse> getTokenLiveData() {
        return tokenLiveData;
    }

    public void setTokenLiveData(ObservableField<TokenResponse> tokenLiveData) {
        this.tokenLiveData = tokenLiveData;
    }

    public ObservableField<User> getUserLiveData() {
        return userLiveData;
    }

    public void setUserLiveData(ObservableField<User> userLiveData) {
        this.userLiveData = userLiveData;
    }

    public ObservableField<Boolean> getIs_loadded_data() {
        return is_loadded_data;
    }

    public void setIs_loadded_data(ObservableField<Boolean> is_loadded_data) {
        this.is_loadded_data = is_loadded_data;
    }

    public ObservableField<String> getErrorMessageLiveData() {
        return errorMessageLiveData;
    }

    public ObservableField<Boolean> getIsLoadingLiveData() {
        return isLoadingLiveData;
    }

    public void onclickLoginOauth2(){
        click_oauth2.set(true);
    }

    public ObservableArrayList<DeviceItem> getList_device_item() {
        return list_device_item;
    }

    public void setList_device_item(ObservableArrayList<DeviceItem> list_device_item) {
        this.list_device_item = list_device_item;
    }

    private void sendTokenOauth2(SharedPreferences.Editor editor,String token){
        tokenManager.saveOauth2AccessToken(token);
        UserAPIService.USER_API_SERVICE.sendToken()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseObject>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }
                    @Override
                    public void onNext(@NonNull ResponseObject responseObject) {

                        Gson gson = new Gson();
                        String json = gson.toJson(responseObject.getData());
                         tokenResponse = gson.fromJson(json, TokenResponse.class);
                         tokenLiveData.set(tokenResponse);
                         tokenManager.saveToken(tokenResponse.getAccess_token(),tokenResponse.getRefresh_token());
                        if (checkUserExist()){
                            fetchUser(editor);
                        }
                    }
                    @Override
                    public void onError(@NonNull Throwable e) {
                        errorMessageLiveData.set("Error: " + e.getMessage());
                    }
                    @Override
                    public void onComplete() {
                    }
                });
    }

    public void fetchUser(SharedPreferences.Editor editor){
        UserAPIService.USER_API_SERVICE.showProfile(Validation.extractUserID(tokenManager.getAccessToken()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseObject>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull ResponseObject responseObject) {
                        Gson gson = new Gson();
                        String userJson = gson.toJson(responseObject.getData());
                        User user = gson.fromJson(userJson,User.class);
                        userLiveData.set (user);
                        UserSecurePreferencesManager.saveUser(user);
                        String jsonDeviceItems = sharedPreferences.getString(Validation.KEY_DEVICE_ITEMS, null);
                        if (jsonDeviceItems != null){
                            getListDeviceItemByUserID(editor,UserSecurePreferencesManager.getUser().getUser_id());
                        }
                    }
                    @Override
                    public void onError(@NonNull Throwable e) {
                        errorMessageLiveData.set("Error: " + e.getMessage());
                    }
                    @Override
                    public void onComplete() {
                        errorMessageLiveData.set("Call api success !");
                    }
                });
    }
    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }
    public void getListDeviceItemByUserID(SharedPreferences.Editor editor,Long user_id){
        DeviceItemClient.getDeviceItemApiService().findDeviceItem(user_id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseObject>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@io.reactivex.rxjava3.annotations.NonNull ResponseObject responseObject) {
                        List<DeviceItem> list = gson.fromJson(new Gson().toJson(responseObject.getData()),
                                new TypeToken<List<DeviceItem>>(){}.getType()
                        );
                        list_device_item.addAll(list);
                        is_loadded_data.set(true);
                        String json = gson.toJson(list);
                        editor.putString(Validation.KEY_DEVICE_ITEMS, json);
                        editor.apply();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        is_loadded_data.set(true);
                    }

                    @Override
                    public void onComplete() {
                        is_loadded_data.set(false);
                    }
                });
    }
    public void setMessage(GetCredentialException e) {
        errorMessageLiveData.set(e.getMessage());
    }
    public boolean handleSignIn(SharedPreferences.Editor editor,GetCredentialResponse result) {
        Credential credential = result.getCredential();
            if (GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL.equals(credential.getType())) {
                GoogleIdTokenCredential googleIdTokenCredential = GoogleIdTokenCredential.createFrom(((CustomCredential) credential).getData());
                String token = googleIdTokenCredential.getIdToken();
                sendTokenOauth2(editor,token);
                return true;
            }
            return false;
    }
    public boolean checkUserExist(){
        if (UserSecurePreferencesManager.getUser() != null){
            return true;
        }
        return false;
    }

}
