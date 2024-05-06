package com.example.gp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gp.R;
import com.example.gp.Items.Friend;

import java.util.List;

/**
 * Adapter for RecyclerView to display list of friends
 * @author Tianci
 */

public class FriendsRecyclerViewAdapter extends RecyclerView.Adapter<FriendsRecyclerViewAdapter.ViewHolder> {

    private List<Friend> friends;
    private OnItemClickListener listener;
    private Context context;

    public FriendsRecyclerViewAdapter(List<Friend> friends, Context context, OnItemClickListener listener) {
        this.friends = friends;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.content_friend, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Friend friend = friends.get(position);
        holder.bind(friend);
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(position, friend);
            }
        });
    }

    @Override
    public int getItemCount() {
        return friends.size();
    }

    public void updateFriends(List<Friend> newFriends) {
        friends = newFriends;
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(int position, Friend friend);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView friendName;
        public ImageView friendAvatar;

        public ViewHolder(View itemView) {
            super(itemView);
            friendName = itemView.findViewById(R.id.tv_friend_name);
            friendAvatar = itemView.findViewById(R.id.iv_friend_avatar);
        }

        public void bind(Friend friend) {
            friendName.setText(friend.getName());
            // Assuming friendAvatarUrl is a method you might implement to get URL of the avatar
            friendAvatar.setImageResource(R.mipmap.sample_avatar_1); // This can be replaced with image loading logic
        }
    }
}
