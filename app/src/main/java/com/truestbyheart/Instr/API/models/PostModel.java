package com.truestbyheart.Instr.API.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Array;

public class PostModel implements Parcelable{
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

    public PostModel(Parcel parcel){
        imgURL = parcel.readString();
        postText = parcel.readString();
        videoURL = parcel.readString();
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(imgURL);
        dest.writeString(postText);
        dest.writeString(videoURL);
    }

    public static final Parcelable.Creator<PostModel> CREATOR = new Parcelable.Creator<PostModel>(){
        @Override
        public PostModel createFromParcel(Parcel source) {
            return new PostModel(source);
        }

        @Override
        public PostModel[] newArray(int size) {
            return new PostModel[0];
        }
    };
}
