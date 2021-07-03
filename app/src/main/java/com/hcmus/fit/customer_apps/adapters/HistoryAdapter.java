package com.hcmus.fit.customer_apps.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hcmus.fit.customer_apps.R;
import com.hcmus.fit.customer_apps.models.OrderModel;
import com.hcmus.fit.customer_apps.models.UserInfo;
import com.hcmus.fit.customer_apps.utils.AppUtil;
import com.squareup.picasso.Picasso;

public class HistoryAdapter extends BaseAdapter {
    private final LayoutInflater layoutInflater;

    public HistoryAdapter(Context context) {
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return UserInfo.getInstance().getHistory().size();
    }

    @Override
    public Object getItem(int position) {
        return UserInfo.getInstance().getHistory().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.adapter_history_order, null);
            holder = new MyViewHolder();
            holder.tvOrderId = convertView.findViewById(R.id.tv_order_id);
            holder.tvOrderDate = convertView.findViewById(R.id.tv_order_date);
            holder.ivAvatarRestaurant = convertView.findViewById(R.id.iv_avatar_restaurant);
            holder.tvMerchantName = convertView.findViewById(R.id.tv_merchant_name);
            //holder.tvMerchantAddress = convertView.findViewById(R.id.tv_merchant_address);
            holder.tvOrderPrice = convertView.findViewById(R.id.tv_order_price);
            convertView.setTag(holder);
        } else {
            holder = (MyViewHolder) convertView.getTag();
        }

        OrderModel order = UserInfo.getInstance().getHistory().get(position);
        holder.tvOrderId.setText("#" + order.getId());
        holder.tvOrderDate.setText(AppUtil.getDateString(order.getCalendar()));
        Picasso.with(convertView.getContext()).load(order.getAvatarRestaurant()).into(holder.ivAvatarRestaurant);
        holder.tvMerchantName.setText(order.getRestaurantName());
        holder.tvOrderPrice.setText(AppUtil.convertCurrency(order.getTotal()));

        return convertView;
    }

    static class MyViewHolder {
        TextView tvOrderId;
        TextView tvOrderDate;
        ImageView ivAvatarRestaurant;
        TextView tvMerchantName;
        //TextView tvMerchantAddress;
        TextView tvOrderPrice;
    }
}
