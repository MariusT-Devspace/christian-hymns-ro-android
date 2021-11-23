package com.example.imnuricrestine;

public class Hymn {
    private int index;
    private String title;
    private String lyrics;

    Hymn(int index, String title, String lyrics){
        this.index = index;
        this.title = title;
        this.lyrics = lyrics;
    }

    private void setIndex(int index){
        this.index = index;
    }

    private void setTitle(String title){
        this.title = title;
    }

    private void setLyrics(String lyrics){
        this.lyrics = lyrics;
    }

    protected int getIndex(){
        return index;
    }

    protected String getTitle(){
        return title;
    }

    protected String getLyrics(){
        return lyrics;
    }
}
