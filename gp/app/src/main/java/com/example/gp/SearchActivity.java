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

public class SearchActivity extends AppCompatActivity {
    private static final String TAG = "SearchActivity";
    private SearchBar searchBar;
    private SearchView searchView;
    private List<Post> searchResults; // 存储搜索结果

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchBar = findViewById(R.id.search_bar);
        searchView = findViewById(R.id.search_view);
        searchResults = new ArrayList<>(); // 初始化搜索结果
//        Date time = TimeUtil.getCurDate();
//        Database.PostDB.getUserPost(time,10,this,"searchPosts");
//        // 从 Intent 获取初始查询
//        String initialQuery = getIntent().getStringExtra("Note1");
//        if (initialQuery != null && !initialQuery.isEmpty()) {
//            searchBar.setText(initialQuery);
//            performSearch(initialQuery);
//        }
//        //listener
//        setupSearchListeners();
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

//    private void performSearch(String query) {
//        // 假设 searchNotes 是实现搜索逻辑的一个函数
//        searchResults = searchPosts(query);
//        Log.d(TAG, "Searching for: " + query);
//
//        // 当查询结束后，将结果返回给调用的 Activity
//        Intent resultIntent = new Intent();
//        resultIntent.putParcelableArrayListExtra("searchResults", (ArrayList<? extends Parcelable>) new ArrayList<Post>(searchResults));
//        setResult(RESULT_OK, resultIntent);
//    }
//
//    private List<Post> searchPosts(String query) {
//        List<Post> results = new ArrayList<>();
//
//        // 根据新数据结构的属性过滤
//        for (Post post : Database.PostDB.getAllPosts()) {
//            if (post.getTitle().toLowerCase().contains(query) ||
//                    post.getmContent().toLowerCase().contains(query)) {
//                results.add(post);
//            }
//        }
//
//        return results;
//    }

}
