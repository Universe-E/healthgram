package com.example.gp.setting;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gp.BaseActivity;
import com.example.gp.R;
import com.example.gp.data.Post;
import com.example.gp.databinding.ActivityPostVisibilityBinding;
import com.example.gp.setting.Adapter.PostAdapter;

import java.util.ArrayList;
import java.util.List;

public class PostVisibilityActivity extends BaseActivity {

    private com.example.gp.databinding.ActivityPostVisibilityBinding binding;
    private RecyclerView recyclerView;
    private PostAdapter mPostAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityPostVisibilityBinding.inflate(getLayoutInflater());
        String activityName = "Post visibility";
        setContentView(binding.getRoot());
        setUpTitleBar(R.layout.activity_post_visibility,activityName);

        recyclerView = binding.recyclerViewPosts;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Test Code
        Post post_test1 = new Post("01", "02", "This is test01", "Test01", true);
        Post post_test2 = new Post("02", "02", "This is test02", "Test02", false);
        List<Post> posts = new ArrayList<>();;
        posts.add(post_test1);
        posts.add(post_test2);
        // TODO: get all post created or managed by this user
        // List<Post> posts = getUserAllPosts();

        // Set post adapter
        mPostAdapter = new PostAdapter(posts);
        recyclerView.setAdapter(mPostAdapter);


    }
}