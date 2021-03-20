package com.hcmus.fit.customer_apps.activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.hcmus.fit.customer_apps.MainActivity;
import com.hcmus.fit.customer_apps.R;
import com.hcmus.fit.customer_apps.contants.Constant;
import com.hcmus.fit.customer_apps.contants.Tag;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class LoginActivity extends AppCompatActivity {
    private static final int SIGN_IN_GOOGLE = 123;

    private Button btnSignInFb;
    private Button btnSignInGg;
    private Button btnSignInPhone;
    private Button btnSignUp;

    private GoogleSignInClient googleSignInClient;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnSignInFb = findViewById(R.id.sign_in_fb);
        btnSignInGg = findViewById(R.id.sign_in_gg);
        btnSignInPhone = findViewById(R.id.sign_in_phone_number);
        btnSignUp = findViewById(R.id.sign_up);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                //.requestIdToken(Constant.WEB_ID)
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, gso);

        btnSignInFb.setOnClickListener(v -> {
            if (isNetworkConnected()) {
                Toast.makeText(v.getContext(), "Sign in with fb", Toast.LENGTH_LONG).show();
            }
        });

        btnSignInGg.setOnClickListener(v -> {
            Toast.makeText(v.getContext(), "Sign in with gg", Toast.LENGTH_LONG).show();
            signIn();
        });

        btnSignInPhone.setOnClickListener(v -> {
            Intent intent = new Intent(this, PhoneLoginActivity.class);
            startActivity(intent);
        });

        btnSignUp.setOnClickListener(v -> {
            Toast.makeText(v.getContext(), "Sign up", Toast.LENGTH_LONG).show();
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    private void handleSignInResult(@NonNull Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            //String idToken = account.getIdToken();

            // TODO(developer): send ID Token to server and validate

            //updateUI(account);
        } catch (ApiException e) {
            Log.w(Tag.GG, "handleSignInResult:error", e);
            //updateUI(null);
        }
    }

    private void signIn() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, SIGN_IN_GOOGLE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == SIGN_IN_GOOGLE) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Log.d(Tag.GG, "Result Sign in google");
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }
}
