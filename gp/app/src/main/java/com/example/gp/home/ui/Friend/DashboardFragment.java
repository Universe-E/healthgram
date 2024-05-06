package com.example.gp.home.ui.Friend;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gp.Adapter.NoteRecyclerViewAdapter;
import com.example.gp.R;
import com.example.gp.SearchActivity;
import com.example.gp.Items.Friend;
import com.example.gp.databinding.FragmentDashboardBinding;
import com.google.android.material.search.SearchBar;

import java.util.ArrayList;

/*
* 登录成功跳转的主界面
* 显示:头像 好友列表
*
* */
public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;
    private SearchBar searchBar;
    private NoteRecyclerViewAdapter adapter;
    private ArrayList<Friend> friends;

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
            Toast.makeText(getContext(), "mamba in", Toast.LENGTH_SHORT).show();
        } else {
            Log.e("DashboardFragment", "未找到 SearchBar");
            Toast.makeText(getContext(), "mamba out", Toast.LENGTH_SHORT).show();
        }

        // Initialize RecyclerView
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new NoteRecyclerViewAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);

        // TODO: Set click listener to navigate to friend detail page
        adapter.setOnItemClickListener(friend -> {
            // open friend detail page
        });

        // TODO: get friends list from database
        loadFriends();

        // TODO: Observe friends in UserData
//        UserData.friends().observe(getViewLifecycleOwner(), friends -> {
//            adapter.updateFriends(friends); // Update adapter with new data
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
        friends.add(new Friend("user1", "Alice", "avatar1"));
        friends.add(new Friend("user2", "Bob", "avatar2"));
    }
}