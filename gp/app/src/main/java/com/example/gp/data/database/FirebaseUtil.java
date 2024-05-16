package com.example.gp.data.database;

import com.example.gp.data.Database;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

/**
 * A utility class for basic Firebase operations
 *
 * @author Han Bao
 */
public class FirebaseUtil {
    private static final String TAG = "FirebaseUtil";

    /**
     * Get the storage reference
     * @return StorageReference
     */
    public static StorageReference getStorageRef() {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        return storage.getReference();
    }

    /**
     * Get the current user id
     * @return current user id
     */
    public static String getCurrentUserId() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        return auth.getCurrentUser().getUid();
    }

    /**
     * Get the user collection reference
     * @return CollectionReference of users
     */
    public static CollectionReference getUsersRef() {
        return FirebaseFirestore
                .getInstance()
                .collection(Database.getUserCollection());
    }

    /**
     * Get the current user email
     * @return current user email
     */
    public static String getCurrentEmail() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        return auth.getCurrentUser().getEmail();
    }

    /**
     * Get the post collection reference
     * @return CollectionReference of posts
     */
    public static CollectionReference getPostRef() {
        return FirebaseFirestore
                .getInstance()
                .collection(Database.getPostCollection());
    }

    /**
     * Get the friend request collection reference
     * @return CollectionReference of friend requests
     */
    public static CollectionReference getFriendRequestRef() {
        return FirebaseFirestore
                .getInstance()
                .collection(Database.getFriendRequestCollection());
    }

    /**
     * Get firebase auth instance
     * @return firebase auth instance
     */
    public static FirebaseAuth getFireAuth() {
        return FirebaseAuth.getInstance();
    }

    /**
     * Get firebase user instance
     * @return firebase user instance
     */
    public static FirebaseUser getFireUser() {
        return FirebaseAuth.getInstance().getCurrentUser();
    }
}
