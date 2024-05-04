package com.example.gp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gp.Utils.ToastUtil;
import com.example.gp.Utils.AuthUtil;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
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

        if (validateForm(username, email, password, repeat_password)) {
            // Create account
            mAuth = FirebaseAuth.getInstance();

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();

                                // TODO: Update UI with user information
                                // TODO: Save user information to Firestore

                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                ToastUtil.showLong(SignUpActivity.this, "Authentication failed: " + Objects.requireNonNull(task.getException()).getMessage());
                            }
                        }
                    });
        }

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
            ToastUtil.showLong(this, "Invalid email address");
        } else if (AuthUtil.isUsernameTaken(username)) {
            // Username is already taken
            ToastUtil.showLong(this, "Username already taken");
        } else if (AuthUtil.isEmailTaken(email)) {
            // Email is already taken
            ToastUtil.showLong(this, "Email already taken");
        } else if (!AuthUtil.isValidUsername(username)) {
            // Username is invalid
            ToastUtil.showLong(this, "Invalid username");
        } else {
            // All fields are valid
            return true;
        }

        return false;
    }
}