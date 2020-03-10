package com.truestbyheart.Instr.API.models;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.RequiresApi;

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

    @RequiresApi(api = Build.VERSION_CODES.Q)
   public OwnerModel(Parcel parcel){
        username = parcel.readString();
        profilePic = parcel.readString();
        fullName =parcel.readString();
        isPrivate = parcel.readBoolean();
        isVerified =parcel.readBoolean();

   }

    @Override
    public int describeContents() {
        return hashCode();
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void writeToParcel(Parcel dest, int flags) {
       dest.writeString(username);
       dest.writeString(profilePic);
       dest.writeString(fullName);
       dest.writeBoolean(isPrivate);
       dest.writeBoolean(isVerified);

    }

    public static Parcelable.Creator<OwnerModel> CREATOR = new Parcelable.Creator<OwnerModel>(){
        @RequiresApi(api = Build.VERSION_CODES.Q)
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
