package com.example.gp.data.database;

import static com.example.gp.data.database.FirebaseUtil.getCurrentEmail;
import static com.example.gp.data.database.FirebaseUtil.getCurrentUserId;
import static com.example.gp.data.database.FirebaseUtil.getFireAuth;
import static com.example.gp.data.database.FirebaseUtil.getFireUser;
import static com.example.gp.data.database.FirebaseUtil.getFriendRequestRef;
import static com.example.gp.data.database.FirebaseUtil.getUsersRef;

import android.content.Context;
import android.util.Log;

import com.example.gp.Items.Friend;
import com.example.gp.Items.FriendRequest;
import com.example.gp.Items.User;
import com.example.gp.Utils.AuthUtil;
import com.example.gp.Utils.MethodUtil;
import com.example.gp.Utils.TimeUtil;
import com.example.gp.Utils.ToastUtil;
import com.example.gp.data.database.model.FriendModel;
import com.example.gp.data.database.model.FriendRequestModel;
import com.example.gp.data.database.model.UserModel;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.firestore.Source;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

// Design pattern Singleton
public class UserDB {
    // User data
    private static final String TAG = "Database.User";
    private static String username;
    private static UserDB instance;
    private static String userId;
    private static String email;

    private UserDB() {
        userId = getCurrentUserId();
        email = getCurrentEmail();
        setUsername(null, null);
    }


    private void setUsername(Object object, String methodName) {
        if (username != null) {
            MethodUtil.invokeMethod(object, methodName, true, username);
            return;
        }

        CollectionReference usersRef = getUsersRef();
        usersRef.document(userId).get(Source.CACHE)
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful() || task.getResult() == null) {
                        setUsernameFromServer(userId, object, methodName);
                        return;
                    }
                    username = task.getResult().getString("username");
                    MethodUtil.invokeMethod(object, methodName, true, username);
                });
    }

    private void setUsernameFromServer(String userId, Object object, String methodName) {
        CollectionReference usersRef = getUsersRef();
        usersRef.document(userId).get()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.e(TAG, "Error getting documents: ", task.getException());
                        MethodUtil.invokeMethod(object, methodName, false, task.getException().getMessage());
                        return;
                    }
                    username = task.getResult().getString("username");
                    MethodUtil.invokeMethod(object, methodName, true, username);
                });
    }

    private static void clearUserDB() {
        username = null;
        userId = null;
        email = null;
        instance = null;
    }

    public static UserDB getInstance() {
        if (instance == null) {
            instance = new UserDB();
        }
        return instance;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getUserId() {
        return userId;
    }

    public void getUsernameById(String userId, Object object, String methodName) {
        setUsernameFromServer(userId, object, methodName);
    }

    /**
     * Allow user to sign in
     *
     * @param input
     * @param password
     * @param object
     * @param methodName
     */
    public static void signIn(String input, String password, Object object, String methodName) {
        if (AuthUtil.isEmail(input)) {
//            signInWithEmail(input, password, object, methodName);
            newSignInWithEmail(input, password, object, methodName);
        } else {
//            signInWithUsername(input, password, object, methodName);
            newSignInWithUsername(input, password, object, methodName);
        }
    }

    /**
     * Allow user to sign up
     *
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
//                                            saveUserData(userId, username, email);
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
     * @param timestamp Don't need this parameter for this stage just use null
     * @param limit     not used
     * @param object
     */
    public static void getFriendRequest(Timestamp timestamp, int limit, Object object, String methodName) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String userId = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();

        db.collection("friendRequests")
                .whereEqualTo("receiverId", userId)
                .get()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Exception e = task.getException();
                        /*
                          Callback
                          Return error message
                         */
                        MethodUtil.invokeMethod(object, methodName, false, Objects.requireNonNull(e).getMessage());
                        Log.e(TAG, "Error getting documents: ", e);
                        return;
                    }
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
                });
    }

    public static void processFriendRequest(FriendRequest friendRequest, Object object, String methodName) {
        CollectionReference friendRequestsRef = getFriendRequestRef();

        Map<String, Object> update = new HashMap<>();
        update.put("accepted", friendRequest.isAccepted());
        update.put("read", friendRequest.isRead());
        friendRequestsRef.document(friendRequest.getRequestId())
                .update(update)
                .addOnSuccessListener(aVoid -> {
                    if (friendRequest.isAccepted()) {
                        Friend friend = new Friend();
                        friend.setId(friendRequest.getSenderId());
                        friend.setNickname(friendRequest.getSenderName());
                        follow(friend, object, methodName);
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Error writing updated friend request", e);
                    MethodUtil.invokeMethod(object, methodName, false, e.getMessage());
                });
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
        clearUserDB();

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
        // Check if fireUser is signed in
        FirebaseUser fireUser = getFireUser();

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

        getInstance();

        User userForCallback = new User();
        userForCallback.setUserId(fireUser.getUid());
        userForCallback.setEmail(fireUser.getEmail());

        /*
          Callback
          Return true and User object
         */
        MethodUtil.invokeMethod(object, methodName, true, userForCallback);
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

        UserModel userModel = new UserModel();
        userModel.setModelFromUser(user);

        db.collection("users").document(userId).set(userModel)
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Error writing NewTestUsers", e);
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

    // New APIs

    // Sign up

    public static void newSignUp(
            String username, String email, String password, Object object, String methodName) {
        Log.d(TAG, "newSignUP username: " + username);

        CollectionReference databaseRef = getUsersRef();
        databaseRef
                .whereEqualTo("username", username)
                .get()
                .addOnCompleteListener(queryNameTask -> {
                    if (!queryNameTask.isSuccessful()) {
                        Log.e(TAG, "Error getting documents: ", queryNameTask.getException());
                        MethodUtil.invokeMethod(object, methodName, false, queryNameTask.getException().getMessage());
                        return;
                    }
                    if (!queryNameTask.getResult().isEmpty()) {
                        String msg = "Username already exist";
                        Log.d(TAG, msg);
                        MethodUtil.invokeMethod(object, methodName, false, msg);
                        return;
                    }
                    fireAuthCreate(username, email, password, object, methodName);
                });
    }

    // Friend operations

    public static void follow(Friend friend, Object object, String methodName) {
        CollectionReference usersRef = getUsersRef();
        String userId = getCurrentUserId();
        FriendModel friendModel = new FriendModel(friend);

        usersRef.document(userId)
                .update("friendList", FieldValue.arrayUnion(friendModel))
                .addOnSuccessListener(aVoid -> {
                    Log.d(TAG, "new follow successfully written!");
                    MethodUtil.invokeMethod(object, methodName, true, friend);
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Error writing NewTestUsers", e);
                    MethodUtil.invokeMethod(object, methodName, false, e.getMessage());
                });
    }

    public static void newSendFriendRequestTo(FriendRequest friendRequest, Object object, String methodName) {
        CollectionReference friendRequestsRef = getFriendRequestRef();

        String userId = getCurrentUserId();
        FriendRequestModel friendRequestModel = new FriendRequestModel(friendRequest);
        friendRequestModel.setSenderName(username);
        friendRequestModel.setSenderId(userId);
        friendRequestModel.setRead(false);
        friendRequestModel.setAccepted(false);
        friendRequestModel.setRequestTimestamp(TimeUtil.getTimestamp());

        DocumentReference docRef = friendRequestsRef.document();
        friendRequestModel.setRequestId(docRef.getId());

        docRef.set(friendRequestModel)
                .addOnSuccessListener(aVoid -> {
                    setFriendReqNotification(friendRequestModel, object, methodName);
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Error writing NewTestUsers", e);
                    MethodUtil.invokeMethod(object, methodName, false, e.getMessage());
                });
    }

    public static void newGetFollowList(String nickname, Integer limit, Object object, String methodName) {
        String userId = getCurrentUserId();

        CollectionReference usersRef = getUsersRef();
        usersRef.document(userId)
                .get()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Exception e = task.getException();
                        Log.e(TAG, "Error getting documents: ", e);
                        MethodUtil.invokeMethod(object, methodName, false, e.getMessage());
                        return;
                    }

                    UserModel userModel = task.getResult().toObject(UserModel.class);
                    List< FriendModel> friendList = userModel.getMyFriends();

                    if (friendList == null) {
                        String msg = "No friend";
                        Log.d(TAG, msg);
                        MethodUtil.invokeMethod(object, methodName, false, msg);
                        return;
                    }

                    MethodUtil.invokeMethod(object, methodName, true, friendList);
                    Log.d(TAG, "FriendList: " + friendList.toString());
                });
    }

    public static void getFriendRequestList(Timestamp timestamp, Integer limit, Object object, String methodName) {
        CollectionReference friendRequestsRef = getFriendRequestRef();
        String userId = getCurrentUserId();
        timestamp = TimeUtil.getTimestamp(timestamp);
        if (limit == null)
            limit = 10;

        friendRequestsRef.whereEqualTo("receiverId", userId)
                .orderBy("requestTimestamp", Query.Direction.DESCENDING)
                .limit(limit)
                .get()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Exception e = task.getException();
                        Log.e(TAG, "Error getting documents: ", e);
                        MethodUtil.invokeMethod(object, methodName, false, e.getMessage());
                        return;
                    }

                    List<DocumentSnapshot> documents = task.getResult().getDocuments();
                    if (documents.isEmpty()) {
                        String msg = "No friend request";
                        Log.d(TAG, msg);
                        MethodUtil.invokeMethod(object, methodName, false, msg);
                        return;
                    }

                    List<FriendRequest> friendRequests = new ArrayList<>();
                    for (DocumentSnapshot document : documents) {
                        friendRequests.add(new FriendRequest(document.toObject(FriendRequestModel.class)));
                    }
                    MethodUtil.invokeMethod(object, methodName, true, friendRequests);
                    Log.d(TAG, "FriendRequests: " + friendRequests.toString());
                });
    }

    public static void getFriendRequestById(String requestId, Object object, String methodName) {
        CollectionReference friendRequestsRef = getFriendRequestRef();

        friendRequestsRef.document(requestId)
                .get()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Exception e = task.getException();
                        Log.e(TAG, "Error getting documents: ", e);
                        MethodUtil.invokeMethod(object, methodName, false, e.getMessage());
                        return;
                    }

                    FriendRequestModel friendRequestModel = task.getResult().toObject(FriendRequestModel.class);
                    FriendRequest friendRequest = new FriendRequest(friendRequestModel);

                    MethodUtil.invokeMethod(object, methodName, true, friendRequest);
                    Log.d(TAG, "FriendRequest: " + friendRequest.toString());
                });
    }

    // Private Utils

    private static void setFriendReqNotification(FriendRequestModel friendRequestModel, Object object, String methodName) {
        CollectionReference usersRef = getUsersRef();
        usersRef.document(friendRequestModel.getReceiverId())
                .update("myNotifications", FieldValue.arrayUnion(friendRequestModel.getNotificationModel()))
                .addOnSuccessListener(aVoid -> {
                    MethodUtil.invokeMethod(object, methodName, true, friendRequestModel);
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Error writing NewTestUsers", e);
                    MethodUtil.invokeMethod(object, methodName, false, e.getMessage());
                });
    }

    private static void newSignInWithEmail(String email, String password, Object object, String methodName) {
        FirebaseAuth mAuth = getFireAuth();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Exception e = task.getException();
                        MethodUtil.invokeMethod(object, methodName, false, e.getMessage());
                        Log.e(TAG, "Error: " + e);
                        return;
                    }

                    User user = firebaseUserToUser(getFireUser());
                    MethodUtil.invokeMethod(object, methodName, true, user);
                });
    }

    private static void newSignInWithUsername(String username, String password, Object object, String methodName) {
        CollectionReference usersRef = getUsersRef();
        usersRef.whereEqualTo("username", username)
                .get()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Exception e = task.getException();
                        MethodUtil.invokeMethod(object, methodName, false, e.getMessage());
                        Log.e(TAG, "Error: " + e);
                        return;
                    }

                    if (task.getResult().isEmpty() || task.getResult().getDocuments().isEmpty()) {
                        String msg = "Username does not exist";
                        Log.d(TAG, msg);
                        MethodUtil.invokeMethod(object, methodName, false, msg);
                        return;
                    }

                    DocumentSnapshot document = task.getResult().getDocuments().get(0);
                    if (!document.exists()) {
                        String msg = "Username does not exist";
                        Log.d(TAG, msg);
                        MethodUtil.invokeMethod(object, methodName, false, msg);
                        return;
                    }

                    String email = document.getString("email");
                    newSignInWithEmail(email, password, object, methodName);
                });
    }

    private static void fireAuthCreate(String username, String email, String password, Object object, String methodName) {
        FirebaseAuth mAuth = getFireAuth();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(authResultTask -> {
                    if (!authResultTask.isSuccessful()) {
                        Exception e = authResultTask.getException();
                    /*
                      Callback
                      Return error message
                     */
                        MethodUtil.invokeMethod(object, methodName, false, Objects.requireNonNull(e).getMessage());
                        Log.e(TAG, "Error: " + e);
                    }
                    FirebaseUser fireUser = getFireUser();
                    UserModel userModel = new UserModel();
                    userModel.setUsername(username);
                    userModel.setEmail(email);
                    userModel.setUserId(fireUser.getUid());

                    newSaveUserData(userModel, object, methodName);
                });
    }

    private static void newSaveUserData(UserModel userModel, Object object, String methodName) {
        CollectionReference databaseRef = getUsersRef();
        databaseRef.document(userModel.getUserId()).set(userModel)
                .addOnSuccessListener(aVoid -> {
                    Log.d(TAG, "UserData successfully written!");
                    MethodUtil.invokeMethod(object, methodName, true, null);
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Error writing NewTestUsers", e);
                    MethodUtil.invokeMethod(object, methodName, false, e.getMessage());
                });
    }
}
