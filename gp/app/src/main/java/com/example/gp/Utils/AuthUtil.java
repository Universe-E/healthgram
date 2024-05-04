package com.example.gp.Utils;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.Contract;

import java.util.Objects;


public class AuthUtil {

    private static final String TAG = "Authorization";
    private static final String USERS_NODE = "users";

    /*********** API ***********/

    public static boolean isValidEmail(String email) {
        // Check if the email is in the correct format
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        return email.matches(emailPattern);
    }

    public static boolean isUsernameTaken(String username) {
        // Check if the username is already in the database
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child(USERS_NODE);
        final boolean[] isTaken = {false};
        usersRef.orderByChild("username").equalTo(username).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                isTaken[0] = dataSnapshot.exists();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, "Error checking username availability", databaseError.toException());
            }
        });
        return isTaken[0];
    }

    public static boolean isEmailTaken(String email) {
        // Check if the email is already in the database
        final boolean[] isTaken = {false};
        FirebaseAuth.getInstance().fetchSignInMethodsForEmail(email).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                isTaken[0] = !Objects.requireNonNull(task.getResult().getSignInMethods()).isEmpty();
            }
        });
        return isTaken[0];
    }

    public static boolean isValidUsername(String username) {
        // i.e. check if the username is toxic
        // i.e. check if the username is spam
        // i.e. check if the username is offensive
        return !username.isEmpty() && !username.contains(" ")
                && !(username.length() <= 18);
    }

    public static boolean isEmail(String input) {
        return isValidEmail(input);
    }

    @Nullable
    @Contract(pure = true)
    public static String getEmailByUsername(String username) {
        // Get email by username from the database
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child(USERS_NODE);
        final String[] email = {null};
        usersRef.orderByChild("username").equalTo(username).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                        email[0] = userSnapshot.child("email").getValue(String.class);
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, "Error retrieving email by username", databaseError.toException());
            }
        });
        return email[0];
    }

    /********** END API **********/
}
