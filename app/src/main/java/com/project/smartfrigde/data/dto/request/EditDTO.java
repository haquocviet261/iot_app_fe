package com.project.smartfrigde.data.dto.request;

import java.sql.Date;

public class EditDTO {
    private String firstname;
    private String lastname;
    private String address;
    private Date dateofbirth;

    public EditDTO() {
    }

    public EditDTO(String firstname, String lastname, String address, Date dateofbirth) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.address = address;
        this.dateofbirth = dateofbirth;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getDateofbirth() {
        return dateofbirth;
    }

    public void setDateofbirth(Date dateofbirth) {
        this.dateofbirth = dateofbirth;
    }
}
