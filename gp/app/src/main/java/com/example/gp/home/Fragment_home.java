package com.example.gp.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.example.gp.BaseActivity;
import com.example.gp.R;
import com.example.gp.databinding.ActivitySettingBinding;
import com.example.gp.setting.SettingActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.gp.databinding.ActivityFragmentHomeBinding;
/**
 * Fragment_home
 * The main page for switch between three subpage: home/notification/friends
 * Author: Xingchen Zhang
 * Date: 2024-05-01
 */
public class Fragment_home extends BaseActivity {
    private final String activityName = "GP community";

    private ActivityFragmentHomeBinding binding;
    private ImageView iv_avatar;

    /*通过点击下方图标跳转到不同的其他界面: home notification friends(dashboard)*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 使用 View Binding 设置布局
        binding = ActivityFragmentHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setUpTitleBar(R.layout.activity_fragment_home,activityName);
        setLeftIcon(R.drawable.user_avatar);

        iv_avatar = findViewById(R.id.iv_back);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_fragment_home);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }

    @Override
    public void onClick(View v) {
        if(v == iv_avatar) {
            Intent intent = new Intent(this, SettingActivity.class);
            startActivity(intent);
        }
    }
}