package com.example.gp.data.database;

import static com.example.gp.data.FriendsData.clearFriendsData;
import static com.example.gp.data.PostRepository.clearPostsData;
import static com.example.gp.data.database.FirebaseUtil.getCurrentEmail;
import static com.example.gp.data.database.FirebaseUtil.getCurrentUserId;
import static com.example.gp.data.database.FirebaseUtil.getFireAuth;
import static com.example.gp.data.database.FirebaseUtil.getFireUser;
import static com.example.gp.data.database.FirebaseUtil.getFriendRequestRef;
import static com.example.gp.data.database.FirebaseUtil.getUsersRef;

import android.util.Log;

import com.example.gp.Items.Friend;
import com.example.gp.Items.FriendRequest;
import com.example.gp.Items.Notification;
import com.example.gp.Items.User;
import com.example.gp.Utils.AuthUtil;
import com.example.gp.Utils.MethodUtil;
import com.example.gp.Utils.TimeUtil;
import com.example.gp.data.FriendsData;
import com.example.gp.data.database.model.FriendModel;
import com.example.gp.data.database.model.FriendRequestModel;
import com.example.gp.data.database.model.NotificationModel;
import com.example.gp.data.database.model.UserModel;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.firestore.Source;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

// Design pattern Singleton

/**
 * A utility class for user operation in Firebase Firestore
 *
 * @author Han Bao
 */
public class UserDB {
    // User data
    private static final String TAG = "Database.User";
    private static String username;
    private static UserDB instance;
    private static String userId;
    private static String email;
    private static String avatarUUID;
    private static final FriendsData friendsData = FriendsData.getInstance();
    private OnAvatarUUIDChangedListener listener;

    private UserDB() {
        userId = getCurrentUserId();
        email = getCurrentEmail();
        setUserData(null, null);
    }

    /**
     * Get notification list from firebase firestore
     *
     * @param object     object calls this function
     * @param methodName method name for callback
     */
    public static void getNotificationList(Object object, String methodName) {
        CollectionReference usersRef = getUsersRef();

        usersRef.document(getCurrentUserId())
                .get()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Exception e = task.getException();
                        Log.e(TAG, "Error getting documents: ", e);
                        MethodUtil.invokeMethod(object, methodName, false, e.getMessage());
                        return;
                    }
                    UserModel userModel = task.getResult().toObject(UserModel.class);
                    Map<String, NotificationModel> notificationMapList = userModel.getMyNotifications();
                    if (notificationMapList == null) {
                        String msg = "No notification";
                        Log.d(TAG, msg);
                        MethodUtil.invokeMethod(object, methodName, true, msg);
                        return;
                    }
                    List<Notification> notificationList = new ArrayList<>();
                    for (Map.Entry<String, NotificationModel> entry : notificationMapList.entrySet()) {
                        notificationList.add(new Notification(entry.getValue()));
                    }
                    MethodUtil.invokeMethod(object, methodName, true, notificationList);
                    Log.d(TAG, "NotificationList: " + notificationList.toString());
                });
    }

    /**
     * Change avatar
     *
     * @param avatarUUID avatarUUID
     * @param object     object calls this function
     * @param methodName method name for callback
     */
    public static void changeAvatar(String avatarUUID, Object object, String methodName) {
        CollectionReference usersRef = getUsersRef();
        usersRef.document(getCurrentUserId())
                .update("avatarUUID", avatarUUID)
                .addOnSuccessListener(aVoid -> {
                    UserDB.avatarUUID = avatarUUID;
                    MethodUtil.invokeMethod(object, methodName, true, avatarUUID);
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Error writing NewTestUsers", e);
                    MethodUtil.invokeMethod(object, methodName, false, e.getMessage());
                });
    }

    public String getAvatarUUID() {
        return avatarUUID;
    }

    /**
     * Get user data from local caches and set up the singleton
     *
     * @param object     object calls this function
     * @param methodName method name for callback
     */
    private void setUserData(Object object, String methodName) {
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
                    avatarUUID = task.getResult().getString("avatarUUID");
                    Map<String, FriendModel> myFriends;
                    if (task.getResult().getData() == null) {
                        String msg = "No friend, friend data is null";
                        Log.d(TAG, msg);
                        MethodUtil.invokeMethod(object, methodName, false, msg);
                        return;
                    } else {
                        myFriends = (Map<String, FriendModel>) task.getResult().getData().get("myFriends");
                    }
                    if (myFriends != null) {
                        friendsData.addNewFriends(myFriends);
                    }
                    Log.d(TAG, "username: " + username);
                    Log.d(TAG, "myFriends: " + myFriends);
                    MethodUtil.invokeMethod(object, methodName, true, username);
                });
    }

    /**
     * Get username from server
     *
     * @param userId     userId
     * @param object     object calls this function
     * @param methodName method name for callback
     */
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

    public interface OnAvatarUUIDChangedListener {
        void onAvatarUUIDChanged(String newAvatarUUID);
    }


    public void setOnAvatarUUIDChangedListener(OnAvatarUUIDChangedListener listener) {
        this.listener = listener;
        notifyAvatarUUIDChanged();
    }


    private void notifyAvatarUUIDChanged() {
        if (listener != null) {
            listener.onAvatarUUIDChanged(avatarUUID);
        }
    }

    /**
     * Clear user data
     */
    private static void clearUserDB() {
        username = null;
        userId = null;
        email = null;
        instance = null;
    }

    /**
     * Get UserDB instance
     *
     * @return UserDB instance
     */
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
     * @param input      username or email
     * @param password   password
     * @param object     object calls this function
     * @param methodName method name for callback
     */
    public static void signIn(String input, String password, Object object, String methodName) {
        if (AuthUtil.isEmail(input)) {
//            signInWithEmail(input, password, object, methodName);
            signInWithEmail(input, password, object, methodName);
        } else {
//            signInWithUsername(input, password, object, methodName);
            signInWithUsername(input, password, object, methodName);
        }
    }


    /**
     * Sign out from firebase
     *
     * @param object     object calls this function
     * @param methodName Callback return true if success, false and error message if fail
     */
    public static void signOut(Object object, String methodName) {
        FirebaseAuth.getInstance().signOut();
        clearUserDB();
        clearPostsData();
        clearFriendsData();

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

    /**
     * Check if user is signed in
     *
     * @param object     Object calls this function
     * @param methodName Callback return true and User object if success, false and error message if fail
     */
    public static void checkSignedIn(Object object, String methodName) {
        // Check if fireUser is signed in
        FirebaseUser fireUser = getFireUser();
        clearPostsData();

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

    /**
     * Get user from firebase
     *
     * @param user User object
     * @return User object
     */
    private static User firebaseUserToUser(FirebaseUser user) {
        User userReturn = new User();
        userReturn.setUserId(user.getUid());
        userReturn.setEmail(user.getEmail());

        return userReturn;
    }

    // Sign up

    /**
     * Sign up new user
     *
     * @param username   username
     * @param email      email
     * @param password   password
     * @param object     object calls this function
     * @param methodName Callback return true if success, false and error message if fail
     */
    public static void signUp(
            String username, String email, String password, Object object, String methodName) {

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

    /**
     * Follow user
     *
     * @param friend     Friend object
     * @param object     object calls this function
     * @param methodName Callback return true if success, false and error message if fail
     */
    public static void follow(Friend friend, Object object, String methodName) {
        CollectionReference usersRef = getUsersRef();
        FriendModel friendModel = new FriendModel(friend);
        Map<String, Object> update = new HashMap<>();
        update.put("myFriends", Map.of(friend.getId(), friendModel));

        usersRef.document(getCurrentUserId())
                .set(update, SetOptions.merge())
                .addOnSuccessListener(aVoid -> {
                    Log.d(TAG, "new follow successfully written!");
                    setFollowNotification(friend.getId(), object, methodName);
                    friendsData.addNewFriends(Map.of(friend.getId(), friendModel));
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Error writing NewTestUsers", e);
                    MethodUtil.invokeMethod(object, methodName, false, e.getMessage());
                });
    }

    /**
     * Unfollow user
     *
     * @param userId     userId
     * @param object     object calls this function
     * @param methodName Callback return true if success, false and error message if fail
     */
    public static void unfollow(String userId, Object object, String methodName) {
        Log.d(TAG, "unfollow: " + userId);
        FriendModel friendModel = friendsData.getFriendById(userId);
        if (friendModel == null) {
            String msg = "Friend does not exist";
            Log.d(TAG, msg);
            MethodUtil.invokeMethod(object, methodName, false, msg);
            return;
        }
        Map<String, FriendModel> friendModelMap = friendsData.getAllFriends();
        friendModelMap.remove(userId);

        CollectionReference usersRef = getUsersRef();
        usersRef.document(getCurrentUserId())
                .update("myFriends", friendModelMap)
                .addOnSuccessListener(aVoid -> {
                    friendsData.removeFriendById(userId);
                    MethodUtil.invokeMethod(object, methodName, true);
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Error writing NewTestUsers", e);
                    MethodUtil.invokeMethod(object, methodName, false, e.getMessage());
                });
    }

    /**
     * Send friend request
     *
     * @param friendRequest FriendRequest object
     * @param object        object calls this function
     * @param methodName    Callback return true if success, false and error message if fail
     */
    public static void sendFriendRequestTo(FriendRequest friendRequest, Object object, String methodName) {
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

    /**
     * Get friend list
     *
     * @param nickname   nickname
     * @param limit      limit
     * @param object     object calls this function
     * @param methodName Callback return true if success, false and error message if fail
     */
    public static void getFollowList(String nickname, Integer limit, Object object, String methodName) {
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
                    if (userModel == null) {
                        String msg = "No friend, user model is null";
                        Log.d(TAG, msg);
                        MethodUtil.invokeMethod(object, methodName, false, msg);
                        return;
                    }
                    Map<String, FriendModel> friendsMap = userModel.getMyFriends();

                    if (friendsMap == null) {
                        String msg = "No friend";
                        Log.d(TAG, msg);
                        MethodUtil.invokeMethod(object, methodName, false, msg);
                        return;
                    }
                    FriendsData friendsData = FriendsData.getInstance();
                    friendsData.addNewFriends(friendsMap);

                    List<Friend> friendList = new ArrayList<>();
                    // TODO: implement this
                    for (Map.Entry<String, FriendModel> entry : friendsMap.entrySet()) {
                        friendList.add(new Friend(entry.getValue()));
                    }

                    MethodUtil.invokeMethod(object, methodName, true, friendList);
                    Log.d(TAG, "FriendList: " + friendList.toString());
                });
    }

    /**
     * Get friend request list
     *
     * @param timestamp  Timestamp
     * @param limit      Integer
     * @param object     Object
     * @param methodName String
     */
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

    /**
     * Get friend request by id
     *
     * @param requestId  requestId
     * @param object     object calls this function
     * @param methodName Callback return true if success, false and error message if fail
     */
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

    /**
     * Process friend request
     *
     * @param friendRequest FriendRequest object
     * @param object        object calls this function
     * @param methodName    Callback return true if success, false and error message if fail
     */
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

    // Private Utils

    /**
     * Set follow notification
     *
     * @param receiverId receiverId
     * @param object     object calls this function
     * @param methodName Callback return true if success, false and error message if fail
     */
    private static void setFollowNotification(String receiverId, Object object, String methodName) {
        CollectionReference usersRef = getUsersRef();

        NotificationModel notificationModel = new NotificationModel();
        String msg = "New follower: " + username;
        DocumentReference documentReference = usersRef.document();
        notificationModel.setMessage(msg);
        notificationModel.setNotificationId(documentReference.getId());
        notificationModel.setSenderId(getCurrentUserId());
        notificationModel.setType("follow");
        notificationModel.setRead(false);
        notificationModel.setUsername(username);
        notificationModel.setTimestamp(Timestamp.now());

        Map<String, Object> myNotifications = new HashMap<>();
        myNotifications.put("myNotifications", Map.of(documentReference.getId(), notificationModel));

        usersRef.document(receiverId)
                .set(myNotifications, SetOptions.merge())
                .addOnSuccessListener(aVoid -> {
                    MethodUtil.invokeMethod(object, methodName, true, notificationModel);
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Error send follow notification", e);
                    MethodUtil.invokeMethod(object, methodName, false, e.getMessage());
                });
    }

    /**
     * Set friend request notification
     *
     * @param friendRequestModel FriendRequestModel object
     * @param object             object calls this function
     * @param methodName         Callback return true if success, false and error message if fail
     */
    private static void setFriendReqNotification(FriendRequestModel friendRequestModel, Object object, String methodName) {
        CollectionReference usersRef = getUsersRef();

        NotificationModel notificationModel = friendRequestModel.notification();
        notificationModel.setNotificationId(friendRequestModel.getRequestId());
        notificationModel.setMessage(friendRequestModel.getSenderName() + " sent you a friend request");
        notificationModel.setSenderId(getCurrentUserId());
        notificationModel.setType("friend_request");
        notificationModel.setRead(false);
        notificationModel.setUsername(username);
        notificationModel.setTimestamp(Timestamp.now());

        Map<String, Object> update = new HashMap<>();
        update.put("myNotifications", Map.of(friendRequestModel.getRequestId(), notificationModel));

        usersRef.document(friendRequestModel.getReceiverId())
                .set(update, SetOptions.merge())
                .addOnSuccessListener(aVoid -> {
                    MethodUtil.invokeMethod(object, methodName, true, friendRequestModel);
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Error writing NewTestUsers", e);
                    MethodUtil.invokeMethod(object, methodName, false, e.getMessage());
                });
    }

    /**
     * Sign in with email
     *
     * @param email      email
     * @param password   password
     * @param object     object calls this function
     * @param methodName Callback return true if success, false and error message if fail
     */
    private static void signInWithEmail(String email, String password, Object object, String methodName) {
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

    /**
     * Sign in with username
     *
     * @param username   username
     * @param password   password
     * @param object     object calls this function
     * @param methodName Callback return true if success, false and error message if fail
     */
    private static void signInWithUsername(String username, String password, Object object, String methodName) {
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
                    signInWithEmail(email, password, object, methodName);
                });
    }

    /**
     * Create user account
     *
     * @param username   username
     * @param email      email
     * @param password   password
     * @param object     object calls this function
     * @param methodName Callback return true if success, false and error message if fail
     */
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
                    userModel.setAvatarUUID("1");
                    userModel.setUsername(username);
                    userModel.setEmail(email);
                    userModel.setUserId(fireUser.getUid());

                    UserDB userDB = getInstance();

                    newSaveUserData(userModel, object, methodName);
                });
    }

    /**
     * Save user data to firebase
     *
     * @param userModel  User model
     * @param object     object calls this function
     * @param methodName Callback return true if success, false and error message if fail
     */
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
