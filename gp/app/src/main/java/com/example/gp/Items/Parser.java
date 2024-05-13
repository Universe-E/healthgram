package com.example.gp.Items;

public class Parser {
    private static Parser instance;

    private Parser() {}

    public static Parser getInstance() {
        if (instance == null) {
            instance = new Parser();
        }
        return instance;
    }

    public boolean isMentioningSomeone(String text) {
        Tokenizer tokenizer = new Tokenizer(text);
        Tokenizer.Token token;

        while ((token = tokenizer.nextToken()).type != Tokenizer.Token.Type.EOF) {
            if (token.type == Tokenizer.Token.Type.AT) {
                return true;
            }
        }

        return false;
    }

    public String parseUserName(String text) {
        Tokenizer tokenizer = new Tokenizer(text);
        Tokenizer.Token token;
        String username = "";

        while ((token = tokenizer.nextToken()).type != Tokenizer.Token.Type.EOF) {
            if (token.type == Tokenizer.Token.Type.AT) {
                token = tokenizer.nextToken();
                if (token.type == Tokenizer.Token.Type.NAME) {
                    username = token.value;
                }
            }
        }

        return username;
    }

    public String parseTitle(String text) {
        Tokenizer tokenizer = new Tokenizer(text);
        Tokenizer.Token token;
        String title = "";

        while ((token = tokenizer.nextToken()).type != Tokenizer.Token.Type.EOF) {
            if (token.type == Tokenizer.Token.Type.TITLE) {
                StringBuilder sb = new StringBuilder();
                while ((token = tokenizer.nextToken()).type == Tokenizer.Token.Type.NAME) {
                    sb.append(token.value);
                }
                title = sb.toString();
            }
        }

        return title;
    }

    public Boolean parsePublic(String text) {
        Tokenizer tokenizer = new Tokenizer(text);
        Tokenizer.Token token;
        Boolean isPublic = null;

        while ((token = tokenizer.nextToken()).type != Tokenizer.Token.Type.EOF) {
            if (token.type == Tokenizer.Token.Type.PUBLIC) {
                token = tokenizer.nextToken();
                if (token.type == Tokenizer.Token.Type.NAME) {
                    String value = token.value.toLowerCase();
                    if (value.equals("true") || value.equals("false")) {
                        isPublic = Boolean.parseBoolean(value);
                    }
                }
            }
        }

        return isPublic;
    }
}