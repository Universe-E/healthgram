package com.example.gp;



import android.content.Intent;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gp.Items.Parser;
import com.example.gp.Items.Post;
import com.example.gp.data.BTree;
import com.example.gp.data.Database;
import com.example.gp.data.PostRepository;

import com.example.gp.databinding.ActivitySearchPostsBinding;
import com.example.gp.interaction.PostCardAdapter;
import com.example.gp.interaction.PostDetailActivity;
import com.google.android.material.search.SearchBar;
import com.google.android.material.search.SearchView;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
/**
 * Search the results of posts to show on this activity
 * All the posts are stored in the BTree then show up in this activity then clearAll
 * @author Xingchen Zhang
 * Date: 2024-05-16
 */

public class SearchPostsActivity extends BaseActivity {

    private static final String TAG = "SearchResults";
    private PostCardAdapter postCardAdapter;
    private ActivitySearchPostsBinding binding;

    private RecyclerView recyclerView;
    private SearchBar searchBar;
    private SearchView searchView; // to show the search list
    private List<Post> searchResults; // store the search result
    private Object thisActivity;

    private static final PostRepository postRepo = PostRepository.getInstance();

    // search
    private BTree postTree; // use BTree to store posts


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_posts);
        // use view binding to bind the layout
        binding = ActivitySearchPostsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setUpTitleBar(R.layout.activity_search_posts,TAG);


        recyclerView = findViewById(R.id.rv_post_container);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        searchResults = new ArrayList<>();
        postCardAdapter = new PostCardAdapter(searchResults);
        recyclerView.setAdapter(postCardAdapter);

        postTree = new BTree();

        Database.getNewestFiftyPosts(this, "performSearch");



    }

    /**
     * Search by input query string, or input token as prefix
     *
     */
    public void performSearch(boolean isSuccessful, Object object) {
        Log.d("Posts","performSearch");
        if (!isSuccessful) {
            return;
        }
        List<Post> filteredPosts = (List<Post>) object;

        // get posts from postList
        for (Post post : filteredPosts) {
            int postId = post.getPostId().hashCode();
            postTree.add(postId, post);
        }


        Intent intent = getIntent();
        String query = intent.getStringExtra("query");
        //User can search by starting with:
        //title:
        //public:
        String titleQuery = Parser.getInstance().parseTitle(query);
        Boolean publicQuery = Parser.getInstance().parsePublic(query);

        ArrayList<Integer> keys = postTree.getKeys(postTree.mRootNode); // get all keys
        ArrayList<Post> retList = new ArrayList<>();
        for (int key : keys) {
            Post post = (Post) postTree.search(key);

            // If no token used, use title query in default
            if (titleQuery == null && publicQuery == null) {
                if (post.getTitle().toLowerCase().contains(query.toLowerCase())) {
                    retList.add(post);
                }
            }
            // If only one token used, filter by token
            else if (titleQuery == null || publicQuery == null) {
                // Use public
                if (titleQuery == null) {
                    if (post.isPublic() == Boolean.TRUE.equals(publicQuery)) {
                        retList.add(post);
                    }
                }
                // Use title
                else {
                    if (post.getTitle().toLowerCase().contains(titleQuery.toLowerCase())) {
                        retList.add(post);
                    }
                }
            }
            //Use more than 1 token is an invalid operation
            else {
                return;
            }
        }
        Log.d("Posts",retList.size()+retList.toString());
        postCardAdapter.updatePosts(retList);  // update posts RecyclerView


        postCardAdapter.setOnPostClickListener(this::onPostClick);

        searchResults.clear();
        searchResults.addAll(retList);
        postCardAdapter.notifyDataSetChanged();

    }
    private void onPostClick(String postId) {
        Intent intent = new Intent(this, PostDetailActivity.class);
        intent.putExtra("postId", postId);
        intent.putExtra("getPost", true);
        startActivity(intent);
    }
}