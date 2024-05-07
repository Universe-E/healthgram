package com.example.gp.interaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gp.Items.Post;
import com.example.gp.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PostCardAdapter extends RecyclerView.Adapter<PostCardAdapter.PostViewHolder> {

    private List<Post> postList = new ArrayList<>();
    private Random random = new Random();

    public void setPostList(List<Post> postList) {
        this.postList = postList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post_card, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        Post post = postList.get(position);

        // Set the post data to the views
        holder.textViewHeading.setText(post.getTitle());
        holder.textViewContent.setText(post.getmContent());

        // Set a random avatar image for the post
        int randomNumber = random.nextInt(8) + 1;
        String avatarName = "sample_avatar_" + randomNumber;
        holder.imageView.setImageResource(holder.itemView.getResources().getIdentifier(avatarName, "drawable", holder.itemView.getContext().getPackageName()));

        // Set a random height for the post card
        int minHeight = 700;
        int maxHeight = 900;
        int randomHeight = random.nextInt(maxHeight - minHeight + 1) + minHeight;
        holder.itemView.getLayoutParams().height = randomHeight;
        holder.itemView.requestLayout();
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }
    public void updatePosts(List<Post> userposts) {
        this.postList = userposts;
        notifyDataSetChanged();
    }

    public static class PostViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textViewHeading;
        TextView textViewContent;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv_post_thumbnail);
            textViewHeading = itemView.findViewById(R.id.tv_post_heading);
            textViewContent = itemView.findViewById(R.id.tv_post_content_snippet);
        }
    }
}