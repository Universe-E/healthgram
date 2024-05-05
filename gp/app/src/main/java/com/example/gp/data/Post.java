package com.example.gp.data;

/**
 * Post class
 * Each post has a unique identifier (postId), an author identifier (authorId),
 * content, title, and a flag indicating whether the post is public or private.
 * Author: Yulong Chen
 * Date: 2024-05-05
 */
public class Post {
    public String postId;
    public String authorId;
    public String mContent;
    public String title;
    public boolean isPublic;

    public Post(String postId, String authorId, String content, String title, boolean isPublic) {
        this.postId = postId;
        this.authorId = authorId;
        this.mContent = content;
        this.title = title;
        this.isPublic = isPublic;
    }
    public Post(String postId, String authorId, String content, String title){
        this.postId = postId;
        this.authorId = authorId;
        this.mContent = content;
        this.title = title;
        this.isPublic = true;
    }

    public void setPublic(boolean isPublic){
        this.isPublic = isPublic;
    }


    @Override
    public String toString() {
        return "Post{" +
                "postId='" + postId + '\'' +
                ", authorId='" + authorId + '\'' +
                ", content='" + mContent + '\'' +
                ", title='" + title + '\'' +
                ", isPublic=" + isPublic +
                '}';
    }
}
