package com.example.gp;

import com.example.gp.Items.Friend;
import com.example.gp.Items.FriendRequest;
import com.example.gp.Items.Notification;
import com.example.gp.Items.Post;
import com.example.gp.Items.User;
import com.example.gp.Utils.AuthUtil;
import com.example.gp.Utils.TimeUtil;
import com.google.firebase.Timestamp;

import org.junit.Test;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class ModelAndUtilUnitTest {

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
    public void testUser() {
        User user = new User();
        user.setUserId("userId");
        user.setUsername("username");
        user.setEmail("user@example.com");

        assertEquals("userId", user.getUserId());
        assertEquals("username", user.getUsername());
        assertEquals("user@example.com", user.getEmail());
    }

    @Test
    public void testUserConstructorWithFullParams() {
        Map<String, Friend> friendMap = new HashMap<>();
        Map<String, Post> postMap = new HashMap<>();
        Map<String, Notification> notificationMap = new HashMap<>();

        User user = new User("userId", "username", "user@example.com", "description",
                "avatar", friendMap, postMap, notificationMap);

        assertEquals("userId", user.getUserId());
        assertEquals("username", user.getUsername());
        assertEquals("user@example.com", user.getEmail());
        assertEquals("description", user.getDescription());
        assertEquals("avatar", user.getAvatar());
        assertEquals(friendMap, user.getFriendMap());
        assertEquals(postMap, user.getPostMap());
        assertEquals(notificationMap, user.getNotificationMap());
    }

    @Test
    public void testUserConstructorWithRequiredParams() {
        User user = new User("userId", "username", "user@example.com");

        assertEquals("userId", user.getUserId());
        assertEquals("username", user.getUsername());
        assertEquals("user@example.com", user.getEmail());
        assertEquals("set description", user.getDescription());
        assertEquals("set avatar", user.getAvatar());
        assertNotNull(user.getFriendMap());
        assertNotNull(user.getPostMap());
        assertNotNull(user.getNotificationMap());
    }

    @Test
    public void testPost() {
        Post post = new Post();
        post.setPostId("postId");
        post.setAuthorId("authorId");
        post.setmContent("This is a test post.");
        post.setImgUUID("image-UUID");
        post.setPostTimestamp(new Timestamp(Calendar.getInstance().getTime()));

        assertEquals("postId", post.getPostId());
        assertEquals("authorId", post.getAuthorId());
        assertEquals("This is a test post.", post.getmContent());
        assertEquals("image-UUID", post.getImgUUID());
        assertNotNull(post.getPostTimestamp());
    }

    @Test
    public void testPostConstructorWithContent() {
        Post post = new Post("content", "title", true);

        assertEquals("content", post.getmContent());
        assertEquals("title", post.getTitle());
        assertTrue(post.isPublic());
    }

    @Test
    public void testPostConstructorWithPostIdAndAuthorId() {
        Post post = new Post("postId", "authorId", "content", "title", true);

        assertEquals("postId", post.getPostId());
        assertEquals("authorId", post.getAuthorId());
        assertEquals("content", post.getmContent());
        assertEquals("title", post.getTitle());
        assertTrue(post.isPublic());
    }

    @Test
    public void testPostSetters() {
        Post post = new Post();
        post.setPostId("postId");
        post.setAuthorId("authorId");
        post.setmContent("content");
        post.setTitle("title");
        post.setIsPublic(true);
        post.setPostTimestamp(new Timestamp(Calendar.getInstance().getTime()));
        post.setAuthorName("authorName");
        post.setImgId(1);
        post.setLikeCount(10);
        // post.setImg(createMockBitmap());
        post.setImgUUID("imageUUID");

        assertEquals("postId", post.getPostId());
        assertEquals("authorId", post.getAuthorId());
        assertEquals("content", post.getmContent());
        assertEquals("title", post.getTitle());
        assertTrue(post.isPublic());
        assertNotNull(post.getPostTimestamp());
        assertEquals("authorName", post.getAuthorName());
        assertEquals(1, post.getImgId());
        assertEquals(10, post.getLikeCount());
        // assertEquals(createMockBitmap(), post.getImg());
        assertEquals("imageUUID", post.getImgUUID());
    }

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
    public void testTimeUtil() {
        assertNotNull(TimeUtil.getTimestamp());
    }

}