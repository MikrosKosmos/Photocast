package com.pm.estrello.cirro.Objects;

public class Comment {
    private int id;
    private int postId;
    private String firstName, lastName;
    private int userId;
    private String role;
    private String comment;

    public Comment(int id, int postId, String firstName, String lastName, int userId, String role, String comment) {
        this.id = id;
        this.postId = postId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userId = userId;
        this.role = role;
        this.comment = comment;
    }

    public Comment() {
        //Default Constructor.
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
