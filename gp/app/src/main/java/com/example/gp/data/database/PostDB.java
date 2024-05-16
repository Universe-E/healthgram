package com.example.gp.data.database;

import static com.example.gp.Utils.TimeUtil.getTimestamp;
import static com.example.gp.data.database.FirebaseUtil.getCurrentUserId;
import static com.example.gp.data.database.FirebaseUtil.getFireUser;
import static com.example.gp.data.database.FirebaseUtil.getPostRef;
import static com.example.gp.data.database.FirebaseUtil.getUsersRef;

import android.util.Log;

import com.example.gp.Items.Post;
import com.example.gp.Utils.MethodUtil;
import com.example.gp.data.PostRepository;
import com.example.gp.data.database.model.FriendModel;
import com.example.gp.data.database.model.NotificationModel;
import com.example.gp.data.database.model.PostModel;
import com.example.gp.data.database.model.UserModel;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.Filter;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.firestore.WriteBatch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A utility class for post operation in Firebase Firestore
 *
 * @author Han Bao
 */
public class PostDB {
    // Aka Note's complete version
    private static final String TAG = "Database.Post";
    private static final String POST_PATH = "";
    private static final PostRepository POSTS_REPOSITORY = PostRepository.getInstance();

    /**
     * Save post data to firebase firestore
     *
     * @param post       post object
     * @param object     object that calls this method
     * @param methodName method name that calls this method
     */
    public static void savePostData(Post post, Object object, String methodName) {
        Log.d(TAG, "newSavePost: " + post.toString());

        // Save image first
        FileDB.newSaveImage(post, object, methodName);
    }

    /**
     * Save post to firebase firestore
     * Do Not Use This Method from front end
     *
     * @param isSuccessful
     * @param result
     * @param object
     * @param methodName
     */
    public static void savePost(boolean isSuccessful, Object result, Object object, String methodName) {
        Log.d(TAG, "newSavePost: " + isSuccessful);
        if (!isSuccessful) {
            MethodUtil.invokeMethod(object, methodName, false, result);
            return;
        }

        // get user data
        UserDB userDB = UserDB.getInstance();
        // get posts reference
        CollectionReference postsRef = getPostRef();
        // get current user
        FirebaseUser fireUser = getFireUser();
        // create post model
        Post post = (Post) result;
        PostModel postModel = new PostModel();
        postModel.setModelFromPost(post);
        postModel.setAuthorId(fireUser.getUid());
        postModel.setPostTimestamp(getTimestamp());
        postModel.setAuthorName(userDB.getUsername());

        // get post id
        DocumentReference docRef = postsRef.document();
        postModel.setPostId(docRef.getId());

        // save post model to firebase firestore
        docRef.set(postModel)
                .addOnSuccessListener(dRef -> {
                    // create notification model
                    NotificationModel notificationModel = new NotificationModel();
                    notificationModel.setSenderId(userDB.getUserId());
                    notificationModel.setUsername(userDB.getUsername());
                    notificationModel.setType("post_update");
                    notificationModel.setMessage("New post from " + userDB.getUsername());
                    notificationModel.setTimestamp(getTimestamp());
                    notificationModel.setRead(false);
                    notificationModel.setNotificationId(docRef.getId());
                    // Notify followers
                    notifyNewPostToFollowers(notificationModel, object, methodName);
//                    MethodUtil.invokeMethod(object, methodName, true, postModel.getPostId());
                })
                .addOnFailureListener(e -> {
                    MethodUtil.invokeMethod(object, methodName, false, e.getMessage());
                    Log.e(TAG, "Error: " + e);
                });
    }

    /**
     * Delete post from firebase firestore
     *
     * @param postId     post id
     * @param object     object that calls this method
     * @param methodName method name that calls this method
     */
    public static void deletePost(String postId, Object object, String methodName) {
        CollectionReference postsRef = getPostRef();

        // Delete post from firebase firestore
        postsRef.document(postId)
                .delete()
                .addOnSuccessListener(aVoid -> {
                    MethodUtil.invokeMethod(object, methodName, true, postId);
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Error delete document", e);
                    MethodUtil.invokeMethod(object, methodName, false, e.getMessage());
                });
    }

    /**
     * Get new posts by time
     *
     * @param timestamp  Timestamp
     * @param limit      Integer
     * @param object     Object
     * @param methodName String
     */
    public static void GetNewPostsByTime(Timestamp timestamp, Integer limit, Object object, String methodName) {
        timestamp = getTimestamp(timestamp);
        if (limit == null) {
            limit = 9;
        }

        Timestamp lastestPostTimestamp = null;
        PostRepository postRepository = PostRepository.getInstance();
        if (!postRepository.getAllPosts().isEmpty())
            lastestPostTimestamp = postRepository.getAllPosts().get(0).getPostTimestamp();

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

    /**
     * Get previous posts by time
     *
     * @param timestamp  Timestamp
     * @param limit      Integer
     * @param object     Object
     * @param methodName String
     */
    public static void GetPreviousPostsByTime(Timestamp timestamp, Integer limit, Object object, String methodName) {
        timestamp = getTimestamp(timestamp);
        if (limit == null) {
            limit = 9;
        }

        Timestamp oldestPostTimestamp = null;
        PostRepository postRepository = PostRepository.getInstance();
        if (!postRepository.getAllPosts().isEmpty())
            oldestPostTimestamp = postRepository.getAllPosts().get(postRepository.getAllPosts().size() - 1).getPostTimestamp();

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

    /**
     * Get posts by author id
     *
     * @param timestamp  timestamp
     * @param limit      limit
     * @param object     object calls this function
     * @param methodName method name for callback
     */
    public static void getUserPost(Timestamp timestamp, int limit, Object object, String methodName) {
        String userId = getCurrentUserId();

        getPostByAuthorId(timestamp, limit, userId, object, methodName);
    }

    /**
     * Get posts by author id
     *
     * @param timestamp  timestamp
     * @param limit      limit
     * @param userId     author id
     * @param object     object calls this function
     * @param methodName method name for callback
     */
    public static void getPostByAuthorId(Timestamp timestamp, int limit, String userId, Object object, String methodName) {

        CollectionReference postsRef = getPostRef();
        postsRef.orderBy("postTimestamp", Query.Direction.DESCENDING)
                .whereEqualTo("authorId", userId)
                .get()
                .addOnCompleteListener(task -> {
                    query2postList(task, object, methodName);
                });
    }

    /**
     *
     * @param postId
     * @param isPublic
     * @param object
     * @param methodName
     */
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

    public static void likePost(String postId, Object object, String methodName) {
        CollectionReference postsRef = getPostRef();

        postsRef.document(postId)
                .update("likeCount", FieldValue.increment(1))
                .addOnSuccessListener(aVoid ->
                        MethodUtil.invokeMethod(object, methodName, true, postId)
                )
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Error writing document", e);
                    MethodUtil.invokeMethod(object, methodName, false, e.getMessage());
                });
    }

    // Search

    public static void getNewestFiftyPosts(Object object, String methodName) {
        CollectionReference postsRef = getPostRef();

        postsRef.orderBy("postTimestamp", Query.Direction.DESCENDING)
                .limit(50)
                .get()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Exception e = task.getException();
                        MethodUtil.invokeMethod(object, methodName, false, e.getMessage());
                        Log.e(TAG, "Error getting documents: ", e);
                        return;
                    }
                    List<DocumentSnapshot> documentSnapshots = task.getResult().getDocuments();
                    List<Post> posts = new ArrayList<>();
                    documentSnapshots.forEach(documentSnapshot -> {
                        PostModel postModel = documentSnapshot.toObject(PostModel.class);
                        Post post = new Post();
                        post.setFromModel(postModel);
                        post.setImg(null);
                        posts.add(post);
                    });
                    MethodUtil.invokeMethod(object, methodName, true, posts);
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Error getting documents: ", e);
                    MethodUtil.invokeMethod(object, methodName, false, e.getMessage());
                });
    }

    public static void getPostById(String postId, Object object, String methodName) {
        CollectionReference postsRef = getPostRef();

        postsRef.whereEqualTo("postId", postId)
                .get()
                .addOnCompleteListener(task -> {
                    query2postList(task, object, methodName);
                });
    }

    // Private Utils

    private static void query2postList(Task<QuerySnapshot> task, Object object, String
            methodName) {
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

    private static void notifyNewPostToFollowers(NotificationModel notificationModel, Object
            object, String methodName) {
        CollectionReference usersRef = getUsersRef();
        UserDB userDB = UserDB.getInstance();
        Log.d(TAG, "NotificationModel: " + notificationModel.getSenderId());
        String query = "myFriends." + notificationModel.getSenderId() + ".userId";


        usersRef.where(Filter.greaterThanOrEqualTo(query, notificationModel.getSenderId()))
                .get()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Exception e = task.getException();
                        MethodUtil.invokeMethod(object, methodName, false, e.getMessage());
                        Log.e(TAG, "Error getting documents: ", e);
                        return;
                    }
                    if (task.getResult() == null) {
                        Log.d(TAG, "No followers");
                        MethodUtil.invokeMethod(object, methodName, true, notificationModel.getNotificationId());
                        return;
                    }
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    WriteBatch batch = db.batch();
                    List<DocumentSnapshot> documentSnapshots = task.getResult().getDocuments();
                    Log.d(TAG, "documentSnapshots: " + documentSnapshots);
                    for (DocumentSnapshot document : documentSnapshots) {
                        Log.d(TAG, "document: " + document.toString());
                        Map<String, Object> update = new HashMap<>();
                        update.put("myNotifications", Map.of(notificationModel.getNotificationId(), notificationModel));
                        batch.set(document.getReference(), update, SetOptions.merge());
                    }
                    batch.commit();
                    MethodUtil.invokeMethod(object, methodName, true, notificationModel.getNotificationId());
                });
    }

    private static void getViewers(List<Post> posts, int position, Object object, String
            methodName) {
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
