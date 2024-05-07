package com.example.gp.home.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gp.R;
import com.example.gp.Utils.ToastUtil;
import com.example.gp.data.Database;
import com.example.gp.Items.Post;
import com.example.gp.interaction.NewPostActivity;
import com.example.gp.interaction.PostCardAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.search.SearchView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HomeFragment extends Fragment {

    private PostCardAdapter postCardAdapter1;
    private PostCardAdapter postCardAdapter2;
    private SearchView searchView;
    private List<Post> postList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Initialize the search view, recycler views, and add note button
        initializeSearchView(view);
        initializeRecyclerViews(view);
        setupAddNoteButton(view);

        // Load post data from the database
        Database.PostDB.getPostsByTime(new Date(), 5, this, "loadPostCards");

        return view;
    }

    public void loadPostCards(boolean isSuccess, Object object) {
        if (isSuccess) {
            postList = (List<Post>) object;
            int halfSize = postList.size() / 2;
            List<Post> postList1 = postList.subList(0, halfSize);
            List<Post> postList2 = postList.subList(halfSize, postList.size());
            postCardAdapter1.setPostList(postList1);
            postCardAdapter2.setPostList(postList2);
            Log.d("HomeFragment", "Posts loaded successfully");
        } else {
            // Handle error
            ToastUtil.showLong(getContext(), "Failed to load posts");
        }
    }

    private void initializeSearchView(View view) {
        View searchLayout = view.findViewById(R.id.search_layout);
        searchView = searchLayout.findViewById(R.id.search_view);
        searchView.getEditText().addTextChangedListener(new SearchViewTextWatcher());
    }

    private void initializeRecyclerViews(View view) {
        RecyclerView recyclerView1 = view.findViewById(R.id.recycler_view_column1);
        RecyclerView recyclerView2 = view.findViewById(R.id.recycler_view_column2);

        recyclerView1.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView2.setLayoutManager(new LinearLayoutManager(getContext()));

        postCardAdapter1 = new PostCardAdapter();
        postCardAdapter2 = new PostCardAdapter();

        recyclerView1.setAdapter(postCardAdapter1);
        recyclerView2.setAdapter(postCardAdapter2);
    }

    private void setupAddNoteButton(View view) {
        FloatingActionButton fabAddNote = view.findViewById(R.id.fab_add_note);
        fabAddNote.setOnClickListener(v -> openAddNoteActivity());
    }

    private void openAddNoteActivity() {
        Intent intent = new Intent(getContext(), NewPostActivity.class);
        startActivity(intent);
    }

    private class SearchViewTextWatcher implements android.text.TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // Perform search logic if needed
        }

        @Override
        public void afterTextChanged(android.text.Editable s) {
        }
    }
}