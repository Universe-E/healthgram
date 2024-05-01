package com.example.gp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gp.databinding.ActivityMainBinding;
import com.example.gp.home.Fragment_home;
import com.example.gp.setting.SettingActivity;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // load the layout using view binding
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        // set the content view
        setContentView(binding.getRoot());

    }

    public void createAccount(View view) {
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, SettingActivity.class);
        startActivity(intent);
    }

    public void login(View view) {
        EditText usernameInputField = findViewById(R.id.username_input_field);
        EditText passwordInputField = findViewById(R.id.password_input_field);

        String username = usernameInputField.getText().toString();
        String password = passwordInputField.getText().toString();

        boolean correct = false;

        // check if the username and password are correct
        // TODO: pass the username and password to the server to check if they are correct
        // for now, we will use a hardcoded username and password
        if (username.equals("123") && password.equals("123")) {
            correct = true;
        }

        if (correct) {
            // if correct, go to the main page
            Intent intent = new Intent(this, Fragment_home.class);
            startActivity(intent);
        } else {
            // if not correct, show a toast
            Toast.makeText(this, "Username or password is incorrect", Toast.LENGTH_SHORT).show();

            // show text for debug
            // Toast.makeText(this, "Username: " + username + " Password: " + password, Toast.LENGTH_SHORT).show();
        }
    }
}
