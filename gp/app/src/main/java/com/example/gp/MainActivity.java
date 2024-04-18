package com.example.gp;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gp.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {
    private com.example.gp.databinding.ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());


    }
}