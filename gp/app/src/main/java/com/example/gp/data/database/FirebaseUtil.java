package com.example.gp.data.database;

import com.google.firebase.auth.FirebaseAuth;
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
}
