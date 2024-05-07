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
import com.example.gp.Utils.ToastUtil;
import com.example.gp.data.Database;
import com.example.gp.data.UserData;

import java.util.List;

/**
 * Adapter class to populate a RecyclerView with a list of Post objects.
 * @author : Yulong Chen
 * @since : 2024-05-04
 */
public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {
    private static final String TAG = "PA100000000";
    private static List<Post> posts;

    public PostAdapter(List<Post> posts) {
        PostAdapter.posts = posts;
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
        Log.d(TAG,post.getPostId());
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
        private Post post;

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
            tv_is_public.setText(post.isPublic ? "Public" : "Private");
            tv_is_public.setTextColor(post.isPublic ? Color.GREEN : Color.RED);
        }
        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                post = posts.get(position);
                // Change the state
                post.setPublic(!post.isPublic);
                Database.PostDB.setPublic(post.getPostId(), post.getIsPublic(),this,"updateIsPublicTextView");
                Log.d("PVA100000", post.toString());
            }
        }
        private void updateIsPublicTextView(boolean isSuccess,int postId) {
            if (!isSuccess) {
                Log.d(TAG, "failed!");
                ToastUtil.show(itemView.getContext(), "Change state failed, Please try again");
            } else {
                Log.d(TAG, "postId:" + postId);
                ToastUtil.show(itemView.getContext(), "Successfully change status");
                tv_is_public.setText(post.isPublic ? "Public" : "Private");
                tv_is_public.setTextColor(post.isPublic ? Color.GREEN : Color.RED);
            }

        }
    }
}
