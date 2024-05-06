package com.example.gp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.gp.Items.Friend;
import com.example.gp.Items.Post;
import com.example.gp.Items.User;
import com.example.gp.Utils.AuthUtil;
import com.example.gp.Utils.TimeUtil;
import com.example.gp.data.Database;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

public class SimpleTestActivity extends AppCompatActivity {
    private static final String TAG = "SimpleTestActivity";

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

        testFirebaseFirestore();
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
        Database.UserDB userDB = new Database.UserDB();
//        Database.User.getEmailByUsername("123", this, this.getClass().getMethod("toastUsername", String.class));
//        Database.User.getEmailByUsername("123", this, "toastUsername");
    }

    public void testReflect() {
    }

    public void toastUsername(String username) {
        Log.d(TAG, "toastUsername: " + username);
    }

    public void click(View view) {
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

    void updateUI(Post post) {
        EditText editText = findViewById(R.id.test_editText);
        editText.setText(post.title);
        editText.setTag(post.postId);
    }
}