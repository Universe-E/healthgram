package com.example.gp.home.ui.notifications;

import static com.example.gp.Items.NotificationFactory.createFollowNotification;
import static com.example.gp.Utils.TimeUtil.convertTimestampToString;

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
import com.example.gp.Adapter.FriendFragmentViewAdapter;
import com.example.gp.Items.Notification;
import com.example.gp.Items.User;
import com.example.gp.R;
import com.example.gp.Utils.ToastUtil;
import com.example.gp.data.Database;
import com.google.firebase.Timestamp;

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

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_follower_notification_fragment, container, false);
        Database.getNotificationList(this,"updateUI");
        initializeRecyclerViews(view);

        return view;
    }

    public void updateUI(boolean isSuccess, Object object) {
        if (!isSuccess) {
            if (object == null) {
                ToastUtil.show(getContext(), "No Notification Yet");
            } else {
                ToastUtil.show(getContext(), "Please check your network!");
            }
        } else {
            if (object instanceof List) {
                List<Notification> allNotifications = (List<Notification>) object;
                List<Notification> followerPostNotifications = new ArrayList<>();

                // filter type
                for (Notification notification : allNotifications) {
                    if (notification.getType() == Notification.NotificationType.POST_UPDATE) {
                        followerPostNotifications.add(notification);
                    }
                }

                if (!followerPostNotifications.isEmpty()) {
                    notifications = followerPostNotifications;
                    followerNotificationAdapter.updateFriends(notifications);
                    Log.d("UpdateUI", "Notifications updated, count: " + notifications.size());

                    // 打印 notifications 列表的内容
                    Log.d("UpdateUI", "Notifications:");
                    for (Notification notification : notifications) {
                        Log.d("UpdateUI", "Title: " + notification.getTitle());
                        Log.d("UpdateUI", "Message: " + notification.getMessage());
                        Log.d("UpdateUI", "Date: " + (notification.getTimestamp() != null ? notification.getTimestamp().toString() : "null"));
                        Log.d("UpdateUI", "Type: " + notification.getType());
                        Log.d("UpdateUI", "User ID: " + notification.getUserId());
                        Log.d("UpdateUI", "Notification ID: " + notification.getNotificationId());
                        Log.d("UpdateUI", "Is Read: " + notification.isRead());
                        Log.d("UpdateUI", "User: " + notification.getSenderName());
                        Log.d("UpdateUI", "---");
                    }
                } else {
                    Log.d("UpdateUI", "No POST_UPDATE notifications found.");
                    notifications.clear();
                    followerNotificationAdapter.updateFriends(notifications);
                }
            } else {
                ToastUtil.showLong(getContext(), "Incorrect data type received");
            }
        }
    }


    private void initializeRecyclerViews(View view) {
        recyclerView = view.findViewById(R.id.recycler_view_posts);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        notifications= new ArrayList<>();
        followerNotificationAdapter = new FollowerFragmentViewAdapter(notifications);
        recyclerView.setAdapter(followerNotificationAdapter);
    }
}