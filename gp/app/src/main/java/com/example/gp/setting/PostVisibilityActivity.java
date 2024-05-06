package com.example.gp.setting;


import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gp.BaseActivity;
import com.example.gp.R;
import com.example.gp.Items.Post;
import com.example.gp.Utils.TimeUtil;
import com.example.gp.data.Database;
import com.example.gp.databinding.ActivityPostVisibilityBinding;
import com.example.gp.setting.Adapter.PostAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
//        Date time = TimeUtil.getCurDate();
//        Database.PostDB.getUserPost(time,10,this,"updateUI");
    }
    public void updateUI(List<Post> posts){
        // Test Code
        Post post_test1 = new Post("01", "02", "This is test01", "Test01", true);
        Post post_test2 = new Post("02", "02", "This is test02", "Test02", false);
        posts.add(post_test1);
        posts.add(post_test2);
        // Set post adapter
        mPostAdapter = new PostAdapter(posts);
        recyclerView.setAdapter(mPostAdapter);
    }
}

