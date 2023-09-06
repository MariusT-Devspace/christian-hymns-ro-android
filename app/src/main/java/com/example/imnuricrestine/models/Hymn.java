package com.example.imnuricrestine.models;

import java.util.ArrayList;

public class Hymn{
    //private short index;
    private final String index;
    private final String title;
    private final ArrayList<Verse> lyrics;

    public Hymn(String index, String title, ArrayList<Verse> lyrics){
        this.index = index;
        this.title = title;
        this.lyrics = lyrics;
    }

    public String getIndex(){
        return this.index;
    }

    public String getTitle(){
        return this.title;
    }

    public ArrayList<Verse> getLyrics(){
        return this.lyrics;
    }

}
