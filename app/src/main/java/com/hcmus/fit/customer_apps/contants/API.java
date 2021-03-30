package com.hcmus.fit.customer_apps.contants;

public class API {
    /**
     * 0: local,  1: release
     */
    private static final int env = 0;
    private static final String localHost = "http://10.0.2.2:8000";
    private static final String releaseHost = "https://flashfood.online";
    private static final String SERVER = env == 0 ? localHost : releaseHost;

    public static final String SIGN_IN_WITH_GOOGLE = SERVER + "/customer/auth/google";
    public static final String SIGN_IN_WITH_PHONE_NUMBER = SERVER + "/customer/auth/vertify-phone";
}
