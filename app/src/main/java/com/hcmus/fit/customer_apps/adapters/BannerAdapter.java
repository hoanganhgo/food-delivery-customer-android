package com.hcmus.fit.customer_apps.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hcmus.fit.customer_apps.R;
import com.hcmus.fit.customer_apps.models.Banner;

import java.util.List;

public class BannerAdapter extends RecyclerView.Adapter<BannerAdapter.MyViewHolder> {
    private List<Banner> bannerList;

    public BannerAdapter(List<Banner> bannerList) {
        this.bannerList = bannerList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_banner, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Banner banner = bannerList.get(position);
        holder.button.setBackgroundResource(R.drawable.banner);
    }

    @Override
    public int getItemCount() {
        return bannerList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageButton button;
        MyViewHolder(View view) {
            super(view);
            button = view.findViewById(R.id.btn_banner);
        }
    }
}
