package com.example.gp;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;

import com.example.gp.databinding.ActivitySharePageBinding;

public class SharePageActivity extends BaseActivity {

    private final String activityName = "Share with...";
    private ActivitySharePageBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivitySharePageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // initialize top bar
        setUpTitleBar(R.layout.activity_share_page, activityName);
    }

    /**
     * Send the share message to the User from the input field
     * @param view the current view
     */
    public void sendTheShareMessage(View view) {
        // TODO: find the User by ID
        // do something

        // TODO: send the message to the User
        // do something

        Toast.makeText(SharePageActivity.this, "Yay, message sent!", Toast.LENGTH_SHORT).show();

        // back to the previous activity
        onBackPressed();
    }
}