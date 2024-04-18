package com.example.gp;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.gp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 使用 View Binding 加载布局
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        // 将布局的根视图设置为当前活动的内容视图
        setContentView(binding.getRoot());
    }
}
