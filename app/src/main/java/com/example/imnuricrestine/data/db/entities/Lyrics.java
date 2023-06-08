package com.example.imnuricrestine.data.db.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(primaryKeys = {"hymn_index", "verse_number"}, foreignKeys = @ForeignKey(
        entity = Hymn.class,
        parentColumns = "hymn_index",
        childColumns = "hymn_index"
))

public class Lyrics{
    @NonNull
    public short verse_number;

    @NonNull
    public short hymn_index;

    @NonNull
    public String verse_text;

    @NonNull
    public boolean is_chorus;


}
