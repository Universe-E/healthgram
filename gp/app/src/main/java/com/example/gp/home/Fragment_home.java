package com.example.gp.home;

import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

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
public class Fragment_home extends AppCompatActivity {

    private ActivityFragmentHomeBinding binding;
    /*通过点击下方图标跳转到不同的其他界面: home notification friends(dashboard)*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_home);
        // temporarily change to toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_fragment_home);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }

}