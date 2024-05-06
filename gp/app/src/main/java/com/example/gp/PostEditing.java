package com.example.gp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;

import com.example.gp.databinding.ActivitySharePageBinding;

public class PostEditing extends BaseActivity {

    private final String activityName = "New Post";
    private ActivitySharePageBinding binding;

    EditText title;
    EditText content;
    EditText visibilityString;
    CheckBox isPublic;
    Button fireButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivitySharePageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // initialize top bar
        setUpTitleBar(R.layout.activity_share_page, activityName);

        // Set up the components on the page
        setUpComponent();

        // Set up the checkbox listener
        setupVisibilityCheckboxListener();

        // Set up the button listener
        setUpFireButtonListener();
    }

    @SuppressLint("WrongViewCast")
    private void setUpComponent() {
        this.title = findViewById(R.id.tv_post_title);
        this.content = findViewById(R.id.et_edit_post_content);
        this.visibilityString = findViewById(R.id.et_edit_who_can_see_the_post);
        this.isPublic = findViewById(R.id.cb_is_public);
        this.fireButton = findViewById(R.id.btn_fire_post);
    }

    private void setupVisibilityCheckboxListener() {
        isPublic.setOnCheckedChangeListener((buttonView, isChecked) -> {
            visibilityString.setEnabled(!isChecked); // Disable the EditText if CheckBox is checked
        });
    }

    private void setUpFireButtonListener() {
        // TODO: do something when the button is clicked
    }


}