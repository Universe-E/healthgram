package com.example.gp.Items;

/**
 * Tokenizer for search
 * @author Zehua Kong
 */
public class Tokenizer {
    private String text;
    private int pos;

    public Tokenizer(String text) {
        this.text = text;
        this.pos = 0;
    }

    /**
     * Get next token
     * @return next token
     */
    public Token nextToken() {
        if (pos >= text.length()) {
            return new Token(Token.Type.EOF, null);
        }

        char currentChar = text.charAt(pos);

        if (currentChar == '@') {
            pos++;
            return new Token(Token.Type.AT, "@");
        }

        //detect prefix: title
        if (pos < text.length() - 5 && text.startsWith("title:", pos)) {
            pos += 6;
            return new Token(Token.Type.TITLE, "title:");
        }

        //detect prefix: public
        if (pos < text.length() - 6 && text.startsWith("public:", pos)) {
            pos += 7;
            return new Token(Token.Type.PUBLIC, "public:");
        }

        //tokenize content as NAME type
        if (pos < text.length() && (Character.isAlphabetic(currentChar) || Character.isDigit(currentChar))) {
            StringBuilder sb = new StringBuilder();
            while (pos < text.length() && (Character.isAlphabetic(currentChar) || Character.isDigit(currentChar))) {
                sb.append(currentChar);
                pos++;
                if (pos < text.length()) {
                    currentChar = text.charAt(pos);
                }
            }
            return new Token(Token.Type.NAME, sb.toString());
        }

        pos++;
        return nextToken();
    }

    /**
     * Inner class token
     * @author Zehua Kong
     */
    public static class Token {
        public enum Type {
            AT, NAME, TITLE, PUBLIC, EOF
        }

        public Type type;
        public String value;

        Token(Type type, String value) {
            this.type = type;
            this.value = value;
        }
    }
}