package com.hcmus.fit.customer_apps.activities;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.hcmus.fit.customer_apps.R;

public class OTPLoginActivity extends AppCompatActivity {
    private ImageButton btnBack;
    private EditText[] edtOTP = new EditText[4];
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
        btnResend = findViewById(R.id.btn_resend);
        btnConfirm = findViewById(R.id.btn_confirm);

        btnBack.setOnClickListener(v -> super.onBackPressed());
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
            if (edtOTP[edtOTP.length - 1].length() == 0) {
                return;
            }

        });
    }
}
