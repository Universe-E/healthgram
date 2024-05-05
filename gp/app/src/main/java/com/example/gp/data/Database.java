package com.example.gp.data;

import android.content.Context;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.gp.Utils.AuthUtil;
import com.example.gp.Utils.MethodUtil;
import com.example.gp.Utils.ToastUtil;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

public class Database {
    // User

    private static final String TAG = "Database";

    public static class User {
        // User data
        private static final String TAG = "Database.User";

        private static String userId;
        private static String username;
        private static String email;

        public User() {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) {
                User.userId = user.getUid();
                User.email = user.getEmail();
                FirebaseFirestore.getInstance().collection("users")
                        .whereEqualTo("email", User.email)
                        .get()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                if (task.getResult().isEmpty()) {
                                    Log.d(TAG, "No such document");
                                } else {
                                    setUsername((String) task.getResult().getDocuments().get(0).get("username"));
                                }
                            }
                        });
            }
        }

        public static void saveUserData(String userId, String username, String email) {
            // save User data to firestore
            FirebaseFirestore db = FirebaseFirestore.getInstance();

            Map<String, Object> user = new HashMap<>();
            user.put("userId", userId);
            user.put("username", username);
            user.put("email", email);

            db.collection("users").document(username).set(user)
                    .addOnSuccessListener(aVoid -> {
                        Log.d(TAG, "UserData successfully written!");
                    })
                    .addOnFailureListener(e -> {
                        Log.w(TAG, "Error writing userData", e);
                    });
        }

        public static void signIn(String input, String password, Object object, String methodName, Object... args) {
            if (AuthUtil.isEmail(input)) {
                FirebaseAuth.getInstance().signInWithEmailAndPassword(input, password)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                try {
                                    MethodUtil.getMethod(object, methodName, args).invoke(object, args);
                                } catch (Exception e) {
                                    Log.e(TAG, "Error: " + e.getMessage());
                                }
                            } else {
                                ToastUtil.showLong((Context) object, "Wrong password or email");
                            }
                        });
            } else {
                signInWithUsername(input, password, object, methodName, args);
            }
        }

        private static void signInWithUsername(String username, String password, Object object, String methodName, Object... args) {
            // Using username to find user email and sign in with email
            FirebaseFirestore.getInstance()
                    .collection("users")
                    .document(username)
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Check if username exist");
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                                if (document.getData().get("email") != null)
                                    // Sign in with email
                                    FirebaseAuth.getInstance().signInWithEmailAndPassword((String) document.getData().get("email"), password)
                                            .addOnCompleteListener(task1 -> {
                                                if (task1.isSuccessful()) {
                                                    try {
                                                        Log.d(TAG, "Method: " + methodName);
                                                        Method method = MethodUtil.getMethod(object, methodName, args);
                                                        method.invoke(object, args);
                                                    } catch (Exception e) {
                                                        ToastUtil.showLong((Context) object, e.getMessage());
                                                    }
                                                } else {
                                                    ToastUtil.showLong((Context) object, "Wrong password");
                                                }
                                            });
                            } else {
                                Log.d(TAG, "Username does not exist");
                                ToastUtil.showLong((Context) object, "Username does not exist");
                            }
                        } else {
                            ToastUtil.showLong((Context) object, task.getException().getMessage());
                        }
                    });
        }

        public static void getEmailByUsername(String username, Object object, Method method) {
            // Get User email by username
            Log.d(TAG, "Method: " + method.getName());

            FirebaseFirestore.getInstance()
                    .collection("users")
                    .document(username)
                    .get()
                    .addOnCompleteListener(task -> {
                       if (task.isSuccessful()) {
                           DocumentSnapshot document = task.getResult();
                           if (document.exists()) {
                               Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                               try {
                                   method.invoke(object, document.getData().get("email"));
                               } catch (Exception e) {
                                   Log.e(TAG, "Error: " + e.getMessage());
                               }
                           } else {
                               Log.d(TAG, "No such document");
                           }
                       }
                    });
        }

        @Nullable
        public static String getEmailByUsername(String username) {
            // Get User email by username
            FirebaseFirestore db = FirebaseFirestore.getInstance();

            Log.d(TAG, "Username: " + username);

            ArrayList<String> email = new ArrayList<>();

            DocumentReference docRef = db.collection("users").document(username);
            Log.d(TAG, "DocumentRef: " + docRef);

            return null;
        }

        public static Task<Boolean> isUsernameExist(String username) {
            // Check if username exist
            FirebaseFirestore db = FirebaseFirestore.getInstance();

            DocumentReference docRef = db.collection("users").document(username);
            return docRef.get().continueWith(task -> {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    return document.exists();
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                    throw Objects.requireNonNull(task.getException());
                }
            });
        }

        public static Task<Boolean> isEmailExist(String email) {
            // Check if email exist
            FirebaseFirestore db = FirebaseFirestore.getInstance();

            Query query = db.collection("users").whereEqualTo("email", email);
            return query.get().continueWith(task -> {
                if (task.isSuccessful()) {
                    return !task.getResult().isEmpty();
                } else {
                    throw Objects.requireNonNull(task.getException());
                }
            });
        }

        private void setUsername(String username) {
            // Set username for using inside async method
            User.username = username;
        }

        public String getUsername() {
            return User.username;
        }
    }

    public static class Post {
        // Note

        private static String TAG = "Database.Post";

        public static void addPost(String post) {
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            mAuth.getCurrentUser();

            if (mAuth.getCurrentUser() != null) {
                Log.d(TAG, "Current user: " + mAuth.getCurrentUser().getEmail());
            } else {
                Log.d(TAG, "No current user");
            }
        }
    }
}
