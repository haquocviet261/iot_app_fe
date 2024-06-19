package com.project.smartfrigde.viewmodel;

import android.annotation.SuppressLint;

import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableField;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.project.smartfrigde.data.dto.request.DeviceItemRequest;
import com.project.smartfrigde.data.dto.request.FoodItemRequest;
import com.project.smartfrigde.data.dto.response.FoodItemResponse;
import com.project.smartfrigde.data.dto.response.ResponseObject;
import com.project.smartfrigde.data.remote.api.DeviceAPIService;
import com.project.smartfrigde.data.remote.api.retrofit.DeviceItemClient;
import com.project.smartfrigde.data.remote.api.retrofit.FoodItemClient;
import com.project.smartfrigde.model.DeviceItem;
import com.project.smartfrigde.utils.Validation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DetailDeviceViewModel extends ViewModel {
    private static final Gson gson = new Gson();
    private final CompositeDisposable disposable = new CompositeDisposable();
    private ObservableField<Boolean> is_loaded_data = new ObservableField<>(false);
    private ObservableField<Boolean> onclickBack = new ObservableField<>(false);
    private ObservableField<Boolean> onclickFood = new ObservableField<>(false);
    private ObservableField<Boolean> addTray = new ObservableField<>(false);
    private ObservableField<Boolean> deleteFooditem = new ObservableField<>(false);
    private ObservableField<String> repply_message = new ObservableField<>();
    private ObservableField<String> food_name = new ObservableField<>();
    private ObservableField<String> unit = new ObservableField<>();
    private ObservableArrayList<FoodItemResponse> list_food_item = new ObservableArrayList<>();
    private ObservableField<String> expired_date = new ObservableField<>();
    private ObservableField<String> device_name = new ObservableField<>();
    private ObservableField<Boolean> is_loadded_message = new ObservableField<>();

    public DetailDeviceViewModel() {
    }

    public ObservableArrayList<FoodItemResponse> getList_food_item() {
        return list_food_item;
    }

    public void setList_food_item(ObservableArrayList<FoodItemResponse> list_food_item) {
        this.list_food_item = list_food_item;
    }

    public ObservableField<Boolean> getDeleteFooditem() {
        return deleteFooditem;
    }

    public void setDeleteFooditem(ObservableField<Boolean> deleteFooditem) {
        this.deleteFooditem = deleteFooditem;
    }

    public ObservableField<Boolean> getIs_loadded_message() {
        return is_loadded_message;
    }

    public void setIs_loadded_message(ObservableField<Boolean> is_loadded_message) {
        this.is_loadded_message = is_loadded_message;
    }

    public ObservableField<Boolean> getIs_loaded_data() {
        return is_loaded_data;
    }

    public void setIs_loaded_data(ObservableField<Boolean> is_loaded_data) {
        this.is_loaded_data = is_loaded_data;
    }

    public ObservableField<Boolean> getOnclickBack() {
        return onclickBack;
    }

    public void setOnclickBack(ObservableField<Boolean> onclickBack) {
        this.onclickBack = onclickBack;
    }

    public ObservableField<Boolean> getAddTray() {
        return addTray;
    }

    public void setAddTray(ObservableField<Boolean> addTray) {
        this.addTray = addTray;
    }

    public ObservableField<String> getFood_name() {
        return food_name;
    }

    public void setFood_name(ObservableField<String> food_name) {
        this.food_name = food_name;
    }

    public ObservableField<String> getUnit() {
        return unit;
    }

    public void setUnit(ObservableField<String> unit) {
        this.unit = unit;
    }

    public ObservableField<String> getExpired_date() {
        return expired_date;
    }

    public void setExpired_date(ObservableField<String> expired_date) {
        this.expired_date = expired_date;
    }

    public ObservableField<Boolean> getOnclickFood() {
        return onclickFood;
    }

    public void setOnclickFood(ObservableField<Boolean> onclickFood) {
        this.onclickFood = onclickFood;
    }


    public ObservableField<String> getDevice_name() {
        return device_name;
    }

    public void setDevice_name(ObservableField<String> device_name) {
        this.device_name = device_name;
    }

    public ObservableField<String> getRepply_message() {
        return repply_message;
    }

    public void setRepply_message(ObservableField<String> repply_message) {
        this.repply_message = repply_message;
    }
    public void onclickBack() {
        onclickBack.set(true);
    }
    public void addFood() {
        onclickFood.set(true);
    }
    public void onClickAddTray() {
        addTray.set(true);
    }
    public void onClickDeleteFoodItem() {
        deleteFooditem.set(true);
    }
    public void addFoodItem(Long device_item_id,Long food_id) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = formatter.parse(expired_date.get());
        FoodItemRequest foodItemRequest = new FoodItemRequest(food_name.get(),new Date(),Integer.parseInt(unit.get()),date,device_item_id,food_id);
        FoodItemClient.getFoodItemAPIService().addFoodItem(foodItemRequest);
    }
    public void getFoodItemByDeviceItemID(Long device_item_id){
        FoodItemClient.getFoodItemAPIService().getFoodItemByDeviceItemID(device_item_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseObject>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull ResponseObject responseObject) {
                        List<FoodItemResponse> list = gson.fromJson(new Gson().toJson(responseObject.getData()),
                                new TypeToken<List<FoodItemResponse>>(){}.getType()
                        );
                        list_food_item.addAll(list);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        is_loaded_data.set(true);
                    }

                    @Override
                    public void onComplete() {
                        is_loaded_data.set(false);
                    }
                });
    }
    public void deleteFoodItemExpired(Long food_item_id){
        FoodItemClient.getFoodItemAPIService().deleteFoodFoodItemByID(food_item_id);
    }
    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}
