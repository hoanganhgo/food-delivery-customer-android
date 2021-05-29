package com.hcmus.fit.customer_apps.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.hcmus.fit.customer_apps.R;
import com.hcmus.fit.customer_apps.activities.CartActivity;
import com.hcmus.fit.customer_apps.models.Cart;
import com.hcmus.fit.customer_apps.models.DishModel;
import com.hcmus.fit.customer_apps.models.UserInfo;
import com.hcmus.fit.customer_apps.utils.AppUtil;
import com.squareup.picasso.Picasso;

public class DishOrderAdapter extends BaseAdapter {

    private final LayoutInflater layoutInflater;

    public DishOrderAdapter(Context context) {
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return UserInfo.getInstance().getCart().getDishListSize();
    }

    @Override
    public Object getItem(int position) {
        return UserInfo.getInstance().getCart().getDishModel(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.adapter_dish_order, null);
            holder = new MyViewHolder();
            holder.tvName = convertView.findViewById(R.id.tv_dish_name);
            holder.ivAvatar = convertView.findViewById(R.id.iv_avatar_dish);
            holder.tvPrice = convertView.findViewById(R.id.tv_price);
            holder.tvNumDish = convertView.findViewById(R.id.tv_num_dish);
            holder.btnIncrease = convertView.findViewById(R.id.btn_increase);
            holder.btnDecrease = convertView.findViewById(R.id.btn_decrease);
            convertView.setTag(holder);
        } else {
            holder = (MyViewHolder) convertView.getTag();
        }

        Cart cart = UserInfo.getInstance().getCart();
        DishModel dishModel = cart.getDishModel(position);
        holder.tvName.setText(dishModel.getName());
        Picasso.with(convertView.getContext()).load(dishModel.getAvatar()).into(holder.ivAvatar);
        holder.tvPrice.setText( AppUtil.convertCurrency(dishModel.getPrice()));
        int num = cart.getNumberByIndex(position);
        holder.tvNumDish.setText(String.valueOf(num));

        CartActivity cartActivity = (CartActivity) convertView.getContext();

        holder.btnIncrease.setOnClickListener(v -> {
            int numAfter = cart.increaseNumber(position);
            holder.tvNumDish.setText(String.valueOf(numAfter));
            cartActivity.updateCart();
        });

        holder.btnDecrease.setOnClickListener(v -> {
            int numAfter = cart.decreaseNumber(position);
            holder.tvNumDish.setText(String.valueOf(numAfter));
            cartActivity.updateCart();
        });

        return convertView;
    }

    static class MyViewHolder {
        ImageView ivAvatar;
        TextView tvName;
        TextView tvPrice;
        TextView tvNumDish;
        ImageButton btnIncrease;
        ImageButton btnDecrease;
    }
}
