package com.project.smartfrigde.model;

import android.bluetooth.BluetoothAdapter;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.project.smartfrigde.viewmodel.AddDeviceViewModel;

public class BluetoothDevice implements Parcelable {
    private Long device_item_id;
    private String name;
    private String mac;
    private android.bluetooth.BluetoothDevice bluetoothDevice;
    private BluetoothAdapter bluetoothAdapter;
    private AddDeviceViewModel addDeviceViewModel;

    public BluetoothDevice() {
    }

    public BluetoothDevice(Long device_item_id, String name, String mac) {
        this.device_item_id = device_item_id;
        this.name = name;
        this.mac = mac;
    }

    public BluetoothDevice(Long device_item_id, String name, String mac, android.bluetooth.BluetoothDevice bluetoothDevice, AddDeviceViewModel addDeviceViewModel) {
        this.device_item_id = device_item_id;
        this.name = name;
        this.mac = mac;
        this.bluetoothDevice = bluetoothDevice;
        this.addDeviceViewModel = addDeviceViewModel;
    }

    protected BluetoothDevice(Parcel in) {
        name = in.readString();
        mac = in.readString();
        bluetoothDevice = in.readParcelable(android.bluetooth.BluetoothDevice.class.getClassLoader());
        bluetoothAdapter = in.readParcelable(BluetoothAdapter.class.getClassLoader());
    }

    public Long getDevice_item_id() {
        return device_item_id;
    }

    public void setDevice_item_id(Long device_item_id) {
        this.device_item_id = device_item_id;
    }

    public AddDeviceViewModel getAddDeviceViewModel() {
        return addDeviceViewModel;
    }

    public void setAddDeviceViewModel(AddDeviceViewModel addDeviceViewModel) {
        this.addDeviceViewModel = addDeviceViewModel;
    }

    public static final Creator<BluetoothDevice> CREATOR = new Creator<BluetoothDevice>() {
        @Override
        public BluetoothDevice createFromParcel(Parcel in) {
            return new BluetoothDevice(in);
        }

        @Override
        public BluetoothDevice[] newArray(int size) {
            return new BluetoothDevice[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(mac);
        parcel.writeParcelable(bluetoothDevice, i);
    }

    public android.bluetooth.BluetoothDevice getBluetoothDevice() {
        return bluetoothDevice;
    }

    public void setBluetoothDevice(android.bluetooth.BluetoothDevice bluetoothDevice) {
        this.bluetoothDevice = bluetoothDevice;
    }


}
