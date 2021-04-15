package com.hcmus.fit.customer_apps.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hcmus.fit.customer_apps.R;
import com.hcmus.fit.customer_apps.models.Restaurant;
import com.squareup.picasso.Picasso;

import java.util.LinkedList;
import java.util.List;

public class RestaurantVAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater;
    private Context context;
    private List<Restaurant> restaurantList;

    public RestaurantVAdapter(Context context, List<Restaurant> restaurantList) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.restaurantList = restaurantList;
    }

    @Override
    public int getCount() {
        return restaurantList.size();
    }

    @Override
    public Object getItem(int position) {
        return restaurantList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.adapter_restaurant_vertical, null);
            holder = new MyViewHolder();
            holder.btnRestaurant = convertView.findViewById(R.id.btn_restaurant);
            holder.ivAvatar = convertView.findViewById(R.id.iv_avatar_restaurant);
            holder.tvName = convertView.findViewById(R.id.tv_restaurant_name);
            holder.tvSale = convertView.findViewById(R.id.tv_big_sale);
            convertView.setTag(holder);
        } else {
            holder = (MyViewHolder) convertView.getTag();
        }

        String imageUri = "https://images.foody.vn/delivery/collection/s320x200/image-900af801-201118134119.jpeg";
        Picasso.with(convertView.getContext()).load(imageUri).into(holder.ivAvatar);

        return convertView;
    }

    static class MyViewHolder {
        Button btnRestaurant;
        ImageView ivAvatar;
        TextView tvName;
        TextView tvSale;
    }
}
