package com.example.acer.lltgh_dogadoption;

import java.io.Serializable;

import com.google.firebase.firestore.Exclude;

public class UserInformation implements Serializable {
    @Exclude
    private String id;
    private String UserName;
    private String UserIC;
    private String UserGender;
    private String UserContact;
    private String UserEmail;

    public UserInformation() {
        this.UserName = null;
        this.UserContact = null;
        this.UserGender = null;
        this.UserIC = null;
        this.UserEmail = null;
    }

    public UserInformation(String UserName, String UserIC, String UserGender, String UserContact, String UserEmail) {
        this.UserName = UserName;
        this.UserContact = UserContact;
        this.UserGender = UserGender;
        this.UserIC = UserIC;
        this.UserEmail = UserEmail;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return UserName;
    }

    public String getUserGender() {
        return UserGender;
    }


    public String getUserContact() {
        return UserContact;
    }

    public String getUserIC() {
        return UserIC;
    }

    public String getUserEmail() {
        return UserEmail;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    public void setUserIC(String UserIC) {
        this.UserIC = UserIC;
    }

    public void setUserGender(String UserGender) {
        this.UserGender = UserGender;
    }

    public void setUserContact(String UserContact) {
        this.UserContact = UserContact;
    }

    public void setUserEmail(String UserEmail) {
        this.UserEmail = UserEmail;
    }

}
