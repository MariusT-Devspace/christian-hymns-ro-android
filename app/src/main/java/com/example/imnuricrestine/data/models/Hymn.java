package com.example.imnuricrestine.data.models;

import java.util.ArrayList;

public class Hymn{
    //private short index;
    private String index;
    private String title;
    private ArrayList<Verse> lyrics;

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
