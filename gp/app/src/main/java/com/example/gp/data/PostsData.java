package com.example.gp.data;

import com.example.gp.Items.Post;

import java.util.ArrayList;
import java.util.List;

// Design pattern singleton

/**
 * Posts data class
 */
public class PostsData {
    private static final String TAG = "PostData";
    private static List<Post> _allPosts;
    private static PostsData instance;

    private PostsData() {
        _allPosts = new ArrayList<>();
    }

    public static PostsData getInstance() {
        if (instance == null) {
            instance = new PostsData();
        }
        return instance;
    }

    public void addNewPosts(List<Post> posts) {
        if (_allPosts == null) {
            _allPosts = new ArrayList<>();
        }
        if (posts == null) {
            return;
        }
        _allPosts.addAll(0, posts);
    }

    public void getPreviousPosts(List<Post> posts) {
        if (_allPosts == null) {
            _allPosts = new ArrayList<>();
        }
        if (posts == null) {
            return;
        }
        _allPosts.addAll(posts);
    }

    public List<Post> getPosts() {
        return _allPosts;
    }
}
