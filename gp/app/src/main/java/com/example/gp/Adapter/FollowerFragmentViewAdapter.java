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

/**
 * FollowerNotification Fragment Adapter Class
 * The class to handle with data adapter and refresh on layout of the follower post fragment
 * Author: Xingchen Zhang
 * Date: 2024-05-06
 */

public class FollowerFragmentViewAdapter extends RecyclerView.Adapter<FollowerFragmentViewAdapter.ViewHolder> {

    private List<Notification> notifications;

    public FollowerFragmentViewAdapter(List<Notification> notifications) {
        this.notifications = notifications;
    }

    @NonNull
    @Override
    public FollowerFragmentViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_follower_notification, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull FollowerFragmentViewAdapter.ViewHolder holder, int position) {
        Notification notification = notifications.get(position);

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