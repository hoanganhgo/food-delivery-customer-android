package com.hcmus.fit.customer_apps.activities;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.hcmus.fit.customer_apps.R;
import com.hcmus.fit.customer_apps.models.UserInfo;
import com.hcmus.fit.customer_apps.networks.SignInNetwork;

public class OTPLoginActivity extends AppCompatActivity {
    private ImageButton btnBack;
    private EditText[] edtOTP = new EditText[6];
    private Button btnResend;
    private Button btnConfirm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_otp);

        btnBack = findViewById(R.id.btn_back);
        edtOTP[0] = findViewById(R.id.edt_otp1);
        edtOTP[1] = findViewById(R.id.edt_otp2);
        edtOTP[2] = findViewById(R.id.edt_otp3);
        edtOTP[3] = findViewById(R.id.edt_otp4);
        edtOTP[4] = findViewById(R.id.edt_otp5);
        edtOTP[5] = findViewById(R.id.edt_otp6);
        btnResend = findViewById(R.id.btn_resend);
        btnConfirm = findViewById(R.id.btn_confirm);

        btnBack.setOnClickListener(v -> onBackPressed());
        edtOTP[0].requestFocus();

        for (int i = 0; i < edtOTP.length; i++) {
            int index = i + 1;
            edtOTP[i].addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (index == edtOTP.length) {
                        btnConfirm.setEnabled(true);
                    }
                    else if (count == 1) {
                        edtOTP[index].requestFocus();
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });
        }

        btnConfirm.setOnClickListener(v -> {
            if (isFillOTP()) {
                Log.d("OTP", getOTP());
                SignInNetwork.authVerify(this, UserInfo.getInstance().getId(), getOTP());
            }

        });
    }

    private boolean isFillOTP() {
        int num = 0;

        for (EditText ed : edtOTP) {
            num += ed.getText().length();
        }

        return num == 6;
    }

    private String getOTP() {
        StringBuilder otp = new StringBuilder();

        for (EditText ed : edtOTP) {
            otp.append(ed.getText());
        }

        return otp.toString();
    }
}
