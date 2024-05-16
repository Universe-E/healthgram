package com.example.gp;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gp.Items.Parser;
import com.example.gp.Items.Post;
import com.example.gp.data.BTree;
import com.example.gp.data.PostRepository;
import com.example.gp.databinding.ActivityFragmentHomeBinding;
import com.example.gp.databinding.ActivitySearchBinding;
import com.example.gp.databinding.ActivitySearchPostsBinding;
import com.example.gp.interaction.PostCardAdapter;
import com.google.android.material.search.SearchBar;
import com.google.android.material.search.SearchView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;


public class SearchPostsActivity extends BaseActivity {

    private static final String TAG = "SearchResults";
    private PostCardAdapter postCardAdapter;
    private ActivitySearchPostsBinding binding;
    private SearchBar searchBar;
    private SearchView searchView; // to show the search list
    private List<Post> searchResults; // store the search result

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
    }
    /**
     * Search by input query string, or input token as prefix
     *
     * @param query query string
     */
    private void performSearch(String query) {
        List<Post> filteredPosts = new ArrayList<>();

        if (searchResults == null) {
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