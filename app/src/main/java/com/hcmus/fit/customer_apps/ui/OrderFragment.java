package com.hcmus.fit.customer_apps.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.hcmus.fit.customer_apps.R;
import com.hcmus.fit.customer_apps.activities.OrderStatusActivity;
import com.hcmus.fit.customer_apps.adapters.OrderAdapter;
import com.hcmus.fit.customer_apps.models.OrderManager;
import com.hcmus.fit.customer_apps.models.OrderModel;

public class OrderFragment extends Fragment {

    private ListView lvOrder;
    private OrderAdapter orderAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_order, container, false);
        lvOrder = root.findViewById(R.id.lv_order);

        orderAdapter = new OrderAdapter(getContext());
        lvOrder.setAdapter(orderAdapter);

        OrderManager.getInstance().setOrderAdapter(orderAdapter);

        lvOrder.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), OrderStatusActivity.class);
                OrderModel orderModel = OrderManager.getInstance().getOrderModel(position);
                intent.putExtra("orderId", orderModel.getId());
                startActivity(intent);
            }
        });

        return root;
    }
}