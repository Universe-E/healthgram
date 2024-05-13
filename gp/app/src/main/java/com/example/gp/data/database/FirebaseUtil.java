package com.example.gp.data.database;

import com.example.gp.data.Database;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class FirebaseUtil {
    private static final String TAG = "FirebaseUtil";

    public static StorageReference getStorageRef() {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        return storage.getReference();
    }

    public static String getCurrentUserId() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        return auth.getCurrentUser().getUid();
    }

    public static CollectionReference getUsersRef() {
        return FirebaseFirestore
                .getInstance()
                .collection(Database.getUserCollection());
    }

    public static String getCurrentEmail() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        return auth.getCurrentUser().getEmail();
    }

    public static CollectionReference getPostRef() {
        return FirebaseFirestore
                .getInstance()
                .collection(Database.getPostCollection());
    }

    public static CollectionReference getNotificationRef() {
        return FirebaseFirestore
                .getInstance()
                .collection(Database.getNotificationCollection());
    }

    public static FirebaseAuth getFireAuth() {
        return FirebaseAuth.getInstance();
    }

    public static FirebaseUser getFireUser() {
        return FirebaseAuth.getInstance().getCurrentUser();
    }
}
