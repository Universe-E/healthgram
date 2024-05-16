package com.example.gp.data;

import com.example.gp.Items.Friend;
import com.example.gp.Items.FriendRequest;
import com.example.gp.Items.Post;
import com.example.gp.Utils.MethodUtil;
import com.example.gp.data.database.PostDB;
import com.example.gp.data.database.UserDB;
import com.google.firebase.Timestamp;

import java.util.List;

public class Database {
    private static final String TAG = "Database";
    private static final String USER_PATH = "Users";
    private static final String POST_PATH = "Posts";
    private static final String NOTIFICATION_PATH = "Notifications";
    private static final String FRIEND_REQUEST_PATH = "FriendRequests";

    public static String getUserCollection() {
        return USER_PATH;
    }

    public static String getPostCollection() {
        return POST_PATH;
    }

    public static String getNotificationCollection() {
        return NOTIFICATION_PATH;
    }

    public static String getFriendRequestCollection() {
        return FRIEND_REQUEST_PATH;
    }

    // User account operations

    /**
     * Clear all data
     */
    public static void clearAll() {
        PostRepository.clearPostsData();
        FriendsData.clearFriendsData();
    }

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
        clearAll();
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

    public static void changeAvatar(String avatarUUID, Object object, String methodName) {
        UserDB.changeAvatar(avatarUUID, object, methodName);
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
    public static void follow(Friend friend, Object object, String methodName) {
        UserDB.follow(friend, object, methodName);
    }

    public static void unfollow(String userId, Object object, String methodName) {
        UserDB.unfollow(userId, object, methodName);
    }

    /**
     * Send friend request to user
     * Callback parameters: true and list of FriendRequest object if success, false and error message if fail
     *
     * @param friendRequest friend request object to be added
     * @param object        The object that calls the method
     * @param methodName    Callback method name
     */
    public static void sendFriendRequestTo(FriendRequest friendRequest, Object object, String methodName) {
        UserDB.newSendFriendRequestTo(friendRequest, object, methodName);
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
    public static void getFriendRequestList(Timestamp timpestamp, int limit, Object object, String methodName) {
        UserDB.getFriendRequestList(timpestamp, limit, object, methodName);
    }

    public static void getFriendRequestById(String requestId, Object object, String methodName) {
        UserDB.getFriendRequestById(requestId, object, methodName);
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
    public static void getFollowList(String nickname, Integer limit, Object object, String methodName) {
        UserDB.newGetFollowList(nickname, limit, object, methodName);
    }

    public static void processFriendRequest(FriendRequest friendRequest, Object object, String methodName) {
        UserDB.processFriendRequest(friendRequest, object, methodName);
    }

    public static void getNotificationList(Object object, String methodName) {
        UserDB.getNotificationList(object, methodName);
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
        //TODO: implement this method
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
        //TODO: implement this method
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
        //TODO: implement this method
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
        PostDB.savePostData(post, object, methodName);
    }

    /**
     * Delete post
     *
     * @param postId
     * @param object
     * @param methodName
     */
    public static void deletePost(String postId, Object object, String methodName) {
        PostDB.deletePost(postId, object, methodName);
    }

    /**
     * Get a list of posts by post timestamp, descending sorted by post timestamp
     * Callback parameters: true and PostMap object, false and error message if fail
     *
     * @param timestamp  post timestamp, e.g., if you try to get the list first time, set time to null, or set time to the last post you already got timestamp
     * @param limit      The number of post retrieval, you can leave it as null for default 9 limit
     * @param object     The object that calls the method
     * @param methodName The method name
     */
    public static void getNewPostsByTime(Timestamp timestamp, int limit, Object object, String methodName) {
        PostDB.GetNewPostsByTime(timestamp, limit, object, methodName);
    }

    /**
     * Get a list of posts by post timestamp, descending sorted by post timestamp
     *
     * @param timestamp  we are not using it for now just leave it null;
     * @param limit      The number of post retrieval, you can leave it as null for default 9 limit
     * @param object     The object that calls the method
     * @param methodName The method name
     */
    public static void getPreviousPostsByTime(Timestamp timestamp, int limit, Object object, String methodName) {
        PostDB.GetPreviousPostsByTime(timestamp, limit, object, methodName);
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
        PostDB.newGetUserPost(timestamp, limit, object, methodName);
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
        PostDB.newSetPublic(postId, isPublic, object, methodName);
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
        PostDB.newGetPostByAuthorId(timestamp, limit, authorId, object, methodName);
    }
}
