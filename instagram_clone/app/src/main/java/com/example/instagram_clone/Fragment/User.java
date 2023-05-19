package com.example.instagram_clone.Fragment;

public class User {

    private String id, UserName,Name,Bio,ImageUrl;

    public User(String id, String userName, String name, String bio, String imageUrl) {
        this.id = id;
        UserName = userName;
        Name = name;
        Bio = bio;
        ImageUrl = imageUrl;
    }

    public User() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getBio() {
        return Bio;
    }

    public void setBio(String bio) {
        Bio = bio;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }
}
