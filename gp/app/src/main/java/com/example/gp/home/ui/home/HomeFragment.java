package com.example.gp.home.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gp.R;
import com.example.gp.Utils.ToastUtil;
import com.example.gp.Items.Post;
import com.example.gp.data.Database;
import com.example.gp.interaction.NewPostActivity;
import com.example.gp.interaction.PostCardAdapter;
import com.example.gp.interaction.PostDetailActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.search.SearchView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Home fragment
 * The page to show home page of posts sent by all other users you followed
 * Author: Xingchen Zhang
 * Date: 2024-05-01
 */
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
        Database.getPostsByTime(new Date(), 10, this, "loadPostCards");

        return view;
    }

    public List<Post> loadPostCards(boolean isSuccess, Object object) {
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

        return postList;
    }

    private void initializeSearchView(View view) {
        View searchLayout = view.findViewById(R.id.search_layout);
        searchView = searchLayout.findViewById(R.id.search_view);
        setupSearchViewListener();
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

        postCardAdapter1.setOnPostClickListener(this::onPostClick);
        postCardAdapter2.setOnPostClickListener(this::onPostClick);
    }

    private void onPostClick(Post post) {
        Intent intent = new Intent(getContext(), PostDetailActivity.class);
        intent.putExtra("postId", post.getPostId());
        startActivity(intent);
    }

    private void setupAddNoteButton(View view) {
        FloatingActionButton fabAddNote = view.findViewById(R.id.fab_add_note);
        fabAddNote.setOnClickListener(v -> openAddNoteActivity());
    }

    private void openAddNoteActivity() {
        Intent intent = new Intent(getContext(), NewPostActivity.class);
        startActivity(intent);
    }

    // Set up SearchView listener
    private void setupSearchViewListener() {
        // Use TextWatcher to monitor content changes in the input box
        searchView.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                performSearch(s.toString());
            }

            @Override
            public void afterTextChanged(android.text.Editable s) {}
        });
        // Close SearchView after search is complete
        searchView.getEditText().setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                String query = v.getText().toString();
                performSearch(query);
                searchView.hide();
                return true;
            }
            return false;
        });
    }

    private void performSearch(String query) {
        List<Post> filteredPosts = new ArrayList<>();

        if (postList == null) {
            // Handle the case when post data is not loaded yet
            Log.e("HomeFragment", "Post data is not loaded yet");
            return;
        }

        // get the post data of user
        for (Post post : postList) {
            if (post.title.toLowerCase().contains(query.toLowerCase()) ) {
                filteredPosts.add(post);
            }
        }
        postCardAdapter1.updatePosts(filteredPosts);  // update posts RecyclerView
        postCardAdapter2.updatePosts(filteredPosts);


    }
}