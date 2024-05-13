package com.example.gp.setting;


import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gp.BaseActivity;
import com.example.gp.R;
import com.example.gp.Items.Post;
import com.example.gp.Utils.TimeUtil;
import com.example.gp.Utils.ToastUtil;
import com.example.gp.data.Database;
import com.example.gp.databinding.ActivityPostVisibilityBinding;
import com.example.gp.setting.Adapter.PostAdapter;
import com.google.firebase.Timestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class PostVisibilityActivity extends BaseActivity {
    private static String TAG = "PVA1000000";
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

        Timestamp timestamp = TimeUtil.getTimestamp();
        Database.getUserPost(timestamp,10,this,"updateUI");


    }

    // Call back method
    public void updateUI(boolean isSuccess, Object object){
        Log.d(TAG, "updateUI");
        if (!isSuccess) {
            if (object == null) {
                ToastUtil.showLong(this, "Get posts failed");
                return;
            }
            ToastUtil.show(this, object.toString());
            Log.d(TAG, "success");
        } else {

            Map<String, Post> postMap = (Map<String, Post>) object;
            Log.d(TAG, postMap.toString());
            //Get posts
            for (Map.Entry<String, Post> entry : postMap.entrySet()) {
                Log.d(TAG, entry.getValue().toString());
                posts.add(entry.getValue());
            }
//            Collection<Post> posts = postMap.values();
//            this.posts.addAll(posts);
            // Set post adapter
            mPostAdapter = new PostAdapter(posts);
            recyclerView.setAdapter(mPostAdapter);
        }


    }

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        if (mPostAdapter != null) {
//            mPostAdapter = null;
//        }
//
//    }
}

