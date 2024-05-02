// SearchActivity.java
package com.example.gp;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_input_layout);

        TextInputEditText editText = findViewById(R.id.searchTextInputEditText);
        TextInputLayout textInputLayout = findViewById(R.id.searchTextInputLayout);

        // click icon to search
        textInputLayout.setEndIconOnClickListener(view -> {
            String query = editText.getText().toString().trim();
            if (!query.isEmpty()) {
                performSearch(query);
            } else {
                Log.d("SearchActivity", "Search query is empty");
            }
        });
    }

    private void performSearch(String query) {
        // handle search logic
        Log.d("SearchActivity", "Searching for: " + query);
        // update UI to print the result...
    }
}
