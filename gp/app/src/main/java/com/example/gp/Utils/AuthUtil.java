package com.example.gp.Utils;


import androidx.annotation.Nullable;

import com.example.gp.data.Database;

import org.jetbrains.annotations.Contract;



public class AuthUtil {

    private static final String TAG = "Authorization";
    private static final String USERS_NODE = "users";

    /*********** API ***********/

    public static boolean isValidEmail(String email) {
        // Check if the email is in the correct format
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        return email.matches(emailPattern);
    }

//    public static Task<Boolean> isUsernameTaken(String username) {
//        // Check if the username is already in the database
////        return Database.User.isUsernameExist(username);
//    }

//    public static Task<Boolean> isEmailTaken(String email) {
//        // Check if the email is already in the database
////        return Database.User.isEmailExist(email);
//    }

    public static boolean isValidUsername(String username) {
        // i.e. check if the username is toxic
        // i.e. check if the username is spam
        // i.e. check if the username is offensive
        return !username.isEmpty() && !username.contains(" ")
                && username.length() >= 3 && username.length() <= 18;
    }

    public static boolean isEmail(String input) {
        return isValidEmail(input);
    }

    @Nullable
    @Contract(pure = true)
    public static String getEmailByUsername(String username) {
        // Get email by username from the database
        return Database.UserDB.getEmailByUsername(username);
    }

    /********** END API **********/
}
