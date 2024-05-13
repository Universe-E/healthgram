package com.example.gp.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.gp.home.ui.notifications.FollowNotificationFragment;
import com.example.gp.home.ui.notifications.FriendNotificationFragment;
import com.example.gp.home.ui.notifications.MentionNotificationFragment;

import java.util.ArrayList;
import java.util.List;

public class NotificationsPagerAdapter extends FragmentStateAdapter {
    private String[] titles = new String[] { "动态", "@我", "通知" };

    public NotificationsPagerAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new FollowNotificationFragment();
            case 1:
                return new MentionNotificationFragment();
            case 2:
                return new FriendNotificationFragment();
            default:
                throw new IllegalStateException("Unexpected position: " + position);
        }
    }

    @Override
    public int getItemCount() {
        return titles.length;
    }

    public CharSequence getTabTitle(int position) {
        return titles[position];
    }

    private final List<Fragment> fragmentList = new ArrayList<>();
    private final List<String> fragmentTitleList = new ArrayList<>();



    public void addFragment(Fragment fragment, String title) {
        fragmentList.add(fragment);
        fragmentTitleList.add(title);
    }


    public CharSequence getPageTitle(int position) {
        return fragmentTitleList.get(position);
    }
}
