package com.example.gp.data;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gp.BaseActivity;
import com.example.gp.R;
import com.example.gp.databinding.ActivityNoteDetailBinding;

/**
 * 跳转到帖子具体界面
 * Author: Xingchen Zhang
 * Date: 2024-05-04
 */

public class Activity_note_detail extends BaseActivity {
    private final String activityName = "Note Detail Page";

    private ActivityNoteDetailBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNoteDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setUpTitleBar(R.layout.activity_note_detail,activityName);
//        toggleLeftIcon();
        toggleRightIcon();

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