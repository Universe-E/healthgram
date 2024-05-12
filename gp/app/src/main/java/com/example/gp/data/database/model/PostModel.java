package com.example.gp.data.database.model;

import com.example.gp.Items.Post;
import com.google.firebase.Timestamp;

import java.util.List;

public class PostModel {
    private String postId;
    private String authorId;
    private String content;
    private String title;
    private String imgUUID;
    private int likeCount;
    private Timestamp postTimestamp;
    private boolean isPublic;
    private List<String> viewers;

    public PostModel() {
    }

    public void setModelFromPost(Post post) {
        this.postId = post.getPostId();
        this.authorId = post.getAuthorId();
        this.content = post.getmContent();
        this.title = post.getTitle();
        this.imgUUID = post.getImgUUID();
        this.likeCount = post.getLikeCount();
        this.postTimestamp = post.getPostTimestamp();
        this.isPublic = post.isPublic();
        this.viewers = post.getViewers();
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgUUID() {
        return imgUUID;
    }

    public void setImgUUID(String imgUUID) {
        this.imgUUID = imgUUID;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public Timestamp getPostTimestamp() {
        return postTimestamp;
    }

    public void setPostTimestamp(Timestamp postTimestamp) {
        this.postTimestamp = postTimestamp;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public List<String> getViewers() {
        return viewers;
    }

    public void setViewers(List<String> viewers) {
        this.viewers = viewers;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

}
