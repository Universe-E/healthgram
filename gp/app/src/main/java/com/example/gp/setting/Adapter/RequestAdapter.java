package com.example.gp.setting.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gp.Items.Friend;
import com.example.gp.R;

import java.util.List;

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

    public class RequestViewHolder extends RecyclerView.ViewHolder {
        public RequestViewHolder(@NonNull View itemView){
            super(itemView);
        }

        public void bind(Friend friend) {

        }
    }
}
