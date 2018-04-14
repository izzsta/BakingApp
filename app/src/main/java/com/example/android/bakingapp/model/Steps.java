package com.example.android.bakingapp.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by izzystannett on 10/04/2018.
 */

public class Steps implements Parcelable {

    private String mShortDescription;
    private String mDescription;
    private String mVideoUrl;

    public Steps(String shortDescription, String description, String videoUrl) {
        mShortDescription = shortDescription;
        mDescription = description;
        mVideoUrl = videoUrl;
    }

    //getter and setter methods
    public String getShortDescription() {
        return mShortDescription;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getVideoUrl() {
        return mVideoUrl;
    }

    public void setShortDescription(String description) {
        mShortDescription = description;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public void setVideoUrl(String videoUrl) {
        mVideoUrl = videoUrl;
    }

    //parcelable methods

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mShortDescription);
        parcel.writeString(mDescription);
        parcel.writeString(mVideoUrl);
    }

    public static final Parcelable.Creator<Steps> CREATOR = new Parcelable.Creator<Steps>() {
        @Override
        public Steps createFromParcel(Parcel in) {
            return new Steps(in);
        }

        @Override
        public Steps[] newArray(int i) {
            return new Steps[i];
        }
    };

    public Steps(Parcel parcel) {
        mShortDescription = parcel.readString();
        mDescription = parcel.readString();
        mVideoUrl = parcel.readString();
    }


}
