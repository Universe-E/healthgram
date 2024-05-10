package com.example.gp.setting.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gp.Items.Friend;
import com.example.gp.R;
import com.example.gp.Utils.ToastUtil;
import com.example.gp.data.database.UserDB;

import java.util.List;

/**
 * Adapter class to populate a RecyclerView with a list of friend objects
 * who send the friend request to current user.
 * @author Yulong Chen
 * @since 2024-05-07
 */
public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.RequestViewHolder> {
    private List<Friend> friends;

    public RequestAdapter(List<Friend> friends){
        this.friends = friends;
    }
    @NonNull
    @Override
    public RequestAdapter.RequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_request,parent,false);
        return new RequestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RequestAdapter.RequestViewHolder holder, int position) {
        Friend friend = friends.get(position);
        holder.bind(friend);

    }

    @Override
    public int getItemCount() {
        return friends.size();
    }

    public class RequestViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final ImageView mRequestFriendAvatar;
        private final TextView mRequestFriendName;
        private final Button mRequestAccept;
        private final Button mRequestReject;

        public RequestViewHolder(@NonNull View itemView){
            super(itemView);
            mRequestFriendAvatar = itemView.findViewById(R.id.iv_request_friend_avatar);
            mRequestFriendName = itemView.findViewById(R.id.tv_request_friend_name);
            mRequestAccept = itemView.findViewById(R.id.btn_request_accept);
            mRequestReject = itemView.findViewById(R.id.btn_request_reject);
            mRequestAccept.setOnClickListener(this);
            mRequestAccept.setOnClickListener(this);
        }

        public void bind(Friend friend) {
            mRequestFriendName.setText(friend.getNickname());
            mRequestFriendAvatar.setImageResource(friend.getAvatar());

        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (v == mRequestAccept) {
                // Update friend list
                Friend awaitFriend = friends.get(position);
                UserDB.addFriend(awaitFriend,this,"updateUI");
                // Delete this message
                friends.remove(position);
                notifyItemRemoved(position);
            } else if (v == mRequestReject) {
                // Delete this message
                friends.remove(position);
                notifyItemRemoved(position);
            }
        }

        public void updateUI(boolean isSuccess,Object object){
            Log.d("Accept request", "isSuccess: " + isSuccess);
            if (!isSuccess) {
                ToastUtil.show(itemView.getContext(), object.toString());
            } else {
                ToastUtil.show(itemView.getContext(), "Friend request accepted!");
            }
        }
    }
}
