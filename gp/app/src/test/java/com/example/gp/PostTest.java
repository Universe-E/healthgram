package com.example.gp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.example.gp.Items.Post;
import com.google.firebase.Timestamp;

import org.junit.Test;

import java.util.Calendar;
public class PostTest {
    @Test
    public void testPost() {
        Post post = new Post();
        post.setPostId("postId");
        post.setAuthorId("authorId");
        post.setPostContent("This is a test post.");
        post.setImgUUID("image-UUID");
        post.setPostTimestamp(new Timestamp(Calendar.getInstance().getTime()));

        assertEquals("postId", post.getPostId());
        assertEquals("authorId", post.getAuthorId());
        assertEquals("This is a test post.", post.getPostContent());
        assertEquals("image-UUID", post.getImgUUID());
        assertNotNull(post.getPostTimestamp());
    }

    @Test
    public void testPostConstructorWithContent() {
        Post post = new Post("content", "title", true);

        assertEquals("content", post.getPostContent());
        assertEquals("title", post.getTitle());
        assertTrue(post.isPublic());
    }

    @Test
    public void testPostSetters() {
        Post post = new Post();
        post.setPostId("postId");
        post.setAuthorId("authorId");
        post.setPostContent("content");
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
        assertEquals("content", post.getPostContent());
        assertEquals("title", post.getTitle());
        assertTrue(post.isPublic());
        assertNotNull(post.getPostTimestamp());
        assertEquals("authorName", post.getAuthorName());
        assertEquals(1, post.getImgId());
        assertEquals(10, post.getLikeCount());
        // assertEquals(createMockBitmap(), post.getImg());
        assertEquals("imageUUID", post.getImgUUID());
    }
}
