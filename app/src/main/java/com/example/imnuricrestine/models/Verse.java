package com.example.imnuricrestine.models;

public class Verse{
    private final String lyrics;
    private final String tag;
    private final boolean isChorus;

    public Verse(String lyrics, String tag, boolean isChorus){
        this.lyrics = escape(lyrics);
        this.tag = tag;
        this.isChorus = isChorus;
    }


    public String getLyrics(){
        return this.lyrics;
    }

    public String getTag(){ return this.tag; }

    public boolean isChorus(){ return this.isChorus; }

    private String escape(String lyrics) {
        // Escape special characters in the string.
        return lyrics.replace("'", "\\'");
    }
}
