package com.example.gp;

import org.junit.Test;

import static org.junit.Assert.*;

import com.example.gp.data.Parser;

public class ParserTest {

    @Test
    public void notFinishedMentioning() {
        Parser parser = Parser.getInstance();
        assertTrue(parser.isMentioningSomeone("@user"));
    }

    @Test
    public void isMentioningSomeone_returnsFalse_whenAtSymbolFollowedBySpace() {
        Parser parser = Parser.getInstance();
        assertFalse(parser.isMentioningSomeone("@ user"));
    }

    @Test
    public void isMentioningSomeone_returnsFalse_whenNoAtSymbol() {
        Parser parser = Parser.getInstance();
        assertFalse(parser.isMentioningSomeone("user"));
    }

    @Test
    public void isMentioningSomeone_returnsTrue_whenAtSymbolIsLastCharacter() {
        Parser parser = Parser.getInstance();
        assertTrue(parser.isMentioningSomeone("user@"));
    }

    @Test
    public void isMentioningSomeone_returnsFalse_whenAtSymbolFollowedBySpaceAndCharacters() {
        Parser parser = Parser.getInstance();
        assertTrue(parser.isMentioningSomeone("@ user1@"));
    }

    @Test
    public void isMentioningSomeone_returnsFalse_whenAtSymbolFollowedBySpaceAndCharacters() {
        Parser parser = Parser.getInstance();
        assertTrue(parser.isMentioningSomeone("@ user1@"));
    }

    @Test
    public void isMentioningSomeone_returnsTrue_whenMultipleAtSymbolsFollowedByCharacters() {
        Parser parser = Parser.getInstance();
        assertTrue(parser.isMentioningSomeone("@user1@user2"));
    }

    @Test
    public void isMentioningSomeone_returnsFalse_whenMultipleAtSymbolsFollowedBySpaceAndCharacters() {
        Parser parser = Parser.getInstance();
        assertFalse(parser.isMentioningSomeone("@ user1 @ user2"));
    }
}