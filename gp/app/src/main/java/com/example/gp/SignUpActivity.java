package com.example.gp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gp.data.Database;
import com.example.gp.Utils.ToastUtil;
import com.example.gp.Utils.AuthUtil;

import com.example.gp.home.Fragment_home;


public class SignUpActivity extends AppCompatActivity {

    private static final String TAG = SignUpActivity.class.getSimpleName();

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
            Database.UserDB.signUp(username, email, password, this, "updateUI");
        }

    }

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
            ToastUtil.showLong(this, "Invalid email address, eg: ABCabc123@example.com, or ABCabc123@uni.edu.com");
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