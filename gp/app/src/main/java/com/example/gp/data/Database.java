package com.example.gp.data;

import android.util.Log;

import androidx.annotation.Nullable;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class Database {
    // User

    private static final String TAG = "Database";

    public static class user {
        public static boolean saveUserData(String userId, String username, String email) {
            // save user data to firestore
            AtomicBoolean isSaved = new AtomicBoolean(false);

            FirebaseFirestore db = FirebaseFirestore.getInstance();

            Map<String, Object> user = new HashMap<>();
            user.put("userId", userId);
            user.put("username", username);
            user.put("email", email);

            db.collection("users").document(username).set(user)
                    .addOnSuccessListener(aVoid -> {
                        isSaved.set(true);
                        Log.d(TAG, "UserData successfully written!");
                    })
                    .addOnFailureListener(e -> {
                        isSaved.set(false);
                        Log.w(TAG, "Error writing userData", e);
                    });

            return isSaved.get();
        }

        @Nullable
        public static String getUserEmailByUsername(String username) {
            // Get user email by username
            FirebaseFirestore db = FirebaseFirestore.getInstance();

            AtomicReference<String> email = new AtomicReference<>();

            DocumentReference docRef = db.collection("users").document(username);
            docRef.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        email.set(document.getString("email"));
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            });

            return email.get();
        }
    }
}
