package com.example.gp.data;

import android.util.Log;

import com.google.android.gms.common.internal.FallbackServiceBroker;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

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

            db.collection("users").document(userId).set(user)
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
    }
}
