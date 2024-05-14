package com.example.gp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gp.DirectMessageActivity;
import com.example.gp.R;

import java.util.List;

/**
 * Adapter class to populate a RecyclerView with a list of message which two users sent to each other
 * @author Yulong Chen
 * @since 2024-05-14
 */
public class DirectMessageAdapter extends RecyclerView.Adapter<DirectMessageAdapter.DirectMessageViewHolder> {

    private List<String> messages;

    public DirectMessageAdapter(List<String> messages) {
        this.messages = messages;
    }

    @NonNull
    @Override
    public DirectMessageAdapter.DirectMessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_message, parent, false);
        return new DirectMessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DirectMessageAdapter.DirectMessageViewHolder holder, int position) {
        String message = messages.get(position);
        holder.bind(message);
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public static class DirectMessageViewHolder extends RecyclerView.ViewHolder {

        private TextView tvMessageText;

        public DirectMessageViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMessageText = itemView.findViewById(R.id.tv_message_text);
        }

        public void bind(String message) {
            tvMessageText.setText(message);
        }
    }
}
