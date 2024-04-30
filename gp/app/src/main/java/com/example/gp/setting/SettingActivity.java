package com.example.gp.setting;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import com.example.gp.databinding.ActivitySettingBinding;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {

    private final String activityName = "Settings";
    private ActivitySettingBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.tbTopNavigationBar.tbTopNavigationBar);

        // Change the value of the top_nav text
        binding.tbTopNavigationBar.tvActivityName.setText(activityName);
        binding.tbTopNavigationBar.ivBack.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v == binding.tbTopNavigationBar.ivBack){
            onBackPressed();
        }
    }
}