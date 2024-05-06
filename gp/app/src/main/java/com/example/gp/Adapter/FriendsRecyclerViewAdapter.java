package com.example.gp.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gp.R;
import com.example.gp.Items.Friend; // Assuming you have a Friend model class
import com.example.gp.setting.Adapter.PostAdapter;

import java.util.List;

public class FriendsRecyclerViewAdapter extends RecyclerView.Adapter<FriendsRecyclerViewAdapter.ViewHolder> {

    private List<Friend> friends;
    private Context context;

    public FriendsRecyclerViewAdapter(List<Friend> friends) {
        this.friends = friends;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.content_friend, parent, false);
        return new FriendsRecyclerViewAdapter.ViewHolder(view);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // Get the data model based on position
        Friend friend = friends.get(position);
        holder.bind(friend);
    }

    @Override
    public int getItemCount() {
        return friends.size();
    }

    public void setOnItemClickListener(Object o) {
    }

    public static class ViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener{
        public TextView friendName;
        public ImageView friendAvatar;

        public ViewHolder(View itemView) {
            super(itemView);
            friendName = (TextView) itemView.findViewById(R.id.tv_friend_name);
            friendAvatar = (ImageView) itemView.findViewById(R.id.iv_friend_avatar);
        }

        public void bind(Friend friend) {
            friendName.setText(friend.getName());
            friendAvatar.setImageResource(friend.getAvatar());
        }

        @Override
        public void onClick(View v) {

        }
    }
}
