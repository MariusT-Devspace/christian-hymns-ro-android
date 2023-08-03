package com.example.imnuricrestine.data.db.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(primaryKeys = {"hymn_id", "verse_number"}, foreignKeys = @ForeignKey(
        entity = Hymn.class,
        parentColumns = "id",
        childColumns = "hymn_id"
))

public class Lyrics{
    @NonNull
    public short verse_number;

    @NonNull
    public short hymn_id;

    @NonNull
    public String verse_text;

    @NonNull
    public boolean is_chorus;


}
