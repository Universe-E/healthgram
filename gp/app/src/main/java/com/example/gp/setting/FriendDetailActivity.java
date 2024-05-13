package com.example.gp.setting;

import static androidx.databinding.DataBindingUtil.setContentView;

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
import com.example.gp.R;
import com.example.gp.databinding.ActivityFriendDetailBinding;
import com.google.android.material.switchmaterial.SwitchMaterial;

/**
 * @author Yulong Chen
 * @since 2024-05-07
 */
public class FriendDetailActivity extends BaseActivity implements CompoundButton.OnCheckedChangeListener {

    private com.example.gp.databinding.ActivityFriendDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityFriendDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setUpTitleBar(R.layout.activity_friend_detail,"Friend");

        binding.swBlockMessage.setOnCheckedChangeListener(this);
        binding.llFriendDetailDirectMessageLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v == binding.llFriendDetailDirectMessageLayout) {
            // TODO: Jump to Direct Message Activity(P2P part)

        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(isChecked){
            blockUserMessage();
        } else {
            unBlockUserMessage();
        }
    }

    private void unBlockUserMessage() {
        // TODO: p2p part
    }

    private void blockUserMessage() {
        // TODO: p2p part
    }
}