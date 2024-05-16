package com.example.gp.Items;

/**
 * Parse texts by following multiple grammars for different types of queries
 * @author Zehua Kong
 */
public class Parser {
    private static Parser instance;

    private Parser() {}

    /**
     * User Singleton Pattern to initialize Parser
     * @return Parser instance
     */
    public static Parser getInstance() {
        if (instance == null) {
            instance = new Parser();
        }
        return instance;
    }

    /**
     * Tokenizer class to tokenize the text
     * @param text: text to be tokenized
     * @return: tokenized text
     */
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

    /**
     * Mention grammar:
     * <mention> ::= '@' + <name>
     * <name> ::= <char> + <char>
     * <char> ::= any alphanumeric character | empty character
     * @param text text to be parsed
     * @return parsed result
     */
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

    /**
     * Title grammar:
     * <title> ::= 'title:' + <name>
     * <name> ::= <char> + <char>
     * <char> ::= any alphanumeric character | empty character
     * @param text text to be parsed
     * @return parsed result
     */
    public String parseTitle(String text) {
        Tokenizer tokenizer = new Tokenizer(text);
        Tokenizer.Token token;
        String title = null;

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

    /**
     * Public grammar:
     * <public> ::= 'public:' + <bool>
     * <bool> ::= 'true' | 'false'
     * @param text text to be parsed
     * @return parsed result
     */
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