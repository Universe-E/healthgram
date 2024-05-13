package com.example.gp;

import com.example.gp.Utils.AuthUtil;
import com.example.gp.Utils.TimeUtil;

import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;

public class UtilTest {

    @Test
    public void testAuthUtil() {
        assertTrue(AuthUtil.isValidEmail("user@example.com"));
        assertFalse(AuthUtil.isValidEmail("userexample.com"));

        assertTrue(AuthUtil.isValidUsername("username"));
        assertFalse(AuthUtil.isValidUsername("user name"));
        assertFalse(AuthUtil.isValidUsername("us"));
        assertFalse(AuthUtil.isValidUsername("username_too_long_more_than_18"));
    }

    @Test
    public void testAuthUtil_EmailVariations() {
        assertTrue(AuthUtil.isValidEmail("user@example.co.uk"));
        assertTrue(AuthUtil.isValidEmail("user.name@example.com"));
        assertFalse(AuthUtil.isValidEmail("user+name@example.com"));
        assertFalse(AuthUtil.isValidEmail("user@example"));
        assertFalse(AuthUtil.isValidEmail("@example.com"));
    }

    @Test
    public void testAuthUtil_UsernameVariations() {
        assertTrue(AuthUtil.isValidUsername("username123"));
        assertTrue(AuthUtil.isValidUsername("user_name"));
        assertTrue(AuthUtil.isValidUsername("USERNAME"));
        assertTrue(AuthUtil.isValidUsername("user-name"));
        assertTrue(AuthUtil.isValidUsername("user.name"));
    }

    @Test
    public void testAuthUtil_EmailSubDomain() {
        assertTrue(AuthUtil.isValidEmail("user@subdomain.example.com"));
        assertTrue(AuthUtil.isValidEmail("user@subdomain.example.co.uk"));
    }

    @Test
    public void testAuthUtil_isEmail() {
        assertTrue(AuthUtil.isEmail("user@example.com"));
        assertFalse(AuthUtil.isEmail("username"));
    }

    @Test
    public void testTimeUtil_getTimestamp() {
        assertNotNull(TimeUtil.getTimestamp());
    }

}