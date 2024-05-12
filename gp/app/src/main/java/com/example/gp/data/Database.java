package com.example.gp.data;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.gp.Items.Friend;
import com.example.gp.Items.FriendRequest;
import com.example.gp.Items.Post;
import com.example.gp.Items.User;
import com.example.gp.Utils.AuthUtil;
import com.example.gp.Utils.MethodUtil;
import com.example.gp.Utils.TimeUtil;
import com.example.gp.Utils.ToastUtil;
import com.example.gp.data.database.FileDB;
import com.example.gp.data.database.PostDB;
import com.example.gp.data.database.UserDB;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.SetOptions;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Database {
    private static final String TAG = "Database";

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
        UserDB.signUp(username, email, password, object, methodName);
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
     * @param date       Don't need this parameter for this stage just use null
     * @param limit      The number of request retrieval
     * @param object     The object that calls the method
     * @param methodName Callback method name
     */
    public static void getFriendRequest(Date date, int limit, Object object, String methodName) {
        UserDB.getFriendRequest(date, limit, object, methodName);
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

    // Post operations

    /**
     * Save post data to firestore
     * Callback parameters: true and postId if success, false and error message if fail
     *
     * @param post Post object to be saved
     * @param object The object that calls the method
     * @param methodName Callback method name
     */
    public static void savePostData(Post post, Object object, String methodName) {
        PostDB.savePostData(post, object, methodName);
    }

    /**
     * Get a list of posts by post timestamp, descending sorted by post timestamp
     * Callback parameters: true and PostMap object, false and error message if fail
     *
     * @param time   post timestamp, e.g., if you try to get the list first time, set time to null, or set time to the last post you already got timestamp
     * @param limit  The number of post retrieval
     * @param object The object that calls the method
     * @param methodName The method name
     */
    public static void getPostsByTime(Date time, int limit, Object object, String methodName) {
        PostDB.getPostsByTime(time, limit, object, methodName);
    }

    /**
     * Get post by postId, note that postId is unique
     * Callback parameters: true and Post object if success, false and error message if fail
     *
     * @param postId     post id
     * @param object     The object that calls the method
     * @param methodName  Callback method name
     */
    public static void getPostByPostId(String postId, Object object, String methodName) {
        PostDB.getPostByPostId(postId, object, methodName);
    }

    /**
     * Get current user's post
     * Callback parameters: true and PostMap object, false and error message if fail
     *
     * @param time The timestamp, e.g., if you try to get the list first time, set time to null, or set time to the last post you already got timestamp
     * @param limit The number of post retrieval
     * @param object The object that calls the method
     * @param methodName The method name
     */
    public static void getUserPost(Date time, int limit, Object object, String methodName) {
        PostDB.getUserPost(time, limit, object, methodName);
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
     * @param postId   post id
     * @param viewers  list of user id
     * @param object   The object that calls the method
     */
    public static void setAllowedViewers(String postId, List<String> viewers, Object object, String methodName) {
        PostDB.setAllowedViewers(postId, viewers, object, methodName);
    }

    /**
     * Get a list of posts by author id
     * Callback parameters: true and PostMap object, false and error message if fail
     *
     * @param time   post timestamp, e.g., if you try to get the list first time, set time to null, or set time to the last post you already got timestamp
     * @param limit  The number of post retrieval
     * @param authorId   author id aka user id
     * @param object The object that calls the method
     * @param methodName The method name
     */
    public static void getPostsByAuthorId(Date time, int limit, String authorId, Object object, String methodName) {
        PostDB.getPostsByAuthorId(time, limit, authorId, object, methodName);
    }
}
