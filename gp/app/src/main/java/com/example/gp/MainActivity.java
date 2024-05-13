package com.example.gp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gp.Utils.ToastUtil;
import com.example.gp.data.Database;
import com.example.gp.databinding.ActivityMainBinding;
import com.example.gp.home.Fragment_home;
import com.example.gp.setting.SettingActivity;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private ActivityMainBinding binding;

    @Override
    protected void onStart() {
        super.onStart();

        Database.checkSignedIn(this, "updateStartUI");
        Log.d(TAG, "onStart: checkSignedIn");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // If not root task, cannot go back to previous page
        if (!isTaskRoot()) {
            final Intent intent = getIntent();
            final String intentAction = intent.getAction();
            if (intent.hasCategory(Intent.CATEGORY_LAUNCHER) && intentAction != null
                    && intentAction.equals(Intent.ACTION_MAIN)) {
                finish();
                return;
            }
        }

        // Check if user is already signed in
        Database.checkSignedIn(this, "updateStartUI");

        // load the layout using view binding
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        // set the content view
        setContentView(binding.getRoot());
    }

    public void createAccount(View view) {
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, SignUpActivity.class);
        startActivity(intent);
    }

    public void login(View view) {
        EditText usernameInputField = findViewById(R.id.username_input_field);
        EditText passwordInputField = findViewById(R.id.password_input_field);

        String username = usernameInputField.getText().toString();
        String password = passwordInputField.getText().toString();

        // check if the username and password are correct
        // Sign in with email and password

        if (validateForm(username, password)) {
            Log.d(TAG, "login: " + username);
            // authenticate the user
            Database.signIn(username, password, this, "updateUI");
        }
    }

    public void updateStartUI(boolean isSuccess, Object args) {
        Log.d(TAG, "updateStartUI: " + isSuccess);
        if (isSuccess) {
            Log.d(TAG, "updateStartUI: " + args.toString());
            Intent intent = new Intent(MainActivity.this, Fragment_home.class);
            startActivity(intent);
        }
    }

    public void updateUI(boolean isSuccess, Object args) {
        Log.d(TAG, "updateUI: " + isSuccess);
        if (isSuccess) {
            Log.d(TAG, "updateUI: " + args.toString());
            Intent intent = new Intent(MainActivity.this, Fragment_home.class);
            startActivity(intent);
        } else {
            if (args != null) {
                ToastUtil.showLong(this, "Login failed: wrong credentials");
            } else {
                ToastUtil.showLong(this, "Login failed");
            }
        }
    }

    /**
     * Validate username, email, and password
     * @param usernameOrEmail username, email
     * @param password password
     * @return true if valid
     */
    private boolean validateForm(String usernameOrEmail, String password) {
        if (usernameOrEmail.isEmpty() || password.isEmpty()) {
            ToastUtil.showLong(this, "Please fill in all fields");
        } else if (password.length() < 6) {
            ToastUtil.showLong(this, "Password must be at least 6 characters");
        } else {
            return true;
        }

        return false;
    }

    // Debug
    public void superToken(View v){
        Intent intent = new Intent(this, SettingActivity.class);
        startActivity(intent);
    }

}
