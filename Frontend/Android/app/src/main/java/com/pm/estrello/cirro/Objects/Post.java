package com.pm.estrello.cirro.Objects;

public class Post {
    private int id;
    private String userName;
    private String description;
    private int likesCount;
    private String imageURL;

    public Post(int id, String userName, String description, int likesCount, String imageURL) {
        this.id = id;
        this.userName = userName;
        this.description = description;
        this.likesCount = likesCount;
        this.imageURL = imageURL;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}