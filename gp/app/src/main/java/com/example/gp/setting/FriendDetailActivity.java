package com.example.gp.setting;

import static androidx.databinding.DataBindingUtil.setContentView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.gp.BaseActivity;
import com.example.gp.DirectMessageActivity;
import com.example.gp.R;
import com.example.gp.Utils.ToastUtil;
import com.example.gp.data.Database;
import com.example.gp.data.database.UserDB;
import com.example.gp.databinding.ActivityFriendDetailBinding;
import com.google.android.material.switchmaterial.SwitchMaterial;

/**
 * @author Yulong Chen
 * @since 2024-05-07
 */
public class FriendDetailActivity extends BaseActivity implements CompoundButton.OnCheckedChangeListener {

    private com.example.gp.databinding.ActivityFriendDetailBinding binding;
    private String friendId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityFriendDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setUpTitleBar(R.layout.activity_friend_detail,"Friend");

        binding.swBlockMessage.setOnCheckedChangeListener(this);
        binding.llFriendDetailDirectMessageLayout.setOnClickListener(this);

        // Get the friend id from the friend recycler view adapter
        Intent intent = getIntent();
        if (intent != null) {
            friendId = intent.getStringExtra("friend_id");
        } else {
            ToastUtil.show(this,"Something goes wrong, please try again");
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v == binding.llFriendDetailDirectMessageLayout) {
            // Jump to Direct Message Activity(P2P part)
            Intent intent = new Intent(this, DirectMessageActivity.class);
            startActivity(intent);

        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            blockUserMessage();
        } else {
            unBlockUserMessage();
        }
    }

    private void unBlockUserMessage() {
        if (friendId != null) {
            Database.blockUserById(friendId,false,this,"toastResult");
        } else {
            ToastUtil.show(this,"Friend id doesn't exist!");
        }

    }

    private void blockUserMessage() {
        if (friendId != null) {
            Database.blockUserById(friendId,true,this,"toastResult");
        }
        else {
            ToastUtil.show(this,"Friend id doesn't exist!");
        }
    }

    public void toastResult(boolean isSuccess, Object object){
        if (!isSuccess) {
            ToastUtil.show(this,"Block failed!");
        } else {
            ToastUtil.show(this,"Block succeed!");
        }
    }
}