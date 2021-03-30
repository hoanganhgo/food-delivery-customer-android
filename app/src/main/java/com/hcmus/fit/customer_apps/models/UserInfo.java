package com.hcmus.fit.customer_apps.models;

public class UserInfo {
    private static UserInfo instance = null;

    private String id = "";
    private String firstName = "";
    private String lastName = "";
    private String phoneNumber = "";
    private String email = "";
    private String avatar = "";
    private String token = "";

    private UserInfo() {

    }

    public static UserInfo getInstance() {
        if (instance == null) {
            instance = new UserInfo();
        }
        return instance;
    }

    public UserInfo withUserId(String userId) {
        this.id = userId;
        return this;
    }

    public UserInfo withFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public UserInfo withLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public UserInfo withPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public UserInfo withEmail(String email) {
        this.email = email;
        return this;
    }

    public UserInfo withAvatar(String avatar) {
        this.avatar = avatar;
        return this;
    }

    public UserInfo withToken(String token) {
        this.token = token;
        return this;
    }

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getToken() {
        return token;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void clear() {
        instance = null;
    }
}
