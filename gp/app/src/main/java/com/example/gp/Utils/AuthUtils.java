package com.example.gp.Utils;

import androidx.annotation.Nullable;

import org.jetbrains.annotations.Contract;


public class AuthUtils {

    private static final String TAG = "Authorization";

    /*********** API ***********/

    public static boolean isValidEmail(String email) {
        // TODO: Check if the email is valid (is in the correct format)

        return true;  // TODO: Change this
    }

    public static boolean isUsernameTaken(String username) {
        // TODO: Check if the username is taken
        // i.e. check if the username is already in the database

        return false; // TODO: Change this
    }

    public static boolean isEmailTaken(String email) {
        // TODO: Check if the email is taken
        // i.e. check if the email is already in the database

        return false; // TODO: Change this
    }

    public static boolean isValidUsername(String username) {
        // TODO: Check if the username is available

        // i.e. check if the username is toxic
        // i.e. check if the username is spam
        // i.e. check if the username is offensive

        return true; // TODO: Change this
    }

    public static boolean isEmail(String input) {
        return isValidEmail(input);
    }

    @Nullable
    @Contract(pure = true)
    public static String getEmailByUsername(String username) {
        // TODO: get email by username

        return null; // TODO: Change this
    }

    /********** END API **********/
}
