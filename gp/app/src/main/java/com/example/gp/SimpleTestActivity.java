package com.example.gp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.gp.Items.Friend;
import com.example.gp.Items.FriendRequest;
import com.example.gp.Items.Post;
import com.example.gp.Items.User;
import com.example.gp.Utils.TimeUtil;
import com.example.gp.Utils.UserParserActivity;
import com.example.gp.data.Database;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.Map;

public class SimpleTestActivity extends AppCompatActivity {
    private static final String TAG = "SimpleTestActivity";

    private static int anInt = 0;

    @Override
    protected void onStart() {
        super.onStart();

        Log.d(TAG, "onStart");

        // tests
//        testMultiAuth();
//        testGetEmailByUsername();
//        try {
//            testUser();
//        } catch (NoSuchMethodException e) {
//            Log.e(TAG, "testUser: ", e);
//        }
//        try {
//            Method method = getClass().getMethod("testReflect");
//            Log.d(TAG, "testReflect: " + Arrays.toString(method.getParameters()));
//        } catch (NoSuchMethodException e) {
//            Log.e(TAG, "testReflect: ", e);
//        }

//        testFirebaseFirestore();
//        testGetDocIdFromDocRefBeforeSet();

//        testSavePost(new Post("testpoqewrq", "455rtrgdf", true));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_simple_test);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Log.d(TAG, "onCreate");
    }

    private void testMultiAuth() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword("123@gmail.com", "123123")
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "signInWithEmail:success");
                    } else {
                        Log.d(TAG, "signInWithEmail:failure", task.getException());
                    }
                });

        FirebaseAuth newAuth = FirebaseAuth.getInstance();
        newAuth.getCurrentUser();
        if (newAuth.getCurrentUser() != null) {
            Log.d(TAG, "onCreate: user is signed in");
        } else {
            Log.d(TAG, "onCreate: user is not signed in god help me");
        }
    }

    private void testGetEmailByUsername() {
//        String email = AuthUtil.getEmailByUsername("123");
//        Log.d(TAG, "testGetEmailByUsername: " + email);
    }

    private void testUser() throws NoSuchMethodException {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.getCurrentUser();
        if (mAuth.getCurrentUser() == null) {
            Log.d(TAG, "User is not signed in");
            mAuth.signInWithEmailAndPassword("123@gmail.com", "123123");
        } else {
            Log.d(TAG, "User is signed in");
        }
//        Database.UserDB userDB = new Database.UserDB();
//        Database.User.getEmailByUsername("123", this, this.getClass().getMethod("toastUsername", String.class));
//        Database.User.getEmailByUsername("123", this, "toastUsername");
    }

    public void testReflect() {
    }

    public void toastUsername(String username) {
        Log.d(TAG, "toastUsername: " + username);
    }

    public void click(View view) {
//        testPost();
//        testTag(view);
//        testGetPost();
//        testGetPost();
//        testAddFriend();
//        testGetFriendList();
        testUserParserActivity();
    }

    private void testUserParserActivity() {
        Intent intent = new Intent();
        intent.setClass(SimpleTestActivity.this, UserParserActivity.class);
        startActivity(intent);
    }
    private void testGetFriendList() {
        Database.UserDB.getFriendList("", 10, this, "getFriendList");
    }

    public void getFriendList(boolean isSuccess, Object object) {
//        List<Map<String, Friend>> friendMap = (List<Map<String, Friend>>) object;
        Log.d(TAG, "getFriendList: " + object.toString());
    }

    private void testTag(View view) {
        Log.d(TAG, view.getClass().toString());
        view.setTag(anInt++);
        Log.d(TAG, view.getTag().toString());
    }

    public void testPost() {
        Date date = TimeUtil.getCurDate();
        Database.PostDB.getPostsByTime(date, 1, this, "updateUI");
    }
    public void testFirebaseFirestore() {
        Log.d(TAG, "testFirebaseFirestore");
        User user = new User("123", "123", "123");
        Friend friend = new Friend("123", "123", 123);
        user.setFriendMap(Map.of("123", friend));
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("testUsers").document("123").set(user)
                .addOnSuccessListener(aVoid -> Log.d(TAG, "testFirebaseFirestore: success"))
                .addOnFailureListener(e -> Log.e(TAG, "testFirebaseFirestore: ", e));
        db.collection("testUsers").document("123").get()
                .addOnSuccessListener(documentSnapshot -> {
                    User user1 = documentSnapshot.toObject(User.class);
                    Log.d(TAG, "testFirebaseFirestore: " + user1.getUserId());
                })
                .addOnFailureListener(e -> Log.e(TAG, "testFirebaseFirestore: ", e));
    }

    public void testGetDocIdFromDocRefBeforeSet() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        DocumentReference docRef = db.collection("testDoc").document();
        String preDocId = docRef.getId();
        docRef.set(Map.of("title", "123"))
                .addOnSuccessListener(aVoid -> {
                    Log.d(TAG, "Pre Doc Id: " + preDocId);
                    DocumentReference docRef1 = db.collection("testDoc").document(preDocId);
                    docRef1.get()
                            .addOnSuccessListener(documentSnapshot -> {
                                Log.d(TAG, "Post Doc Id: " + documentSnapshot.getId());
                            });
                });
    }

    void testSavePost(Post post) {
        Database.PostDB.savePostData(post, null, null);
    }

    void testGetPost() {
        Log.d(TAG, "this: " + this);
        Database.PostDB.getUserPost(null, 10, this, "updateUITestPost");
    }

    public void updateUITestPost(boolean isSuccess, Object object) {
        Log.d(TAG, "updateUITestPost: " + isSuccess);
        if (isSuccess) {
            Map<String, Post> posts = (Map<String, Post>) object;
            Log.d(TAG, "updateUITestPost: " + posts.toString());
            Post post = (Post) posts.values().toArray()[0];
            EditText editText = findViewById(R.id.test_editText);
            editText.setText(post.title);
            editText.setTag(post.postId);
        }
    }

    void testAddFriend() {
        Friend friend = new Friend();
        friend.setId(String.valueOf(friend.hashCode()));
        friend.setNickname("test" + friend.hashCode());
        friend.setAvatar(1);
        Database.UserDB.addFriend(friend, null, null);
    }

    void updateUI(boolean isSuccess, Object object) {
        Post post = (Post) object;
        EditText editText = findViewById(R.id.test_editText);
        editText.setText(post.title);
        editText.setTag(post.postId);
    }

    private void showAddFriendDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you want to send a friend request？")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        FriendRequest fr = new FriendRequest("user1@gmail.com");
                        Database.UserDB.sendFriendRequestTo(fr,null,null);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        // Show the dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}