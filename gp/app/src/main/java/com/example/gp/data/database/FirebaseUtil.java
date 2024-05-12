package com.example.gp.data.database;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class FirebaseUtil {
    private static final String TAG = "FirebaseUtil";

    public static StorageReference getStroegeRef() {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        return storage.getReference();
    }
}
