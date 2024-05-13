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
import com.example.gp.R;
import com.example.gp.Utils.ToastUtil;
import com.example.gp.Items.Post;
import com.example.gp.data.BTree;
import com.example.gp.data.Database;
import com.example.gp.data.PostsData;
import com.example.gp.interaction.NewPostActivity;
import com.example.gp.interaction.PostCardAdapter;
import com.example.gp.interaction.PostDetailActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.search.SearchView;

import java.util.ArrayList;
import java.util.List;

/**
 * Home fragment
 * The page to show home page of posts sent by all other users you followed
 * @author  Xingchen Zhang
 * {@code @editor} Zehua Kong
 * Date: 2024-05-01
 */
public class HomeFragment extends Fragment {
    private static final String TAG = "HomeFragment";

    private PostCardAdapter postCardAdapter;
    private SearchView searchView;
    private List<Post> postList;
    private static final PostsData postsData = PostsData.getInstance();

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
//        Timestamp timestamp = new Timestamp(new Date());
        Database.getNewPostsByTime(null, 19, this, "loadPostCards");

        return view;
    }

    public void loadPostCards(boolean isSuccess, Object object) {
        if (isSuccess) {
            Log.d(TAG, "loadPostCards: ");
            postList = (List<Post>) object;
            for (Post post : postList) {
                int postId = post.getPostId().hashCode();
                postTree.add(postId, post);
            }
            postsData.addNewPosts(postList);

            // renew the UI
            ArrayList<Integer> keys = postTree.getKeys(postTree.mRootNode);
            List<Post> posts = new ArrayList<>();
            for (Integer key : keys) {
                Post post = (Post) postTree.search(key);
                if (post != null) {
                    posts.add(post);
                }
            }
//            postCardAdapter.setPostList(posts);
            postCardAdapter.setPostList();


            Log.d("HomeFragment", "Posts loaded successfully");
        } else {
            // handle failure
            ToastUtil.showLong(getContext(), "Failed to load posts");
        }
    }

    private void initializeSearchView(View view) {
        View searchLayout = view.findViewById(R.id.search_layout);
        searchView = searchLayout.findViewById(R.id.search_view);
        setupSearchViewListener();
    }

    /**
     * Initialize the recycler views for the posts.
     * @param view view
     */
    private void initializeRecyclerViews(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.rv_post_container);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        postCardAdapter = new PostCardAdapter();

        recyclerView.setAdapter(postCardAdapter);

        postCardAdapter.setOnPostClickListener(this::onPostClick);
    }

    private void onPostClick(int position) {
        Intent intent = new Intent(getContext(), PostDetailActivity.class);
//        intent.putExtra("postId", post.getPostId());
//        intent.putExtra("post", post);
        intent.putExtra("position", position);
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

    /**
     * Search by input query string, or input token as prefix
     * @param query query string
     */
    private void performSearch(String query) {
        List<Post> filteredPosts = new ArrayList<>();

        if (postList == null) {
            // Handle the case when post data is not loaded yet
            Log.e("HomeFragment", "Post data is not loaded yet");
            return;
        }

        //User can search by starting with:
        //title:
        //public:
        String titleQuery = Parser.getInstance().parseTitle(query);
        Boolean publicQuery = Parser.getInstance().parsePublic(query);


        ArrayList<Integer> keys = postTree.getKeys(postTree.mRootNode); // get all keys
        for (int key : keys) {
            Post post = (Post) postTree.search(key);

            // If no token used, use title query in default
            if (titleQuery == null && publicQuery == null) {
                if (post.getTitle().toLowerCase().contains(query.toLowerCase())) {
                    filteredPosts.add(post);
                }
            }
            // If only one token used, filter by token
            else if (titleQuery == null || publicQuery == null) {
                // Use public
                if (titleQuery == null) {
                    if (post.isPublic() == Boolean.TRUE.equals(publicQuery)) {
                        filteredPosts.add(post);
                    }
                }
                // Use title
                else {
                    if (post.getTitle().toLowerCase().contains(titleQuery.toLowerCase())) {
                        filteredPosts.add(post);
                    }
                }
            }
            //Use more than 1 token is an invalid operation
            else {
                return;
            }

        }
        postCardAdapter.updatePosts(filteredPosts);  // update posts RecyclerView
    }
}