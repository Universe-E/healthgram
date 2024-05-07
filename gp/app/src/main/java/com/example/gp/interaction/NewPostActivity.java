package com.example.gp.interaction;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;

import com.example.gp.BaseActivity;
import com.example.gp.Items.Post;
import com.example.gp.R;
import com.example.gp.Utils.ToastUtil;
import com.example.gp.data.Database;
import com.example.gp.data.UserData;
import com.example.gp.databinding.ActivityPostEditingBinding;

/**
 * This class represents the activity for creating a new post.
 * @author Tianci
 */

public class NewPostActivity extends BaseActivity {

    private final String activityName = "New Post";
    private ActivityPostEditingBinding binding;

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

    private void setUpFireButtonListener() {
        binding.btnFirePost.setOnClickListener(v -> {
            // 1. Get the information from the components
            String heading = this.heading.getText().toString();
            String content = this.content.getText().toString();
            String visibilityString = this.visibilityString.getText().toString();
            boolean isPublic = this.isPublic.isChecked();

            // 2. Create a new post
            Post newPost = new Post(content, heading, isPublic);

            // TODO: 3. handle visibility
            // parse the visibilityString to a list of user ids
            // for each user id, add it to the visibility list of the post

            // 4. Add the post to the database
            Database.PostDB.savePostData(newPost, this, "postOut");

        });
    }

    public void postOut(boolean isSuccess, Object object) {
        Log.d("NewPostActivity", "mamba out");
        if (!isSuccess) {
            Toast.makeText(this, "Post failed!", Toast.LENGTH_SHORT).show();
        } else {
//            Toast.makeText(this, (String)object, Toast.LENGTH_SHORT).show();
            ToastUtil.showLong(this, (String)object);
            onBackPressed();
        }
    }
}