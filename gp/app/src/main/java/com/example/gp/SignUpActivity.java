package com.example.gp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText emailEditText;
    private EditText pwdEditText;
    private EditText reapeatPwdEditText;
    private Button createAccountButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);

        // Initialize views
        usernameEditText = findViewById(R.id.user_name_text);
        emailEditText = findViewById(R.id.email_text);
        pwdEditText = findViewById(R.id.pwd_text);
        reapeatPwdEditText = findViewById(R.id.repeat_pwd_text);
        createAccountButton = findViewById(R.id.tv_create_account);

        createAccountButton.setOnClickListener(v -> {
            String username = usernameEditText.getText().toString().trim();
            String email = emailEditText.getText().toString().trim();
            String password = pwdEditText.getText().toString().trim();
            String repeat_password = reapeatPwdEditText.getText().toString().trim();
            if (password.equals(repeat_password)) {
                createUserAccount(username, email, password);
            }
            else {
                //password and repeat password are not match
                Toast.makeText(SignUpActivity.this, "Repeat password doesn't match",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void createUserAccount(String username, String email, String password) {
        //user email as the unique user id
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    // User account created successfully
                    String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    saveUserData(userId, username, email);
                    // Redirect to Main Activity after successful create
                    Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                    startActivity(intent);


                } else {
                    // Account creation failed
                    Toast.makeText(SignUpActivity.this, "Sign Up Failed: " + task.getException().getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            });
    }

    private void saveUserData(String userId, String username, String email) {
        Map<String, Object> user = new HashMap<>();
        user.put("username", username);
        user.put("email", email);

        FirebaseFirestore.getInstance().collection("users").document(userId)
            .set(user).addOnSuccessListener(
                    aVoid -> Toast.makeText(SignUpActivity.this, "Sigun up success", Toast.LENGTH_SHORT).show())
            .addOnFailureListener(
                    e -> Toast.makeText(SignUpActivity.this, "Save data failed: " + e.getMessage(),
                    Toast.LENGTH_SHORT).show());
    }
}