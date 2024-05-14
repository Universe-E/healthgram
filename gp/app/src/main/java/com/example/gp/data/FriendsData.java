package com.example.gp.data;

import com.example.gp.data.database.model.FriendModel;

import java.util.HashMap;
import java.util.Map;

/**
 * Friends data class
 */
public class FriendsData {
    private static final String TAG = "FriendsData";
    private static Map<String, FriendModel> _allFriends;
    private static FriendsData instance;

    private FriendsData() {
        _allFriends = new HashMap<>();
    }

    public static FriendsData getInstance() {
        if (instance == null) {
            instance = new FriendsData();
        }
        return instance;
    }

    public static void clearFriendsData() {
        instance = null;
        _allFriends = null;
    }

    public void addNewFriends(Map<String, FriendModel> friends) {
        if (_allFriends == null) {
            _allFriends = new HashMap<>();
        }
        if (friends == null) {
            return;
        }
        _allFriends.putAll(friends);
    }

    public FriendModel getFriendById(String id) {
        return _allFriends.get(id);
    }

    public void removeFriendById(String id) {
        _allFriends.remove(id);
    }

    public Map<String, FriendModel> getAllFriends() {
        return _allFriends;
    }
}
