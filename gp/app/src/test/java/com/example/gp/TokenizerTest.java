package com.example.gp;

import org.junit.Test;
import static org.junit.Assert.*;

import com.example.gp.Items.Tokenizer;

public class TokenizerTest {

    @Test
    public void nextToken_returnsAT_whenAtSymbol() {
        Tokenizer tokenizer = new Tokenizer("@");
        Tokenizer.Token token = tokenizer.nextToken();
        assertEquals(Tokenizer.Token.Type.AT, token.type);
        assertEquals("@", token.value);
    }

    @Test
    public void nextToken_returnsUSERNAME_whenValidUsername() {
        Tokenizer tokenizer = new Tokenizer("user");
        Tokenizer.Token token = tokenizer.nextToken();
        assertEquals(Tokenizer.Token.Type.USERNAME, token.type);
        assertEquals("user", token.value);
    }

    @Test
    public void nextToken_returnsUSERNAME_whenUsernameContainsNumbers() {
        Tokenizer tokenizer = new Tokenizer("user123");
        Tokenizer.Token token = tokenizer.nextToken();
        assertEquals(Tokenizer.Token.Type.USERNAME, token.type);
        assertEquals("user123", token.value);
    }

    @Test
    public void nextToken_returnsUSERNAME_whenUsernameContainsUnderscores() {
        Tokenizer tokenizer = new Tokenizer("user_name");
        Tokenizer.Token token = tokenizer.nextToken();
        assertEquals(Tokenizer.Token.Type.USERNAME, token.type);
        assertEquals("user_name", token.value);
    }

    @Test
    public void nextToken_returnsMultipleTokens_whenValidInput() {
        Tokenizer tokenizer = new Tokenizer("@user1 @user2");
        Tokenizer.Token token1 = tokenizer.nextToken();
        assertEquals(Tokenizer.Token.Type.AT, token1.type);
        assertEquals("@", token1.value);

        Tokenizer.Token token2 = tokenizer.nextToken();
        assertEquals(Tokenizer.Token.Type.USERNAME, token2.type);
        assertEquals("user1", token2.value);

        Tokenizer.Token token3 = tokenizer.nextToken();
        assertEquals(Tokenizer.Token.Type.AT, token3.type);
        assertEquals("@", token3.value);

        Tokenizer.Token token4 = tokenizer.nextToken();
        assertEquals(Tokenizer.Token.Type.USERNAME, token4.type);
        assertEquals("user2", token4.value);

        Tokenizer.Token token5 = tokenizer.nextToken();
        assertEquals(Tokenizer.Token.Type.EOF, token5.type);
        assertNull(token5.value);
    }

    @Test
    public void nextToken_skipsWhitespace() {
        Tokenizer tokenizer = new Tokenizer("  @user  ");
        Tokenizer.Token token1 = tokenizer.nextToken();
        assertEquals(Tokenizer.Token.Type.AT, token1.type);
        assertEquals("@", token1.value);

        Tokenizer.Token token2 = tokenizer.nextToken();
        assertEquals(Tokenizer.Token.Type.USERNAME, token2.type);
        assertEquals("user", token2.value);

        Tokenizer.Token token3 = tokenizer.nextToken();
        assertEquals(Tokenizer.Token.Type.EOF, token3.type);
        assertNull(token3.value);
    }

    @Test
    public void nextToken_returnsEOF_whenInputIsEmpty() {
        Tokenizer tokenizer = new Tokenizer("");
        Tokenizer.Token token = tokenizer.nextToken();
        assertEquals(Tokenizer.Token.Type.EOF, token.type);
        assertNull(token.value);
    }
}