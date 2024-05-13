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
import com.example.gp.databinding.ActivityPostEditingBinding;
import com.example.gp.home.Fragment_home;

import java.io.IOException;

/**
 * This class represents the activity for creating a new post.
 * @author Tianci
 */

public class NewPostActivity extends BaseActivity {

    private final String activityName = "New Post";
    private ActivityPostEditingBinding binding;

    private static final int PICK_IMAGE = 1;
    private Uri imageUri;
    private Post newPost;
    EditText heading;
    EditText content;
    EditText visibilityString;
    CheckBox isPublic;
    Button fireButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityPostEditingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // initialize top bar
        setUpTitleBar(R.layout.activity_post_editing, activityName);

        // Set up the components on the page
        setUpComponent();

        // Set up the checkbox listener
        setupVisibilityCheckboxListener();

        // Set up the button listener
        setUpUploadImageButtonListener();
        setUpFireButtonListener();
    }

    private void setUpComponent() {
        this.heading = binding.etEditPostHeading;
        this.content = binding.etEditPostContent;
        this.visibilityString = binding.etEditWhoCanSeeThePost;
        this.isPublic = binding.cbIsPublic;
        this.fireButton = binding.btnFirePost;
    }

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

    private void setUpUploadImageButtonListener() {
        binding.btnUploadImage.setOnClickListener(v -> {
            openFileChooser();
        });
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            binding.ivPostImage.setImageURI(imageUri);
        }
    }

    private void setUpFireButtonListener() {
        binding.btnFirePost.setOnClickListener(v -> {
            // 1. Get the information from the components
//            Bitmap img = this.imageUri == null ? null : binding.ivPostImage.getDrawingCache();
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

            // TODO: 3. handle visibility
            // parse the visibilityString to a list of user ids
            // for each user id, add it to the visibility list of the post

            // 4. Add the post to the database
            Database.savePostData(newPost, this, "postOut");

        });
    }

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