package com.hcmus.fit.customer_apps.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.hcmus.fit.customer_apps.R;
import com.hcmus.fit.customer_apps.adapters.HistoryAdapter;
import com.hcmus.fit.customer_apps.networks.SignInNetwork;

public class BillFragment extends Fragment {

    private EditText edtFromDate;
    private EditText edtToDate;
    private ListView lvHistory;

    private BaseAdapter historyAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_bill, container, false);
        edtFromDate = root.findViewById(R.id.edt_from_date);
        edtToDate = root.findViewById(R.id.edt_to_date);
        lvHistory = root.findViewById(R.id.lv_history);

        historyAdapter = new HistoryAdapter(getContext());
        lvHistory.setAdapter(historyAdapter);

        SignInNetwork.getOrderHistory(getContext(), historyAdapter);
        return root;
    }
}