package com.hcmus.fit.customer_apps.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.MyViewHolder> {

    private List<Restaurant> restaurantList = new LinkedList<>();

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
    }

    @Override
    public int getItemCount() {
        return restaurantList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        Button btnRestaurant;
        ImageView ivAvatar;
        TextView tvName;
        TextView tvSale;

        MyViewHolder(View view) {
            super(view);
            btnRestaurant = view.findViewById(R.id.btn_restaurant);
            ivAvatar = view.findViewById(R.id.iv_avatar_restaurant);
            tvName = view.findViewById(R.id.tv_restaurant_name);
            tvSale = view.findViewById(R.id.tv_big_sale);

            String imageUri = "https://images.foody.vn/delivery/collection/s320x200/image-900af801-201118134119.jpeg";
            Picasso.with(view.getContext()).load(imageUri).into(ivAvatar);
        }
    }
}
