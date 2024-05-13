package com.example.gp.data.database;

import static com.example.gp.Utils.TimeUtil.getTimestamp;
import static com.example.gp.data.database.FirebaseUtil.getCurrentUserId;
import static com.example.gp.data.database.FirebaseUtil.getFireUser;
import static com.example.gp.data.database.FirebaseUtil.getPostRef;

import android.util.Log;

import com.example.gp.Items.Post;
import com.example.gp.Items.User;
import com.example.gp.Utils.MethodUtil;
import com.example.gp.data.PostsData;
import com.example.gp.data.database.model.PostModel;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class PostDB {
    // Aka Note's complete version
    private static final String TAG = "Database.Post";
    private static final String POST_PATH = "";
    private static final PostsData postsData = PostsData.getInstance();

    /**
     * Save post data to firestore
     *
     * @param post
     * @param object
     * @param methodName Callback return true and Post object if success, false and error message if fail
     */
    public static void savePostData(Post post, Object object, String methodName) {
        FileDB.saveImage(post, object, methodName);
    }

    /**
     * Get a list of posts by post timestamp, descending sorted by post timestamp
     *
     * @param timestamp
     * @param limit
     * @param object
     * @param methodName Callback return true and list of PostMap object List<Post>
     */
    public static void getPostsByTime(Timestamp timestamp, int limit, Object object, String methodName) {
        timestamp = getTimestamp(timestamp);

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
                        FileDB.getImages(posts, 0, object, methodName);
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
        Log.d(TAG, "getPostByPostId: " + postId);
        FirebaseFirestore.getInstance().collection("posts").document(postId).get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                        /*
                          Callback
                          Return true and Post object
                         */
                            Post post = document.toObject(Post.class);
                            FileDB.getImage(post, object, methodName);
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
     * @param timestamp
     * @param limit
     * @param object
     * @param methodName Callback return true and list of PostMap object List<Map<String postId, Post post>>
     */
    public static void getUserPost(Timestamp timestamp, int limit, Object object, String methodName) {
        Log.d(TAG, "Method: " + methodName);
        Log.d(TAG, "object: " + object);
        String userId = getCurrentUserId();

        getPostsByAuthorId(timestamp, limit, userId, object, methodName);
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
     * @param timestamp
     * @param limit
     * @param authorId
     * @param object
     * @param methodName
     */
    public static void getPostsByAuthorId(Timestamp timestamp, int limit, String authorId, Object object, String methodName) {
        Log.d(TAG, "Get posts by author id: object: " + object);
        timestamp = getTimestamp(timestamp);

        Log.d(TAG, "Method: " + methodName);
        Log.d(TAG, "userId: " + authorId);

        FirebaseFirestore.getInstance()
                .collection("users")
                .document(authorId)
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

    private static void savePostMap(String postId, PostModel postModel) {
        Log.d(TAG, "savePostMap: " + postId);
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        String userId = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        Log.d(TAG, "userId: " + userId);

        db.collection("newTestUsers").document(userId).update("myPosts", FieldValue.arrayUnion(postId))
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                        return;
                    }
                    Log.e(TAG, "Error writing document", task.getException());
                });
        Log.d(TAG, "myPosts: " + postModel);

        db.collection("users").document(userId)
                .set(Map.of("postMap", Map.of(postId, postModel)), SetOptions.merge())
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                        return;
                    }
                    Log.e(TAG, "Error writing document", task.getException());
                });
    }

    /**
     * DO NOT USE THIS METHOD
     *
     * @param isSuccessful
     * @param result
     * @param object
     * @param methodName
     */
    public static void savePost(boolean isSuccessful, Object result, Object object, String methodName) {
        if (!isSuccessful) {
            /*
              Callback
              Return error message
             */
            MethodUtil.invokeMethod(object, methodName, false, result);
            return;
        }
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("posts").document();
        Post post = (Post) result;
        PostModel postModel = new PostModel();
        postModel.setModelFromPost(post);
        Log.d(TAG, "postModel: " + postModel.toString());

        docRef.set(postModel)
                .addOnSuccessListener(dRef -> {
                    /*
                      Callback
                      Return true and Post object
                     */
                    MethodUtil.invokeMethod(object, methodName, true, post);
                    Log.d(TAG, "DocumentSnapshot successfully written!");
                    savePostMap(docRef.getId(), postModel);
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

    // New APIs

    public static void newSavePostData(Post post, Object object, String methodName) {
        Log.d(TAG, "newSavePost: " + post.toString());

        FileDB.newSaveImage(post, object, methodName);
    }

    public static void newSavePost(boolean isSuccessful, Object result, Object object, String methodName) {
        Log.d(TAG, "newSavePost: " + isSuccessful);
        if (!isSuccessful) {
            MethodUtil.invokeMethod(object, methodName, false, result);
            return;
        }

        CollectionReference postsRef = getPostRef();
        FirebaseUser fireUser = getFireUser();
        Post post = (Post) result;
        PostModel postModel = new PostModel();
        postModel.setModelFromPost(post);
        postModel.setAuthorId(fireUser.getUid());
        postModel.setPostTimestamp(getTimestamp());

        Log.d(TAG, "postModel: " + postModel.toString());

        DocumentReference docRef = postsRef.document();
        postModel.setPostId(docRef.getId());

        Log.d(TAG, "docRef: " + docRef);

        docRef.set(postModel)
                .addOnSuccessListener(dRef -> {
                    MethodUtil.invokeMethod(object, methodName, true, post);
                    Log.d(TAG, "DocumentSnapshot successfully written!");
                })
                .addOnFailureListener(e -> {
                    MethodUtil.invokeMethod(object, methodName, false, e.getMessage());
                    Log.e(TAG, "Error: " + e);
                });
    }

    public static void GetNewPostsByTime(Timestamp timestamp, int limit, Object object, String methodName) {
        timestamp = getTimestamp(timestamp);
        Log.d(TAG, "timestamp: " + timestamp);

        Timestamp lastPostTimestamp = null;
        if (!postsData.getPosts().isEmpty())
            lastPostTimestamp = postsData.getPosts().get(0).getPostTimestamp();

        CollectionReference postsRef = getPostRef();
        postsRef.orderBy("postTimestamp", Query.Direction.DESCENDING)
                .whereLessThan("postTimestamp", timestamp)
                .endBefore(lastPostTimestamp)
                .limit(limit)
                .get()
                .addOnCompleteListener(task -> {
                    Log.d(TAG, "task: " + task);
                    if (!task.isSuccessful()) {
                        Exception e = task.getException();
                        MethodUtil.invokeMethod(object, methodName, false, e.getMessage());
                        Log.e(TAG, "Error getting documents: ", e);
                        return;
                    }
                    if (task.getResult() == null) {
                        MethodUtil.invokeMethod(object, methodName, false, "No post");
                        return;
                    }
                    List<DocumentSnapshot> documentSnapshots = task.getResult().getDocuments();
                    List<Post> posts = new ArrayList<>();
                    for (DocumentSnapshot document : documentSnapshots) {
                        Post post = new Post();
                        post.setFromModel(document.toObject(PostModel.class));
                        posts.add(post);
                    }
                    FileDB.getImages(posts, 0, object, methodName);
                });
    }

    public static void newGetUserPost(Timestamp timestamp, int limit, Object object, String methodName) {
        String userId = getCurrentUserId();

        newGetPostByAuthorId(timestamp, limit, userId, object, methodName);
    }

    private static void newGetPostByAuthorId(Timestamp timestamp, int limit, String userId, Object object, String methodName) {
        timestamp = getTimestamp(timestamp);

        CollectionReference postsRef = getPostRef();
        postsRef.orderBy("postTimestamp", Query.Direction.DESCENDING)
                .whereEqualTo("authorId", userId)
                .get()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Exception e = task.getException();
                        MethodUtil.invokeMethod(object, methodName, false, e.getMessage());
                        return;
                    }

                    if (task.getResult().isEmpty()) {
                        String msg = "No post";
                        MethodUtil.invokeMethod(object, methodName, false, msg);
                        return;
                    }

                    List<DocumentSnapshot> documentSnapshots = task.getResult().getDocuments();
                    List<Post> posts = new ArrayList<>();

                    for (DocumentSnapshot document : documentSnapshots) {
                        Post post = new Post();
                        post.setFromModel(document.toObject(PostModel.class));
                        posts.add(post);
                    }

                    FileDB.getImages(posts, 0, object, methodName);
                });
    }
}
