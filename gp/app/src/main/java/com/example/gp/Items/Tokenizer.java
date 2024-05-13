package com.example.gp.Items;

public class Tokenizer {
    private String text;
    private int pos;

    public Tokenizer(String text) {
        this.text = text;
        this.pos = 0;
    }

    public Token nextToken() {
        if (pos >= text.length()) {
            return new Token(Token.Type.EOF, null);
        }

        char currentChar = text.charAt(pos);

        if (currentChar == '@') {
            pos++;
            return new Token(Token.Type.AT, "@");
        }

        if (pos < text.length() - 5 && text.startsWith("title:", pos)) {
            pos += 6;
            return new Token(Token.Type.TITLE, "title:");
        }

        if (pos < text.length() - 6 && text.startsWith("public:", pos)) {
            pos += 7;
            return new Token(Token.Type.PUBLIC, "public:");
        }

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