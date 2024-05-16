package com.example.gp.setting;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;

import com.example.gp.BaseActivity;
import com.example.gp.LoginActivity;
import com.example.gp.R;
import com.example.gp.data.Database;
import com.example.gp.data.database.UserDB;
import com.example.gp.databinding.ActivitySettingBinding;

/**
 * Setting page, showing user settings
 * @author Yulong Chen
 */
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
        // quit
        binding.btnSettingMainQuit.setOnClickListener(this);

        // Get the user info
        UserDB userDB = UserDB.getInstance();
        String username = userDB.getUsername();

        // initialize the user info layout
        binding.tvSettingNickname.setText(username);

    }

    /**
     * Jump to the certain setting pages
     * @param v which button is clicked
     */
    @Override
    public void onClick(View v) {
        super.onClick(v);
        if(v == binding.llSettingChangeAvatarLayout) {
            Intent intent = new Intent(this,ChangeAvatarActivity.class);
            startActivity(intent);
        } else if(v == binding.llSettingNickNameLayout) {
            // extensive function

        } else if(v == binding.llSettingChangePhoneNumberLayout) {
            // extensive function

        } else if(v == binding.llSettingChangePasswordLayout) {
            // extensive function

        } else if(v == binding.llSettingRequestLayout) {
            Intent intent = new Intent(this, RequestActivity.class);
            startActivity(intent);
        } else if(v == binding.llSettingVisibilityLayout) {
            Intent intent = new Intent(this, PostVisibilityActivity.class);
            startActivity(intent);
        } else if(v == binding.btnSettingMainQuit) {
            Database.signOut(null,null);
            Intent intent = new Intent(this, LoginActivity.class);
            // start new activity, clear old tasks
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }
    }

    /**
     * Reload the avatar to ensure activity shows the latest avatar
     */
    @Override
    protected void onResume() {
        super.onResume();
        loadAvatarData();
    }

    /**
     * get the avatar data from the firebase
     */
    private void loadAvatarData() {
        String avatarUUID = UserDB.getInstance().getAvatarUUID();
        int userAvatar = Integer.parseInt(avatarUUID);

        if (avatarUUID == null) {
            avatarUUID = "0";
        }
        Log.d("SettingActivity", "avatarUUID: " + avatarUUID);
        if (userAvatar == 0 || userAvatar == 1) {
            userAvatar = R.drawable.user_avatar;
            Log.d("SettingActivity", "userAvatar: " + String.valueOf(userAvatar));
            Database.changeAvatar(String.valueOf(userAvatar),null,null);
        }
        Log.d("loadAvatarData", "loadAvatarData: "+ avatarUUID);
        binding.ivSettingAvatar.setImageResource(userAvatar);
    }
}