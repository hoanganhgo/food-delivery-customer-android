package com.hcmus.fit.customer_apps.contants;

public class API {
    /**
     * 0: local,  1: release
     */
    private static final int env = 0;
    private static final String localHost = "http://192.168.1.4:8000";
    private static final String releaseHost = "https://api.customer.flash.bin.edu.vn";
    private static final String localSocket = "http://192.168.1.4:8010";
    private static final String releaseSocket = "https://api.socket.flash.bin.edu.vn";
    private static final String SERVER = env == 0 ? localHost : releaseHost;
    public static final String SERVER_SOCKET = env == 0 ? localSocket : releaseSocket;

    public static final String SIGN_IN_WITH_GOOGLE = SERVER + "/auth/google";
    public static final String SEND_GG_OTP = SERVER + "/auth/google/otp/call";
    public static final String SEND_PHONE_OTP = SERVER + "/auth/phone/otp/call";
    public static final String AUTH_GG_VERIFY = SERVER + "/auth/google/otp/verify";
    public static final String AUTH_PHONE_VERIFY = SERVER + "/auth/phone/otp/verify";
    public static final String SIGN_IN_WITH_PHONE_NUMBER = SERVER + "/customer/auth/vertify-phone";
    public static final String GET_USER_INFO = SERVER + "/users/{id}";
    public static final String GET_RESTAURANTS = SERVER + "/restaurants";
    public static final String GET_RESTAURANT_DETAIL = SERVER + "/restaurants/{restaurantID}";
    public static final String GET_MENU_RESTAURANT = SERVER + "/restaurants/{restaurantID}/foods";
    public static final String ORDER = SERVER + "/orders";
    public static final String GET_SHIP_FEE = SERVER + "/orders/shippingfee?restaurant={merchantId}&destination={addressDelivery}";
    public static final String REVIEW_SHIPPER = SERVER + "/orders/{orderId}/shipperreviews/raw";
    public static final String REVIEW_MERCHANT = SERVER + "/orders/{orderId}/restaurantreviews/raw";
    public static final String GET_RESTAURANT_REVIEW = SERVER + "/restaurants/{restaurantID}/reviews";
    public static final String redirectUrl = SERVER_SOCKET + "/callback-zalopay";
}
