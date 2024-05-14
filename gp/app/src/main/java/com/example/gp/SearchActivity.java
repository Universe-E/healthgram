package com.example.gp; // 确保你的包名正确
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gp.Items.Post;
import com.google.android.material.search.SearchBar;
import com.google.android.material.search.SearchView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;


/**
 * Search activity function example code to implement
 * You can implement search component following this code file
 * Author: Xingchen Zhang
 * Date: 2024-05-03
 */
public class SearchActivity extends AppCompatActivity {
    private static final String TAG = "SearchActivity";
    private SearchBar searchBar;
    private SearchView searchView; // to show the search list
    private List<Post> searchResults; // store the search result

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchBar = findViewById(R.id.search_bar);
        searchView = findViewById(R.id.search_view);
        searchResults = new ArrayList<>(); // 初始化搜索结果
    }

    private void setupSearchListeners() {
        searchView.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 当搜索查询改变时打印输入内容
                Log.d(TAG, "User entered: " + s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

}
