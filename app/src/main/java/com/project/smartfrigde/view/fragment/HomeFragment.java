package com.project.smartfrigde.view.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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

import com.project.smartfrigde.R;
import com.project.smartfrigde.adapter.DeviceAdapter;
import com.project.smartfrigde.bluetooth.BluetoothService;
import com.project.smartfrigde.databinding.FragmentHomeBinding;
import com.project.smartfrigde.model.BluetoothDevice;
import com.project.smartfrigde.model.DeviceItem;
import com.project.smartfrigde.model.Food;
import com.project.smartfrigde.model.User;
import com.project.smartfrigde.service.WebSocketService;
import com.project.smartfrigde.utils.ProgressDialog;
import com.project.smartfrigde.utils.UserSecurePreferencesManager;
import com.project.smartfrigde.view.AddDeviceActivity;
import com.project.smartfrigde.view.ChatActivity;
import com.project.smartfrigde.view.DetailDeviceActivity;
import com.project.smartfrigde.view.NotificationActivity;
import com.project.smartfrigde.viewmodel.HomeViewmodel;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import ua.naiksoftware.stomp.dto.StompMessage;


public class HomeFragment extends Fragment {

    private FragmentHomeBinding fragmentHomeBinding;
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
//        Intent serviceIntent = new Intent(getContext(), WebSocketService.class);
//        ContextCompat.startForegroundService(getContext(), serviceIntent);
        progressDialog = new ProgressDialog(getContext(),R.layout.progressdialog);
        fragmentHomeBinding = FragmentHomeBinding.inflate(inflater, container, false);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),3);
        deviceAdapter = new DeviceAdapter(list,getContext(),null);
        homeViewmodel = new HomeViewmodel();
        recyclerView = fragmentHomeBinding.listDevice;
        recyclerView.setAdapter(deviceAdapter);
        fragmentHomeBinding.setHomeViewModel(homeViewmodel);

        homeViewmodel.getAllFood();
        homeViewmodel.getIsDetailDevice().addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                if(Boolean.TRUE.equals(homeViewmodel.getIsLoaddedData().get())){
                    progressDialog.show();
                    homeViewmodel.getIsLoaddedData().addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
                        @Override
                        public void onPropertyChanged(Observable sender, int propertyId) {
                            if(Boolean.TRUE.equals(homeViewmodel.getIsLoaddedData().get())){
                                progressDialog.dismiss();
                                Intent intent = new Intent(getContext(),DetailDeviceActivity.class);
                                ArrayList<Food> list_food = homeViewmodel.getFoods();
                                intent.putParcelableArrayListExtra("list_food",list_food);

                                startActivity(intent);
                            }
                        }
                    });
                }else {
                    progressDialog.dismiss();
                }
            }
        });
        homeViewmodel.getIsDetailDevice().addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                if (Boolean.TRUE.equals(homeViewmodel.getIsDetailDevice().get())){
                    startActivity(new Intent(getContext(), DetailDeviceActivity.class));
                }
            }
        });
        Intent intent = getActivity().getIntent();
        if (intent.hasExtra("list_device_item")){

            ArrayList<DeviceItem> deviceItemList = intent.getParcelableArrayListExtra("deviceItemList");
            for (int i = 0; i < list.size(); i++) {
                if (deviceItemList != null){
                    list.add(new BluetoothDevice(deviceItemList.get(i).getDevice_item_id(),deviceItemList.get(i).getDevice_name(),deviceItemList.get(i).getMac_address()));
                }
            }
            deviceAdapter.setData(list);
            homeViewmodel.getIs_device_exist().set(View.VISIBLE);
        }
        if ( intent.hasExtra("device")){
            bluetoothDevice =  intent.getParcelableExtra("device");
            homeViewmodel.getIs_device_exist().set(View.VISIBLE);
            BluetoothService bluetoothService = new BluetoothService(getContext(),bluetoothDevice.getBluetoothDevice());
            bluetoothService.start();
            list.add(bluetoothDevice);
            deviceAdapter.setData(list);
            deviceAdapter.setBluetoothService(bluetoothService);
        }else {
            homeViewmodel.getIs_device_exist().set(View.GONE);
        }

        recyclerView.setLayoutManager(gridLayoutManager);
        User user = UserSecurePreferencesManager.getUser();
        fragmentHomeBinding.userName.setText(user.getFirst_name() + " "+ user.getLast_name());
        homeViewmodel.getAdd_device().addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                if (Boolean.TRUE.equals(homeViewmodel.getAdd_device().get())){
                    startActivity(new Intent(getContext(), AddDeviceActivity.class));
                }
            }
        });
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