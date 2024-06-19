package com.project.smartfrigde.model;

import com.project.smartfrigde.utils.Role;

import java.util.Date;

public class User {
    private Long user_id;
    private String user_name;
    private String first_name;
    private String last_name;
    private String phone_number;
    private Date date_of_birth;
    private String email;
    private String address;
    private com.project.smartfrigde.utils.Role role;
    private Long status;

    private String image_src;

    public User() {
    }

    public User(String user_name, String password) {
        this.user_name = user_name;
    }

    public User(Long user_id, String user_name, String first_name, String last_name, String phone_number, Date date_of_birth, String email, String address, Role role, Long status, String image_src) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.first_name = first_name;
        this.last_name = last_name;
        this.phone_number = phone_number;
        this.date_of_birth = date_of_birth;
        this.email = email;
        this.address = address;
        this.role = role;
        this.status = status;
        this.image_src = image_src;
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

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public Date getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(Date date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public String getImage_src() {
        return image_src;
    }

    public void setImage_src(String image_src) {
        this.image_src = image_src;
    }
}
