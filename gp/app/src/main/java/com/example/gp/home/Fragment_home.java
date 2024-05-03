package com.example.gp.home;

import android.os.Bundle;

import com.example.gp.BaseActivity;
import com.example.gp.R;
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
    /*home notification friends(dashboard)*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 使用 View Binding 设置布局
        binding = ActivityFragmentHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setUpTitleBar(R.layout.activity_fragment_home,activityName);
        toggleLeftIcon();

        BottomNavigationView navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_fragment_home);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }

}