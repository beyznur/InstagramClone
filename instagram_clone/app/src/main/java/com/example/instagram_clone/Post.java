package com.example.instagram_clone;

public class Post {
    private String postId;

    private String userId;

    private String photoUrl;

    private String description;

    private String timestamp;

    public Post(String postId, String userId, String photoUrl, String description, String timestamp) {
        this.postId = postId;
        this.userId = userId;
        this.photoUrl = photoUrl;
        this.description = description;
        this.timestamp = timestamp;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}