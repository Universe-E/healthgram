package com.example.gp.data;

import com.example.gp.Items.Friend;
import com.example.gp.Items.FriendRequest;
import com.example.gp.Items.Post;
import com.example.gp.Utils.MethodUtil;
import com.example.gp.data.database.PostDB;
import com.example.gp.data.database.UserDB;
import com.google.firebase.Timestamp;

import java.util.Date;
import java.util.List;

public class Database {
    private static final String TAG = "Database";
    private static final String USER_PATH = "Users";
    private static final String POST_PATH = "Posts";
    private static final String NOTIFICATION_PATH = "Notifications";

    public static String getUserCollection() {
        return USER_PATH;
    }

    public static String getPostCollection() {
        return POST_PATH;
    }

    public static String getNotificationCollection() {
        return NOTIFICATION_PATH;
    }

    // User account operations

    /**
     * Allow user to sign in
     * Callback parameters: true and User object if success, false and error message if fail
     *
     * @param input      username or email
     * @param password   password
     * @param object     The object that calls the method
     * @param methodName Callback method name
     */
    public static void signIn(String input, String password, Object object, String methodName) {
        UserDB.signIn(input, password, object, methodName);
    }

    /**
     * Allow user to sign up
     * Callback parameters: true and User object if success, false and error message if fail
     *
     * @param username   email
     * @param email      email
     * @param password   password
     * @param object     The object that calls the method
     * @param methodName Callback method name
     */
    public static void signUp(String username, String email, String password, Object object, String methodName) {
//        UserDB.signUp(username, email, password, object, methodName);
        UserDB.newSignUp(username, email, password, object, methodName);
    }

    /**
     * Allow user to sign out
     * Callback parameters: true and user object if success, false and error message if fail
     *
     * @param object     The object that calls the method
     * @param methodName Callback method name
     */
    public static void signOut(Object object, String methodName) {
        UserDB.signOut(object, methodName);
    }

    /**
     * Check if user is signed in
     * Callback parameters: true and User object if success, false and error message if fail
     *
     * @param object     The object that calls the method
     * @param methodName Callback method name
     */
    public static void checkSignedIn(Object object, String methodName) {
        UserDB.checkSignedIn(object, methodName);
    }

    // Friend operations

    /**
     * Add friend to friendList
     * Callback parameters: true and Friend object if success, false and error message if fail
     *
     * @param friend     friend object to be added
     * @param object     The object that calls the method
     * @param methodName Callback method name
     */
    public static void addFriend(Friend friend, Object object, String methodName) {
        UserDB.addFriend(friend, object, methodName);
    }

    /**
     * Get friend request
     * Callback parameters: true and list of FriendRequest object if success, false and error message if fail
     *
     * @param friendRequest friend request object to be added
     * @param object        The object that calls the method
     * @param methodName    Callback method name
     */
    public static void sendFriendRequestTo(FriendRequest friendRequest, Object object, String methodName) {
        UserDB.sendFriendRequestTo(friendRequest, object, methodName);
    }

    /**
     * Get friend request
     * Callback parameters: true and list of FriendRequest object if success, false and error message if fail
     *
     * @param timpestamp Don't need this parameter for this stage just use null
     * @param limit      The number of request retrieval
     * @param object     The object that calls the method
     * @param methodName Callback method name
     */
    public static void getFriendRequest(Timestamp timpestamp, int limit, Object object, String methodName) {
        UserDB.getFriendRequest(timpestamp, limit, object, methodName);
    }

    /**
     * Get friend list
     * Callback parameters: true and list of Friend object if success, false and error message if fail
     *
     * @param nickname   nickname
     * @param limit      The number of friend retrieval
     * @param object     The object that calls the method
     * @param methodName Callback method name
     */
    public static void getFriendList(String nickname, int limit, Object object, String methodName) {
        UserDB.getFriendList(nickname, limit, object, methodName);
    }

    // Block operations

    /**
     * Block user by id
     * To block someone
     * Callback parameters: true and user object if success, false and error message if fail
     *
     * @param userId     user id
     * @param block      true if block, false if unblock
     * @param object     The object that calls the method
     * @param methodName Callback method name
     */
    public static void blockUserById(String userId, boolean block, Object object, String methodName) {
        // TODO: implement this method
        String msg = "Placeholder";
        MethodUtil.invokeMethod(object, methodName, true, (Object) msg);
    }

    /**
     * Check if current user is blocked by userId
     * Callback parameters: true and user object if success, false and error message if fail
     *
     * @param userId     user id
     * @param object     The object that calls the method
     * @param methodName Callback method name
     */
    public static void checkBlockById(String userId, Object object, String methodName) {
        // TODO: implement this method
        String msg = "Placeholder";
        MethodUtil.invokeMethod(object, methodName, true, (Object) msg);
    }

    /**
     * Get your block list
     * Callback parameters: true and list of Friend object if success, false and error message if fail
     *
     * @param object     The object that calls the method
     * @param methodName Callback method name
     */
    public static void getBlockList(Object object, String methodName) {
        // TODO: implement this method
        List<Friend> blockList = null;
        MethodUtil.invokeMethod(object, methodName, true, (Object) blockList);
    }

    // Post operations

    /**
     * Save post data to firestore
     * Callback parameters: true and postId if success, false and error message if fail
     *
     * @param post       Post object to be saved
     * @param object     The object that calls the method
     * @param methodName Callback method name
     */
    public static void savePostData(Post post, Object object, String methodName) {
//        PostDB.savePostData(post, object, methodName);
        PostDB.newSavePostData(post, object, methodName);
    }

    /**
     * Get a list of posts by post timestamp, descending sorted by post timestamp
     * Callback parameters: true and PostMap object, false and error message if fail
     *
     * @param timestamp  post timestamp, e.g., if you try to get the list first time, set time to null, or set time to the last post you already got timestamp
     * @param limit      The number of post retrieval
     * @param object     The object that calls the method
     * @param methodName The method name
     */
    public static void getPostsByTime(Timestamp timestamp, int limit, Object object, String methodName) {
//        PostDB.getPostsByTime(timestamp, limit, object, methodName);
        PostDB.newGetPostsByTime(timestamp, limit, object, methodName);
    }

    /**
     * Get post by postId, note that postId is unique
     * Callback parameters: true and Post object if success, false and error message if fail
     *
     * @param postId     post id
     * @param object     The object that calls the method
     * @param methodName Callback method name
     */
    public static void getPostByPostId(String postId, Object object, String methodName) {
        PostDB.getPostByPostId(postId, object, methodName);
    }

    /**
     * Get current user's post
     * Callback parameters: true and PostMap object, false and error message if fail
     *
     * @param timestamp  The timestamp, e.g., if you try to get the list first timestamp, set timestamp to null, or set timestamp to the last post you already got timestamp
     * @param limit      The number of post retrieval
     * @param object     The object that calls the method
     * @param methodName The method name
     */
    public static void getUserPost(Timestamp timestamp, int limit, Object object, String methodName) {
        PostDB.getUserPost(timestamp, limit, object, methodName);
    }

    /**
     * Set public or private
     * Callback parameters: true and postId if success, false and error message if fail
     *
     * @param postId     post id
     * @param isPublic   true if public, false if private
     * @param object     The object that calls the method
     * @param methodName The method name
     */
    public static void setPublic(String postId, Boolean isPublic, Object object, String methodName) {
        PostDB.setPublic(postId, isPublic, object, methodName);
    }

    /**
     * Set allowed viewers
     * Callback parameters: true and postId if success, false and error message if fail
     *
     * @param postId  post id
     * @param viewers list of user id
     * @param object  The object that calls the method
     */
    public static void setAllowedViewers(String postId, List<String> viewers, Object object, String methodName) {
        PostDB.setAllowedViewers(postId, viewers, object, methodName);
    }

    /**
     * Get a list of posts by author id
     * Callback parameters: true and PostMap object, false and error message if fail
     *
     * @param timestamp  post timestamp, e.g., if you try to get the list first time, set time to null, or set time to the last post you already got timestamp
     * @param limit      The number of post retrieval
     * @param authorId   author id aka user id
     * @param object     The object that calls the method
     * @param methodName The method name
     */
    public static void getPostsByAuthorId(Timestamp timestamp, int limit, String authorId, Object object, String methodName) {
        PostDB.getPostsByAuthorId(timestamp, limit, authorId, object, methodName);
    }
}
