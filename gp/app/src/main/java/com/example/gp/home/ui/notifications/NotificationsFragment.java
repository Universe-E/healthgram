package com.example.gp.home.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.gp.Adapter.NotificationsPagerAdapter;
import com.example.gp.databinding.FragmentNotificationsBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

/**
 * Notification fragment
 * The page to show notifcation of posts received by other users
 * Author: Xingchen Zhang
 * Date: 2024-05-01
 */
public class NotificationsFragment extends Fragment {

    private FragmentNotificationsBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        NotificationsPagerAdapter adapter = new NotificationsPagerAdapter(this);
        adapter.addFragment(new FollowNotificationFragment(), "Follow Updates");
        adapter.addFragment(new MentionNotificationFragment(), "@ME");
        adapter.addFragment(new FriendNotificationFragment(), "Friend Requests");

        binding.viewPager.setAdapter(adapter);

        // Set up the TabLayout with ViewPager2
        TabLayout tabLayout = binding.tabs;
        new TabLayoutMediator(tabLayout, binding.viewPager,
                (tab, position) -> tab.setText(adapter.getPageTitle(position))
        ).attach();

        return root;
    }

}