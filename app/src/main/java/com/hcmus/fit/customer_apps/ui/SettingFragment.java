package com.hcmus.fit.customer_apps.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.hcmus.fit.customer_apps.R;
import com.hcmus.fit.customer_apps.models.UserInfo;

import static com.hcmus.fit.customer_apps.networks.SignInNetwork.googleSignInClient;

public class SettingFragment extends Fragment {
    private Button btnSignOut;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_setting, container, false);
        final TextView textView = root.findViewById(R.id.text_setting);
        btnSignOut = root.findViewById(R.id.btn_sign_out);

        textView.setText("Setting");
        btnSignOut.setOnClickListener(v -> {
            googleSignInClient.signOut()
                    .addOnCompleteListener(getActivity(), (OnCompleteListener<Void>) task -> {
                        Log.d("google", "Sign out GG success");
                        UserInfo.getInstance().clear();
                        getActivity().finish();
                        Toast.makeText(getActivity(), R.string.notify_sign_out,Toast.LENGTH_LONG).show();
                    });
        });

        return root;
    }
}