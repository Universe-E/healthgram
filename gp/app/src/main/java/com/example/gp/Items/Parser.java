package com.example.gp.Items;

public class Parser {
    // Declare a static variable of the Parser type
    private static Parser instance;

    // Make the constructor private
    private Parser() {}

    // Provide a public static method that returns the single instance
    public static Parser getInstance() {
        if (instance == null) {
            instance = new Parser();
        }
        return instance;
    }

    public boolean isMentioningSomeone(String text) {
        int flag = 0;

        // Iterate through each character in the string
        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            // Increment flag if the character is '@'
            if (ch == '@') {
                flag++;
            }
            // Decrement flag if the character is a space
            if (ch == ' ') {
                flag--;
            }
        }

        // Return true if flag is not zero after processing the whole string
        return flag != 0;
    }

    // Parse the text to implement '@' mention functionality
    public String parseUserName(String text) {
        // get the text between the last '@' and the next space
        // Find the index of the last '@' character
        int atIndex = text.lastIndexOf('@');
        if (atIndex == -1) {
            // Return null or empty if no '@' is found
            return "";
        }

        // Find the index of the next space after the last '@'
        int spaceIndex = text.indexOf(' ', atIndex);
        if (spaceIndex == -1) {
            // If there is no space, return the substring from '@' to the end of the string
            return text.substring(atIndex + 1);
        } else {
            // Return the substring between the last '@' and the next space
            return text.substring(atIndex + 1, spaceIndex);
        }
    }
}
