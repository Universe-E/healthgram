package com.example.gp;// SearchActivity.java
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gp.R;
import com.google.android.material.search.SearchBar;
import com.google.android.material.search.SearchView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

public class SearchActivity extends AppCompatActivity {
    private static final String TAG = "SearchActivity";
    private SearchBar searchBar;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchBar = findViewById(R.id.search_bar);
        searchView = findViewById(R.id.search_view);

        // 从 Intent 获取初始查询
        String initialQuery = getIntent().getStringExtra("QUERY");
        if (initialQuery != null && !initialQuery.isEmpty()) {
            searchBar.setText(initialQuery);
            performSearch(initialQuery);
        }

        setupSearchListeners();
    }

    private void setupSearchListeners() {
        searchView.addTransitionListener((view, previousState, newState) -> {
            if (newState == SearchView.TransitionState.HIDDEN) {
                searchBar.setText("");
            }
        });

        searchView.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                performSearch(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void performSearch(String query) {
        // 打印搜索内容
        Log.d(TAG, "Searching for: " + query);
    }
}
