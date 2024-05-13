package com.example.gp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gp.R;
import com.example.gp.Items.Friend;
import com.example.gp.databinding.ActivityFriendDetailBinding;
import com.example.gp.setting.FriendDetailActivity;

import java.util.List;

/**
 * Adapter for RecyclerView to display list of friends
 * @author Tianci
 */

public class FriendsRecyclerViewAdapter extends RecyclerView.Adapter<FriendsRecyclerViewAdapter.ViewHolder> {

    private List<Friend> friends;
    private OnItemClickListener listener;
    private Context context;

    public FriendsRecyclerViewAdapter(Context context, List<Friend> friends) {
        this.context = context;
        this.friends = friends;
    }

    public FriendsRecyclerViewAdapter(List<Friend> friends) {
        this.friends = friends;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
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
        this.friends = newFriends;
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(int position, Friend friend);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private LinearLayout friendContent;
        public TextView friendName;
        public ImageView friendAvatar;

        public ViewHolder(View itemView) {
            super(itemView);
            friendName = itemView.findViewById(R.id.tv_friend_name);
            friendAvatar = itemView.findViewById(R.id.iv_friend_avatar);
            friendContent = itemView.findViewById(R.id.ll_friend_content);
            friendContent.setOnClickListener(this);
            Log.d("FRVA10000", "inflate");
        }

        public void bind(Friend friend) {
            friendName.setText(friend.getNickname());
            // Assuming friendAvatarUrl is a method you might implement to get URL of the avatar
            friendAvatar.setImageResource(R.mipmap.sample_avatar_1); // This can be replaced with image loading logic
        }

        @Override
        public void onClick(View v) {
            Log.d("FRVA10000", "onClick: this outside");
            if (v == friendContent) {
                // Get the position of the item clicked
                Log.d("FRVA10000", "onClick: this inside");
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    Context context = itemView.getContext();
                    // Create an intent to start the FriendDetailActivity
                    Intent intent = new Intent(context, FriendDetailActivity.class);

//                    // pass key info
//                    Friend friend = friends.get(position);
//                    intent.putExtra("friend_id", friend.getId());

                    // Start the FriendDetailActivity
                    context.startActivity(intent);
                }
            }

        }
    }
}
