package com.example.imnuricrestine.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Verse implements Parcelable {
    private String lyrics;
    private String tag;

    public Verse(String lyrics, String tag){
        this.lyrics = lyrics;
        this.tag = tag;
    }

    protected Verse(Parcel in) {
        lyrics = in.readString();
        tag = in.readString();
    }

    public static final Creator<Verse> CREATOR = new Creator<Verse>() {
        @Override
        public Verse createFromParcel(Parcel in) {
            return new Verse(in);
        }

        @Override
        public Verse[] newArray(int size) {
            return new Verse[size];
        }
    };

    public String getLyrics(){
        return this.lyrics;
    }

    public String getTag(){ return this.tag; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(lyrics);
        parcel.writeString(tag);
    }
}
