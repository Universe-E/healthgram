package com.example.gp.data;

import com.example.gp.Items.Post;

import java.util.ArrayList;
import java.util.List;

// Design pattern singleton

/**
 * Posts data class
 */
public class PostsRepository {
    private static final String TAG = "PostData";
    private static List<Post> _allPosts;
    private static PostsRepository instance;

    private PostsRepository() {
        _allPosts = new ArrayList<>();
    }

    public static PostsRepository getInstance() {
        if (instance == null) {
            instance = new PostsRepository();
        }
        return instance;
    }

    public static void clearPostsData() {
        instance = null;
        _allPosts = null;
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

    public void addPreviousPosts(List<Post> posts) {
        if (_allPosts == null) {
            _allPosts = new ArrayList<>();
        }
        if (posts == null) {
            return;
        }
        _allPosts.addAll(posts);
    }

    public List<Post> getPosts() {
        if (_allPosts == null) {
            _allPosts = new ArrayList<>();
        }
        return _allPosts;
    }
}
