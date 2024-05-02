package com.example.gp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gp.databinding.ActivityMainBinding;
import com.example.gp.home.Fragment_home;
import com.example.gp.setting.SettingActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
        // TODO: pass the username and password to the server to check if they are correct
        // Sign in with email and password
        mAuth.signInWithEmailAndPassword(username, password)
            .addOnCompleteListener(this, task -> {
                if (task.isSuccessful()) {
                    // Sign in success, go to the main page
                    Intent intent = new Intent(MainActivity.this, Fragment_home.class);
                    startActivity(intent);
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(MainActivity.this,
                            "Authentication failed, please check your email or password",
                            Toast.LENGTH_SHORT).show();
                }
            });

    }
}
