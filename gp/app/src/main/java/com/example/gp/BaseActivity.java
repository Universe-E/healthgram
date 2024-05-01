package com.example.gp;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

/**
 * Please extend this class instead of AppCompatActivity
 * if you want to set up the title bar for activities with top navigation bar.
 * ADD any code you find common in the activity
 * eg. Change the activity name
 * Author: Yulong Chen
 * Date: 2024-04-30
 */
public class BaseActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView iv_back;
    private ImageView iv_more;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    /**
     * The basic setting for the top navigation bar
     * Click back icon can return to the previous activity
     * @param layoutResId certain activity
     */
    protected void setUpTitleBar(int layoutResId) {
        setContentView(layoutResId);
        Toolbar top_nav_bar = findViewById(R.id.tb_top_navigation_bar);
        if (top_nav_bar != null) {
            setSupportActionBar(top_nav_bar);
            iv_back = findViewById(R.id.iv_back);
            iv_back.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        if(v == iv_back){
            onBackPressed();
        }
    }
//    public void toggleLeftIcon(){
//        iv_back.setVisibility(View.GONE);
//    }
//
//    public void toggleRightIcon() {
//        iv_more.setVisibility(View.GONE);
//    }
}
