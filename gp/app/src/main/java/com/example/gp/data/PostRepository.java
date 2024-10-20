package com.example.gp.data;

import android.util.Log;

import com.example.gp.Items.Post;
import com.google.firebase.Timestamp;

import java.util.ArrayList;
import java.util.List;

// Design pattern singleton

/**
 * Posts data class
 * @author Tianci Li
 */
public class PostRepository {
    private static final String TAG = "PostData";
    private static List<Post> _allPosts;
    private static List<Post> filteredPosts;
    private static PostRepository instance;
    private static Timestamp newestPostTime;
    private static Timestamp oldestPostTime;
    private final int LOAD_SIZE = 10;

    private PostRepository() {
        _allPosts = new ArrayList<>();
        filteredPosts = new ArrayList<>();
    }

    public static PostRepository getInstance() {
        if (instance == null) {
            instance = new PostRepository();
        }
        return instance;
    }

    public static void clearPostsData() {
        instance = null;
        _allPosts = null;
        filteredPosts = null;
    }

    public void addNewPosts(List<Post> posts) {
        if (_allPosts == null) {
            _allPosts = new ArrayList<>();
        }
        if (posts == null) {
            return;
        }
        // add refreshed posts
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

    public List<Post> getAllPosts() {
        if (_allPosts == null) {
            _allPosts = new ArrayList<>();
        }
        return _allPosts;
    }

    public void refreshPostMemory() {
        Database.getNewPostsByTime(null, LOAD_SIZE, this, "cbmAddRefreshedPosts");
    }

    public void cbmAddRefreshedPosts(boolean isSuccess, Object args) {
        if (isSuccess) {
            List<Post> posts = (List<Post>) args;
            if (posts.size() > 0) {
                // delete all existing posts
                _allPosts.clear();

                // add posts to the list
                addNewPosts(posts);

                // update the timestamp
                newestPostTime = _allPosts.get(0).getPostTimestamp();
                oldestPostTime = _allPosts.get(_allPosts.size() - 1).getPostTimestamp();

            }
        }
        else {
            // failed to refresh
            Log.d(TAG, "cbmAddLatestPosts: failed");
        }
    }

    public void loadMorePosts() {
        if (oldestPostTime == null) {
            return;
        }
        Database.getPreviousPostsByTime(oldestPostTime, LOAD_SIZE, this, "cbmAddOldPosts");
    }

    public void cbmAddOldPosts(boolean isSuccess, Object args) {
        if (isSuccess) {
            List<Post> posts = (List<Post>) args;
            if (posts.size() > 0) {
                addPreviousPosts(posts);
                oldestPostTime = _allPosts.get(_allPosts.size() - 1).getPostTimestamp();
            }
        }
        else {
            Log.d(TAG, "cbmAddOldPosts: failed");
        }
    }

    public void deletePostAt(String postId) {
        if (_allPosts == null) {
            _allPosts = new ArrayList<>();
        }
        for (int i = 0; i < _allPosts.size(); i++) {
            if (_allPosts.get(i).getPostId().equals(postId)) {
                _allPosts.remove(i);
                return;
            }
        }
    }


//    private int numberChecker(int number) {
//        if (number < 0) {
//            return 0;
//        }
//        return Math.min(_allPosts.size(), number);
//    }

    public List<Post> getPostsByPage(int page, int pageSize) {
        int index = page - 1;
        return _allPosts.subList(index * pageSize, Math.min(_allPosts.size(), index * pageSize + pageSize));
    }

    public Post getPostById(String postId) {
        for (Post post : _allPosts) {
            if (post.getPostId().equals(postId)) {
                return post;
            }
        }
        return null;
    }
}
