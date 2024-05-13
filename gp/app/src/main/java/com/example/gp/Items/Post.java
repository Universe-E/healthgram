package com.example.gp.Items;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

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
public class Post implements Parcelable {
    public String postId;
    public String authorId;
    public String authorName;
    public String postContent;
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
     * @param postContent
     * @param title
     * @param img
     * @param isPublic
     */
    public Post(String postContent, String title, Bitmap img, boolean isPublic) {
        this.postContent = postContent;
        this.title = title;
        this.img = img;
        this.isPublic = isPublic;
    }

    /**
     * Please use this constructor when creating a new post.
     * @author Han Bao
     * @param postContent
     * @param title
     * @param isPublic
     */
    public Post(String postContent, String title, boolean isPublic) {
        this.postContent = postContent;
        this.title = title;
        this.isPublic = isPublic;
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
        this.authorName = postModel.getAuthorName();
        this.postContent = postModel.getContent();
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

    public String getPostContent() {
        return postContent;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;
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
                ", content='" + postContent + '\'' +
                ", title='" + title + '\'' +
                ", imgId=" + imgId +
                ", likeCount=" + likeCount +
                ", postTimestamp=" + postTimestamp +
                ", authorName='" + authorName + '\'' +
                ", imgUUID='" + imgUUID + '\'' +
                ", isPublic=" + isPublic +
                '}';
    }

    public static final Parcelable.Creator<Post> CREATOR = new Parcelable.Creator<Post>() {
        @Override
        public Post createFromParcel(Parcel in) {
            return new Post(in);
        }

        @Override
        public Post[] newArray(int size) {
            return new Post[size];
        }
    };

    protected Post(Parcel in) {
        postId = in.readString();
        authorId = in.readString();
        authorName = in.readString();
        postContent = in.readString();
        title = in.readString();
        imgId = in.readInt();
        img = in.readParcelable(Bitmap.class.getClassLoader());
        imgUUID = in.readString();
        likeCount = in.readInt();
        isPublic = in.readByte() != 0;
        postTimestamp = in.readParcelable(Timestamp.class.getClassLoader());
        viewers = in.createStringArrayList();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(this.postId);
        dest.writeString(this.authorId);
        dest.writeString(this.authorName);
        dest.writeString(this.postContent);
        dest.writeString(this.title);
        dest.writeInt(this.imgId);
        dest.writeParcelable(this.img, flags);
        dest.writeString(this.imgUUID);
        dest.writeInt(this.likeCount);
        dest.writeByte(this.isPublic ? (byte) 1 : (byte) 0);
        dest.writeParcelable(this.postTimestamp, flags);
        dest.writeStringList(this.viewers);
    }
}
