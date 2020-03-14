package com.truestbyheart.Instr.API.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class OwnerModel implements Parcelable {
    @SerializedName("is_verified")
    private Boolean isVerified;

    @SerializedName("profile_pic_url")
    private String profilePic;

    @SerializedName("username")
    private String username;

    @SerializedName("full_name")
    private String fullName;

    @SerializedName("is_private")
    private Boolean isPrivate;

    public OwnerModel(Boolean isVerified, String profilePic, String username, String fullName, Boolean isPrivate) {
        this.isVerified = isVerified;
        this.profilePic = profilePic;
        this.username = username;
        this.fullName = fullName;
        this.isPrivate = isPrivate;
    }

    public Boolean getVerified() {
        return isVerified;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public String getUsername() {
        return username;
    }

    public String getFullName() {
        return fullName;
    }

    public Boolean getPrivate() {
        return isPrivate;
    }

   public OwnerModel(Parcel parcel){
        username = parcel.readString();
        profilePic = parcel.readString();
        fullName =parcel.readString();
        isPrivate = parcel.readInt() == 1;
        isVerified =parcel.readInt() == 0;

   }

    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
       dest.writeString(username);
       dest.writeString(profilePic);
       dest.writeString(fullName);
       dest.writeInt(isPrivate ? 1 : 0);
       dest.writeInt(isVerified ? 1 : 0);

    }

    public static Parcelable.Creator<OwnerModel> CREATOR = new Parcelable.Creator<OwnerModel>(){
        @Override
        public OwnerModel createFromParcel(Parcel source) {
            return new OwnerModel(source);
        }

        @Override
        public OwnerModel[] newArray(int size) {
            return new OwnerModel[0];
        }
    };
}
