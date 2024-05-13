package com.example.gp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.example.gp.Items.Friend;
import com.example.gp.Items.FriendRequest;
import com.google.firebase.Timestamp;

import org.junit.Test;

import java.util.Calendar;

public class FriendTest {
    @Test
    public void testFriendRequest() {
        FriendRequest request = new FriendRequest("receiverId");
        request.setSenderId("senderId");
        request.setRead(false);
        request.setAccepted(true);
        request.setRequestTimestamp(new Timestamp(Calendar.getInstance().getTime()));

        assertEquals("receiverId", request.getReceiverId());
        assertEquals("senderId", request.getSenderId());
        assertFalse(request.isRead());
        assertTrue(request.isAccepted());
        assertNotNull(request.getRequestTimestamp());
    }

    @Test
    public void testFriendConstructor() {
        Friend friend = new Friend("friendId", "nickname", 1);

        assertEquals("friendId", friend.getId());
        assertEquals("nickname", friend.getNickname());
        assertEquals(1, friend.getAvatar());
    }

    @Test
    public void testFriendSetters() {
        Friend friend = new Friend();
        friend.setId("friendId");
        friend.setNickname("nickname");
        friend.setAvatar(1);

        assertEquals("friendId", friend.getId());
        assertEquals("nickname", friend.getNickname());
        assertEquals(1, friend.getAvatar());
    }

    @Test
    public void testFriendGetters() {
        Friend friend = new Friend("friendId", "nickname", 1);

        assertEquals("nickname", friend.getName());
        assertEquals(1, friend.getAvatar());
    }
}
