package com.example.imagegallery.model;

import com.google.gson.annotations.SerializedName;

public class Images {
    int likes;
    @SerializedName("largeImageURL")
    String imageURL;
    String user;
    String tags;

    public Images() {
    }

    public Images(int likes, String imageURL, String user, String tags) {
        this.likes = likes;
        this.imageURL = imageURL;
        this.user = user;
        this.tags = tags;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String gettags() {
        return tags;
    }

    public void settags(String downloads) {
        this.tags = downloads;
    }
}

