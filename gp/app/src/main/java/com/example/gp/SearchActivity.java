package com.example.gp;


import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity_layout);

        TextInputEditText editText = findViewById(R.id.searchTextInputEditText);
        TextInputLayout textInputLayout = findViewById(R.id.searchTextInputLayout);

        // 设置清除图标的点击监听器来执行搜索
        textInputLayout.setEndIconOnClickListener(view -> {
            performSearch(editText.getText().toString());
        });
    }

    private void performSearch(String query) {
        // 这里处理搜索逻辑，`query` 是用户输入的搜索关键词
        // 例如，你可以打印出来或者将其用于查询数据库、调用搜索API等
        System.out.println("Searching for: " + query);
        // 在实际应用中，你可能需要做更复杂的操作，如更新UI、调用API等
    }

}
