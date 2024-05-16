package com.example.gp;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gp.Items.Post;
import com.example.gp.databinding.ActivityFragmentHomeBinding;
import com.example.gp.databinding.ActivitySearchBinding;
import com.google.android.material.search.SearchBar;
import com.google.android.material.search.SearchView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;


/**
 * Search activity function example code to implement (tests)
 * You can implement search component following this code file
 * Author: Xingchen Zhang
 * Date: 2024-05-03
 */
public class SearchActivity  extends BaseActivity {
    private static final String TAG = "SearchActivity";

    private ActivitySearchBinding binding;
    private SearchBar searchBar;
    private SearchView searchView; // to show the search list
    private List<Post> searchResults; // store the search result

    /**
     * Initialize search result
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        // use view binding to bind the layout
        binding = ActivitySearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setUpTitleBar(R.layout.activity_fragment_home,TAG);

        searchBar = findViewById(R.id.search_bar);
        searchView = findViewById(R.id.search_view);
        searchResults = new ArrayList<>();
    }

    private void setupSearchListeners() {
        searchView.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d(TAG, "User entered: " + s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

}
