package com.mtcnextlabs.imnuricrestine.models;

import java.util.ArrayList;

public class Hymn{
    private final int id;
    private final String index;
    private final String title;
    private final ArrayList<Verse> lyrics;
    private boolean isFavorite;

    public Hymn(
            int id,
            String index,
            String title,
            ArrayList<Verse> lyrics,
            boolean isFavorite
    ){
        this.id = id;
        this.index = index;
        this.title = title;
        this.lyrics = lyrics;
        this.isFavorite = isFavorite;
    }

    public int getId() { return this.id; }

    public String getIndex(){
        return this.index;
    }

    public String getTitle(){
        return this.title;
    }

    public ArrayList<Verse> getLyrics(){
        return this.lyrics;
    }

    public boolean getIsFavorite(){ return this.isFavorite; }

}
