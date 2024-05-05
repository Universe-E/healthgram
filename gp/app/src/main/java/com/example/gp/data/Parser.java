package com.example.gp.data;

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
        // Split the string by spaces to check each part separately
        String[] parts = text.split("\\s+");
        for (String part : parts) {
            // Check each part for '@' followed by non-space characters without a space in between
            int atIndex = part.indexOf('@');
            while (atIndex != -1) {
                // Check if '@' is followed directly by non-space characters
                if (atIndex + 1 < part.length() && !Character.isWhitespace(part.charAt(atIndex + 1))) {
                    return true;
                }
                // Look for the next '@' in the current part
                atIndex = part.indexOf('@', atIndex + 1);
            }
        }
        return false;
    }

    // Parse the text to implement '@' mention functionality
    public String parseUserName(String text) {
        // get the text between '@' and the next space
        int start = text.indexOf("@");
        int end = text.indexOf(" ", start);
        if (end == -1) {
            end = text.length();
        }
        return text.substring(start, end);
    }
}
