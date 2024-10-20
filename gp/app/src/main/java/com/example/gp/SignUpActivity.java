package com.example.gp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;

import com.example.gp.Utils.AuthUtil;
import com.example.gp.Utils.ToastUtil;
import com.example.gp.data.Database;
import com.example.gp.home.Fragment_home;

/**
 * Sign Up activity page
 * Author: Han Bao
 * Date: 2024-05-03
 */

public class SignUpActivity extends BaseActivity {

    private static final String TAG = SignUpActivity.class.getSimpleName();
    private String activityName = "Sign Up";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);

        // initialize top bar
        setUpTitleBar(R.layout.activity_new_post, activityName);
    }

    /**
     * Get user inputs
     *
     * @param view current view
     */
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
            Database.signUp(username, email, password, this, "updateUI");
        }

    }

    /**
     * Update UI for reflection in Database.signUp
     *
     * @param isSuccess service call success or not
     * @param args      service call result
     */
    public void updateUI(Boolean isSuccess, Object args) {
        if (isSuccess) {
            Intent intent = new Intent(this, Fragment_home.class);
            startActivity(intent);
        } else {
            if (args != null) {
                String errorMsg = (String) args;
                ToastUtil.showLong(this, "Create account failed: " + errorMsg);
            } else {
                ToastUtil.showLong(this, "Create account failed");
            }
        }
    }

    /**
     * Validate user inputs
     *
     * @param username        username
     * @param email           email
     * @param password        password
     * @param repeat_password repeat password
     * @return validation result
     */
    private boolean validateForm(String username, String email, String password, String repeat_password) {

        if (!password.equals(repeat_password)) {
            // Passwords don't match
            ToastUtil.showLong(this, "Repeat password doesn't match");
            return false;
        }
        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            // Fields are empty
            ToastUtil.showLong(this, "Please fill all fields");
            return false;
        }
        if (password.length() < 6) {
            // Password is too short
            ToastUtil.showLong(this, "Password must be at least 6 characters");
            return false;
        }
        if (!AuthUtil.isValidEmail(email)) {
            // Email is invalid
            ToastUtil.showLong(this, "Invalid email address, eg: ABCabc123@example.com, or ABCabc123@uni.edu.com");
            return false;
        }
        if (!AuthUtil.isValidUsername(username)) {
            // Username is already taken
            ToastUtil.showLong(this, "Invalid username: contains space, or length not range from 3 to 18");
            return false;
        }
        return true;
    }
}