package com.example.gp.home.ui.Friend;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gp.Adapter.FriendsRecyclerViewAdapter;

import com.example.gp.R;
import com.example.gp.Items.Friend;
import com.example.gp.Utils.ToastUtil;
import com.example.gp.data.Database;
import com.example.gp.databinding.ActivityFriendDetailBinding;
import com.example.gp.databinding.FragmentFriendboardBinding;
import com.google.android.material.search.SearchView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * FriendList fragment
 * The page to show the friends you followed
 * @author  Xingchen Zhang
 * Date: 2024-05-01
 */
public class FriendFragment extends Fragment {

    private FragmentFriendboardBinding binding;
    private SearchView searchView;
    private FriendsRecyclerViewAdapter friendadapter;

    private List<Friend> friends;
    RecyclerView recyclerView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        // load fragment_dashboard.xml
        View view = inflater.inflate(R.layout.fragment_friendboard, container, false);


        initializeSearchView(view);
        Database.getFollowList(null, null, this, "updateUI");
        // Load friends
        initializeRecyclerViews(view);

        // Set click listener to navigate to friend's profile
//        adapter.setOnItemClickListener(new FriendsRecyclerViewAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(Friend friend) {
//                openFriendProfile(friend);
//            }
//        });


        return view;
    }
    private void initializeSearchView(View view) {
        View searchLayout = view.findViewById(R.id.search_layout);
        searchView = searchLayout.findViewById(R.id.search_view);
        setupSearchViewListener();
        // 监听 SearchView 的关闭事件

    }

    private void initializeRecyclerViews(View view) {
        friends = new ArrayList<>();
        //input context to prevent null pointer exception

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_friend);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        friendadapter = new FriendsRecyclerViewAdapter(getContext(),friends);
        recyclerView.setAdapter(friendadapter);
    }

    private void openFriendProfile(Friend friend) {
        Intent intent = new Intent(getContext(), ActivityFriendDetailBinding.class);
        startActivity(intent);
    }
    public void updateUI(boolean isSuccess, Object object) {
        if (!isSuccess) {
            ToastUtil.showLong(getContext(), "Failed to load friends: " + object);
            return;
        }

        if (object instanceof List) {
            List<Friend> newFriends = (List<Friend>) object;

            if (!newFriends.isEmpty()) {
                friends = newFriends;  //Assign the new friend list to the friends variable
                friendadapter.updateFriends(friends);
                Log.d("UpdateUI", "Friends updated, count: " + friends.size());
            } else {
                Log.d("UpdateUI", "Friends list is empty after update.");
            }
        } else {
            ToastUtil.showLong(getContext(), "Incorrect data type received");
        }
    }
    // Set up SearchView listener
    private void setupSearchViewListener() {
        // Use TextWatcher to monitor changes in the content of the input box
        searchView.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                performSearch(s.toString());
            }

            @Override
            public void afterTextChanged(android.text.Editable s) {}
        });

        // Close SearchView after search is complete
        searchView.getEditText().setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                String query = v.getText().toString();
                performSearch(query);
                searchView.hide();
                return true;
            }
            return false;
        });
    }

    private void performSearch(String query) {
        if (friends == null || friends.isEmpty()) {
            Log.e("HomeFragment", "User data is not loaded yet");
            return;
        }
        List<Friend> searchedFriends = new ArrayList<>();

        if (friends == null) {
            Log.e("HomeFragment", "User data is not loaded yet");
            return;
        }

        String lowerCaseQuery = query.toLowerCase();

        // Find friends starting with the search term first
        for (Friend friend : friends) {
            if (friend.getNickname().toLowerCase().startsWith(lowerCaseQuery)) {
                searchedFriends.add(friend);
            }
        }

        // Find friends that contain the search term
        for (Friend friend : friends) {
            if (friend.getNickname().toLowerCase().contains(lowerCaseQuery) &&
                    !searchedFriends.contains(friend)) {
                searchedFriends.add(friend);
            }
        }

        Log.d("SearchResults", "Searched Friends:");
        for (Friend friend : searchedFriends) {
            Log.d("SearchResults", "- " + friend.getNickname());
        }

        friendadapter.updateFriends(searchedFriends);

    }
}