package com.hcmus.fit.customer_apps.adapters;

import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hcmus.fit.customer_apps.R;
import com.hcmus.fit.customer_apps.activities.MerchantActivity;
import com.hcmus.fit.customer_apps.models.Restaurant;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.MyViewHolder> {

    public List<Restaurant> restaurantList;

    public RestaurantAdapter(List<Restaurant> restaurantList) {
        this.restaurantList = restaurantList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_restaurant, parent, false);
        return new RestaurantAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Restaurant restaurant = restaurantList.get(position);
        holder.ivOpening.setColorFilter(restaurant.isOpening() ? Color.GREEN : Color.RED);
        holder.tvName.setText(restaurant.getName());
        Picasso.with(holder.itemView.getContext()).load(restaurant.getAvatar()).into(holder.ivAvatar);
        holder.btnRestaurant.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), MerchantActivity.class);
            intent.putExtra("id", restaurant.getId());
            intent.putExtra("opening", restaurant.isOpening());
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return restaurantList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        CircleImageView ivOpening;
        Button btnRestaurant;
        ImageView ivAvatar;
        TextView tvName;

        MyViewHolder(View view) {
            super(view);
            ivOpening = view.findViewById(R.id.iv_opening);
            btnRestaurant = view.findViewById(R.id.btn_restaurant);
            ivAvatar = view.findViewById(R.id.iv_avatar_restaurant);
            tvName = view.findViewById(R.id.tv_restaurant_name);
        }
    }
}
