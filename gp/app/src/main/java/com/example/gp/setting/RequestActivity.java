package com.example.gp.setting;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gp.BaseActivity;
import com.example.gp.Items.Friend;
import com.example.gp.Items.FriendRequest;
import com.example.gp.R;
import com.example.gp.Utils.TimeUtil;
import com.example.gp.Utils.ToastUtil;
import com.example.gp.data.database.UserDB;
import com.example.gp.databinding.ActivityRequestBinding;
import com.example.gp.setting.Adapter.RequestAdapter;
import com.google.firebase.Timestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Yulong Chen
 * @since 2024-05-13
 */
public class RequestActivity extends BaseActivity {

    private com.example.gp.databinding.ActivityRequestBinding binding;
    private List<Friend> friends;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityRequestBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setUpTitleBar(R.layout.activity_request,"Friend Request");

        mRecyclerView = binding.recyclerViewFriendRequest;
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        friends = new ArrayList<>();
        Timestamp time = TimeUtil.getTimestamp();
        UserDB.getFriendRequest(time,10,this,"updateUI");
    }


    /**
     * load the friend requests list
     * @param isSuccess if the right info is successfully returned
     * @param object the friend request info
     */
    public void updateUI(boolean isSuccess, Object object){
        if (!isSuccess) {
            if (object == null) {
                ToastUtil.show(this, "No Friend Request");
            } else {
                ToastUtil.show(this, "Please check your network!");
            }
        } else {
            List<Friend> friendRequests = (List<Friend>) object;
            for (Friend friendRequest : friendRequests) {
                friends.add(friendRequest);
            }
            RequestAdapter mRequestAdapter = new RequestAdapter(friends);
            mRecyclerView.setAdapter(mRequestAdapter);
        }
    }
}