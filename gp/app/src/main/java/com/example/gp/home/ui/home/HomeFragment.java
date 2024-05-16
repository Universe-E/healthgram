package com.example.gp.home.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.gp.R;
import com.example.gp.SearchPostsActivity;
import com.example.gp.Utils.ToastUtil;
import com.example.gp.Items.Post;
import com.example.gp.data.Database;
import com.example.gp.data.PostRepository;
import com.example.gp.interaction.NewPostActivity;
import com.example.gp.interaction.PostCardAdapter;
import com.example.gp.interaction.PostDetailActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.search.SearchBar;
import com.google.android.material.search.SearchView;

import java.util.List;

/**
 * Home fragment
 * The page to show home page of posts sent by all other users you followed
 *
 * @author Tianci Li
 * {@code @editor} Zehua Kong, Xingchen Zhang, Han Bao
 * Date: 2024-05-01
 */
public class HomeFragment extends Fragment {
    private static final String TAG = "HomeFragment";
    private final int PAGE_SIZE = 10;
    private int PAGE = 1;


    // skeleton
    private PostCardAdapter postCardAdapter;
    private SearchView searchView;
    private SearchBar searchBar;
    private SwipeRefreshLayout swipeRefreshLayout;
    Object thisActivity;
    FloatingActionButton fabLoadMore;


    // show posts
    private List<Post> postList;
    private static final PostRepository postRepo = PostRepository.getInstance();

    // search
    private OnBackPressedCallback onBackPressedCallback;

    /**
     * Create a new instance of HomeFragment
     *
     * @return HomeFragment
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // initialize all views, buttons
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initializeSearchView(view);
        initializeRecyclerViews(view);
        initializeSwipeRefreshLayout(view);
        initializePageButtons(view);
        setupAddNoteButton(view);

        // load posts
        Database.getNewPostsByTime(null, (int)((0.5) * PAGE_SIZE), this, "cbmAddInitialPosts");

        // return
        thisActivity = this;
        return view;
    }

    /**
     * Callback method for getting initial posts
     *
     * @param isSuccess whether the operation is successful
     * @param args      the arguments
     */
    public void cbmAddInitialPosts(boolean isSuccess, Object args) {

        if (isSuccess) {
            List<Post> posts = (List<Post>) args;
            postRepo.addNewPosts(posts);
            this.postList = postRepo.getPostsByPage(PAGE, PAGE_SIZE);
            showPostCards();
        } else {
            Log.e(TAG, "cbmAddInitialPosts: Failed to get new posts");
        }
    }

    /**
     * Create a callback to handle the back button
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        onBackPressedCallback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                //Exit the application directly and return to the desktop
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, onBackPressedCallback);
    }

    /**
     * Destroy the callback when the fragment is destroyed
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        onBackPressedCallback.remove();
    }

    /**
     * simply show all posts in the postList
     */
    public void showPostCards() {
        // show log
        Log.d(TAG, "loadPostCards: ");

        // set postList to adapter to show
        postCardAdapter.setPostList(postList);

        // show log
        Log.d("HomeFragment", "Posts loaded successfully");
    }

    /**
     * Initialize the page buttons
     *
     * @param view view
     */
    private void initializePageButtons(View view) {
        // find the button
        fabLoadMore = view.findViewById(R.id.fab_load_more);

        // set listener
        fabLoadMore.setOnClickListener(v -> {
            Log.d(TAG, "initializePageButtons: Next page");
            fabLoadMore.setEnabled(false);
            thisActivity = this;
            Database.getPreviousPostsByTime(null, PAGE_SIZE, thisActivity, "cbmNextPage");
        });
    }

    /**
     * Callback method for getting next page of posts
     *
     * @param isSuccess whether the operation is successful
     * @param args      the arguments
     */
    public void cbmNextPage(boolean isSuccess, Object args) {
        if (isSuccess) {
            Log.d(TAG, "cbmNextPage: Successfully get new posts");
            List<Post> posts = (List<Post>) args;
            postRepo.addPreviousPosts(posts);

            postList = postRepo.getAllPosts();

            showPostCards();
        } else {
            Log.e(TAG, "cbmNextPage: Failed to get new posts");
            ToastUtil.showLong(getContext(), (String) args);
        }
        fabLoadMore.setEnabled(true);
    }

    /**
     * Initialize the search view
     *
     * @param view view
     */
    private void initializeSearchView(View view) {
        View searchLayout = view.findViewById(R.id.search_layout);
        searchView = searchLayout.findViewById(R.id.search_view);
        setupSearchViewListener();
    }

    /**
     * Initialize the recycler views for the posts.
     *
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

    /**
     * Initialize the swipe refresh layout
     *
     * @param view view
     */
    public void initializeSwipeRefreshLayout(View view) {
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Database.getNewPostsByTime(null, PAGE_SIZE, thisActivity, "cbmAddRefreshedPosts");
            }
        });
    }

    /**
     * Callback method for getting refreshed posts
     *
     * @param isSuccess whether the operation is successful
     * @param args      the arguments
     */
    public void cbmAddRefreshedPosts(boolean isSuccess, Object args) {
        if (isSuccess) {
            Log.d(TAG, "cbmAddRefreshedPosts: Successfully get new posts");
            List<Post> posts = (List<Post>) args;
            postRepo.addNewPosts(posts);
            this.postList.clear();
            this.postList = postRepo.getAllPosts();
            showPostCards();
            swipeRefreshLayout.setRefreshing(false);
        } else {
            Log.e(TAG, "cbmAddRefreshedPosts: Failed to get new posts");
            ToastUtil.showLong(getContext(), (String) args);
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    /**
     * Handle the click event of a post
     *
     * @param postId
     */
    private void onPostClick(String postId) {
        Intent intent = new Intent(getContext(), PostDetailActivity.class);
        intent.putExtra("postId", postId);

        startActivity(intent);
    }

    /**
     * Set up the add note button
     *
     * @param view view
     */
    private void setupAddNoteButton(View view) {
        FloatingActionButton fabAddNote = view.findViewById(R.id.fab_add_note);
        fabAddNote.setOnClickListener(v -> openAddNoteActivity());
    }

    private void openAddNoteActivity() {
        Intent intent = new Intent(getContext(), NewPostActivity.class);
        startActivity(intent);
    }

    /**
     * Set up SearchView listener
     */
    private void setupSearchViewListener() {
        // Use TextWatcher to monitor content changes in the input box
        searchView.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                performSearch(s.toString());
            }

            @Override
            public void afterTextChanged(android.text.Editable s) {
            }
        });

        // Close SearchView after search is complete
        searchView.getEditText().setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                String query = v.getText().toString();
                searchView.hide();

                // create a new intent to start the search activity
                Intent intent = new Intent(getContext(), SearchPostsActivity.class);
                intent.putExtra("query", query);
                startActivity(intent);

                return true;
            }
            return false;
        });
    }

}