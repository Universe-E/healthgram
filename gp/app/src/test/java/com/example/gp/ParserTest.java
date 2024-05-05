package com.example.gp;

import org.junit.Test;

import static org.junit.Assert.*;

import com.example.gp.data.Parser;

public class ParserTest {

    @Test
    public void notYetFinishedMentioning() {
        Parser parser = Parser.getInstance();
        assertTrue(parser.isMentioningSomeone("@user"));
    }

    @Test
    public void finishedMentioning_1() {
        Parser parser = Parser.getInstance();
        assertFalse(parser.isMentioningSomeone("@ user"));
    }

    @Test
    public void notMentioning() {
        Parser parser = Parser.getInstance();
        assertFalse(parser.isMentioningSomeone("user"));
    }

    @Test
    public void symbolAtTextEnd() {
        Parser parser = Parser.getInstance();
        assertTrue(parser.isMentioningSomeone("user@"));
    }

    @Test
    public void mentioningTheSecondGuyAtStart() {
        Parser parser = Parser.getInstance();
        assertTrue(parser.isMentioningSomeone("@ user1@"));
    }

    @Test
    public void mentioningTheSecondGuyNotFinished() {
        Parser parser = Parser.getInstance();
        assertTrue(parser.isMentioningSomeone("@user1@user2"));
    }

    @Test
    public void mentioningTheSecondGuyFinished() {
        Parser parser = Parser.getInstance();
        assertFalse(parser.isMentioningSomeone("@user1 @user2 "));
    }

    @Test
    public void parseUserName_returnsEmpty_whenNoAtSymbol() {
        Parser parser = Parser.getInstance();
        assertEquals("", parser.parseUserName("user"));
    }

    @Test
    public void parseUserName_returnsSubstring_whenAtSymbolIsLast() {
        Parser parser = Parser.getInstance();
        assertEquals("user", parser.parseUserName("hello@user"));
    }

    @Test
    public void parseUserName_returnsSubstring_whenAtSymbolFollowedBySpace() {
        Parser parser = Parser.getInstance();
        assertEquals("user", parser.parseUserName("hello@user moretext"));
    }

    @Test
    public void parseUserName_returnsSubstring_whenMultipleAtSymbols() {
        Parser parser = Parser.getInstance();
        assertEquals("user2", parser.parseUserName("hello@user1 moretext@user2"));
    }

    @Test
    public void parseUserName_returnsSubstring_whenMultipleAtSymbolsFollowedBySpace() {
        Parser parser = Parser.getInstance();
        assertEquals("user2", parser.parseUserName("hello@user1 moretext@user2 moretext"));
    }

}