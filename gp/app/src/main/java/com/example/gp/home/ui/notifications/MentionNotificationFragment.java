package com.example.gp.home.ui.notifications;

import static com.example.gp.Items.NotificationFactory.createMentionNotification;

import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import com.google.firebase.Timestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Friend notification fragment
 * The page to show the information of who @you in his/her posts
 * Author: Xingchen Zhang
 * Date: 2024-05-13
 */
public class MentionNotificationFragment extends Fragment {
    private RecyclerView recyclerView;
    private FollowerFragmentViewAdapter adapter;
    private List<Notification> notifications;

    private CharSequence currentDate;
    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_mention_notification_fragment, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Initialization data
        notifications = loadNotifications(); // load data
        adapter = new FollowerFragmentViewAdapter(notifications);
        recyclerView.setAdapter(adapter);
        return view;
    }
    private List<Notification> loadNotifications() {

        List<Notification> data = new ArrayList<>();
        Timestamp timestamp = Timestamp.now();
        data.add(createMentionNotification("@you","",timestamp,"Rick"));

        return data;
    }
}