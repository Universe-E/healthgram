package com.example.gp.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gp.Items.Notification;
import com.example.gp.R;

import java.util.List;

public class FriendFragmentViewAdapter extends RecyclerView.Adapter<FriendFragmentViewAdapter.ViewHolder> {
    private List<Notification> notifications;

    public FriendFragmentViewAdapter(List<Notification> notifications) {
        this.notifications = notifications;
    }

    @NonNull
    @Override
    public FriendFragmentViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_friend_notification, parent, false);
        return new FriendFragmentViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FriendFragmentViewAdapter.ViewHolder holder, int position) {
        Notification notification = notifications.get(position);

        // 设置 holder 中的视图
        holder.usernameTextView.setText(notification.getUserId());
        holder.timeTextView.setText(notification.getDate());
        holder.headlineTextView.setText(notification.getTitle());

    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView avatarImageView;
        TextView usernameTextView;
        TextView timeTextView;
        TextView headlineTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            avatarImageView = itemView.findViewById(R.id.iv_friend_avatar);
            usernameTextView = itemView.findViewById(R.id.tv_username);
            timeTextView = itemView.findViewById(R.id.tv_time);
            headlineTextView = itemView.findViewById(R.id.tv_headline);
        }
    }
}
