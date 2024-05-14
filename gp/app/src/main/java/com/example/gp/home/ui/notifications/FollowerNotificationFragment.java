package com.example.gp.home.ui.notifications;

import static com.example.gp.Items.NotificationFactory.createFollowNotification;


import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gp.Adapter.FollowerFragmentViewAdapter;
import com.example.gp.Items.Notification;
import com.example.gp.Items.User;
import com.example.gp.R;
import com.example.gp.Utils.ToastUtil;
import com.example.gp.data.Database;

import java.util.*;

/**
 * Follower notification fragment
 * The page to show your following friends send posts recently
 * Author: Xingchen Zhang
 * Date: 2024-05-13
 */
public class FollowerNotificationFragment extends Fragment {

    private RecyclerView recyclerView;
    private FollowerFragmentViewAdapter followerNotificationAdapter;
    private List<Notification> notifications;

    private CharSequence currentDate;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_follower_notification_fragment, container, false);
        recyclerView = view.findViewById(R.id.recycler_view_posts);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Initialization data
        notifications = loadNotifications(); // load data
        followerNotificationAdapter = new FollowerFragmentViewAdapter(notifications);
        recyclerView.setAdapter(followerNotificationAdapter);

        return view;
    }

    private List<Notification> loadNotifications() {
        List<Notification> data = new ArrayList<>();
        currentDate = DateFormat.format("yyyy-MM-dd", new Date());
        //using factory method
        data.add(createFollowNotification("Vacation","go to Italy",currentDate,"Lin"));
        // 添加更多数据
        return data;
    }
}