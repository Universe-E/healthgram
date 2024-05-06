package com.example.gp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gp.Utils.ToastUtil;
import com.example.gp.data.Database;
import com.example.gp.databinding.ActivityMainBinding;
import com.example.gp.home.Fragment_home;
import com.example.gp.setting.SettingActivity;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private ActivityMainBinding binding;
    private FirebaseAuth mAuth;

    @Override
    protected void onStart() {
        super.onStart();

        Database.UserDB.checkSignedIn(this, "updateUI");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Check if user is already signed in
        Database.UserDB.checkSignedIn(this, "updateUI");

        // load the layout using view binding
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        // set the content view
        setContentView(binding.getRoot());

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

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
            // authenticate the user
            Database.UserDB.signIn(username, password, this, "updateUI");
        }
    }

    public void updateUI() {
        Intent intent = new Intent(MainActivity.this, Fragment_home.class);
        startActivity(intent);
    }

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
