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
    // User

    private static final String TAG = "Database";

    /**
     * Allow user to sign in
     * @param input
     * @param password
     * @param object
     * @param methodName
     */
    static void signIn(String input, String password, Object object, String methodName) {
        UserDB.signIn(input, password, object, methodName);
    }

    /**
     * Allow
     * @param username
     * @param email
     * @param password
     * @param object
     * @param methodName
     */
    static void signUp(String username, String email, String password, Object object, String methodName) {
        UserDB.signUp(username, email, password, object, methodName);
    }
}
