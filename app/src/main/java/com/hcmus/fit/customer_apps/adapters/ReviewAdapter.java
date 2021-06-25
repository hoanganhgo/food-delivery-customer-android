package com.hcmus.fit.customer_apps.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.hcmus.fit.customer_apps.R;
import com.hcmus.fit.customer_apps.models.ReviewModel;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ReviewAdapter extends BaseAdapter {
    private final LayoutInflater layoutInflater;
    private final List<ReviewModel> reviewList;

    public ReviewAdapter(Context context, List<ReviewModel> reviewList) {
        this.layoutInflater = LayoutInflater.from(context);
        this.reviewList = reviewList;
    }

    @Override
    public int getCount() {
        return reviewList.size();
    }

    @Override
    public Object getItem(int position) {
        return reviewList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.adapter_review, null);
            holder = new MyViewHolder();
            holder.ivUserAvatar = convertView.findViewById(R.id.iv_user_avatar);
            holder.tvUserName = convertView.findViewById(R.id.tv_user_name);
            holder.tvCreateAt = convertView.findViewById(R.id.tv_create_at);
            holder.ratingBar = convertView.findViewById(R.id.rating);
            holder.tvComment = convertView.findViewById(R.id.tv_comment);
            convertView.setTag(holder);
        } else {
            holder = (MyViewHolder) convertView.getTag();
        }

        ReviewModel reviewModel = this.reviewList.get(position);
        Picasso.with(convertView.getContext()).load(reviewModel.getAvatarUrl()).into(holder.ivUserAvatar);
        holder.tvUserName.setText(reviewModel.getUserName());
        holder.tvCreateAt.setText(reviewModel.getDateTimeCreate());
        holder.ratingBar.setRating(reviewModel.getStars());
        holder.tvComment.setText(reviewModel.getContent());

        return convertView;
    }

    static class MyViewHolder {
        CircleImageView ivUserAvatar;
        TextView tvUserName;
        TextView tvCreateAt;
        RatingBar ratingBar;
        TextView tvComment;
    }
}
