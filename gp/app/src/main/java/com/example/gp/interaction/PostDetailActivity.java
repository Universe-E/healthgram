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

import com.example.gp.Items.Friend;
import com.example.gp.Items.FriendRequest;
import com.example.gp.Items.Post;
import com.example.gp.MainActivity;
import com.example.gp.R;
import com.example.gp.Utils.ToastUtil;
import com.example.gp.data.Database;
import com.example.gp.data.FriendsData;
import com.example.gp.data.PostRepository;
import com.example.gp.BaseActivity;
import com.example.gp.data.database.UserDB;

import java.util.Objects;

public class PostDetailActivity extends BaseActivity {

    public static final String EXTRA_POST = "extra_post";

    private ImageView ivAuthorAvatar;
    private Button btnFollow;
    private ImageView ivPostImage;
    private TextView tvPostTitle;
    private TextView tvPostContent;
    private Button btnShare;
    private TextView tvAuthorName;
    private static final PostRepository POSTS_REPOSITORY = PostRepository.getInstance();
    private int position;

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
//        post = intent.getParcelableExtra("post");
//        position = intent.getIntExtra("position", 0);
//        post = POSTS_REPOSITORY.getAllPosts().get(position);
        post = POSTS_REPOSITORY.getPostById(postId);

        // Load corresponding post data from database
        loadPostData(true, post);

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
//        ivPostImage.setImageResource(R.mipmap.sample_avatar_1); // TODO1: Load post image
        if (post.getImg() != null) {
            ivPostImage.setImageBitmap(post.getImg());
        }
        tvPostContent.setText(post.getPostContent());

        // Set share button click listener
        btnShare.setOnClickListener(v -> {
            Intent shareIntent = new Intent(this, SharePageActivity.class);
            // TODO: Pass necessary data to ShareActivity
            shareIntent.putExtra("postId", post.getPostId());
            startActivity(shareIntent);
        });

        // Set follow button click listener
        btnFollow.setOnClickListener(v -> {
            if (isAuthorMyself()) {
                Database.deletePost(post.getPostId(), null, null);
                POSTS_REPOSITORY.deletePostAt(position);
                startActivity(new Intent(this, MainActivity.class));
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
}
