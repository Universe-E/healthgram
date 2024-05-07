package com.example.gp.interaction;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;

import com.example.gp.BaseActivity;
import com.example.gp.R;
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
            // TODO: do something when the button is clicked
        });
    }
}