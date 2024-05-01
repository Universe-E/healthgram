package com.example.gp;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

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
            // Set the top navigation bar as the action bar
            setSupportActionBar(top_nav_bar);

            // Set the title of the activity
            getSupportActionBar().setTitle("Title");

            // Set the back icon, default is visible
            iv_back = findViewById(R.id.iv_back);
            iv_back.setOnClickListener(this);

            // Set the more icon, default is invisible
            iv_more = findViewById(R.id.iv_more);
            iv_more.setOnClickListener(this);
            iv_more.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        if(v == iv_back){
            // onBackPressed();
            toggleRightIcon();
        }
        else if(v == iv_more){
            // Do something
            Toast.makeText(this, "More", Toast.LENGTH_SHORT).show();
        }
    }

    /*
    * Toggle the left 'back' icon. Default is visible.
     */
    public void toggleLeftIcon(){
        if(iv_back.getVisibility() == View.VISIBLE){
            iv_back.setVisibility(View.GONE);
        }else{
            iv_back.setVisibility(View.VISIBLE);
        }
    }

    /*
    * Toggle the right 'more' icon. Default is invisible.
     */
    public void toggleRightIcon(){
        if(iv_more.getVisibility() == View.VISIBLE){
            iv_more.setVisibility(View.GONE);
        }else{
            iv_more.setVisibility(View.VISIBLE);
        }
    }
}
