package com.example.gp.Items;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.ServerTimestamp;

/**
 * Post class
 * Each post has a unique identifier (postId), an author identifier (authorId),
 * content, title, and a flag indicating whether the post is public or private.
 * @author : Yulong Chen
 * @since : 2024-05-05
 */
public class Post {
    public String postId;
    public String authorId;
    public String mContent;
    public String title;
    public boolean isPublic;
    public Timestamp postTimestamp;
    @ServerTimestamp
    public Timestamp lastEditTimestamp;

    /**
     * Please use this constructor when creating a new post.
     * @author Han Bao
     * @param mContent
     * @param title
     * @param isPublic
     */
    public Post(String mContent, String title, boolean isPublic) {
        this.mContent = mContent;
        this.title = title;
        this.isPublic = isPublic;
    }

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

    /**
     * DO NOT DELETE THIS CONSTRUCTOR
     */
    public Post() {
    }

    public void setPublic(boolean isPublic){
        this.isPublic = isPublic;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getmContent() {
        return mContent;
    }

    public void setmContent(String mContent) {
        this.mContent = mContent;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public Timestamp getPostTimestamp() {
        return postTimestamp;
    }

    public void setPostTimestamp(Timestamp postTimestamp) {
        this.postTimestamp = postTimestamp;
    }

    public void setLastEditTimestamp(Timestamp lastEditTimestamp) {
        this.lastEditTimestamp = lastEditTimestamp;
    }

    public Timestamp getLastEditTimestamp() {
        return lastEditTimestamp;
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
