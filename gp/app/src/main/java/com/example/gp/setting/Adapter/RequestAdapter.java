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
import com.example.gp.Items.FriendRequest;
import com.example.gp.R;
import com.example.gp.Utils.ToastUtil;
import com.example.gp.data.Database;
import com.example.gp.data.database.UserDB;

import java.util.List;

/**
 * Adapter class to populate a RecyclerView with a list of friend objects
 * who send the friend request to current user.
 * @author Yulong Chen
 * @since 2024-05-07
 */
public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.RequestViewHolder> {

    private List<FriendRequest> friendRequests;

    public RequestAdapter(List<FriendRequest> friendRequests){
        this.friendRequests = friendRequests;
    }
    @NonNull
    @Override
    public RequestAdapter.RequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_request,parent,false);
        return new RequestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RequestAdapter.RequestViewHolder holder, int position) {
        FriendRequest friendRequest = friendRequests.get(position);
        holder.bind(friendRequest);

    }

    @Override
    public int getItemCount() {
        return friendRequests.size();
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
            mRequestReject.setOnClickListener(this);
        }

        public void bind(FriendRequest friendRequest) {
            mRequestFriendName.setText(friendRequest.getSenderName());
            mRequestFriendAvatar.setImageResource(R.mipmap.user_avatar);
        }

        /**
         * Click the accept to call the add friend method
         * Click the reject to call the refuse request method
         * and delete the content after being clicked
         * @param v The view that was clicked.
         */
        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            FriendRequest awaitFriendRequest = friendRequests.get(position);
            if (v == mRequestAccept) {
                // Update friend list
//                Database.follow(awaitFriend, this, "updateUI");
                awaitFriendRequest.setRead(true);
                awaitFriendRequest.setAccepted(true);
                Database.processFriendRequest(awaitFriendRequest,this,"updateUI");
                // Delete this message
                friendRequests.remove(position);
                notifyItemRemoved(position);
            } else if (v == mRequestReject) {
                // Delete this message
                friendRequests.remove(position);
                notifyItemRemoved(position);
                ToastUtil.show(v.getContext(), "Friend request rejected!");
                awaitFriendRequest.setRead(true);
                awaitFriendRequest.setAccepted(false);
                Database.processFriendRequest(awaitFriendRequest,null,null);
            }
        }

        /**
         * Call back method
         * Shows whether the request which sent to other user is successfully sent or not
         * @param isSuccess the process is successful or not
         * @param object the friendRequest
         */
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
