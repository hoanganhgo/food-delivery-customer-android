package com.hcmus.fit.customer_apps.ui;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
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
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.hcmus.fit.customer_apps.networks.SignInNetwork.googleSignInClient;

public class SettingFragment extends Fragment {
    private CircleImageView ivAvatar;
    private TextView tvUserName;
    private Button btnUserId;
    private Button btnEmail;
    private Button btnPhone;
    private Button btnComplain;
    private Button btnSignOut;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_setting, container, false);

        ivAvatar = root.findViewById(R.id.iv_avatar);
        tvUserName = root.findViewById(R.id.tv_user_name);
        btnUserId = root.findViewById(R.id.btn_user_id);
        btnEmail = root.findViewById(R.id.btn_email);
        btnPhone = root.findViewById(R.id.btn_phone);
        btnComplain = root.findViewById(R.id.btn_complain);
        btnSignOut = root.findViewById(R.id.btn_sign_out);

        Picasso.with(getContext()).load(UserInfo.getInstance().getAvatar()).into(ivAvatar);
        tvUserName.setText(UserInfo.getInstance().getFullName());
        btnUserId.setText(UserInfo.getInstance().getId());
        btnEmail.setText(UserInfo.getInstance().getEmail());
        btnPhone.setText(UserInfo.getInstance().getPhoneNumber());

        btnComplain.setOnClickListener(v -> {
            String url = "https://forms.gle/p1U6RdjQJR5dMxJ39";
            try {
                Intent i = new Intent("android.intent.action.MAIN");
                i.setComponent(ComponentName.unflattenFromString("com.android.chrome/com.android.chrome.Main"));
                i.addCategory("android.intent.category.LAUNCHER");
                i.setData(Uri.parse(url));
                startActivity(i);
            }
            catch(ActivityNotFoundException e) {
                // Chrome is not installed
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(i);
            }
        });

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