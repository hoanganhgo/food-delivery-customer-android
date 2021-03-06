package com.hcmus.fit.customer_apps.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.hcmus.fit.customer_apps.R;

public class LoginActivity extends AppCompatActivity {
    Button btnSignInFb;
    Button btnSignInGg;
    Button btnSignInPhone;
    Button btnSignUp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnSignInFb = (Button) findViewById(R.id.sign_in_fb);
        btnSignInGg = (Button) findViewById(R.id.sign_in_gg);
        btnSignInPhone = (Button) findViewById(R.id.sign_in_phone_number);
        btnSignUp = (Button) findViewById(R.id.sign_up);

        btnSignInFb.setOnClickListener(v -> {
            Toast.makeText(v.getContext(), "Sign in with fb", Toast.LENGTH_LONG).show();
        });

        btnSignInGg.setOnClickListener(v -> {
            Toast.makeText(v.getContext(), "Sign in with gg", Toast.LENGTH_LONG).show();
        });

        btnSignInPhone.setOnClickListener(v -> {
            Toast.makeText(v.getContext(), "Sign in with phone number", Toast.LENGTH_LONG).show();
        });

        btnSignUp.setOnClickListener(v -> {
            Toast.makeText(v.getContext(), "Sign up", Toast.LENGTH_LONG).show();
        });
    }
}
