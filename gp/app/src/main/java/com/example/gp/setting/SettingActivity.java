package com.example.gp.setting;

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

        setUpTitleBar(R.layout.activity_setting);

        // Change the value of the top_nav text
        binding.tbTopNavigationBar.tvActivityName.setText(activityName);

//        hideRightIcon();
////        hideLeftIcon();


    }

}