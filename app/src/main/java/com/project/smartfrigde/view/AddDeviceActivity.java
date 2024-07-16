package com.project.smartfrigde.view;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.Observable;
import androidx.databinding.ObservableList;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.project.smartfrigde.R;
import com.project.smartfrigde.adapter.DeviceScanAdapter;
import com.project.smartfrigde.bluetooth.BluetoothService;
import com.project.smartfrigde.databinding.ActivityAddDeviceBinding;
import com.project.smartfrigde.model.User;
import com.project.smartfrigde.utils.UserSecurePreferencesManager;
import com.project.smartfrigde.viewmodel.AddDeviceViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class AddDeviceActivity extends AppCompatActivity {

    private static final int REQUEST_DISCOVER_BT = 1;
    private BluetoothAdapter bluetoothAdapter;
    AddDeviceViewModel addDeviceViewModel ;
    ActivityAddDeviceBinding activityAddDeviceBinding;
    private RecyclerView recyclerView;
    DeviceScanAdapter deviceScanAdapter;
    private ActivityResultLauncher<Intent> enableBluetoothLauncher;
    User user = UserSecurePreferencesManager.getUser();
    private Long device_id;

    List<com.project.smartfrigde.model.BluetoothDevice> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        if ( intent.hasExtra("device_id")){
            device_id = intent.getLongExtra("device_id",-1L);
        }
        addDeviceViewModel = new AddDeviceViewModel(device_id,user.getUser_id());
        activityAddDeviceBinding = DataBindingUtil.setContentView(this,R.layout.activity_add_device);
        activityAddDeviceBinding.setAddDeviceViewModel(addDeviceViewModel);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView = activityAddDeviceBinding.listDeviceScan;
        deviceScanAdapter = new DeviceScanAdapter(list,this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(deviceScanAdapter);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);
        enableBluetoothLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        startDiscovery();
                        addDeviceViewModel.getIs_scan().set(View.GONE);
                    } else {
                        addDeviceViewModel.getIs_scan().set(View.GONE);
                    }
                });
        BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        bluetoothAdapter = bluetoothManager.getAdapter();

        if (bluetoothAdapter == null) {
            showToast("Your device Does not support Bluetooth !");
            return;
        }
        if (!bluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            enableBluetoothLauncher.launch(enableBtIntent);
        }else {
            startDiscovery();
            addDeviceViewModel.getIs_scan().set(View.VISIBLE);
        }
        Intent discoverableIntent =
                new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
        enableBluetoothLauncher.launch(discoverableIntent);
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        addDeviceViewModel.getIs_back().addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                if (addDeviceViewModel.getIs_back().get().equals(Boolean.TRUE)){
                    startActivity(new Intent(AddDeviceActivity.this,DashboardActivity.class));
                }
            }
        });

        registerReceiver(receiver, filter);
        deviceScanAdapter.setBluetoothAdapter(bluetoothAdapter);
    }
    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if (ActivityCompat.checkSelfPermission(AddDeviceActivity.this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                String deviceName = device.getName();
                String deviceHardwareAddress = device.getAddress(); // MAC address
                list.add(new com.project.smartfrigde.model.BluetoothDevice(null,deviceName,deviceHardwareAddress,device,addDeviceViewModel));
                deviceScanAdapter.setData(list);
            }
        }
    };
    private void startDiscovery() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.BLUETOOTH_CONNECT, Manifest.permission.BLUETOOTH_SCAN}, REQUEST_DISCOVER_BT);
            return;
        }
        if (!bluetoothAdapter.isDiscovering()) {
            showToast("Starting discovery...");
            bluetoothAdapter.startDiscovery();
        }
    }
    private void showToast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }
}
