package com.example.gp.home.ui.notifications;

import static com.example.gp.Items.NotificationFactory.createFriendNotification;

import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gp.Adapter.FriendFragmentViewAdapter;
import com.example.gp.Utils.ToastUtil;
import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gp.Adapter.FollowerFragmentViewAdapter;
import com.example.gp.Items.Notification;
import com.example.gp.R;
import com.example.gp.data.Database;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Friend notification fragment
 * The page to show who followed you recently
 * Author: Xingchen Zhang
 * Date: 2024-05-13
 */
public class FriendNotificationFragment extends Fragment {
    private RecyclerView recyclerView;
    private FriendFragmentViewAdapter adapter;
    private List<Notification> notifications;

    private CharSequence currentDate;
    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_friend_notification_fragment, container, false);
        Database.getNotificationList(this,"updateUI");
        initializeRecyclerViews(view);
        return view;
    }

//    private List<Notification> loadNotifications() {
//        notifications= new ArrayList<>();
//        currentDate = DateFormat.format("yyyy-MM-dd", new Date());
//        notifications.add(createFriendNotification("followed you","who followed you",currentDate, "Wallace"));
//        return notifications;
//    }


    public void updateUI(boolean isSuccess, Object object){
        if (!isSuccess) {
            if (object == null) {
                ToastUtil.show(getContext(), "No Notification Yet");
            } else {
                ToastUtil.show(getContext(), "Please check your network!");
            }
        } else {
            if (object instanceof List) {
                List<Notification> friendRequests = (List<Notification>) object;
                if (!friendRequests.isEmpty()) {
                    notifications = friendRequests;  //Assign the new friend list to the friends variable
                    adapter.updateFriends(notifications);
                    Log.d("UpdateUI", "Friends updated, count: " + notifications.size());

                    // 打印 notifications 列表的内容
                    Log.d("UpdateUI", "Notifications: " + notifications.toString());
                } else {
                    Log.d("UpdateUI", "Friends list is empty after update.");
                }
            } else {
                ToastUtil.showLong(getContext(), "Incorrect data type received");
            }
        }
    }


    private void initializeRecyclerViews(View view) {
        recyclerView = view.findViewById(R.id.recycler_view_follow);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        // Initialization data
//        notifications = loadNotifications(); // load data
        notifications= new ArrayList<>();

        adapter = new FriendFragmentViewAdapter(notifications);
        recyclerView.setAdapter(adapter);
    }
}