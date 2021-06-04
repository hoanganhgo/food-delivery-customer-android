package com.hcmus.fit.customer_apps.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.hcmus.fit.customer_apps.R;
import com.hcmus.fit.customer_apps.activities.CartActivity;
import com.hcmus.fit.customer_apps.activities.MerchantActivity;
import com.hcmus.fit.customer_apps.models.Cart;
import com.hcmus.fit.customer_apps.models.DishModel;
import com.hcmus.fit.customer_apps.models.ItemModel;
import com.hcmus.fit.customer_apps.models.OptionModel;
import com.hcmus.fit.customer_apps.models.OrderModel;
import com.hcmus.fit.customer_apps.models.UserInfo;
import com.hcmus.fit.customer_apps.utils.AppUtil;
import com.hcmus.fit.customer_apps.utils.WidgetUtil;
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
            cart.dishSelect = dishModel.clone();

            if (cart.dishSelect.getOptionList().isEmpty()) {
                updateCartButton(merchant, cart);
                return;
            }

            AlertDialog alertDialog = new AlertDialog.Builder(merchant).create();

            LayoutInflater inflaterLayout = (LayoutInflater) merchant.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View alertView = inflaterLayout.inflate(R.layout.alert_option, null);
            LinearLayout layoutOption = alertView.findViewById(R.id.layout_option);
            ImageButton btnDecrease = alertView.findViewById(R.id.btn_decrease);
            ImageButton btnIncrease = alertView.findViewById(R.id.btn_increase);
            TextView tvNumber = alertView.findViewById(R.id.tv_num_dish);
            Button btnAddCart = alertView.findViewById(R.id.btn_add_cart);

            List<OptionModel> optionList = cart.dishSelect.getOptionList();

            createOptionList(merchant, layoutOption, optionList);

            btnDecrease.setOnClickListener(v1 -> {
                if (cart.numSelect > 0) {
                    cart.numSelect--;
                    tvNumber.setText(String.valueOf(cart.numSelect));
                }
            });

            btnIncrease.setOnClickListener(v2 -> {
                cart.numSelect++;
                tvNumber.setText(String.valueOf(cart.numSelect));
            });

            btnAddCart.setOnClickListener(v3 -> {
                updateCartButton(merchant, cart);
                alertDialog.cancel();
            });

            alertDialog.setView(alertView);
            alertDialog.show();
        });

        merchant.btnCart.setOnClickListener(v4 -> {
            Intent intentCart = new Intent(merchant, CartActivity.class);
            merchant.startActivity(intentCart);
        });

        return convertView;
    }

    private void createOptionList(Context merchant, LinearLayout layoutOption, List<OptionModel> optionList) {
        for (OptionModel optionModel : optionList) {
            TextView optionTitle = WidgetUtil.getOptionTitle(merchant);
            optionTitle.setText(optionModel.getName());
            layoutOption.addView(optionTitle);

            if (optionModel.getMaxSelect() == 1) {
                RadioGroup radioGroup = WidgetUtil.getOptionGroup(merchant);
                List<ItemModel> itemList = optionModel.getItemList();

                for (int i = 0; i < itemList.size(); i++) {
                    ItemModel itemModel = itemList.get(i);
                    RadioButton radioButton = WidgetUtil.getRadioButton(merchant);
                    radioButton.setText(AppUtil.convertDishOption(itemModel.getName(), itemModel.getPrice()));
                    radioGroup.addView(radioButton);

                    if (i == 0) {
                        radioButton.setChecked(true);
                        itemList.get(i).setSelected(true);
                    }

                    radioButton.setOnClickListener(v1 -> {
                        for (ItemModel itemInner : itemList) {
                            itemInner.setSelected(false);
                        }

                        itemModel.setSelected(true);
                    });
                }

                layoutOption.addView(radioGroup);
            } else {
                List<ItemModel> itemList = optionModel.getItemList();

                for (ItemModel itemModel : itemList) {
                    CheckBox checkBox = WidgetUtil.getCheckBox(merchant);
                    checkBox.setText(AppUtil.convertDishOption(itemModel.getName(), itemModel.getPrice()));
                    layoutOption.addView(checkBox);

                    checkBox.setOnClickListener(v1 -> {
                        itemModel.setSelected(checkBox.isChecked());
                    });
                }
            }
        }

    }

    private void updateCartButton(MerchantActivity merchant, Cart cart) {
        cart.addDish(cart.dishSelect, cart.numSelect);
        cart.dishSelect = null;
        cart.numSelect = 1;

        merchant.rlCart.setVisibility(View.VISIBLE);
        merchant.tvOrderNum.setText(String.valueOf(cart.getNumDish()));
        merchant.tvTotalCart.setText(AppUtil.convertCurrency(cart.getTotal()));
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
