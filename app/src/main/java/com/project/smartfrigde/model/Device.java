package com.project.smartfrigde.model;

import java.io.Serializable;

public class Device implements Serializable {
    private String device_name;
    private String mac_address;

    public Device(String mac_address, String device_name) {
        this.mac_address = mac_address;
        this.device_name = device_name;
    }

    public String getMac_address() {
        return mac_address;
    }

    public void setMac_address(String mac_address) {
        this.mac_address = mac_address;
    }

    public String getDevice_name() {
        return device_name;
    }

    public void setDevice_name(String device_name) {
        this.device_name = device_name;
    }
}
