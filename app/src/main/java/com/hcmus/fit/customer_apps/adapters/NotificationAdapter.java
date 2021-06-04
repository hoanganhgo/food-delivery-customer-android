package com.hcmus.fit.customer_apps.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hcmus.fit.customer_apps.R;
import com.hcmus.fit.customer_apps.models.UserInfo;

public class NotificationAdapter extends BaseAdapter {
    private final LayoutInflater layoutInflater;

    public NotificationAdapter(Context context) {
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return UserInfo.getInstance().getNotifyList().size();
    }

    @Override
    public Object getItem(int position) {
        return UserInfo.getInstance().getNotifyList().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.adapter_notification, null);
            holder = new MyViewHolder();
            holder.tvTitle = convertView.findViewById(R.id.tv_title);
            holder.tvContent = convertView.findViewById(R.id.tv_content);
            holder.ivAvatar = convertView.findViewById(R.id.iv_avatar_notification);
            convertView.setTag(holder);
        } else {
            holder = (MyViewHolder) convertView.getTag();
        }

        return convertView;
    }

    static class MyViewHolder {
        TextView tvTitle;
        TextView tvContent;
        ImageView ivAvatar;
    }
}
