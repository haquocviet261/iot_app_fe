package com.project.smartfrigde.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class DeviceItem implements Serializable {
    private Long device_item_id;
    private String device_name;
    private String mac_address;
    private Long device_id;
    private Long user_id;

    public DeviceItem() {
    }

    public DeviceItem(Long device_item_id, String device_name, String mac_address, Long device_id, Long user_id) {
        this.device_item_id = device_item_id;
        this.device_name = device_name;
        this.mac_address = mac_address;
        this.device_id = device_id;
        this.user_id = user_id;
    }

    public Long getDevice_item_id() {
        return device_item_id;
    }

    public void setDevice_item_id(Long device_item_id) {
        this.device_item_id = device_item_id;
    }

    public String getDevice_name() {
        return device_name;
    }

    public void setDevice_name(String device_name) {
        this.device_name = device_name;
    }

    public String getMac_address() {
        return mac_address;
    }

    public void setMac_address(String mac_address) {
        this.mac_address = mac_address;
    }

    public Long getDevice_id() {
        return device_id;
    }

    public void setDevice_id(Long device_id) {
        this.device_id = device_id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    // Parcelable implementation

}
