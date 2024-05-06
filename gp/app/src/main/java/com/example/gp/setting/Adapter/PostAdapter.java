package com.example.gp.setting.Adapter;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gp.R;
import com.example.gp.Items.Post;

import java.util.List;

/**
 * Adapter class to populate a RecyclerView with a list of Post objects.
 * Author: Yulong Chen
 * Date: 2024-05-04
 */
public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {
    private static List<Post> posts;

    public PostAdapter(List<Post> posts) {
        this.posts = posts;
    }
    @NonNull
    @Override
    public PostAdapter.PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.content_post, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostAdapter.PostViewHolder holder, int position) {
        Post post = posts.get(position);
        holder.bind(post);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }


    public static class PostViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tv_is_public;
        private TextView tv_post_title;
        private TextView tv_post_content;
        private Button btn_change_state;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_is_public = itemView.findViewById(R.id.tv_is_public);
            tv_post_title = itemView.findViewById(R.id.tv_post_title);
            tv_post_content = itemView.findViewById(R.id.tv_post_content);
            btn_change_state = itemView.findViewById(R.id.btn_change_state);
            btn_change_state.setOnClickListener(this);
        }
        /**
         * Method to bind a Post object to the views in the ViewHolder.
         * @param post The Post object to bind.
         */
        public void bind(Post post) {
            tv_post_title.setText(post.title);
            tv_post_content.setText(post.mContent);
            updateIsPublicTextView(post.isPublic);
        }
        @Override
        public void onClick(View v) {
            Log.d("ClickListener", "Success");
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                Post post = posts.get(position);
                // Change the state
                post.setPublic(!post.isPublic);
                // update the UI
                updateIsPublicTextView(post.isPublic);
            }
        }
        private void updateIsPublicTextView(boolean isPublic) {
            tv_is_public.setText(isPublic ? "Public" : "Private");
            tv_is_public.setTextColor(isPublic ? Color.GREEN : Color.RED);
        }
    }
}
