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

    public static CollectionReference getDatabseRef() {
        return FirebaseFirestore.getInstance().collection(Database.getDatabaseName());
    }

    public static FirebaseAuth getFireAuth() {
        return FirebaseAuth.getInstance();
    }

    public static FirebaseUser getFireUser() {
        return FirebaseAuth.getInstance().getCurrentUser();
    }
}
