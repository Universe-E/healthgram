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
}