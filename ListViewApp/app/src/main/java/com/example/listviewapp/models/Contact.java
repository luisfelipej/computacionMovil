package com.example.listviewapp.models;

import java.io.Serializable;

public class Contact implements Serializable {
    String firstName;
    String surName;
    String maternalSurname;
    String phone;

    public Contact(String firstName, String surName, String maternalSurname, String phone) {
        this.firstName = firstName;
        this.surName = surName;
        this.maternalSurname = maternalSurname;
        this.phone = phone;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public String getMaternalSurname() {
        return maternalSurname;
    }

    public void setMaternalSurname(String maternalSurname) {
        this.maternalSurname = maternalSurname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return firstName + " " + surName + " " + maternalSurname;
    }
}
