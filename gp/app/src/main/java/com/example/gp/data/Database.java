package com.example.gp.data;

import android.content.Context;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.gp.Items.Friend;
import com.example.gp.Items.Post;
import com.example.gp.Utils.AuthUtil;
import com.example.gp.Utils.MethodUtil;
import com.example.gp.Utils.ToastUtil;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Database {
    // User

    private static final String TAG = "Database";

    // Design pattern Singleton
    public static class UserDB {
        // User data
        private static final String TAG = "Database.User";

        private static String userId;
        private static String username;
        private static String email;

        public UserDB() {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) {
                UserDB.userId = user.getUid();
                UserDB.email = user.getEmail();
                FirebaseFirestore.getInstance().collection("users")
                    .document(userId)
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                setUsername((String) document.get("username"));
                            } else {
                                Log.d(TAG, "No such document");
                            }
                        }
                    });
            }
        }

        public static void signIn(String input, String password, Object object, String methodName, Object... args) {
            if (AuthUtil.isEmail(input)) {
                signInWithEmail(input, password, object, methodName, args);
            } else {
                signInWithUsername(input, password, object, methodName, args);
            }
        }

        public static void signUp(String username, String email, String password, Object object, String methodName, Object... args) {
            FirebaseFirestore.getInstance()
                .collection("users")
                .whereEqualTo("username", username)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        if (!task.getResult().isEmpty()) {
                            DocumentSnapshot document = task.getResult().getDocuments().get(0);
                            if (document.exists()) {
                                ToastUtil.showLong((Context) object, "Username already exist");
                            }
                        } else {
                            FirebaseAuth.getInstance()
                                .createUserWithEmailAndPassword(email, password)
                                .addOnCompleteListener(task1 -> {
                                    if (task1.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        FirebaseUser user = task1.getResult().getUser();
                                        String userId = Objects.requireNonNull(user).getUid();
                                        saveUserData(userId, username, email);
                                        ToastUtil.showLong((Context) object, "Create account successfully");
                                        try {
                                            MethodUtil.getMethod(object, methodName, args).invoke(object, args);
                                        } catch (Exception e) {
                                            Log.e(TAG, "Error: " + e.getMessage());
                                        }
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Exception e = task1.getException();
                                        Log.e(TAG, "Error: " + e);
                                        ToastUtil.showLong((Context) object, "Create account failed: " + e.getMessage());
                                    }
                                });
                        }
                    }
                });
        }

        public static void getFriendList(String nickname, int limit, Object object, String methodName, Object ...args) {
            Log.d(TAG, "Method: " + methodName);

            String userId = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();

            FirebaseFirestore.getInstance().document(userId)
                    .collection("friendMap")
                    .orderBy("nickname", Query.Direction.ASCENDING)
                    .whereGreaterThan("nickname", nickname)
                    .limit(limit).get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            List<DocumentSnapshot> documents = task.getResult().getDocuments();
                            List<Map<String, Object>> friends = new ArrayList<>();
                            for (DocumentSnapshot document : documents) {
                                // will return a list of friendMap which is a map of <String userId, Friend friend>
                                friends.add(Map.of(document.getId(), document.toObject(Friend.class)));
                            }
                            try {
                                MethodUtil.invokeMethod(object, methodName, args, friends);
                            } catch (Exception e) {
                                Log.e(TAG, "Error: " + e.getMessage());
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    });
        }

        public static void signOut(Object object, String methodName, Object... args) {
            FirebaseAuth.getInstance().signOut();

            try {
                MethodUtil.getMethod(object, methodName, args).invoke(object, args);
            } catch (Exception e) {
                Log.e(TAG, "Error: " + e.getMessage());
            }
        }

        public static void checkSignedIn(Object object, String methodName, Object... args) {
            // Check if user is signed in
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

            if (user == null) {
                Log.d(TAG, "checkSignedIn: User does not sign in yet");
                return;
            }

            try {
                MethodUtil.getMethod(object, methodName, args).invoke(object, args);
            } catch (Exception e) {
                Log.e(TAG, "Error: " + e.getMessage());
            }
        }

        public String getUsername() {
            return UserDB.username;
        }

        public String getEmail() {
            return UserDB.email;
        }

        public String getUserId() {
            return UserDB.userId;
        }

        @Nullable
        private static String getEmailByUsername(String username) {
            // Get User email by username
            FirebaseFirestore db = FirebaseFirestore.getInstance();

            Log.d(TAG, "Username: " + username);

            ArrayList<String> email = new ArrayList<>();

            DocumentReference docRef = db.collection("users").document(username);
            Log.d(TAG, "DocumentRef: " + docRef);

            return null;
        }

        private static Task<Boolean> isUsernameExist(String username) {
            // Check if username exist
            FirebaseFirestore db = FirebaseFirestore.getInstance();

            DocumentReference docRef = db.collection("users").document(username);
            return docRef.get().continueWith(task -> {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    return document.exists();
                } else {
                    Log.e(TAG, "get failed with ", task.getException());
                    throw Objects.requireNonNull(task.getException());
                }
            });
        }

        private static Task<Boolean> isEmailExist(String email) {
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

        private static void getEmailByUsername(String username, Object object, Method method) {
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

        private void setUsername(String username) {
            // Set username for using inside async method
            UserDB.username = username;
        }

        private static void saveUserData(String userId, String username, String email) {
            // save User data to firestore
            FirebaseFirestore db = FirebaseFirestore.getInstance();

            Map<String, Object> user = new HashMap<>();
            user.put("userId", userId);
            user.put("username", username);
            user.put("email", email);

            db.collection("users").document(userId).set(user)
                .addOnSuccessListener(aVoid -> {
                    Log.d(TAG, "UserData successfully written!");
                })
                .addOnFailureListener(e -> {
                    Log.w(TAG, "Error writing userData", e);
                });
        }

        private static void signInWithEmail(String email, String password, Object object, String methodName, Object... args) {
            // Sign in with email

            // Firebase Auth
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        try {
                            MethodUtil.getMethod(object, methodName, args).invoke(object, args);
                        } catch (Exception e) {
                            Log.e(TAG, "Error: " + e.getMessage());
                        }
                    } else {
                        ToastUtil.showLong((Context) object, "Login failed, please check your email or username and password");
                    }
                });
        }

        private static void signInWithUsername(String username, String password, Object object, String methodName, Object... args) {
            // Using username to find user email and sign in with email

            // Firebase Auth
            FirebaseFirestore.getInstance()
                .collection("users")
                .whereEqualTo("username", username)
                .get()
                // Check if username exist
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "Check if username exist");
                        if (task.getResult().isEmpty()) {
                            ToastUtil.showLong((Context) object, "Username does not exist");
                            return;
                        }
                        // Query successful
                        DocumentSnapshot document = task.getResult().getDocuments().get(0);
                        if (document.exists()) {
                            // Document found in firebase so username exist
                            Map<String, Object> userData = document.getData();
                            Log.d(TAG, "DocumentSnapshot data: " + userData);
                            String email = (String) userData.get("email");
                            if (email != null)
                                // Sign in with email
                                signInWithEmail(email, password, object, methodName, args);
                        } else {
                            Log.d(TAG, "Username does not exist");
                            ToastUtil.showLong((Context) object, "Username does not exist");
                        }
                    } else {
                        ToastUtil.showLong((Context) object, task.getException().getMessage());
                    }
                });
        }
    }

    public static class PostDB {
        // Aka Note's complete version
        private static final String TAG = "Database.Post";

        //Save post data to firestore
        public static void savePostData(Post post, Object object, String methodName, Object... args) {
            FirebaseFirestore db = FirebaseFirestore.getInstance();

            post.setAuthorId(UserDB.userId);

            DocumentReference docRef = db.collection("posts").document();

            post.setPostId(docRef.getId());

            docRef.set(post)
                    .addOnSuccessListener(dRef -> {
                        try {
                            MethodUtil.invokeMethod(object, methodName, args);
                        } catch (Exception e) {
                            Log.e(TAG, "Error: " + e.getMessage());
                        }
                    })
                    .addOnFailureListener(e -> {
                        if (object != null) {
                            ToastUtil.showLong((Context) object, "Error adding post: " + e.getMessage());
                        }
                        Log.e(TAG, "Error: " + e.getMessage());
                    });
        }

        //Get a list of posts by create timestamp, descending sorted by create timestamp
        public static void getPostsByTime(Date time, int limit, Object object, String methodName, Object... args) {
            long timestamp = time.getTime();

            CollectionReference postRef = FirebaseFirestore.getInstance().collection("posts");
            postRef.orderBy("createTimestamp", Query.Direction.DESCENDING)
                .whereLessThan("createTimestamp", timestamp)
                .limit(limit).get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<DocumentSnapshot> documents = task.getResult().getDocuments();
                        List<Map<String, Object>> posts = new ArrayList<>();
                        for (DocumentSnapshot document : documents) {
                            posts.add(document.getData());
                        }
                        try {
                            MethodUtil.getMethod(object, methodName, args).invoke(object, posts);
                        } catch (Exception e) {
                            Log.e(TAG, "Error: " + e.getMessage());
                        }
                    } else {
                        Log.e(TAG, "Error getting documents: ", task.getException());
                    }
                });
        }

        //Get post by postId, note that postId is unique
        public static void getPostByPostId(String postId, Object object, String methodName, Object... args) {
            FirebaseFirestore.getInstance().collection("posts").document(postId).get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            try {
                                MethodUtil.getMethod(object, methodName, args).invoke(object, document.getData());
                            } catch (Exception e) {
                                Log.e(TAG, "Error: " + e.getMessage());
                            }
                        } else {
                            Log.d(TAG, "No such document");
                        }
                    } else {
                        Log.e(TAG, "get failed with ", task.getException());
                    }
                });
        }

        // get current user's post
        public static void getUserPost(Date time, int limit, Object object, String methodName, Object... args) {
            Log.d(TAG, "Method: " + methodName);
            String userId = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();

            getPostsByAuthorId(time, limit, userId, object, methodName, args);
        }

        //Get a list of posts by author id
        public static void getPostsByAuthorId(Date time, int limit,String authorId, Object object, String methodName, Object... args) {
            Timestamp timestamp = Timestamp.now();
            Log.d(TAG, "Method: " + methodName);

            FirebaseFirestore.getInstance().collection("posts")
                    .whereEqualTo("authorId", authorId)
                    .orderBy("postTimestamp", Query.Direction.DESCENDING)
                    .whereLessThan("postTimestamp", timestamp)
                    .limit(limit).get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            if (task.getResult().isEmpty()) {
                                Log.d(TAG, "No post");
                                MethodUtil.invokeMethod(object, methodName, (Object) null);
                            }
                            List<DocumentSnapshot> documents = task.getResult().getDocuments();
                            List<Map<String, Object>> posts = new ArrayList<>();
                            for (DocumentSnapshot document : documents) {
                                posts.add(Map.of(document.getId(), document.toObject(Post.class)));
                            }
                            MethodUtil.invokeMethod(object, methodName, posts);
                        } else {
                            Log.e(TAG, "Error getting documents: ", task.getException());
                        }
                    });
        }

    }
}
