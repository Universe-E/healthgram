package com.example.gp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gp.data.Database;
import com.example.gp.Utils.ToastUtil;
import com.example.gp.Utils.AuthUtil;

import com.example.gp.home.Fragment_home;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;


public class SignUpActivity extends AppCompatActivity {

    private static final String TAG = SignUpActivity.class.getSimpleName();

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);
    }

    public void createUserAccount(View view) {
        // Initialize views
        EditText usernameEditText = findViewById(R.id.user_name_text);
        EditText emailEditText = findViewById(R.id.email_text);
        EditText pwdEditText = findViewById(R.id.pwd_text);
        EditText reapeatPwdEditText = findViewById(R.id.repeat_pwd_text);

        String username = usernameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String password = pwdEditText.getText().toString().trim();
        String repeat_password = reapeatPwdEditText.getText().toString().trim();

        if (!validateForm(username, email, password, repeat_password))
            return;
        // Check if username is taken
        AuthUtil.isUsernameTaken(username).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                boolean isUsernameTaken = task.getResult();
                if (isUsernameTaken) {
                    ToastUtil.showLong(this, "Username already taken");
                } else {
                    // Check if email is taken
                    AuthUtil.isEmailTaken(email).addOnCompleteListener(task1 -> {
                        if (task1.isSuccessful()) {
                            boolean isEmailTaken = task1.getResult();
                            if (isEmailTaken) {
                                ToastUtil.showLong(this, "Email already taken");
                            } else {
                                // Create account
                                mAuth = FirebaseAuth.getInstance();

                                mAuth.createUserWithEmailAndPassword(email, password)
                                .addOnCompleteListener(this, task2 -> {
                                    if (task2.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in User's information
                                        Log.d(TAG, "createUserWithEmail:success");
                                        FirebaseUser user = mAuth.getCurrentUser();

                                        assert user != null;
                                        String userId = user.getUid();

                                        Database.User.saveUserData(userId, username, email);

                                        //Redirect to login page.
                                        Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                                        startActivity(intent);

                                        // TODO: Update UI with User information
                                    } else {
                                        // If sign in fails, display a message to the User.
                                        Log.w(TAG, "createUserWithEmail:failure", task2.getException());
                                        ToastUtil.showLong(SignUpActivity.this, "Authentication failed: " + Objects.requireNonNull(task2.getException()).getMessage());
                                    }
                                });
                            }
                        } else {
                            Log.e(TAG, "Error checking email existence", task1.getException());
                            ToastUtil.showLong(this, "Error checking email existence");
                        }
                    });
                }
            } else {
                Log.e(TAG, "Error checking username existence", task.getException());
                ToastUtil.showLong(this, "Error checking username existence");
            }
        });

    }

    private boolean validateForm(String username, String email, String password, String repeat_password) {

        if (!password.equals(repeat_password)) {
            // Passwords don't match
            ToastUtil.showLong(this, "Repeat password doesn't match");
        } else if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            // Fields are empty
            ToastUtil.showLong(this, "Please fill all fields");
        } else if (password.length() < 6) {
            // Password is too short
            ToastUtil.showLong(this, "Password must be at least 6 characters");
        } else if (!AuthUtil.isValidEmail(email)) {
            // Email is invalid
            ToastUtil.showLong(this, "Invalid email address, eg: ABCabc123@example.com");
        } else if (!AuthUtil.isValidUsername(username)) {
            // Username is already taken
            ToastUtil.showLong(this, "Invalid username: contains space, or length not range from 3 to 18");
        } else {
            // All fields are valid
            return true;
        }

        return false;
    }
}