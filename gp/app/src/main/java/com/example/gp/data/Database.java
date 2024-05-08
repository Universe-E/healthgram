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
import com.example.gp.setting.FriendDetailActivity;
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

    // Design pattern Singleton
    public static class UserDB {
        // User data
        private static final String TAG = "Database.User";

        private static String userId;
        private static String username;
        private static String email;
        private static Uri photoUri;

//        public UserDB() {
//            Log.d(TAG, "UserDB");
//            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//            if (user != null) {
//                UserDB.userId = user.getUid();
//                UserDB.email = user.getEmail();
//                FirebaseFirestore.getInstance().collection("users")
//                    .document(userId)
//                    .get()
//                    .addOnCompleteListener(task -> {
//                        if (task.isSuccessful()) {
//                            DocumentSnapshot document = task.getResult();
//                            if (document.exists()) {
//                                setUsername((String) document.get("username"));
//                                Log.d(TAG, "Username: " + UserDB.username);
//                            } else {
//                                Log.d(TAG, "No such document");
//                            }
//                        }
//                    });
//            }
//        }

        public static void signIn(String input, String password, Object object, String methodName) {
            if (AuthUtil.isEmail(input)) {
                signInWithEmail(input, password, object, methodName);
            } else {
                signInWithUsername(input, password, object, methodName);
            }
        }

        public static void signUp(String username, String email, String password, Object object, String methodName) {
            Log.d(TAG, "signUp username: " + username);
            FirebaseFirestore.getInstance()
                .collection("users")
                .whereEqualTo("username", username)
                .get()
                .addOnCompleteListener(querySnapshotTask -> {
                    if (querySnapshotTask.isSuccessful()) {
                        if (!querySnapshotTask.getResult().isEmpty()) {
                            DocumentSnapshot document = querySnapshotTask.getResult().getDocuments().get(0);
                            if (document.exists()) {
                                ToastUtil.showLong((Context) object, "Username already exist");
                            }
                        } else {
                            FirebaseAuth mAuth = FirebaseAuth.getInstance();
                            mAuth.createUserWithEmailAndPassword(email, password)
                                .addOnCompleteListener(authResultTask -> {
                                    if (authResultTask.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        FirebaseUser user = authResultTask.getResult().getUser();

                                        String userId = Objects.requireNonNull(user).getUid();
                                        saveUserData(userId, username, email);
                                        Objects.requireNonNull(mAuth.getCurrentUser())
                                                .updateProfile(new UserProfileChangeRequest
                                                        .Builder().setDisplayName(username).build());

                                        User userForCallback = new User();
                                        userForCallback.setUserId(userId);
                                        userForCallback.setUsername(username);
                                        userForCallback.setEmail(email);
                                        /*
                                          Callback
                                          Return User object
                                         */
                                        MethodUtil.invokeMethod(object, methodName, true, userForCallback);
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Exception e = authResultTask.getException();
                                        /*
                                          Callback
                                          Return error message
                                         */
                                        MethodUtil.invokeMethod(object, methodName, false, Objects.requireNonNull(e).getMessage());
                                        Log.e(TAG, "Error: " + e);
                                    }
                                });
                        }
                    }
                });
        }

        /**
         * Add friend to firestore
         * @param friend
         * @param object
         * @param methodName
         * Callback return true and friend object if success, false and error message if fail
         */
        public static void addFriend (Friend friend, Object object, String methodName) {
            // Add friend to firestore
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            String userId = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();

            db.collection("users")
                    .document(userId)
                    .set(Map.of("friendMap", Map.of(friend.getId(), friend)), SetOptions.merge())
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            // If successful
                            /*
                              Callback
                              Return true and friend object
                             */
                            MethodUtil.invokeMethod(object, methodName, true, friend);
                            Log.d(TAG, "DocumentSnapshot successfully written!");
                        } else {
                            Exception e = task.getException();
                            /*
                              Callback
                              Return error message
                             */
                            MethodUtil.invokeMethod(object, methodName, false, Objects.requireNonNull(e).getMessage());
                            Log.e(TAG, "Error writing document", e);
                        }
                    });
        }

        /**
         * Send friend request to firestore
         * @param friendRequest that only contains receiver's userId
         * @param object
         * @param methodName
         */
        public static void sendFriendRequestTo (FriendRequest friendRequest, Object object, String methodName) {
            // Send friend request to firestore
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            String senderId = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();

            Timestamp timestamp = TimeUtil.getTimestamp();

            DocumentReference docRef = db.collection("friendRequests").document();

            friendRequest.setRequestId(docRef.getId());
            friendRequest.setRequestTimestamp(timestamp);
            friendRequest.setSenderId(senderId);
            friendRequest.setRead(false);

            docRef.set(friendRequest)
                    .addOnSuccessListener(aVoid -> {
                        /*
                          Callback
                          Return true and friendRequest object
                         */
                        MethodUtil.invokeMethod(object, methodName, true, friendRequest);
                    })
                    .addOnFailureListener(e -> {
                        /*
                          Callback
                          Return error message
                         */
                        MethodUtil.invokeMethod(object, methodName, false, Objects.requireNonNull(e).getMessage());
                        Log.e(TAG, "Error: " + e);
                    });
        }

        /**
         * Get friend request from firestore
         * @param date Don't need this parameter for this stage just use null
         * @param limit not used
         * @param object
         */
        public static void getFriendRequest (Date date, int limit, Object object, String methodName) {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            String userId = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();

            db.collection("friendRequests")
                    .whereEqualTo("receiverId", userId)
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            if (task.getResult() == null) {
                                /*
                                  Callback
                                  Return error message
                                 */
                                MethodUtil.invokeMethod(object, methodName, false, "No friend request");
                                Log.d(TAG, "No friend request");
                                return;
                            }
                            List<DocumentSnapshot> documents = task.getResult().getDocuments();
                            List<FriendRequest> friendRequests = new ArrayList<>();
                            for (DocumentSnapshot document : documents) {
                                friendRequests.add(document.toObject(FriendRequest.class));
                            }
                            /*
                              Callback
                              Return true and list of FriendRequest object
                             */
                            MethodUtil.invokeMethod(object, methodName, true, friendRequests);
                            Log.d(TAG, "FriendRequests: " + friendRequests.toString());
                        }
                        Exception e = task.getException();
                        /*
                          Callback
                          Return error message
                         */
                        MethodUtil.invokeMethod(object, methodName, false, Objects.requireNonNull(e).getMessage());
                        Log.e(TAG, "Error getting documents: ", e);
                    });
        }

        /**
         * Get friend list from firestore
         * @param nickname
         * @param limit
         * @param object
         * @param methodName
         * Callback return true and list of Map List<Map<String userId, Friend friend>> aka friendMap if success, false and error message if fail
         */
        public static void getFriendList(String nickname, int limit, Object object, String methodName) {
            Log.d(TAG, "Method: " + methodName);

            String userId = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();

            FirebaseFirestore.getInstance()
                    .collection("users")
                    .document(userId)
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            if (task.getResult() == null) {
                                String message = "No friend";
                                /*
                                  Callback
                                  Return error message
                                 */
                                MethodUtil.invokeMethod(object, methodName, false, message);
                                Log.d(TAG, message);
                                return;
                            }
                            User user = task.getResult().toObject(User.class);
                            Map<String, Friend> friendMap = user.getFriendMap();
                            if (friendMap == null) {
                                String message = "No friend";
                                /*
                                  Callback
                                  Return error message
                                 */
                                MethodUtil.invokeMethod(object, methodName, false, message);
                                return;
                            }
                            Log.d(TAG, "method name: " + methodName);
                            /*
                              Callback
                              Return true and list of Map List<Map<String userId, Friend friend>> aka friendMap
                             */
                            MethodUtil.invokeMethod(object, methodName, true, friendMap);
                            Log.d(TAG, "FriendMap: " + friendMap.toString());
                        } else {
                            Exception e = task.getException();
                            /*
                              Callback
                              Return error message
                             */
                            MethodUtil.invokeMethod(object, methodName, false, Objects.requireNonNull(e).getMessage());
                            Log.e(TAG, "Error getting documents: ", e);
                        }
                    });
        }

        /**
         * Sign out from firebase
         * @param object
         * @param methodName
         * Callback return true if success, false and error message if fail
         */
        public static void signOut(Object object, String methodName) {
            FirebaseAuth.getInstance().signOut();

            try {
                /*
                  Callback
                  Return true
                 */
                MethodUtil.invokeMethod(object, methodName, true);
            } catch (Exception e) {
                Log.e(TAG, "Error: " + e);
            }
        }

        public static void checkSignedIn(Object object, String methodName) {
            // Check if user is signed in
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

            if (user == null) {
                String message = "User does not sign in yet";
                Log.d(TAG, message);
                /*
                  Callback
                  Return error message
                 */
                MethodUtil.invokeMethod(object, methodName, false, message);
                return;
            }

            UserDB.setUserDB(Objects.requireNonNull(user));

            User userForCallback = new User();
            userForCallback.setUserId(user.getUid());
            userForCallback.setUsername(user.getDisplayName());
            userForCallback.setEmail(user.getEmail());

            /*
              Callback
              Return true and User object
             */
            MethodUtil.invokeMethod(object, methodName, true, userForCallback);
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

        private static void saveUserData(String userId, String username, String email) {
            // save User data to firestore
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

            User user = new User();

            user.setUserId(Objects.requireNonNull(firebaseUser).getUid());
            user.setUsername(username);
            user.setEmail(firebaseUser.getEmail());

            db.collection("users").document(user.getUserId()).set(user)
                .addOnSuccessListener(aVoid -> {
                    Log.d(TAG, "UserData successfully written!");
                })
                .addOnFailureListener(e -> {
                    Log.w(TAG, "Error writing userData", e);
                });
        }

        private static User firebaseUserToUser(FirebaseUser user) {
            User userReturn = new User();
            userReturn.setUserId(user.getUid());
            userReturn.setUsername(user.getDisplayName());
            userReturn.setEmail(user.getEmail());

            return userReturn;
        }

        private static void signInWithEmail(String email, String password, Object object, String methodName) {
            // Sign in with email

            // Firebase Auth
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        setUserDB(Objects.requireNonNull(task.getResult().getUser()));
                        User userForCallback = firebaseUserToUser(Objects.requireNonNull(task.getResult().getUser()));
                        /*
                          Callback
                          Return true and User object
                         */
                        MethodUtil.invokeMethod(object, methodName, true, userForCallback);
                    } else {
                        Exception e = task.getException();
                        /*
                          Callback
                          Return error message
                         */
                        MethodUtil.invokeMethod(object, methodName, false, Objects.requireNonNull(e).getMessage());
                        Log.e(TAG, "signInWithEmail:failure", e);
                    }
                });
        }

        private static void signInWithUsername(String username, String password, Object object, String methodName) {
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
                            String message = "Username does not exist";
                            Log.d(TAG, message);
                            /*
                              Callback
                              Return error message
                             */
                            MethodUtil.invokeMethod(object, methodName, false, message);

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
                                signInWithEmail(email, password, object, methodName);
                        } else {
                            Log.d(TAG, "Username does not exist");
                            String message = "Username does not exist";
                            /*
                              Callback
                              Return error message
                             */
                            MethodUtil.invokeMethod(object, methodName, false, message);
                        }
                    } else {
                        Exception e = task.getException();
                        /*
                          Callback
                          Return error message
                         */
                        MethodUtil.invokeMethod(object, methodName, false, Objects.requireNonNull(e).getMessage());
                        Log.e(TAG, "Error getting documents: ", e);
                    }
                });
        }

        private static void setUserDB(FirebaseUser user) {
            // Set user data for using inside async method
            UserDB.userId = user.getUid();
            UserDB.username = user.getDisplayName();
            UserDB.email = user.getEmail();
            UserDB.photoUri = user.getPhotoUrl();
        }
    }

    public static class PostDB {
        // Aka Note's complete version
        private static final String TAG = "Database.Post";

        /**
         * Save post data to firestore
         * @param post
         * @param object
         * @param methodName
         * Callback return true and Post object if success, false and error message if fail
         */
        public static void savePostData(Post post, Object object, String methodName) {
            FirebaseFirestore db = FirebaseFirestore.getInstance();

            post.setAuthorId(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid());

            DocumentReference docRef = db.collection("posts").document();

            post.setPostId(docRef.getId());
            post.setPostTimestamp(TimeUtil.getTimestamp());

            docRef.set(post)
                    .addOnSuccessListener(dRef -> {
                        /*
                          Callback
                          Return true and Post object
                         */
                        MethodUtil.invokeMethod(object, methodName, true, post);
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                        savePostMap(docRef.getId(), post);
                    })
                    .addOnFailureListener(e -> {
                        /*
                          Callback
                          Return error message
                         */
                        MethodUtil.invokeMethod(object, methodName, false, e.getMessage());
                        Log.e(TAG, "Error: " + e);
                    });
        }

        /**
         * Get a list of posts by post timestamp, descending sorted by post timestamp
         * @param time
         * @param limit
         * @param object
         * @param methodName
         * Callback return true and list of PostMap object List<Post>
         */
        public static void getPostsByTime(Date time, int limit, Object object, String methodName) {
            Timestamp timestamp;
            if (time != null) {
                timestamp = new Timestamp(time);
            } else {
                timestamp = TimeUtil.getTimestamp();
            }

            CollectionReference postRef = FirebaseFirestore.getInstance().collection("posts");
            postRef.orderBy("postTimestamp", Query.Direction.DESCENDING)
                .whereLessThan("postTimestamp", timestamp)
                .limit(limit).get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<DocumentSnapshot> documents = task.getResult().getDocuments();
                        List<Post> posts = new ArrayList<>();
                        for (DocumentSnapshot document : documents) {
                            posts.add(document.toObject(Post.class));
                        }
                        /*
                          Callback
                          Return true and list of PostMap object List<Map<String postId, Post post>>
                         */
                        MethodUtil.invokeMethod(object, methodName, true, posts);
                    } else {
                        Exception e = task.getException();
                        /*
                          Callback
                          Return error message
                         */
                        MethodUtil.invokeMethod(object, methodName, false, Objects.requireNonNull(e).getMessage());
                        Log.e(TAG, "Error getting documents: ", e);
                    }
                });
        }

        /**
         * Get post by postId, note that postId is unique
         * @param postId
         * @param object
         * @param methodName
         * Callback return true and Post object if success, false and error message if fail
         */
        public static void getPostByPostId(String postId, Object object, String methodName) {
            FirebaseFirestore.getInstance().collection("posts").document(postId).get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            /*
                              Callback
                              Return true and Post object
                             */
                            MethodUtil.invokeMethod(object, methodName, true, document.toObject(Post.class));
                        } else {
                            String message = "No such document";
                            /*
                              Callback
                              Return error message
                             */
                            MethodUtil.invokeMethod(object, methodName, false, message);
                            Log.d(TAG, message);
                        }
                    } else {
                        Exception e = task.getException();
                        /*
                          Callback
                          Return error message
                         */
                        MethodUtil.invokeMethod(object, methodName, false, Objects.requireNonNull(e).getMessage());
                        Log.e(TAG, "Error getting documents: ", e);
                    }
                });
        }

        /**
         * get current user's post
         * @param time
         * @param limit
         * @param object
         * @param methodName
         * Callback return true and list of PostMap object List<Map<String postId, Post post>>
         */
        public static void getUserPost(Date time, int limit, Object object, String methodName) {
            Log.d(TAG, "Method: " + methodName);
            Log.d(TAG, "object: " + object);
            String userId = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();

            getPostsByAuthorId(time, limit, userId, object, methodName);
        }

        /**
         * set public or private
         * @param postId
         * @param isPublic
         * @param object
         * @param methodName
         * Callback return true and postId if success, false and error message if fail
         */
        public static void setPublic(String postId, Boolean isPublic, Object object, String methodName) {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("posts")
                    .document(postId)
                    .update("isPublic", isPublic)
                    .addOnSuccessListener(aVoid -> {
                        db.collection("users")
                                .document(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid())
                                        .update("postMap." + postId + ".isPublic", isPublic);
                        /*
                          Callback
                          Return true and postId
                         */
                        MethodUtil.invokeMethod(object, methodName, true, postId);
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                    })
                    .addOnFailureListener(e -> {
                        /*
                          Callback
                          Return error message
                         */
                        MethodUtil.invokeMethod(object, methodName, false, Objects.requireNonNull(e).getMessage());
                        Log.e(TAG, "Error writing document", e);
                    });
        }

        /**
         * set allowed viewers
         * @param postId
         * @param viewers
         * @param object
         * Callback return true and postId if success, false and error message if fail
         */
        public static void setAllowedViewers(String postId, List<String> viewers, Object object, String methodName) {

        }

        /**
         * Get a list of posts by author id
         * @param time
         * @param limit
         * @param authorId
         * @param object
         * @param methodName
         * @param args
         * Callback return true and list of PostMap object List<Map<String postId, Post post>>
         */
        public static void getPostsByAuthorId(Date time, int limit,String authorId, Object object, String methodName, Object... args) {
            Timestamp timestamp;
            Log.d(TAG, "Get posts by author id: object: " + object);

            if (time == null) {
                timestamp = new Timestamp(TimeUtil.getCurDate());
            } else {
                timestamp = new Timestamp(time);
            }

            String userId = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();

            Log.d(TAG, "Method: " + methodName);
            Log.d(TAG, "userId: " + userId);

            FirebaseFirestore.getInstance()
                    .collection("users")
                    .document(userId)
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            if (task.getResult() == null) {
                                String message = "No post";
                                /*
                                  Callback
                                  Return error message
                                 */
                                MethodUtil.invokeMethod(object, methodName, false, message);
                                Log.d(TAG, message);
                                return;
                            }
                            User user = task.getResult().toObject(User.class);
                            Map<String, Post> postMap = user.getPostMap();
                            if (postMap == null) {
                                String message = "No post";
                                /*
                                  Callback
                                  Return error message
                                 */
                                MethodUtil.invokeMethod(object, methodName, false, message);
                                Log.d(TAG, message);
                                return;
                            }
                            Log.d(TAG, "method name: " + methodName);
                            /*
                              Callback
                              Return true and list of PostMap object List<Map<String postId, Post post>>
                             */
                            MethodUtil.invokeMethod(object, methodName, true, postMap);
                            Log.d(TAG, "Posts: " + postMap.toString());
                        } else {
                            Exception e = task.getException();
                            /*
                              Callback
                              Return error message
                             */
                            MethodUtil.invokeMethod(object, methodName, false, Objects.requireNonNull(e).getMessage());
                            Log.e(TAG, "Error getting documents: ", e);
                        }
                    });
        }

        private static void savePostMap(String postId, Post post) {
            FirebaseFirestore db = FirebaseFirestore.getInstance();

            String userId = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
            Log.d(TAG, "userId: " + userId);

            db.collection("users").document(userId)
                    .set(Map.of("postMap", Map.of(postId, post)), SetOptions.merge());
        }
    }
}
