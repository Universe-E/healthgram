package com.example.gp;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.gp.Utils.AuthUtil;
import com.example.gp.data.Database;
import com.google.firebase.auth.FirebaseAuth;

import java.lang.reflect.Method;
import java.util.Arrays;

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
        try {
            Method method = getClass().getMethod("testReflect");
            Log.d(TAG, "testReflect: " + Arrays.toString(method.getParameters()));
        } catch (NoSuchMethodException e) {
            Log.e(TAG, "testReflect: ", e);
        }
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
        String email = AuthUtil.getEmailByUsername("123");
        Log.d(TAG, "testGetEmailByUsername: " + email);
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
}