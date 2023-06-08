package com.example.imnuricrestine.data.db.entities;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class HymnWithLyrics {
    @Embedded public Hymn hymn;
    @Relation(
        parentColumn = "hymn_index",
        entityColumn = "hymn_index"
    )
    public List<Lyrics> lyrics;
}
