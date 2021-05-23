package com.hcmus.fit.customer_apps.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.hcmus.fit.customer_apps.R;
import com.hcmus.fit.customer_apps.activities.MerchantActivity;
import com.hcmus.fit.customer_apps.models.Cart;
import com.hcmus.fit.customer_apps.models.DishModel;
import com.hcmus.fit.customer_apps.models.UserInfo;
import com.hcmus.fit.customer_apps.utils.AppUtil;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DishAdapter extends BaseAdapter {

    private final LayoutInflater layoutInflater;
    public List<DishModel> dishList;

    public DishAdapter(Context context, List<DishModel> dishList) {
        this.layoutInflater = LayoutInflater.from(context);
        this.dishList = dishList;
    }

    @Override
    public int getCount() {
        return dishList.size();
    }

    @Override
    public Object getItem(int position) {
        return dishList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.adapter_dish, null);
            holder = new MyViewHolder();
            holder.ivAvatar = convertView.findViewById(R.id.iv_avatar_dish);
            holder.tvName = convertView.findViewById(R.id.tv_dish_name);
            holder.tvSoldNum = convertView.findViewById(R.id.tv_sold_num);
            holder.tvLikeNum = convertView.findViewById(R.id.tv_like_num);
            holder.tvPrice = convertView.findViewById(R.id.tv_price);
            holder.btnAddCart = convertView.findViewById(R.id.btn_add_cart);
            convertView.setTag(holder);
        } else {
            holder = (MyViewHolder) convertView.getTag();
        }

        DishModel dishModel = dishList.get(position);
        holder.tvName.setText(dishModel.getName());
        Picasso.with(convertView.getContext()).load(dishModel.getAvatar()).into(holder.ivAvatar);
        holder.tvSoldNum.setText(String.valueOf(dishModel.getTotalOrder()));
        holder.tvPrice.setText(String.valueOf(dishModel.getPrice()));

        MerchantActivity merchant = (MerchantActivity) convertView.getContext();
        holder.btnAddCart.setOnClickListener(v -> {
            Cart cart = UserInfo.getInstance().getCart();
            cart.addDish(dishModel);
            merchant.tvOrderNum.setText(String.valueOf(cart.getNumDish()));
            String totalCart = AppUtil.convertCurrency(cart.getTotal());
            merchant.tvTotalCart.setText(totalCart);
            merchant.rlCart.setVisibility(View.VISIBLE);
        });

        return convertView;
    }

    static class MyViewHolder {
        ImageView ivAvatar;
        TextView tvName;
        TextView tvSoldNum;
        TextView tvLikeNum;
        TextView tvPrice;
        ImageButton btnAddCart;
    }
}
