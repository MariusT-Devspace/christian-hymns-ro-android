package com.example.imnuricrestine.data.db.models;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.imnuricrestine.data.db.entities.Hymn;
import com.example.imnuricrestine.data.db.entities.Lyrics;

import java.util.List;

public class HymnWithLyrics {
    @Embedded public Hymn hymn;
    @Relation(
        parentColumn = "id",
        entityColumn = "hymn_id"
    )
    public List<Lyrics> lyrics;
}
