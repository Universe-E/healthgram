package com.example.gp.data;

import android.content.Context;
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
     * @param input
     * @param password
     * @param object
     * @param methodName
     */
    public static void signIn(String input, String password, Object object, String methodName) {
        UserDB.signIn(input, password, object, methodName);
    }

    /**
     * Allow user to sign up
     * @param username
     * @param email
     * @param password
     * @param object
     * @param methodName
     */
    public static void signUp(String username, String email, String password, Object object, String methodName) {
        UserDB.signUp(username, email, password, object, methodName);
    }

    /**
     * Allow user to sign out
     * @param object
     * @param methodName
     */
    public static void signOut(Object object, String methodName) {
        UserDB.signOut(object, methodName);
    }

    /**
     * Check if user is signed in
     * @param object
     * @param methodName
     */
    public static void checkSignedIn(Object object, String methodName) {
        UserDB.checkSignedIn(object, methodName);
    }

    // Friend operations

    /**
     * Add friend to friendList
     * @param friend
     * @param object
     * @param methodName
     */
    public static void addFriend(Friend friend, Object object, String methodName) {
        UserDB.addFriend(friend, object, methodName);
    }

    /**
     * Get friend request
     * @param friendRequest
     * @param object
     * @param methodName
     */
    public static void sendFriendRequestTo(FriendRequest friendRequest, Object object, String methodName) {
        UserDB.sendFriendRequestTo(friendRequest, object, methodName);
    }

    /**
     * Get friend request
     * @param date
     * @param limit
     * @param object
     * @param methodName
     */
    public static void getFriendRequest(Date date, int limit, Object object, String methodName) {
        UserDB.getFriendRequest(date, limit, object, methodName);
    }

    /**
     * Get friend list
     * @param nickname
     * @param limit
     * @param object
     * @param methodName
     */
    public static void getFriendList(String nickname, int limit, Object object, String methodName) {
        UserDB.getFriendList(nickname, limit, object, methodName);
    }


}
