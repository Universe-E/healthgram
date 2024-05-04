package com.example.gp.data;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gp.R;

/**
 * 跳转到帖子具体界面
 * Author: Xingchen Zhang
 * Date: 2024-05-04
 */

public class Activity_note_detail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detail);

        TextView titleView = findViewById(R.id.title);
        TextView descriptionView = findViewById(R.id.description);
        ImageView imageView = findViewById(R.id.image);

        // 从 Intent 中获取传递的笔记数据
        String title = getIntent().getStringExtra("title");
        String description = getIntent().getStringExtra("description");
        int imageResId = getIntent().getIntExtra("imageResId", -1);

        // 将数据设置到视图组件中
        titleView.setText(title);
        descriptionView.setText(description);
        if (imageResId != -1) {
            imageView.setImageResource(imageResId);
        }
    }
}