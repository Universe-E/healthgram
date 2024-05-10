package com.example.gp.data.database;

import android.util.Log;

import com.example.gp.Items.Post;
import com.example.gp.Items.User;
import com.example.gp.Utils.MethodUtil;
import com.example.gp.Utils.TimeUtil;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class PostDB {
    // Aka Note's complete version
    private static final String TAG = "Database.Post";

    /**
     * Save post data to firestore
     *
     * @param post
     * @param object
     * @param methodName Callback return true and Post object if success, false and error message if fail
     */
    public static void savePostData(Post post, Object object, String methodName) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        post.setAuthorId(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid());

        DocumentReference docRef = db.collection("posts").document();

        post.setPostId(docRef.getId());
        post.setPostTimestamp(TimeUtil.getTimestamp());

        docRef.set(post)
                .addOnSuccessListener(dRef -> {
                    /*
                      Callback
                      Return true and Post object
                     */
                    MethodUtil.invokeMethod(object, methodName, true, post);
                    Log.d(TAG, "DocumentSnapshot successfully written!");
                    savePostMap(docRef.getId(), post);
                })
                .addOnFailureListener(e -> {
                    /*
                      Callback
                      Return error message
                     */
                    MethodUtil.invokeMethod(object, methodName, false, e.getMessage());
                    Log.e(TAG, "Error: " + e);
                });
    }

    /**
     * Get a list of posts by post timestamp, descending sorted by post timestamp
     *
     * @param time
     * @param limit
     * @param object
     * @param methodName Callback return true and list of PostMap object List<Post>
     */
    public static void getPostsByTime(Date time, int limit, Object object, String methodName) {
        Timestamp timestamp;
        if (time != null) {
            timestamp = new Timestamp(time);
        } else {
            timestamp = TimeUtil.getTimestamp();
        }

        CollectionReference postRef = FirebaseFirestore.getInstance().collection("posts");
        postRef.orderBy("postTimestamp", Query.Direction.DESCENDING)
                .whereLessThan("postTimestamp", timestamp)
                .limit(limit).get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<DocumentSnapshot> documents = task.getResult().getDocuments();
                        List<Post> posts = new ArrayList<>();
                        for (DocumentSnapshot document : documents) {
                            posts.add(document.toObject(Post.class));
                        }
                    /*
                      Callback
                      Return true and list of PostMap object List<Map<String postId, Post post>>
                     */
                        MethodUtil.invokeMethod(object, methodName, true, posts);
                    } else {
                        Exception e = task.getException();
                    /*
                      Callback
                      Return error message
                     */
                        MethodUtil.invokeMethod(object, methodName, false, Objects.requireNonNull(e).getMessage());
                        Log.e(TAG, "Error getting documents: ", e);
                    }
                });
    }

    /**
     * Get post by postId, note that postId is unique
     *
     * @param postId
     * @param object
     * @param methodName Callback return true and Post object if success, false and error message if fail
     */
    public static void getPostByPostId(String postId, Object object, String methodName) {
        FirebaseFirestore.getInstance().collection("posts").document(postId).get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                        /*
                          Callback
                          Return true and Post object
                         */
                            MethodUtil.invokeMethod(object, methodName, true, document.toObject(Post.class));
                        } else {
                            String message = "No such document";
                        /*
                          Callback
                          Return error message
                         */
                            MethodUtil.invokeMethod(object, methodName, false, message);
                            Log.d(TAG, message);
                        }
                    } else {
                        Exception e = task.getException();
                    /*
                      Callback
                      Return error message
                     */
                        MethodUtil.invokeMethod(object, methodName, false, Objects.requireNonNull(e).getMessage());
                        Log.e(TAG, "Error getting documents: ", e);
                    }
                });
    }

    /**
     * get current user's post
     *
     * @param time
     * @param limit
     * @param object
     * @param methodName Callback return true and list of PostMap object List<Map<String postId, Post post>>
     */
    public static void getUserPost(Date time, int limit, Object object, String methodName) {
        Log.d(TAG, "Method: " + methodName);
        Log.d(TAG, "object: " + object);
        String userId = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();

        getPostsByAuthorId(time, limit, userId, object, methodName);
    }

    /**
     * set public or private
     *
     * @param postId
     * @param isPublic
     * @param object
     * @param methodName Callback return true and postId if success, false and error message if fail
     */
    public static void setPublic(String postId, Boolean isPublic, Object object, String methodName) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("posts")
                .document(postId)
                .update("isPublic", isPublic)
                .addOnSuccessListener(aVoid -> {
                    db.collection("users")
                            .document(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid())
                            .update("postMap." + postId + ".isPublic", isPublic);
                    /*
                      Callback
                      Return true and postId
                     */
                    MethodUtil.invokeMethod(object, methodName, true, postId);
                    Log.d(TAG, "DocumentSnapshot successfully written!");
                })
                .addOnFailureListener(e -> {
                    /*
                      Callback
                      Return error message
                     */
                    MethodUtil.invokeMethod(object, methodName, false, Objects.requireNonNull(e).getMessage());
                    Log.e(TAG, "Error writing document", e);
                });
    }

    /**
     * set allowed viewers
     *
     * @param postId
     * @param viewers
     * @param object  Callback return true and postId if success, false and error message if fail
     */
    public static void setAllowedViewers(String postId, List<String> viewers, Object object, String methodName) {

    }

    /**
     * Get a list of posts by author id
     *
     * @param time
     * @param limit
     * @param authorId
     * @param object
     * @param methodName
     */
    public static void getPostsByAuthorId(Date time, int limit, String authorId, Object object, String methodName) {
        Timestamp timestamp;
        Log.d(TAG, "Get posts by author id: object: " + object);

        if (time == null) {
            timestamp = new Timestamp(TimeUtil.getCurDate());
        } else {
            timestamp = new Timestamp(time);
        }

        String userId = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();

        Log.d(TAG, "Method: " + methodName);
        Log.d(TAG, "userId: " + userId);

        FirebaseFirestore.getInstance()
                .collection("users")
                .document(userId)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        if (task.getResult() == null) {
                            String message = "No post";
                            /*
                              Callback
                              Return error message
                             */
                            MethodUtil.invokeMethod(object, methodName, false, message);
                            Log.d(TAG, message);
                            return;
                        }
                        User user = task.getResult().toObject(User.class);
                        Map<String, Post> postMap = user.getPostMap();
                        if (postMap == null) {
                            String message = "No post";
                            /*
                              Callback
                              Return error message
                             */
                            MethodUtil.invokeMethod(object, methodName, false, message);
                            Log.d(TAG, message);
                            return;
                        }
                        Log.d(TAG, "method name: " + methodName);
                        /*
                          Callback
                          Return true and list of PostMap object List<Map<String postId, Post post>>
                         */
                        MethodUtil.invokeMethod(object, methodName, true, postMap);
                        Log.d(TAG, "Posts: " + postMap.toString());
                    } else {
                        Exception e = task.getException();
                        /*
                          Callback
                          Return error message
                         */
                        MethodUtil.invokeMethod(object, methodName, false, Objects.requireNonNull(e).getMessage());
                        Log.e(TAG, "Error getting documents: ", e);
                    }
                });
    }

    private static void savePostMap(String postId, Post post) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        String userId = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        Log.d(TAG, "userId: " + userId);

        db.collection("users").document(userId)
                .set(Map.of("postMap", Map.of(postId, post)), SetOptions.merge());
    }
}
