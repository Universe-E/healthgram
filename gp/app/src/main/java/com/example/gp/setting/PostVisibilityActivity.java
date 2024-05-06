package com.example.gp.setting;


import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gp.BaseActivity;
import com.example.gp.R;
import com.example.gp.Utils.ToastUtil;
import com.example.gp.data.Database;
import com.example.gp.data.Post;
import com.example.gp.data.UserData;
import com.example.gp.databinding.ActivityPostVisibilityBinding;
import com.example.gp.setting.Adapter.PostAdapter;

import java.util.ArrayList;
import java.util.List;

public class PostVisibilityActivity extends BaseActivity {
    private static String TAG = "PVA";
    private com.example.gp.databinding.ActivityPostVisibilityBinding binding;
    private RecyclerView recyclerView;
    private PostAdapter mPostAdapter;
    private List<Post> posts;

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


        posts = new ArrayList<>();
        updateUI(posts);
        // TODO: get all post created or managed by this user
//        Database.Post.getUserPost(DateTime);

        // Set post adapter
        mPostAdapter = new PostAdapter(posts);
        recyclerView.setAdapter(mPostAdapter);

    }
    public void updateUI(List<Post> posts){
        // Test Code
        Post post_test1 = new Post("01", "02", "This is test01", "Test01", true);
        Post post_test2 = new Post("02", "02", "This is test02", "Test02", false);
        posts.add(post_test1);
        posts.add(post_test2);
    }
}

