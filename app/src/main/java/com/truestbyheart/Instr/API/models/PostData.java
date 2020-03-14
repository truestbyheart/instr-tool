package com.truestbyheart.Instr.API.models;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class PostData implements Parcelable {
    @SerializedName("img_url")
    private String imgUrl;

    @SerializedName("video_url")
    private String videoUrl;

    public PostData(String imgUrl, String videoUrl) {
        this.imgUrl = imgUrl;
        this.videoUrl = videoUrl;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public PostData(Parcel parcel) {
        imgUrl = parcel.readString();
        videoUrl = parcel.readString();
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(imgUrl);
        dest.writeString(videoUrl);
    }

    public static Parcelable.Creator<PostData> CREATOR = new Parcelable.Creator<PostData>(){
        @Override
        public PostData createFromParcel(Parcel source) {
            return new PostData(source);
        }

        @Override
        public PostData[] newArray(int size) {
            return new PostData[0];
        }
    };
}
