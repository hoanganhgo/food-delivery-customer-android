package com.hcmus.fit.customer_apps.business;

import android.util.Log;

import vn.zalopay.sdk.ZaloPayError;
import vn.zalopay.sdk.listeners.PayOrderListener;

public class MyZaloPayListener implements PayOrderListener {
    @Override
    public void onPaymentSucceeded(String s, String s1, String s2) {
        Log.d("zalopay","onPaymentSucceeded");
    }

    @Override
    public void onPaymentCanceled(String s, String s1) {
        Log.d("zalopay","onPaymentCanceled");
    }

    @Override
    public void onPaymentError(ZaloPayError zaloPayError, String s, String s1) {
        Log.d("zalopay", s + "  " + s1 + "  " + zaloPayError);
    }
}
