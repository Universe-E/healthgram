package com.example.gp.data;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.gp.Items.Post;

import java.util.ArrayList;

public class PostData {
    private static final String TAG = "PostData";
    private static MutableLiveData<ArrayList<Post>> _allPosts;
    private static PostData instance;

    private PostData() {
        _allPosts = new MutableLiveData<>();
    }

    private static <T> void notifyObserver(MutableLiveData<T> liveData) {
        liveData.setValue(liveData.getValue());
    }

    public static void notifyObserver() {
        notifyObserver(_allPosts);
    }

    public static PostData getInstance() {
        if (instance == null) {
            instance = new PostData();
        }
        return instance;
    }

    public void addNewPosts(ArrayList<Post> posts) {
        ArrayList<Post> allPosts = _allPosts.getValue();
        if (allPosts != null) {
            allPosts.addAll(0, posts);
            notifyObserver();
        } else {
            _allPosts.setValue(posts);
        }
    }
}
