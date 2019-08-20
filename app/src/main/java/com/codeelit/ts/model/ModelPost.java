package com.codeelit.ts.model;

public class ModelPost {
    String postKey, title, description, picture, timeStamp, userId, userEmail, userName;

    public ModelPost(String postKey, String title, String description, String picture, String timeStamp, String userId, String userEmail, String userName) {
        this.postKey = postKey;
        this.title = title;
        this.description = description;
        this.picture = picture;
        this.timeStamp = timeStamp;
        this.userId = userId;
        this.userEmail = userEmail;
        this.userName = userName;
    }

    public String getPostKey() {
        return postKey;
    }

    public void setPostKey(String postKey) {
        this.postKey = postKey;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
