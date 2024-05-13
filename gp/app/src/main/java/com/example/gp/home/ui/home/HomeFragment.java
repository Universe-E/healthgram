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
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.gp.Items.Parser;
import com.example.gp.Items.Tokenizer;
import com.example.gp.R;
import com.example.gp.Utils.ToastUtil;
import com.example.gp.Items.Post;
import com.example.gp.data.BTree;
import com.example.gp.data.Database;
import com.example.gp.interaction.NewPostActivity;
import com.example.gp.interaction.PostCardAdapter;
import com.example.gp.interaction.PostDetailActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.search.SearchView;
import com.google.firebase.Timestamp;

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

    private PostCardAdapter postCardAdapter;
    private SearchView searchView;
    private List<Post> postList;

    //use BTree to store posts
    private BTree postTree;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        postTree = new BTree();

        // Initialize the search view, recycler views, and add note button
        initializeSearchView(view);
        initializeRecyclerViews(view);
        setupAddNoteButton(view);

        // Load post data from the database
        Timestamp timestamp = new Timestamp(new Date());
        Database.getPostsByTime(timestamp, 10, this, "loadPostCards");

        return view;
    }

    public List<Post> loadPostCards(boolean isSuccess, Object object) {
        if (isSuccess) {
            postList = (List<Post>) object;
            for (Post post : postList) {
                int postId = post.getPostId().hashCode();
                postTree.add(postId, post);
            }

            // renew the UI
            ArrayList<Integer> keys = postTree.getKeys(postTree.mRootNode);
            List<Post> posts = new ArrayList<>();
            for (Integer key : keys) {
                Post post = (Post) postTree.search(key);
                if (post != null) {
                    posts.add(post);
                }
            }
            postCardAdapter.setPostList(posts);

            Log.d("HomeFragment", "Posts loaded successfully");
        } else {
            // handle failure
            ToastUtil.showLong(getContext(), "Failed to load posts");
        }
        return postList;
    }

    private void initializeSearchView(View view) {
        View searchLayout = view.findViewById(R.id.search_layout);
        searchView = searchLayout.findViewById(R.id.search_view);
        setupSearchViewListener();
    }

    /**
     * Initialize the recycler views for the posts.
     * @param view
     */
    private void initializeRecyclerViews(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.rv_post_container);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        postCardAdapter = new PostCardAdapter();

        recyclerView.setAdapter(postCardAdapter);

        postCardAdapter.setOnPostClickListener(this::onPostClick);
    }

    private void onPostClick(Post post) {
        Intent intent = new Intent(getContext(), PostDetailActivity.class);
//        intent.putExtra("postId", post.getPostId());
        intent.putExtra("post", post);
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

        //user can search by starting with:
        //title:
        //public:
        String titleQuery = Parser.getInstance().parseTitle(query);
        Boolean publicQuery = Parser.getInstance().parsePublic(query);


        ArrayList<Integer> keys = postTree.getKeys(postTree.mRootNode); // get all keys
        for (int key : keys) {
            Post post = (Post) postTree.search(key);
            boolean titleMatches = titleQuery == null || post.getTitle().toLowerCase().contains(titleQuery.toLowerCase());
            boolean publicMatches = publicQuery == null || post.isPublic() == publicQuery;

            if (titleMatches && publicMatches) {
                filteredPosts.add(post);
            }
        }
        postCardAdapter.updatePosts(filteredPosts);  // update posts RecyclerView
    }
}