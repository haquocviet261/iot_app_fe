package com.project.smartfrigde.viewmodel;

import android.view.View;

import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableField;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.project.smartfrigde.data.dto.response.ResponseObject;
import com.project.smartfrigde.data.remote.api.FoodAPIService;
import com.project.smartfrigde.data.remote.api.retrofit.DeviceItemClient;
import com.project.smartfrigde.data.remote.api.retrofit.FoodClient;
import com.project.smartfrigde.model.DeviceItem;
import com.project.smartfrigde.model.Food;
import com.project.smartfrigde.utils.TokenManager;
import com.project.smartfrigde.utils.Validation;

import java.io.Closeable;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import ua.naiksoftware.stomp.Stomp;
import ua.naiksoftware.stomp.StompClient;
import ua.naiksoftware.stomp.dto.StompHeader;
import ua.naiksoftware.stomp.dto.StompMessage;

public class HomeViewmodel extends ViewModel {
    private static final Gson gson = new Gson();
    private ObservableField<Integer> is_device_exist = new ObservableField<>(View.GONE);
    private ObservableField<Boolean> add_device = new ObservableField<>(false);
    private ObservableField<Boolean> chat_now = new ObservableField<>(false);
    private ObservableField<Boolean> is_notification = new ObservableField<>(false);
    private CompositeDisposable disposable = new CompositeDisposable();
    private MutableLiveData<Integer> selectedPage = new MutableLiveData<>();
    private ObservableField<Boolean> isLoaddedData = new ObservableField<>(false);
    private ObservableField<Boolean> isDetailDevice = new ObservableField<>(false);
    private ObservableArrayList<Food> foods = new ObservableArrayList<Food>();

    public ObservableField<Boolean> getIsDetailDevice() {
        return isDetailDevice;
    }

    public void setIsDetailDevice(ObservableField<Boolean> isDetailDevice) {
        this.isDetailDevice = isDetailDevice;
    }

    private io.reactivex.disposables.Disposable lifecycleDisposable;
    public HomeViewmodel() {
    }

    public ObservableField<Boolean> getIsLoaddedData() {
        return isLoaddedData;
    }

    public void setIsLoaddedData(ObservableField<Boolean> isLoaddedData) {
        this.isLoaddedData = isLoaddedData;
    }

    public ObservableField<Integer> getIs_device_exist() {
        return is_device_exist;
    }
    public void addDvice(){
        add_device.set(true);
    }
    public void chatNow(){
        chat_now.set(true);
    }

    public ObservableField<Boolean> getChat_now() {
        return chat_now;
    }

    public void setChat_now(ObservableField<Boolean> chat_now) {
        this.chat_now = chat_now;
    }

    public void viewNotification(){
        is_notification.set(true);
    }
    public void setIs_device_exist(ObservableField<Integer> is_device_exist) {
        this.is_device_exist = is_device_exist;
    }

    public ObservableArrayList<Food> getFoods() {
        return foods;
    }

    public void setFoods(ObservableArrayList<Food> foods) {
        this.foods = foods;
    }

    public ObservableField<Boolean> getAdd_device() {
        return add_device;
    }

    public void setAdd_device(ObservableField<Boolean> add_device) {
        this.add_device = add_device;
    }

    public ObservableField<Boolean> getIs_notification() {
        return is_notification;
    }

    public void setIs_notification(ObservableField<Boolean> is_notification) {
        this.is_notification = is_notification;
    }
    public LiveData<Integer> getSelectedPage() {
        return selectedPage;
    }
    public void setSelectedPage(int page) {
        selectedPage.setValue(page);
    }
    public void getAllFood(){
        FoodClient.getFoodApiService().getAllFood()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseObject>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull ResponseObject responseObject) {
                        List<Food> list = gson.fromJson(new Gson().toJson(responseObject.getData()),
                                new TypeToken<List<Food>>(){}.getType()
                        );
                        foods.addAll(list);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        isLoaddedData.set(false);
                    }

                    @Override
                    public void onComplete() {
                        isLoaddedData.set(true);
                    }
                });
    }
    public void isDetailDevice(){
        isDetailDevice.set(true);
    }
    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();

    }

}
