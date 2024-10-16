package com.project.smartfrigde.viewmodel;

import android.content.SharedPreferences;
import android.view.View;

import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableField;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.project.smartfrigde.data.dto.request.DeviceItemRequest;
import com.project.smartfrigde.data.dto.request.DeviceRequest;
import com.project.smartfrigde.data.dto.response.ResponseObject;
import com.project.smartfrigde.data.remote.api.DeviceAPIService;
import com.project.smartfrigde.data.remote.api.retrofit.DeviceItemClient;
import com.project.smartfrigde.model.DeviceItem;
import com.project.smartfrigde.utils.Validation;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class AddDeviceViewModel extends ViewModel {
    private static final Gson gson = new Gson();
    Human bao = new Human();
    private CompositeDisposable disposable = new CompositeDisposable();
    private ObservableField<Integer> is_scan = new ObservableField<>(View.VISIBLE);
    private ObservableField<Boolean> is_back = new ObservableField<>(Boolean.FALSE);
    private ObservableField<Boolean> is_add_device_item = new ObservableField<>(Boolean.FALSE);
    private ObservableField<Boolean> is_loadded_data = new ObservableField<>(Boolean.FALSE);
    public ObservableArrayList<DeviceRequest> list_device = new ObservableArrayList<>();

    public ObservableArrayList<DeviceItem> list_device_item = new ObservableArrayList<>();
    private Long user_id;

    public ObservableField<Boolean> getIs_add_device_item() {
        return is_add_device_item;
    }

    public void setIs_add_device_item(ObservableField<Boolean> is_add_device_item) {
        this.is_add_device_item = is_add_device_item;
    }

    public ObservableField<Boolean> getIs_loadded_data() {
        return is_loadded_data;
    }

    public void setIs_loadded_data(ObservableField<Boolean> is_loadded_data) {
        this.is_loadded_data = is_loadded_data;
    }

    public ObservableArrayList<DeviceRequest> getList_device() {
        return list_device;
    }

    public void setList_device(ObservableArrayList<DeviceRequest> list_device) {
        this.list_device = list_device;
    }

    private Long device_id;
    public AddDeviceViewModel(Long user_id) {
        this.user_id = user_id;
    }

    public ObservableField<Integer> getIs_scan() {
        return is_scan;
    }

    public ObservableField<Boolean> getIs_back() {
        return is_back;
    }

    public void setIs_back(ObservableField<Boolean> is_back) {
        this.is_back = is_back;
    }

    public ObservableArrayList<DeviceItem> getList_device_item() {
        return list_device_item;
    }

    public void setList_device_item(ObservableArrayList<DeviceItem> list_device_item) {
        this.list_device_item = list_device_item;
    }

    public Long getDevice_id() {
        return device_id;
    }

    public void setDevice_id(Long device_id) {
        this.device_id = device_id;
    }

    public void goBack(){
        is_back.set(true);
    }
    public void setIs_scan(ObservableField<Integer> is_scan) {
        this.is_scan = is_scan;
    }


    public void addDeviceItem(Long device_id,String device_name, String mac){
        DeviceItemClient.getDeviceItemApiService().addDeviceItem(new DeviceItemRequest(device_name,mac,device_id,user_id))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseObject>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull ResponseObject responseObject) {
                        is_add_device_item.set(true);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        is_add_device_item.set(false);
                    }

                    @Override
                    public void onComplete() {
                        is_add_device_item.set(true);
                    }
                });
    }
    public void callAPI(SharedPreferences.Editor editor){
        DeviceAPIService.DEVICE_API_SERVICE.getAllDevice().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseObject>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull ResponseObject responseObject) {
                        List<DeviceRequest> list = gson.fromJson(new Gson().toJson(responseObject.getData()),
                                new TypeToken<List<DeviceRequest>>(){}.getType()
                        );
                        list_device.addAll(list);
                        String json = gson.toJson(list);
                        editor.putString(Validation.KEY_DEVICE, json);
                        editor.apply();

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        is_loadded_data.set(false);
                    }

                    @Override
                    public void onComplete() {
                        is_loadded_data.set(true);
                    }
                });
    }
    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();

    }
}
