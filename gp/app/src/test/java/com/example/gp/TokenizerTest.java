package com.example.gp;

import com.example.gp.Items.Tokenizer;
import org.junit.Test;
import static org.junit.Assert.*;

public class TokenizerTest {

    @Test
    public void nextToken_returnsAT_whenAtSymbol() {
        Tokenizer tokenizer = new Tokenizer("@");
        Tokenizer.Token token = tokenizer.nextToken();
        assertEquals(Tokenizer.Token.Type.AT, token.type);
        assertEquals("@", token.value);
    }

    @Test
    public void nextToken_returnsTITLE_whenTitleColonSymbol() {
        Tokenizer tokenizer = new Tokenizer("title:");
        Tokenizer.Token token = tokenizer.nextToken();
        assertEquals(Tokenizer.Token.Type.TITLE, token.type);
        assertEquals("title:", token.value);
    }

    @Test
    public void nextToken_returnsPUBLIC_whenPublicColonSymbol() {
        Tokenizer tokenizer = new Tokenizer("public:");
        Tokenizer.Token token = tokenizer.nextToken();
        assertEquals(Tokenizer.Token.Type.PUBLIC, token.type);
        assertEquals("public:", token.value);
    }

    @Test
    public void nextToken_returnsNAME_whenAlphabeticCharacters() {
        Tokenizer tokenizer = new Tokenizer("hello");
        Tokenizer.Token token = tokenizer.nextToken();
        assertEquals(Tokenizer.Token.Type.NAME, token.type);
        assertEquals("hello", token.value);
    }

    @Test
    public void nextToken_returnsNAME_whenAlphanumericCharacters() {
        Tokenizer tokenizer = new Tokenizer("hello123");
        Tokenizer.Token token = tokenizer.nextToken();
        assertEquals(Tokenizer.Token.Type.NAME, token.type);
        assertEquals("hello123", token.value);
    }

    @Test
    public void nextToken_returnsNAME_afterTITLE() {
        Tokenizer tokenizer = new Tokenizer("title:hello");
        Tokenizer.Token token1 = tokenizer.nextToken();
        assertEquals(Tokenizer.Token.Type.TITLE, token1.type);
        assertEquals("title:", token1.value);

        Tokenizer.Token token2 = tokenizer.nextToken();
        assertEquals(Tokenizer.Token.Type.NAME, token2.type);
        assertEquals("hello", token2.value);
    }

    @Test
    public void nextToken_returnsNAME_afterPUBLIC() {
        Tokenizer tokenizer = new Tokenizer("public:true");
        Tokenizer.Token token1 = tokenizer.nextToken();
        assertEquals(Tokenizer.Token.Type.PUBLIC, token1.type);
        assertEquals("public:", token1.value);

        Tokenizer.Token token2 = tokenizer.nextToken();
        assertEquals(Tokenizer.Token.Type.NAME, token2.type);
        assertEquals("true", token2.value);
    }

    @Test
    public void nextToken_returnsMultipleTokens_whenValidInput() {
        Tokenizer tokenizer = new Tokenizer("@user title:hello public:true");
        Tokenizer.Token token1 = tokenizer.nextToken();
        assertEquals(Tokenizer.Token.Type.AT, token1.type);
        assertEquals("@", token1.value);

        Tokenizer.Token token2 = tokenizer.nextToken();
        assertEquals(Tokenizer.Token.Type.NAME, token2.type);
        assertEquals("user", token2.value);

        Tokenizer.Token token3 = tokenizer.nextToken();
        assertEquals(Tokenizer.Token.Type.TITLE, token3.type);
        assertEquals("title:", token3.value);

        Tokenizer.Token token4 = tokenizer.nextToken();
        assertEquals(Tokenizer.Token.Type.NAME, token4.type);
        assertEquals("hello", token4.value);

        Tokenizer.Token token5 = tokenizer.nextToken();
        assertEquals(Tokenizer.Token.Type.PUBLIC, token5.type);
        assertEquals("public:", token5.value);

        Tokenizer.Token token6 = tokenizer.nextToken();
        assertEquals(Tokenizer.Token.Type.NAME, token6.type);
        assertEquals("true", token6.value);

        Tokenizer.Token token7 = tokenizer.nextToken();
        assertEquals(Tokenizer.Token.Type.EOF, token7.type);
        assertNull(token7.value);
    }

    @Test
    public void nextToken_skipsWhitespace() {
        Tokenizer tokenizer = new Tokenizer("  @user  title:hello  public:true  ");
        Tokenizer.Token token1 = tokenizer.nextToken();
        assertEquals(Tokenizer.Token.Type.AT, token1.type);
        assertEquals("@", token1.value);

        Tokenizer.Token token2 = tokenizer.nextToken();
        assertEquals(Tokenizer.Token.Type.NAME, token2.type);
        assertEquals("user", token2.value);

        Tokenizer.Token token3 = tokenizer.nextToken();
        assertEquals(Tokenizer.Token.Type.TITLE, token3.type);
        assertEquals("title:", token3.value);

        Tokenizer.Token token4 = tokenizer.nextToken();
        assertEquals(Tokenizer.Token.Type.NAME, token4.type);
        assertEquals("hello", token4.value);

        Tokenizer.Token token5 = tokenizer.nextToken();
        assertEquals(Tokenizer.Token.Type.PUBLIC, token5.type);
        assertEquals("public:", token5.value);

        Tokenizer.Token token6 = tokenizer.nextToken();
        assertEquals(Tokenizer.Token.Type.NAME, token6.type);
        assertEquals("true", token6.value);

        Tokenizer.Token token7 = tokenizer.nextToken();
        assertEquals(Tokenizer.Token.Type.EOF, token7.type);
        assertNull(token7.value);
    }

    @Test
    public void nextToken_returnsEOF_whenInputIsEmpty() {
        Tokenizer tokenizer = new Tokenizer("");
        Tokenizer.Token token = tokenizer.nextToken();
        assertEquals(Tokenizer.Token.Type.EOF, token.type);
        assertNull(token.value);
    }
}