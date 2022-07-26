package com.example.imnuricrestine.objects;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class Hymn implements Parcelable {
    private short index;
    private String title;
    private ArrayList<Verse> lyrics;

    public Hymn(short index, String title, ArrayList<Verse> lyrics){
        this.index = index;
        this.title = title;
        this.lyrics = lyrics;
    }

    protected Hymn(Parcel in) {
        index = (short) in.readInt();
        title = in.readString();
        lyrics = in.readArrayList(getClass().getClassLoader());
    }

    public short getIndex(){
        return this.index;
    }

    public String getTitle(){
        return this.title;
    }

    public ArrayList<Verse> getLyrics(){
        return this.lyrics;
    }

    public static final Creator<Hymn> CREATOR = new Creator<Hymn>() {
        @Override
        public Hymn createFromParcel(Parcel in) {
            return new Hymn(in);
        }

        @Override
        public Hymn[] newArray(int size) {
            return new Hymn[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeInt(index);
        parcel.writeString(title);
        parcel.writeArray(lyrics.toArray());
    }
}
