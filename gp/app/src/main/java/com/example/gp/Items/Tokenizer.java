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

        if (!Character.isWhitespace(currentChar)) {
            StringBuilder username = new StringBuilder();
            while (pos < text.length() && !Character.isWhitespace(text.charAt(pos))) {
                username.append(text.charAt(pos));
                pos++;
            }
            return new Token(Token.Type.USERNAME, username.toString());
        }

        pos++;
        return nextToken();
    }

    public static class Token {
        public enum Type {
            AT, USERNAME, EOF
        }

        public Type type;
        public String value;

        Token(Type type, String value) {
            this.type = type;
            this.value = value;
        }
    }
}


