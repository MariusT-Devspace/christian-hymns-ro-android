package com.example.imnuricrestine.data.models;

import java.util.ArrayList;

public class Hymn{
    private short index;
    private String title;
    private ArrayList<Verse> lyrics;

    public Hymn(short index, String title, ArrayList<Verse> lyrics){
        this.index = index;
        this.title = title;
        this.lyrics = lyrics;
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

}
