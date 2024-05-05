package com.example.gp; // 确保你的包名正确
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.gp.R;
import com.example.gp.data.UserData;
import com.example.gp.data.UserData.Note;
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
    private List<Note> searchResults; // 存储搜索结果

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchBar = findViewById(R.id.search_bar);
        searchView = findViewById(R.id.search_view);
        searchResults = new ArrayList<>(); // 初始化搜索结果

        // 从 Intent 获取初始查询
        String initialQuery = getIntent().getStringExtra("Note1");
        if (initialQuery != null && !initialQuery.isEmpty()) {
            searchBar.setText(initialQuery);
            performSearch(initialQuery);
        }

        setupSearchListeners();
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

    private void performSearch(String query) {
        // 假设 searchNotes 是实现搜索逻辑的一个函数
        searchResults = searchNotes(query);
        Log.d(TAG, "Searching for: " + query);

        // 当查询结束后，将结果返回给调用的 Activity
        Intent resultIntent = new Intent();
        resultIntent.putParcelableArrayListExtra("searchResults", new ArrayList<>(searchResults));
        setResult(RESULT_OK, resultIntent);
    }

    private List<Note> searchNotes(String query) {
        List<Note> results = new ArrayList<>();
        // 添加搜索逻辑（此处为示例）
        // 根据查询字符串对 notes 进行筛选
        for (Note note : UserData.getNotes()) {
            if (note.name.contains(query) || note.description.contains(query)) {
                results.add(note);
            }
        }
        return results;
    }
}
