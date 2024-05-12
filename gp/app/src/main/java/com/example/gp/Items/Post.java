package com.example.gp.Items;

import android.graphics.Bitmap;

import com.example.gp.data.database.model.PostModel;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.ServerTimestamp;

import java.io.Serializable;
import java.util.List;

/**
 * Post class
 * Each post has a unique identifier (postId), an author identifier (authorId),
 * content, title, and a flag indicating whether the post is public or private.
 * @author : Yulong Chen
 * @since : 2024-05-05
 */
public class Post implements Serializable {
    public String postId;
    public String authorId;
    public String authorName;
    public String mContent;
    public String title;
    public int imgId;
    public Bitmap img;
    public String imgUUID;
    public int likeCount;
    public boolean isPublic;
    public Timestamp postTimestamp;
    private List<String> viewers;

    /**
     * Please use this constructor when creating a new post.
     * @author Han Bao
     * @param mContent
     * @param title
     * @param img
     * @param isPublic
     */
    public Post(String mContent, String title, Bitmap img, boolean isPublic) {
        this.mContent = mContent;
        this.title = title;
        this.img = img;
        this.isPublic = isPublic;
    }

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

    /**
     * Set post from model
     * @param postModel
     */
    public void setFromModel(PostModel postModel){
        this.postId = postModel.getPostId();
        this.authorId = postModel.getAuthorId();
        this.mContent = postModel.getContent();
        this.title = postModel.getTitle();
        this.isPublic = postModel.isPublic();
        this.postTimestamp = postModel.getPostTimestamp();
        this.imgUUID = postModel.getImgUUID();
        this.likeCount = postModel.getLikeCount();
        this.viewers = postModel.getViewers();
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

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public Bitmap getImg() {
        return img;
    }

    public void setImg(Bitmap img) {
        this.img = img;
    }

    public String getImgUUID() {
        return imgUUID;
    }

    public void setImgUUID(String imgUUID) {
        this.imgUUID = imgUUID;
    }

    public List<String> getViewers() {
        return viewers;
    }

    public void setViewers(List<String> viewers) {
        this.viewers = viewers;
    }

    @Override
    public String toString() {
        return "Post{" +
                "postId='" + postId + '\'' +
                ", authorId='" + authorId + '\'' +
                ", content='" + mContent + '\'' +
                ", title='" + title + '\'' +
                ", imgId=" + imgId +
                ", likeCount=" + likeCount +
                ", postTimestamp=" + postTimestamp +
                ", authorName='" + authorName + '\'' +
                ", imgUUID='" + imgUUID + '\'' +
                ", isPublic=" + isPublic +
                '}';
    }
}
