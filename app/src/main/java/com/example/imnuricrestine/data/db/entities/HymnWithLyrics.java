package com.example.imnuricrestine.data.db.entities;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class HymnWithLyrics {
    @Embedded public Hymn hymn;
    @Relation(
        parentColumn = "id",
        entityColumn = "hymn_id"
    )
    public List<Lyrics> lyrics;
}
