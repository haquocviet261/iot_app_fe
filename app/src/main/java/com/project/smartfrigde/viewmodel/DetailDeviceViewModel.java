package com.project.smartfrigde.viewmodel;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;

import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableField;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.project.smartfrigde.data.dto.request.FoodItemRequest;
import com.project.smartfrigde.data.dto.response.ResponseObject;
import com.project.smartfrigde.data.remote.api.retrofit.FoodItemClient;
import com.project.smartfrigde.model.DeviceItem;
import com.project.smartfrigde.model.FoodItem;
import com.project.smartfrigde.utils.Validation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DetailDeviceViewModel extends ViewModel {
    private static final Gson gson = new Gson();
    private final CompositeDisposable disposable = new CompositeDisposable();
    private ObservableField<Boolean> is_loaded_data = new ObservableField<>(false);
    private ObservableField<Boolean> onclickBack = new ObservableField<>(false);
    private ObservableArrayList<FoodItemRequest> listFoodItem = new ObservableArrayList<>();
    private ObservableField<Boolean> onclickFood = new ObservableField<>(false);
    private ObservableField<Boolean> onClickDeleteTrayExpired = new ObservableField<>(false);
    private ObservableField<Boolean> deleteFooditem = new ObservableField<>(false);
    private ObservableField<Boolean> isDeleteSuccessful = new ObservableField<>(false);
    private ObservableField<Boolean> isLoaddedListFoodItem = new ObservableField<>(false);
    private ObservableField<Boolean> isAddsuccessful = new ObservableField<>(false);
    private ObservableField<String> repply_message = new ObservableField<>();
    private ObservableField<String> food_name = new ObservableField<>();
    private ObservableField<String> unit = new ObservableField<>();
    private ObservableField<String> error_message = new ObservableField<>();
    private Long food_id ;
    private Long device_item_id;

    public CompositeDisposable getDisposable() {
        return disposable;
    }

    public ObservableField<Boolean> getIsLoaddedListFoodItem() {
        return isLoaddedListFoodItem;
    }

    public void setIsLoaddedListFoodItem(ObservableField<Boolean> isLoaddedListFoodItem) {
        this.isLoaddedListFoodItem = isLoaddedListFoodItem;
    }

    public ObservableArrayList<FoodItemRequest> getListFoodItem() {
        return listFoodItem;
    }

    public void setListFoodItem(ObservableArrayList<FoodItemRequest> listFoodItem) {
        this.listFoodItem = listFoodItem;
    }

    private ObservableField<String> expired_date = new ObservableField<>();
    private ObservableField<String> device_name = new ObservableField<>();

    public DetailDeviceViewModel(String device_name) {
        this.device_name.set(device_name);
    }

    public Long getFood_id() {
        return food_id;
    }

    public Long getDevice_item_id() {
        return device_item_id;
    }

    public void setDevice_item_id(Long device_item_id) {
        this.device_item_id = device_item_id;
    }

    public void setFood_id(Long food_id) {
        this.food_id = food_id;
    }

    public ObservableField<Boolean> getDeleteFooditem() {
        return deleteFooditem;
    }

    public void setDeleteFooditem(ObservableField<Boolean> deleteFooditem) {
        this.deleteFooditem = deleteFooditem;
    }

    public ObservableField<Boolean> getIs_loaded_data() {
        return is_loaded_data;
    }

    public void setIs_loaded_data(ObservableField<Boolean> is_loaded_data) {
        this.is_loaded_data = is_loaded_data;
    }

    public ObservableField<Boolean> getIsDeleteSuccessful() {
        return isDeleteSuccessful;
    }

    public void setIsDeleteSuccessful(ObservableField<Boolean> isDeleteSuccessful) {
        this.isDeleteSuccessful = isDeleteSuccessful;
    }

    public ObservableField<Boolean> getIsAddsuccessful() {
        return isAddsuccessful;
    }

    public void setIsAddsuccessful(ObservableField<Boolean> isAddsuccessful) {
        this.isAddsuccessful = isAddsuccessful;
    }

    public ObservableField<Boolean> getOnclickBack() {
        return onclickBack;
    }

    public void setOnclickBack(ObservableField<Boolean> onclickBack) {
        this.onclickBack = onclickBack;
    }

    public ObservableField<Boolean> getOnClickDeleteTrayExpired() {
        return onClickDeleteTrayExpired;
    }

    public void setOnClickDeleteTrayExpired(ObservableField<Boolean> onClickDeleteTrayExpired) {
        this.onClickDeleteTrayExpired = onClickDeleteTrayExpired;
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

    public ObservableField<String> getError_message() {
        return error_message;
    }

    public void setError_message(ObservableField<String> error_message) {
        this.error_message = error_message;
    }

    public void onclickBack() {
        onclickBack.set(true);
    }
    public void addFood() {
        onclickFood.set(true);
    }
    public void onClickDeleteTrayExpired() {
        is_loaded_data.set(true);
    }
    public void onClickDeleteFoodItem() {
        deleteFooditem.set(true);
    }
    public void addFoodItem(List<FoodItem>list) throws ParseException {
        for (int i = 0; i < list.size(); i++) {
            @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            if (!checkValidInput(expired_date.get())){
                error_message.set("is number only");
            }else {
                int days = Integer.parseInt(expired_date.get());
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.DAY_OF_YEAR, days);
                String string_date = formatter.format(calendar.getTime());
                Date date = formatter.parse(string_date);
                FoodItemRequest foodItemRequest = new FoodItemRequest(list.get(i).getFood_item_name(),new Date(),Integer.parseInt(list.get(i).getUnit()),date,device_item_id,food_id);
                FoodItemClient.getFoodItemAPIService().addFoodItem(foodItemRequest).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ResponseObject>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {
                                disposable.add(d);
                            }

                            @Override
                            public void onNext(@NonNull ResponseObject responseObject) {
                                isDeleteSuccessful.set(true);
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
                                isAddsuccessful.set(false);
                            }

                            @Override
                            public void onComplete() {
                                isAddsuccessful.set(true);
                            }
                        });
            }
        }

    }
    private boolean checkValidInput(String date){
        for (int i = 0; i < expired_date.get().length(); i++) {
            if (!Character.isDigit(expired_date.get().charAt(i)) || expired_date.get() == null){
                return false;
            }
        }
        return true;

    }
    public void getAllFoodItem(SharedPreferences.Editor editor,Long device_item_id){
        FoodItemClient.getFoodItemAPIService().getFoodItemByDeviceItemID(device_item_id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseObject>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull ResponseObject responseObject) {
                        List<FoodItemRequest> list = gson.fromJson(new Gson().toJson(responseObject.getData()),
                                new TypeToken<List<FoodItemRequest>>(){}.getType()
                        );
                        listFoodItem.addAll(list);

                        String json = gson.toJson(list);
                        editor.putString(Validation.KEY_FOOD_ITEMS, json);
                        editor.apply();
                        isLoaddedListFoodItem.set(true);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        isLoaddedListFoodItem.set(false);
                    }

                    @Override
                    public void onComplete() {
                        isLoaddedListFoodItem.set(true);
                    }
                });
    }
    public void deleteFoodItemExpired(Long food_item_id){
        FoodItemClient.getFoodItemAPIService().deleteFoodFoodItemByID(food_item_id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseObject>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull ResponseObject responseObject) {
                        isDeleteSuccessful.set(true);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        isDeleteSuccessful.set(false);
                    }

                    @Override
                    public void onComplete() {
                        isDeleteSuccessful.set(true);
                    }
                });
    }
    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}
