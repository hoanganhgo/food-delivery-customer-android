package com.hcmus.fit.customer_apps.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.hcmus.fit.customer_apps.R;

public class PhoneLoginActivity extends AppCompatActivity {
    private final int SIZE_NUMBER = 10;
    private ImageButton btnBack;
    private EditText edtPhoneNumber;
    private Button btnContinue;

    @Override
    protected void onCreate(@Nullable Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_login_phone);

        btnBack = findViewById(R.id.btn_back);
        edtPhoneNumber = findViewById(R.id.edt_phone_number);
        btnContinue = findViewById(R.id.btn_continue);

        btnBack.setOnClickListener(v -> PhoneLoginActivity.super.onBackPressed());
        btnContinue.setOnClickListener(v -> {
            if (edtPhoneNumber.length() < SIZE_NUMBER) {
                Toast.makeText(this, R.string.notify_phone_number, Toast.LENGTH_LONG).show();
                return;
            }

            Intent intent = new Intent(this, OTPLoginActivity.class);
            startActivity(intent);
        });
    }

}
