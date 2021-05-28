package com.hcmus.fit.customer_apps.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.hcmus.fit.customer_apps.R;
import com.hcmus.fit.customer_apps.models.UserInfo;
import com.hcmus.fit.customer_apps.networks.SignInNetwork;

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

        Intent intent = getIntent();
        String userId = intent.getStringExtra("userId");
        Log.d("phone", "PhoneLoginActivity userId: " + userId);

        btnBack.setOnClickListener(v -> onBackPressed());
        btnContinue.setOnClickListener(v -> {
            if (edtPhoneNumber.length() < SIZE_NUMBER) {
                Toast.makeText(this, R.string.notify_phone_number, Toast.LENGTH_LONG).show();
                return;
            }

            UserInfo.getInstance().setPhoneNumber(edtPhoneNumber.getText().toString());
            Log.d("phone_number", edtPhoneNumber.getText().toString());
//            SignInNetwork.verifyPhoneNumber(this, userId,
//                    edtPhoneNumber.getText().toString());

//            SignInNetwork.sendOTP(this, UserInfo.getInstance().getId(),
//                    UserInfo.getInstance().getPhoneNumber());

            Intent intent1 = new Intent(this, OTPLoginActivity.class);
            startActivity(intent1);
        });
    }

}
