package com.example.gp.home.ui.Friend;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gp.Activity_note_detail;
import com.example.gp.Adapter.FriendsRecyclerViewAdapter;
import com.example.gp.Adapter.NoteRecyclerViewAdapter;
import com.example.gp.R;
import com.example.gp.SearchActivity;
import com.example.gp.Items.Friend;
import com.example.gp.databinding.ActivityFriendDetailBinding;
import com.example.gp.databinding.FragmentDashboardBinding;
import com.example.gp.home.Fragment_home;
import com.google.android.material.search.SearchBar;

import java.util.ArrayList;
import java.util.List;

/*
* 登录成功跳转的主界面
* 显示:头像 好友列表
*
* */
public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;
    private SearchBar searchBar;
    private FriendsRecyclerViewAdapter adapter;
    private List<Friend> friends;
    RecyclerView recyclerView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        // 加载 fragment_dashboard.xml 布局
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        // 在包含的 activity_search.xml 中找到 SearchBar
        View searchLayout = view.findViewById(R.id.search_layout);
        searchBar = searchLayout.findViewById(R.id.search_bar);

        // 检查 searchBar 是否成功找到
        if (searchBar != null) {
            Log.d("DashboardFragment", "成功加载 SearchBar");
            searchBar.setOnClickListener(v -> openSearchActivity());
        } else {
            Log.e("DashboardFragment", "未找到 SearchBar");
        }

        // Initialize RecyclerView
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Load friends
        loadFriends();

        // Set click listener to navigate to friend's profile
//        adapter.setOnItemClickListener(new FriendsRecyclerViewAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(Friend friend) {
//                openFriendProfile(friend);
//            }
//        });


        return view;
    }

    private void openSearchActivity() {
        Intent searchIntent = new Intent(getContext(), SearchActivity.class);
        if (searchBar != null) {
            String initialQuery = searchBar.getText().toString(); // 从搜索栏获取文本
            searchIntent.putExtra("QUERY", initialQuery);
        }
        startActivity(searchIntent);
    }

    private void loadFriends() {
        friends = new ArrayList<>();
        friends.add(new Friend("user1", "Alice", R.mipmap.sample_avatar_1));
        friends.add(new Friend("user2", "Bob", R.mipmap.sample_avatar_2));

        //input context to prevent null pointer exception
        adapter = new FriendsRecyclerViewAdapter(getContext(),friends);
        recyclerView.setAdapter(adapter);
    }

    private void openFriendProfile(Friend friend) {
        Intent intent = new Intent(getContext(), ActivityFriendDetailBinding.class);
        startActivity(intent);
    }
}