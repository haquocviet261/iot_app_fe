package com.project.smartfrigde.data.dto.request;
public class UserStatusRequest {




    private Long user_id;
    private String user_name;
    private String first_name;
    private String last_name;
    private String image_src;
    private Long online_status;
    public UserStatusRequest() {
    }

    public UserStatusRequest(Long user_id, String user_name, String first_name, String last_name, String image_src, Long online_status) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.first_name = first_name;
        this.last_name = last_name;
        this.image_src = image_src;
        this.online_status = online_status;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getImage_src() {
        return image_src;
    }

    public void setImage_src(String image_src) {
        this.image_src = image_src;
    }

    public Long getOnline_status() {
        return online_status;
    }

    public void setOnline_status(Long online_status) {
        this.online_status = online_status;
    }
}
