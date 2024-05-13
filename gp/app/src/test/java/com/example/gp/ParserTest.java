package com.example.gp;

import com.example.gp.Items.Parser;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test parser class
 * @author Zehua Kong
 */
public class ParserTest {

    @Test
    public void isMentioningSomeone_returnsTrue_whenTextContainsAtSymbol() {
        Parser parser = Parser.getInstance();
        assertTrue(parser.isMentioningSomeone("@user"));
    }

    @Test
    public void isMentioningSomeone_returnsFalse_whenTextDoesNotContainAtSymbol() {
        Parser parser = Parser.getInstance();
        assertFalse(parser.isMentioningSomeone("user"));
    }

    @Test
    public void parseUserName_returnsUserName_whenTextContainsAtSymbol() {
        Parser parser = Parser.getInstance();
        assertEquals("user", parser.parseUserName("@user"));
    }

    @Test
    public void parseUserName_returnsEmptyString_whenTextDoesNotContainAtSymbol() {
        Parser parser = Parser.getInstance();
        assertEquals("", parser.parseUserName("user"));
    }

    @Test
    public void parseTitle_returnsTitle_whenTextContainsTitleColon() {
        Parser parser = Parser.getInstance();
        assertEquals("hello", parser.parseTitle("title:hello"));
    }

    @Test
    public void parseTitle_returnsEmptyString_whenTextDoesNotContainTitleColon() {
        Parser parser = Parser.getInstance();
        assertEquals("", parser.parseTitle("hello"));
    }

    @Test
    public void parsePublic_returnsTrue_whenTextContainsPublicColonTrue() {
        Parser parser = Parser.getInstance();
        assertEquals(true, parser.parsePublic("public:true"));
    }

    @Test
    public void parsePublic_returnsFalse_whenTextContainsPublicColonFalse() {
        Parser parser = Parser.getInstance();
        assertEquals(false, parser.parsePublic("public:false"));
    }

    @Test
    public void parsePublic_returnsNull_whenTextDoesNotContainPublicColon() {
        Parser parser = Parser.getInstance();
        assertNull(parser.parsePublic("hello"));
    }
}