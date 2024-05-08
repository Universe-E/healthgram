package com.example.gp.interaction;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gp.Items.FriendRequest;
import com.example.gp.Items.Post;
import com.example.gp.R;
import com.example.gp.SimpleTestActivity;
import com.example.gp.Utils.ToastUtil;
import com.example.gp.data.Database;
import com.example.gp.data.UserData;
import com.example.gp.BaseActivity;

import java.util.Objects;
import java.util.Random;

public class PostDetailActivity extends BaseActivity {

    public static final String EXTRA_POST = "extra_post";

    private ImageView ivAuthorAvatar;
    private Button btnFollow;
    private ImageView ivPostImage;
    private TextView tvPostTitle;
    private TextView tvPostContent;
    private Button btnShare;
    private TextView tvAuthorName;

    private Post post;

    @SuppressLint({"MissingInflatedId", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);

        ivAuthorAvatar = findViewById(R.id.iv_post_detail_page_author_avatar);
        btnFollow = findViewById(R.id.btn_post_detail_page_follow);
        ivPostImage = findViewById(R.id.iv_post_detail_page_post_image);
        tvPostTitle = findViewById(R.id.tv_post_detail_page_post_title);
        tvPostContent = findViewById(R.id.tv_post_detail_page_post_content);
        btnShare = findViewById(R.id.btn_post_detail_page_share);
        tvAuthorName = findViewById(R.id.tv_post_detail_page_author_username);

        // Set up the title bar
        setUpTitleBar(R.layout.activity_post_detail, "Post Detail");

        // Get post data from intent
        Intent intent = getIntent();
        String postId = intent.getStringExtra("postId");

        // Load corresponding post data from database
        Database.PostDB.getPostByPostId(postId, this, "loadPostData");

    }

    @SuppressLint("SetTextI18n")
    private void showPostData() {
        if (post == null) {
            // Handle the case when post data is not loaded yet
            ToastUtil.showLong(this, "Post data is not loaded yet");
            return;
        }

        // Load author information
        this.ivAuthorAvatar.setImageResource(R.mipmap.sample_avatar_2); // TODO: Load author avatar
        this.tvAuthorName.setText("Author: " + post.getAuthorId());

        // Set follow button text
        // if the author is myself, hide the follow button
        if (isAuthorMyself()) {
            btnFollow.setVisibility(Button.GONE);
        } else {
            // if the author is already followed, set the button text to "Unfollow"
            // else set the button text to "Follow"
            if (isAuthorFollowed()) {
                btnFollow.setText("Unfollow");
            } else {
                btnFollow.setText("Follow");
            }
        }

        // Set post content: title, image, and content
        tvPostTitle.setText(post.getTitle());
        ivPostImage.setImageResource(R.mipmap.sample_avatar_1); // TODO: Load post image
        tvPostContent.setText(post.getmContent());

        // Set share button click listener
        btnShare.setOnClickListener(v -> {
            Intent shareIntent = new Intent(this, SharePageActivity.class);
            // TODO: Pass necessary data to ShareActivity
            shareIntent.putExtra("postId", post.getPostId());
            startActivity(shareIntent);
        });

        // Set follow button click listener
        btnFollow.setOnClickListener(v -> {
            if (isAuthorFollowed()) {
                // Unfollow the author
                // ...
                btnFollow.setText("Follow");
            } else {
                // Show follow confirmation dialog
                showAddFriendDialog();
                btnFollow.setText("Unfollow");
            }
        });
    }

    public void loadPostData(boolean isSuccess, Object object) {
        if (isSuccess) {
            // Store post data
            post = (Post) object;

            // Show post data
            showPostData();

            Log.d("PostDetailActivity", "Post data loaded successfully");
        } else {
            // Handle error
            ToastUtil.showLong(this, "Failed to load post data");
        }
    }

    private boolean isAuthorFollowed() {
        // TODO: Implement this method
//        UserData.
//        return Objects.equals(this.post.getAuthorId(), UserData.);
        return false;
    }
    private boolean isAuthorMyself() {
        return Objects.equals(this.post.getAuthorId(), UserData.userId.getValue());
    }

    private void showAddFriendDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you want to send a friend request？")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        FriendRequest fr = new FriendRequest("user1@gmail.com");
                        Database.UserDB.sendFriendRequestTo(fr,null,null);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        // Show the dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
