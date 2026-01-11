package com.mtcnextlabs.imnuricrestine.models;

import java.util.ArrayList;

public class Hymn {
    private final int id;
    private final String number;
    private final String title;
    private final ArrayList<Verse> lyrics;
    private final boolean isFavorite;

    public Hymn(
            int id,
            String number,
            String title,
            ArrayList<Verse> lyrics,
            boolean isFavorite
    ){
        this.id = id;
        this.number = number;
        this.title = title;
        this.lyrics = lyrics;
        this.isFavorite = isFavorite;
    }

    public int getId() { return this.id; }

    public String getNumber(){
        return this.number;
    }

    public String getTitle(){
        return this.title;
    }

    public ArrayList<Verse> getLyrics(){
        return this.lyrics;
    }

    public boolean getIsFavorite(){ return this.isFavorite; }

}
