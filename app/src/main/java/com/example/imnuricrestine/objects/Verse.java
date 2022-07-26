package com.example.imnuricrestine.objects;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Verse implements Parcelable {
    private String lyrics;
    private boolean isChorus;

    public Verse(String lyrics, boolean isChorus){
        this.lyrics = lyrics;
        this.isChorus = isChorus;
    }

    protected Verse(Parcel in) {
        lyrics = in.readString();
        isChorus = in.readByte() != 0;
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

    public boolean isChorus(){
        return this.isChorus;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(lyrics);
        parcel.writeByte((byte) (isChorus ? 1 : 0));
    }
}
