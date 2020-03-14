package com.truestbyheart.Instr.API.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Array;
import java.util.List;

public class PostModel implements Parcelable{
    @SerializedName("post_text")
    private String postText;

    @SerializedName("img_url")
    private String imgURL;

    @SerializedName("img_urls")
    private Array imgURLs;

    @SerializedName("video_url")
    private String videoURL;

    @SerializedName("owner")
    private OwnerModel owner;

    @SerializedName("post_data")
    private List<PostData> postData;

    public PostModel(String postText, String imgURL, Array imgURLs, String videoURL, OwnerModel owner, List<PostData> postData) {
        this.postText = postText;
        this.imgURL = imgURL;
        this.imgURLs = imgURLs;
        this.videoURL = videoURL;
        this.owner = owner;
        this.postData = postData;
    }

    public String getPostText() {
        return postText;
    }

    public String getImgURL() {
        return imgURL;
    }

    public Array getImgURLs() {
        return imgURLs;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public OwnerModel getOwner() {
        return owner;
    }

    public List<PostData> getPostData() {
        return postData;
    }

    public PostModel(Parcel parcel){
        imgURL = parcel.readString();
        postText = parcel.readString();
        videoURL = parcel.readString();
        owner =  parcel.readParcelable(OwnerModel.class.getClassLoader());
        postData = (List<PostData>) parcel.readValue(PostData.class.getClassLoader());

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
        dest.writeParcelable(owner, flags);
        dest.writeValue(postData);
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
