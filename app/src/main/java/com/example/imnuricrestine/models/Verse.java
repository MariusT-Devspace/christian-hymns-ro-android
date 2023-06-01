package com.example.imnuricrestine.models;

public class Verse{
    private String lyrics;
    private String tag;

    public Verse(String lyrics, String tag){
        this.lyrics = escape(lyrics);
        this.tag = tag;
    }


    public String getLyrics(){
        return this.lyrics;
    }

    public String getTag(){ return this.tag; }

    private String escape(String lyrics) {
        // Escape special characters in the string.
        return lyrics.replace("'", "\\'");
    }
}
