package com.example.gp.data.database;

import static com.example.gp.Utils.TimeUtil.getTimestamp;
import static com.example.gp.data.database.FirebaseUtil.getCurrentUserId;
import static com.example.gp.data.database.FirebaseUtil.getFireUser;
import static com.example.gp.data.database.FirebaseUtil.getPostRef;
import static com.example.gp.data.database.FirebaseUtil.getUsersRef;

import android.util.Log;

import com.example.gp.Items.Post;
import com.example.gp.Utils.MethodUtil;
import com.example.gp.data.PostsData;
import com.example.gp.data.database.model.FriendModel;
import com.example.gp.data.database.model.PostModel;
import com.example.gp.data.database.model.UserModel;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class PostDB {
    // Aka Note's complete version
    private static final String TAG = "Database.Post";
    private static final String POST_PATH = "";
    private static final PostsData postsData = PostsData.getInstance();

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

        UserDB userDB = UserDB.getInstance();

        CollectionReference postsRef = getPostRef();
        FirebaseUser fireUser = getFireUser();
        Post post = (Post) result;
        PostModel postModel = new PostModel();
        postModel.setModelFromPost(post);
        postModel.setAuthorId(fireUser.getUid());
        postModel.setPostTimestamp(getTimestamp());
        postModel.setAuthorName(userDB.getUsername());

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

    public static void GetNewPostsByTime(Timestamp timestamp, Integer limit, Object object, String methodName) {
        timestamp = getTimestamp(timestamp);
        if (limit == null) {
            limit = 9;
        }

        Timestamp lastestPostTimestamp = null;
        PostsData postsData = PostsData.getInstance();
        if (!postsData.getPosts().isEmpty())
            lastestPostTimestamp = postsData.getPosts().get(0).getPostTimestamp();

        CollectionReference postsRef = getPostRef();
        postsRef.orderBy("postTimestamp", Query.Direction.DESCENDING)
                .endBefore(lastestPostTimestamp)
                .limit(limit)
                .get()
                .addOnCompleteListener(task -> {
                    Log.d(TAG, "task: " + task);
                    query2postList(task, object, methodName);
                });
    }

    public static void GetPreviousPostsByTime(Timestamp timestamp, Integer limit, Object object, String methodName) {
        timestamp = getTimestamp(timestamp);
        if (limit == null) {
            limit = 9;
        }

        Timestamp oldestPostTimestamp = null;
        PostsData postsData = PostsData.getInstance();
        if (!postsData.getPosts().isEmpty())
            oldestPostTimestamp = postsData.getPosts().get(postsData.getPosts().size() - 1).getPostTimestamp();

        CollectionReference postsRef = getPostRef();
        postsRef.orderBy("postTimestamp", Query.Direction.DESCENDING)
                .startAfter(oldestPostTimestamp)
                .limit(limit)
                .get()
                .addOnCompleteListener(task -> {
                    Log.d(TAG, "task: " + task);
                    query2postList(task, object, methodName);
                });
    }

    public static void newGetUserPost(Timestamp timestamp, int limit, Object object, String methodName) {
        String userId = getCurrentUserId();

        newGetPostByAuthorId(timestamp, limit, userId, object, methodName);
    }

    public static void newGetPostByAuthorId(Timestamp timestamp, int limit, String userId, Object object, String methodName) {
        timestamp = getTimestamp(timestamp);

        CollectionReference postsRef = getPostRef();
        postsRef.orderBy("postTimestamp", Query.Direction.DESCENDING)
                .whereEqualTo("authorId", userId)
                .get()
                .addOnCompleteListener(task -> {
                    query2postList(task, object, methodName);
                });
    }

    public static void newSetPublic(String postId, Boolean isPublic, Object object, String methodName) {
        CollectionReference postsRef = getPostRef();
        postsRef.document(postId)
                .update("public", isPublic)
                .addOnSuccessListener(aVoid -> {
                    MethodUtil.invokeMethod(object, methodName, true, postId);
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Error writing document", e);
                    MethodUtil.invokeMethod(object, methodName, false, e.getMessage());
                });
    }

    // Private Utils

    private static void query2postList(Task<QuerySnapshot> task, Object object, String methodName) {
        if (!task.isSuccessful()) {
            Exception e = task.getException();
            MethodUtil.invokeMethod(object, methodName, false, e.getMessage());
            Log.e(TAG, "Error getting documents: ", e);
            return;
        }

        if (task.getResult().isEmpty()) {
            String msg = "There are no more new posts to load.";
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

        getViewers(posts, 0, object, methodName);
    }

    private static void getViewers(List<Post> posts, int position, Object object, String methodName) {
        if (position >= posts.size()) {
            FileDB.getImages(posts, 0, object, methodName);
            return;
        }

        if (posts.get(position).isPublic() || posts.get(position).getAuthorId().equals(getCurrentUserId())) {
            getViewers(posts, position + 1, object, methodName);
            return;
        }

        String authorId = posts.get(position).getAuthorId();
        Log.d(TAG, "authorId: " + authorId);
        String userId = getCurrentUserId();
        CollectionReference usersRef = getUsersRef();
        usersRef.document(authorId)
                .get()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Exception e = task.getException();
                        MethodUtil.invokeMethod(object, methodName, false, e.getMessage());
                        Log.e(TAG, "Error getting documents: ", e);
                        return;
                    }

                    if (task.getResult() == null) {
                        getViewers(posts, position + 1, object, methodName);
                        return;
                    }

                    Post post = posts.get(position);

                    // Get user model
                    UserModel userModel = task.getResult().toObject(UserModel.class);
                    Log.d(TAG, "userModel: " + userModel);
                    List<String> viewers = post.getViewers();
                    List<FriendModel> friendModels = new ArrayList<>();
                    // user account deleted
                    if (userModel == null) {
                        getViewers(posts, position + 1, object, methodName);
                        return;
                    }
                    // Get myFriends
                    Map<String, FriendModel> myFriends = userModel.getMyFriends();
                    if (myFriends != null) {
                        for (Map.Entry<String, FriendModel> entry : myFriends.entrySet()) {
                            friendModels.add(entry.getValue());
                        }
                    }
                    Log.d(TAG, "friendModels: " + friendModels);
                    // Get post viewers
                    if (viewers == null) {
                        viewers = new ArrayList<>();
                    }
                    for (FriendModel friendModel : friendModels) {
                        viewers.add(friendModel.getUserId());
                    }
                    Log.d(TAG, "viewers: " + viewers);
                    posts.get(position).setViewers(viewers);
                    if (!viewers.contains(userId)) {
                        post.setPostContent("Only " + post.getAuthorName() + "'s followings can see this post.");
                        post.setImgUUID(null);
                    }
                    getViewers(posts, position + 1, object, methodName);
                });
    }
}
