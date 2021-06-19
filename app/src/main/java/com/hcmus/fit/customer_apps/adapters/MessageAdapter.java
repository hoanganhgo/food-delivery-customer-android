package com.hcmus.fit.customer_apps.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hcmus.fit.customer_apps.R;
import com.hcmus.fit.customer_apps.models.ChatBox;
import com.hcmus.fit.customer_apps.models.ChatModel;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageAdapter extends BaseAdapter {
    private final LayoutInflater layoutInflater;
    private final ChatBox chatBox;

    public MessageAdapter(Context context, ChatBox chatBox) {
        this.layoutInflater = LayoutInflater.from(context);
        this.chatBox = chatBox;
    }

    @Override
    public int getCount() {
        return chatBox.getChatListSize();
    }

    @Override
    public Object getItem(int position) {
        return chatBox.getChatIndex(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.adapter_message, null);
            holder = new MyViewHolder();
            holder.ivUserAvatar = convertView.findViewById(R.id.iv_user_avatar);
            holder.tvUserMessage = convertView.findViewById(R.id.tv_user_message);
            holder.tvMyMessage = convertView.findViewById(R.id.tv_my_message);

            convertView.setTag(holder);
        } else {
            holder = (MyViewHolder) convertView.getTag();
        }

        ChatModel chatModel = chatBox.getChatIndex(position);
        if (chatModel.isMyself()) {
            holder.ivUserAvatar.setVisibility(View.GONE);
            holder.tvUserMessage.setVisibility(View.GONE);
            holder.tvMyMessage.setVisibility(View.VISIBLE);

            holder.tvMyMessage.setText(chatModel.getContent());
        } else {
            holder.ivUserAvatar.setVisibility(View.VISIBLE);
            holder.tvUserMessage.setVisibility(View.VISIBLE);
            holder.tvMyMessage.setVisibility(View.GONE);

            holder.ivUserAvatar.setImageBitmap(chatBox.getAvatar());
            holder.tvUserMessage.setText(chatModel.getContent());
        }

        return convertView;
    }

    static class MyViewHolder {
        CircleImageView ivUserAvatar;
        TextView tvUserMessage;
        TextView tvMyMessage;
    }
}
