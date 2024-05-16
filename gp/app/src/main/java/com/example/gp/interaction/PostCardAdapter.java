package com.example.gp.interaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gp.Items.Post;
import com.example.gp.R;
import com.example.gp.data.PostRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PostCardAdapter extends RecyclerView.Adapter<PostCardAdapter.PostViewHolder> {

    private List<Post> postList = new ArrayList<>();
    private OnPostClickListener onPostClickListener;
//    private static final PostRepository POSTS_REPOSITORY = PostRepository.getInstance();

    public void clearPosts() {
    }

    public PostCardAdapter(List<Post> postList) {
        this.postList = postList;
    }
    public PostCardAdapter() {
    }

    public interface OnPostClickListener {
        void onPostClick(String position);
    }

    public void setOnPostClickListener(OnPostClickListener listener) {
        this.onPostClickListener = listener;
    }

    public void setPostList(List<Post> postList) {
        this.postList = postList;
        notifyDataSetChanged();
    }

//    public void setPostList() {
////        this.postList = POSTS_REPOSITORY.getPosts();
//        Log.d("PostCardAdapter", "setPostList: " + POSTS_REPOSITORY.getAllPosts().size());
//        notifyDataSetChanged();
//    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post_card, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        Post post = postList.get(position);
        String postId = post.getPostId();
//        Post post = POSTS_REPOSITORY.getAllPosts().get(position);

        // set the post title and content
        holder.textViewHeading.setText(post.getTitle());
        holder.textViewContent.setText(post.getPostContent());

        // set the post thumbnail image
        if (post.getImg() != null)
            holder.imageView.setImageBitmap(post.getImg());

        // set the click listener
        holder.itemView.setOnClickListener(v -> {
            if (onPostClickListener != null) {
                onPostClickListener.onPostClick(postId);
            }
        });
    }

    @Override
    public int getItemCount() {
        return postList.size();
//        return POSTS_REPOSITORY.getAllPosts().size();
    }
    public void updatePosts(List<Post> userposts) {
//        this.postList = userposts;
        // TODO: what is this?
        // postsData.clearPostsData();
        // postsData.addNewPosts(userposts);
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