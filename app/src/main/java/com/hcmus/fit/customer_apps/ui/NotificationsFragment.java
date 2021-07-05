package com.hcmus.fit.customer_apps.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.hcmus.fit.customer_apps.R;
import com.hcmus.fit.customer_apps.adapters.NotificationAdapter;

public class NotificationsFragment extends Fragment {

    private ListView lvNotification;
    private BaseAdapter notifyAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        lvNotification = root.findViewById(R.id.lv_notification);

        notifyAdapter = new NotificationAdapter(getContext());
        lvNotification.setAdapter(notifyAdapter);

        return root;
    }
}