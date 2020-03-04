package com.truestbyheart.instr_tool.API.models;

import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Array;

public class PostModel {
    @SerializedName("post_text")
    private String postText;

    @SerializedName("img_url")
    private String imgURL;

    @SerializedName("img_urls")
    private Array imgURLs;

    @SerializedName("video_url")
    private String videoURL;

    public PostModel(String postText, String imgURL, Array imgURLs, String videoURL) {
        this.postText = postText;
        this.imgURL = imgURL;
        this.imgURLs = imgURLs;
        this.videoURL = videoURL;
    }

    public String getPostText() {
        return postText;
    }

    public void setPostText(String postText) {
        this.postText = postText;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public Array getImgURLs() {
        return imgURLs;
    }

    public void setImgURLs(Array imgURLs) {
        this.imgURLs = imgURLs;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }
}
