package com.project.smartfrigde.view.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.databinding.Observable;
import androidx.databinding.ObservableList;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.project.smartfrigde.R;
import com.project.smartfrigde.adapter.DeviceAdapter;
import com.project.smartfrigde.bluetooth.BluetoothService;
import com.project.smartfrigde.data.dto.request.DeviceRequest;
import com.project.smartfrigde.data.dto.request.FoodItemRequest;
import com.project.smartfrigde.databinding.FragmentHomeBinding;
import com.project.smartfrigde.model.BluetoothDevice;
import com.project.smartfrigde.model.DeviceItem;
import com.project.smartfrigde.model.Food;
import com.project.smartfrigde.model.User;
import com.project.smartfrigde.service.WebSocketService;
import com.project.smartfrigde.utils.ProgressDialog;
import com.project.smartfrigde.utils.UserSecurePreferencesManager;
import com.project.smartfrigde.utils.Validation;
import com.project.smartfrigde.view.AddDeviceActivity;
import com.project.smartfrigde.view.ChatActivity;
import com.project.smartfrigde.view.DetailDeviceActivity;
import com.project.smartfrigde.view.NotificationActivity;
import com.project.smartfrigde.viewmodel.HomeViewmodel;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import ua.naiksoftware.stomp.dto.StompMessage;


public class HomeFragment extends Fragment {

    private FragmentHomeBinding fragmentHomeBinding;
    private  final Gson gson = new Gson();
    private ProgressDialog progressDialog;
    private RecyclerView recyclerView;
    private HomeViewmodel homeViewmodel;
    private DeviceAdapter deviceAdapter;
    private List<BluetoothDevice> list = new ArrayList<>();
    private BluetoothDevice bluetoothDevice;


    User user = UserSecurePreferencesManager.getUser();

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Intent intent = new Intent(getContext(), WebSocketService.class);
            getContext().startService(intent);
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(Validation.PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        progressDialog = new ProgressDialog(getContext(),R.layout.progressdialog);
        fragmentHomeBinding = FragmentHomeBinding.inflate(inflater, container, false);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),3);
        homeViewmodel = new HomeViewmodel();
        fragmentHomeBinding.setHomeViewModel(homeViewmodel);
        deviceAdapter = new DeviceAdapter(list,getContext(),null,homeViewmodel);
        recyclerView = fragmentHomeBinding.listDevice;
        recyclerView.setAdapter(deviceAdapter);
        String jsonFood = sharedPreferences.getString(Validation.KEY_FOOD_LIST, null);
        String jsonFoodItems = sharedPreferences.getString(Validation.KEY_FOOD_ITEMS, null);
        String jsonDevices = sharedPreferences.getString(Validation.KEY_DEVICE, null);
        String jsonDeviceItems = sharedPreferences.getString(Validation.KEY_DEVICE_ITEMS, null);

        if (jsonFood != null) {
            Type type = new TypeToken<List<Food>>() {}.getType();
            homeViewmodel.getFoods().addAll(gson.fromJson(jsonFood, type));
        } else {
            homeViewmodel.getAllFood(editor);
        }
        if (jsonDevices != null){
            Type type = new TypeToken<List<DeviceRequest>>() {}.getType();
            homeViewmodel.getFoods().addAll(gson.fromJson(jsonDevices, type));
        }else {
            homeViewmodel.callAPI(editor);
        }
        homeViewmodel.getIsDetailDevice().addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                if (Boolean.TRUE.equals(homeViewmodel.getIsDetailDevice().get())){
                    startActivity(new Intent(getContext(), DetailDeviceActivity.class));
                }
            }
        });
        homeViewmodel.getIs_device_exist().set(View.GONE);

        if (jsonDeviceItems != null || !(jsonDeviceItems.length() == 0)){
            Type type = new TypeToken<List<DeviceItem>>() {}.getType();
            List<DeviceItem> deviceItemList = gson.fromJson(jsonDeviceItems, type);
            list.add(new BluetoothDevice(deviceItemList.get(0).getDevice_item_id(),deviceItemList.get(0).getDevice_name(),deviceItemList.get(0).getMac_address()) );
            deviceAdapter.setData(list);
            homeViewmodel.getIs_device_exist().set(View.VISIBLE);
            if (jsonFoodItems == null){
                homeViewmodel.getFoodItemByDeviceItemID(editor,deviceItemList.get(0).getDevice_item_id());
            }
        }else {
            homeViewmodel.getIs_device_exist().set(View.GONE);
        }
        Intent add_device_intent = getActivity().getIntent();
        if ( add_device_intent.hasExtra("device")){
            bluetoothDevice =  intent.getParcelableExtra("device");
            homeViewmodel.getIs_device_exist().set(View.VISIBLE);
            BluetoothService bluetoothService = new BluetoothService(getContext(),bluetoothDevice.getBluetoothDevice());
            bluetoothService.start();
            list.add(bluetoothDevice);
            deviceAdapter.setData(list);
            deviceAdapter.setBluetoothService(bluetoothService);
        }

        recyclerView.setLayoutManager(gridLayoutManager);

        fragmentHomeBinding.userName.setText(user.getFirst_name() + " "+ user.getLast_name());

        homeViewmodel.getAdd_device().addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                if (Boolean.TRUE.equals(homeViewmodel.getAdd_device().get())) {
                    Intent intent = new Intent(getContext(), AddDeviceActivity.class);
                    startActivity(intent);
                }
            }
        });
        homeViewmodel.getIs_loadded_data().set(false);
        homeViewmodel.getIs_loadded_data().set(false);
        homeViewmodel.getChat_now().addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                if (Boolean.TRUE.equals(homeViewmodel.getChat_now().get())){
                    startActivity(new Intent(getContext(), ChatActivity.class));
                }
            }
        });
        fragmentHomeBinding.notification.setHomeViewModel(homeViewmodel);
        homeViewmodel.getIs_notification().addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                if (Boolean.TRUE.equals(homeViewmodel.getIs_notification().get())){
                    startActivity(new Intent(getActivity(), NotificationActivity.class));
                }
            }
        });
        return fragmentHomeBinding.getRoot();
    }

}