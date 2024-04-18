package com.example.gp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gp.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private com.example.gp.databinding.ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        binding.btnLogin.setOnClickListener(this);
        binding.btnCreateAccount.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v == binding.btnLogin) {
            // login
            String username = binding.etUsername.getText().toString();
        } else if(v == binding.btnCreateAccount) {
            // create account

        }
    }
}