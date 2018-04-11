package com.example.android.bakingapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by izzystannett on 10/04/2018.
 */

public class Steps implements Serializable{

        private String mShortDescription;
        private String mDescription;
        private String mVideoUrl;

        public Steps(String shortDescription, String description, String videoUrl){
            mShortDescription = shortDescription;
            mDescription = description;
            mVideoUrl = videoUrl;
        }

        //getter and setter methods
        public String getShortDescription(){
            return mShortDescription;
        }

        public String getDescription(){
            return mDescription;
        }

        public String getVideoUrl(){
            return mVideoUrl;
        }

        public void setShortDescription(String description){
            mShortDescription = description;
        }
        public void setDescription(String description){
            mDescription = description;
        }
        public void setVideoUrl(String videoUrl){
            mVideoUrl = videoUrl;
        }

        //parcelable methods
   /* public Steps(Parcel in){
            in.writeString(mShortDescription);
            in.writeString(mDescription);
            in.writeString(mVideoUrl);
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeString(mShortDescription);
        dest.writeString(mDescription);
        dest.writeString(mVideoUrl);
    }*/
}
