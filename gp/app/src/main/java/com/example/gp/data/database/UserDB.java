package com.example.gp.data.database;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.gp.Items.Friend;
import com.example.gp.Items.FriendRequest;
import com.example.gp.Items.User;
import com.example.gp.Utils.AuthUtil;
import com.example.gp.Utils.MethodUtil;
import com.example.gp.Utils.TimeUtil;
import com.example.gp.Utils.ToastUtil;
import com.example.gp.data.Database;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
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

// Design pattern Singleton
public class UserDB {
    // User data
    private static final String TAG = "Database.User";

    //        private static String userId;
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

    /**
     * Allow user to sign in
     * @param input
     * @param password
     * @param object
     * @param methodName
     */
    public static void signIn(String input, String password, Object object, String methodName) {
        if (AuthUtil.isEmail(input)) {
            signInWithEmail(input, password, object, methodName);
        } else {
            signInWithUsername(input, password, object, methodName);
        }
    }

    /**
     * Allow user to sign up
     * @param username
     * @param email
     * @param password
     * @param object
     * @param methodName
     */
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
     *
     * @param friend
     * @param object
     * @param methodName Callback return true and friend object if success, false and error message if fail
     */
    public static void addFriend(Friend friend, Object object, String methodName) {
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
     *
     * @param friendRequest that only contains receiver's userId
     * @param object
     * @param methodName
     */
    public static void sendFriendRequestTo(FriendRequest friendRequest, Object object, String methodName) {
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
     *
     * @param date   Don't need this parameter for this stage just use null
     * @param limit  not used
     * @param object
     */
    public static void getFriendRequest(Date date, int limit, Object object, String methodName) {
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

    public static void processFriendRequest(FriendRequest request, Object object, String methodName) {
        Log.d(TAG, "Process friend request Reflecting Method: " + methodName);


    }

    /**
     * Get friend list from firestore
     *
     * @param nickname
     * @param limit
     * @param object
     * @param methodName Callback return true and list of Map List<Map<String userId, Friend friend>> aka friendMap if success, false and error message if fail
     */
    public static void getFriendList(String nickname, int limit, Object object, String methodName) {
        Log.d(TAG, "Method: " + methodName);

        FirebaseUser fireUser = FirebaseAuth.getInstance().getCurrentUser();
        if (fireUser == null) {
            String message = "User does not sign in yet";
            Log.d(TAG, message);
            /*
              Callback
              Return error message
             */
            MethodUtil.invokeMethod(object, methodName, false, message);
            return;
        }
        String userId = fireUser.getUid();

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
     *
     * @param object
     * @param methodName Callback return true if success, false and error message if fail
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

        User userForCallback = new User();
        userForCallback.setUserId(user.getUid());
        userForCallback.setEmail(user.getEmail());

        /*
          Callback
          Return true and User object
         */
        MethodUtil.invokeMethod(object, methodName, true, userForCallback);
    }

    private String getUsername() {
        return UserDB.username;
    }

    private String getEmail() {
        return UserDB.email;
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
        Log.d("Save user data", "username: " + username);
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
}
