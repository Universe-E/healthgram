package com.example.gp.interaction;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;

import com.example.gp.BaseActivity;
import com.example.gp.Items.Post;
import com.example.gp.R;
import com.example.gp.Utils.ToastUtil;
import com.example.gp.data.Database;
import com.example.gp.databinding.ActivityNewPostBinding;
import com.example.gp.home.Fragment_home;

import java.io.IOException;

/**
 * This class represents the activity for creating a new post.
 * @author Tianci
 */

public class NewPostActivity extends BaseActivity {

    private final String activityName = "New Post";
    private ActivityNewPostBinding binding;

    private static final int PICK_IMAGE = 1;
    private Uri imageUri;
    private Post newPost;
    EditText heading;
    EditText content;
    EditText visibilityString;
    CheckBox isPublic;
    Button fireButton;

    /**
     * Called when the activity is starting.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityNewPostBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // initialize top bar
        setUpTitleBar(R.layout.activity_new_post, activityName);

        // Set up the components on the page
        setUpComponent();

        // Set up the checkbox listener
        setupVisibilityCheckboxListener();

        // Set up the button listener
        setUpUploadImageButtonListener();
        setUpFireButtonListener();
    }

    /**
     * Set up the components on the page.
     */
    private void setUpComponent() {
        this.heading = binding.etEditPostHeading;
        this.content = binding.etEditPostContent;
        this.visibilityString = binding.etEditWhoCanSeeThePost;
        this.isPublic = binding.cbIsPublic;
        this.fireButton = binding.btnFirePost;
    }

    /**
     * Set up the visibility checkbox listener.
     */
    private void setupVisibilityCheckboxListener() {

        // When the checkbox is checked, it means the post is public
        // When the checkbox is unchecked, it means the post is private
        binding.cbIsPublic.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // 1. Change the hint message
            if (isChecked)
                binding.etEditWhoCanSeeThePost.setHint("Everyone can see this post!");
            else
                binding.etEditWhoCanSeeThePost.setHint("All friends can see this unless you specify!");

            // 2. disable the text field if the checkbox is checked
            if (isChecked)
                binding.etEditWhoCanSeeThePost.setEnabled(false);
            else
                binding.etEditWhoCanSeeThePost.setEnabled(true);
        });

    }

    /**
     * Set up the upload image button listener.
     */
    private void setUpUploadImageButtonListener() {
        binding.btnUploadImage.setOnClickListener(v -> {
            openFileChooser();
        });
    }

    /**
     * Open the file chooser.
     */
    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE);
    }

    /**
     * Called when an activity you launched exits, giving you the requestCode you started it with,
     * the resultCode it returned, and any additional data from it.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            binding.ivPostImage.setImageURI(imageUri);
        }
    }

    /**
     * Set up the fire button listener.
     */
    private void setUpFireButtonListener() {
        binding.btnFirePost.setOnClickListener(v -> {
            // 1. Get the information from the components
            String heading = this.heading.getText().toString();
            String content = this.content.getText().toString();
            String visibilityString = this.visibilityString.getText().toString();
            boolean isPublic = this.isPublic.isChecked();

            // 1.5 Check if the heading and content are empty
            if (heading.isEmpty() || content.isEmpty()) {
                ToastUtil.showLong(this, "An empty post? Think it twice!");
                return;
            }

            // 2. Create a new post
            newPost = new Post(content, heading, isPublic);

            // 2.5 set the image
            if (imageUri != null) {
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                    newPost.setImg(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            // 4. Add the post to the database
            Database.savePostData(newPost, this, "postOut");
        });
    }

    /**
     * Called when the post is successfully posted.
     * @param isSuccess whether the post is successfully posted
     * @param object the object
     */
    public void postOut(boolean isSuccess, Object object) {
        if (!isSuccess) {
            ToastUtil.showLong(this, "Failed to post the new post");
        }

        // disable the button and all the input fields
        binding.btnFirePost.setEnabled(false);
        binding.etEditPostHeading.setEnabled(false);
        binding.etEditPostContent.setEnabled(false);
        binding.etEditWhoCanSeeThePost.setEnabled(false);
        binding.cbIsPublic.setEnabled(false);

        // show a toast message
        ToastUtil.showLong(this, "Successfully posted the new post");

        // finish the activity
        finish();

        // go back to the previous activity
        // onBackPressed();

        // Update: load the fragment home page after posting to show the new post
        Intent intent = new Intent(this, Fragment_home.class);
        startActivity(intent);
    }
}