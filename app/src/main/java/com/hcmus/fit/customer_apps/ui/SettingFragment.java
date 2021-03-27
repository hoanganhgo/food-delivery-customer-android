package com.hcmus.fit.customer_apps.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.hcmus.fit.customer_apps.R;

public class SettingFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_setting, container, false);
        final TextView textView = root.findViewById(R.id.text_setting);
        textView.setText("Setting");

        return root;
    }
}