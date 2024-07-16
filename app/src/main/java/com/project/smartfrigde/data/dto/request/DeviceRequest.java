package com.project.smartfrigde.data.dto.request;

import java.io.Serializable;

public class DeviceRequest implements Serializable {
    private Long device_id;
    private String device_type;

    public DeviceRequest() {
    }

    public DeviceRequest(Long device_id, String device_type) {
        this.device_id = device_id;
        this.device_type = device_type;
    }

    public Long getDevice_id() {
        return device_id;
    }

    public void setDevice_id(Long device_id) {
        this.device_id = device_id;
    }

    public String getDevice_type() {
        return device_type;
    }

    public void setDevice_type(String device_type) {
        this.device_type = device_type;
    }
}
