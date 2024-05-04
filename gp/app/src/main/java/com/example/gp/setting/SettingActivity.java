package com.example.gp.setting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gp.BaseActivity;
import com.example.gp.R;
import com.example.gp.databinding.ActivitySettingBinding;

public class SettingActivity extends BaseActivity {

    private final String activityName = "Settings";
    private ActivitySettingBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivitySettingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Set up title bar
        setUpTitleBar(R.layout.activity_setting,activityName);
        // example
        // toggleRightIcon();
        // toggleLeftIcon();

        // 4 changes
        binding.llSettingChangeAvatarLayout.setOnClickListener(this);
        binding.llSettingNickNameLayout.setOnClickListener(this);
        binding.llSettingChangePhoneNumberLayout.setOnClickListener(this);
        binding.llSettingChangePasswordLayout.setOnClickListener(this);
        // 3 privacy
        binding.llSettingRequestLayout.setOnClickListener(this);
        binding.llSettingVisibilityLayout.setOnClickListener(this);
        binding.llSettingFriendPermissionLayout.setOnClickListener(this);

    }

    /**
     * Jump to the certain setting pages
     * @param v which button is clicked
     */
    @Override
    public void onClick(View v) {
        super.onClick(v);
        if(v == binding.llSettingChangeAvatarLayout) {
//            Intent intent = new Intent(this,ChangeAvatarActivity.class);
        } else if(v == binding.llSettingNickNameLayout) {

        } else if(v == binding.llSettingChangePhoneNumberLayout) {

        } else if(v == binding.llSettingChangePasswordLayout) {

        } else if(v == binding.llSettingRequestLayout) {
            Intent intent = new Intent(this, RequestActivity.class);
            startActivity(intent);
        } else if(v == binding.llSettingVisibilityLayout) {
            Intent intent = new Intent(this, PostVisibilityActivity.class);
            startActivity(intent);
        } else if(v == binding.llSettingFriendPermissionLayout) {

        }
    }
}