package com.hcmus.fit.customer_apps.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.hcmus.fit.customer_apps.R;
import com.hcmus.fit.customer_apps.models.UserInfo;
import com.hcmus.fit.customer_apps.networks.SignInNetwork;
import com.hcmus.fit.customer_apps.utils.NetworkUtil;

import static com.hcmus.fit.customer_apps.networks.SignInNetwork.googleSignInClient;


public class LoginActivity extends AppCompatActivity {
    private static final int SIGN_IN_GOOGLE = 123;

    private Button btnSignInFb;
    private Button btnSignInGg;
    private Button btnSignInPhone;
    private Button btnSignUp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnSignInFb = findViewById(R.id.sign_in_fb);
        btnSignInGg = findViewById(R.id.sign_in_gg);
        btnSignInPhone = findViewById(R.id.sign_in_phone_number);
        btnSignUp = findViewById(R.id.sign_up);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.server_client_id))
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, gso);

        btnSignInFb.setOnClickListener(v -> {
            if (NetworkUtil.isNetworkConnected(this)) {
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
//        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
//        if (account != null) {
//            Log.d("google", "OnStart Token: "+account.getIdToken());
//            Log.d("google", "Display name: " + account.getDisplayName());
//            Log.d("google", "Give Name: " + account.getGivenName());
//            Log.d("google", "Family name: " + account.getFamilyName());
//            Log.d("google", "Email: " + account.getEmail());
//            Log.d("google","Id: " + account.getId());
//            Log.d("google","photo: " + account.getPhotoUrl());
//
//            UserInfo.getInstance()
//                    .withUserId(account.getId())
//                    .withFirstName(account.getFamilyName())
//                    .withLastName(account.getGivenName())
//                    .withEmail(account.getEmail())
//                    .withAvatar(account.getPhotoUrl().toString());
//
//            SignInNetwork.sendGGIdTokenToServer(this, account);
//        }

    }

    private void handleSignInResult(@NonNull Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            String idToken = account.getIdToken();
            Log.d("google", "handler token: " + idToken);
            Log.d("google", "Display name: " + account.getDisplayName());
            Log.d("google", "Give Name: " + account.getGivenName());
            Log.d("google", "Family name: " + account.getFamilyName());
            Log.d("google", "Email: " + account.getEmail());
            Log.d("google","Id: " + account.getId());
            Log.d("google","photo: " + account.getPhotoUrl());

            UserInfo.getInstance()
                    .withUserId(account.getId())
                    .withFirstName(account.getFamilyName())
                    .withLastName(account.getGivenName())
                    .withEmail(account.getEmail())
                    .withAvatar(account.getPhotoUrl().toString());

            // TODO(developer): send ID Token to server and validate
            SignInNetwork.sendGGIdTokenToServer(this, account);
        } catch (ApiException e) {
            Log.w("google", "handleSignInResult:error", e);
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
            Log.d("google", "Result Sign in google");
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }
}
