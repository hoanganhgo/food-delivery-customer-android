package com.hcmus.fit.customer_apps.models;

import com.google.android.gms.maps.GoogleMap;

import java.util.ArrayList;
import java.util.List;

public class UserInfo {
    private static UserInfo instance = null;

    private String id = "";
    private String firstName = "";
    private String lastName = "";
    private String fullName = "";
    private String phoneNumber = "0123456789";
    private String email = "";
    private String avatar = "";
    private String token = "";
    private final List<AddressModel> addressModelList = new ArrayList<>();
    private int addressIndex = 0;
    private final List<OrderModel> history = new ArrayList<>();
    private final List<NotifyModel> notifyList = new ArrayList<>();

    private final Cart cart = new Cart();

    private UserInfo() {
        OrderModel orderModel = new OrderModel("98087234");
        history.add(orderModel);
        history.add(orderModel);
        history.add(orderModel);
        history.add(orderModel);
        history.add(orderModel);

        NotifyModel notifyModel = new NotifyModel();
        notifyList.add(notifyModel);
        notifyList.add(notifyModel);
        notifyList.add(notifyModel);
        notifyList.add(notifyModel);
        notifyList.add(notifyModel);
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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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

    public Cart getCart() {
        return cart;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public AddressModel getAddressCurrent() {
        if (addressIndex < this.addressModelList.size()) {
            return this.addressModelList.get(addressIndex);
        }

        return null;
    }

    public void addAddressCurrent(AddressModel addressModel) {
        this.addressModelList.add(addressModel);
        this.addressIndex = this.addressModelList.size() - 1;
    }

    public List<OrderModel> getHistory() {
        return history;
    }

    public List<NotifyModel> getNotifyList() {
        return notifyList;
    }

    public void clear() {
        instance = null;
    }
}
