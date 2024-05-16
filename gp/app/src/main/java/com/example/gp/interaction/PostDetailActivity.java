package com.example.gp.interaction;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.example.gp.Items.Friend;
import com.example.gp.Items.FriendRequest;
import com.example.gp.Items.Post;
import com.example.gp.LoginActivity;
import com.example.gp.R;
import com.example.gp.Utils.ToastUtil;
import com.example.gp.data.Database;
import com.example.gp.data.FriendsData;
import com.example.gp.data.PostRepository;
import com.example.gp.BaseActivity;
import com.example.gp.data.database.PostDB;
import com.example.gp.data.database.UserDB;

import java.util.List;
import java.util.Objects;

/**
 * This class represents the activity for displaying the details of a post.
 * Author: Tianci Li
 * {@code @Author} Han Bao, Yulong Chen
 */
public class PostDetailActivity extends BaseActivity {
    private ImageView ivAuthorAvatar;
    private Button btnFollow;
    private ImageView ivPostImage;
    private TextView tvPostTitle;
    private TextView tvPostContent;
    private Button btnShare;
    private TextView tvAuthorName;
    private static final PostRepository POSTS_REPOSITORY = PostRepository.getInstance();
    private Post post;
    private ImageView ivLikePost;
    private TextView tvLikeAmount;
    private int likeCount;

    /**
     * Called when the activity is starting.
     */
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
        tvAuthorName = findViewById(R.id.tv_post_detail_page_author_username);
        ivLikePost = findViewById(R.id.iv_post_detail_like_post);
        tvLikeAmount = findViewById(R.id.tv_post_detail_like_amount);
        ivLikePost.setOnClickListener(this);

        // Set up the title bar
        setUpTitleBar(R.layout.activity_post_detail, "Post Detail");

        // Get post data from intent
        Intent intent = getIntent();
        String postId = intent.getStringExtra("postId");
        Boolean getPost = intent.getBooleanExtra("getPost", false);
        if (getPost) {
            Database.getPostById(postId, this, "loadPostData");
            return;
        }
        post = POSTS_REPOSITORY.getPostById(postId);

        // Load corresponding post data from database
        loadPostData(true, post);

        // load the post likes
        updateLikeCount(post.getLikeCount());

    }

    private void updateLikeCount(int likeCount) {
        String likeAmount = String.valueOf(likeCount);
        tvLikeAmount.setText(likeAmount);
    }


    /**
     * Show post data on the page
     */
    @SuppressLint("SetTextI18n")
    private void showPostData() {
        if (post == null) {
            // Handle the case when post data is not loaded yet
            ToastUtil.showLong(this, "Post data is not loaded yet");
            return;
        }

        // Load author information
        this.ivAuthorAvatar.setImageResource(R.mipmap.sample_avatar_2); // TODO: Load author avatar
        this.tvAuthorName.setText(post.getAuthorName());

        // Set follow button text
        // if the author is myself, hide the follow button
        if (isAuthorMyself()) {
            btnFollow.setText("Delete");
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
        if (post.getImg() != null) {
            ivPostImage.setImageBitmap(post.getImg());
        }
        tvPostContent.setText(post.getPostContent());

        // Set follow button click listener
        btnFollow.setOnClickListener(v -> {
            if (isAuthorMyself()) {
                Database.deletePost(post.getPostId(), null, null);
                POSTS_REPOSITORY.deletePostAt(post.getPostId());
                startActivity(new Intent(this, LoginActivity.class));
                return;
            } else if (isAuthorFollowed()) {
                // Unfollow the author
                // ...
                Database.unfollow(post.getAuthorId(), null, null);
                btnFollow.setText("Follow");
            } else {
                // Show follow confirmation dialog
                Friend newFriend = new Friend();
                newFriend.setId(post.getAuthorId());
                newFriend.setNickname(post.getAuthorName());
                Database.follow(newFriend, null, null);
                showAddFriendDialog();
                btnFollow.setText("Unfollow");
            }
        });
    }

    public void loadPostData(boolean isSuccess, Object object) {
        if (isSuccess) {
            // Store post data
            if (object instanceof Post) {
                post = (Post) object;
            } else {
                post = ((List<Post>) object).get(0);
            }

            // Show post data
            showPostData();

            Log.d("PostDetailActivity", "Post data loaded successfully");
        } else {
            // Handle error
            ToastUtil.showLong(this, "Failed to load post data");
        }
    }

    private boolean isAuthorFollowed() {
        FriendsData friends = FriendsData.getInstance();
        return friends.getAllFriends().containsKey(this.post.getAuthorId());
    }
    private boolean isAuthorMyself() {
        UserDB userDB = UserDB.getInstance();
        String currentUserId = userDB.getUserId();
        return Objects.equals(this.post.getAuthorId(), currentUserId);
    }

    private void showAddFriendDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you want to send a friend request？")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        FriendRequest fr = new FriendRequest(post.getAuthorId());
                        Database.sendFriendRequestTo(fr,null,null);
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

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v == ivLikePost) {
            Database.likePost(post.getPostId(),this,"processLikeResult");
        }
    }

    public void processLikeResult(boolean isSuccess, Object object){
        if (!isSuccess) {
           ToastUtil.show(this,"Like failed, please try again");
        } else {
            // Turn the like on
            ivLikePost.setImageResource(R.drawable.baseline_favorite_24);
            ToastUtil.show(this,"Oh yeah, your likes make the post shine even more");

            // Update the like count
            post.likeCount++;
            updateLikeCount(post.getLikeCount());
        }
    }
}
