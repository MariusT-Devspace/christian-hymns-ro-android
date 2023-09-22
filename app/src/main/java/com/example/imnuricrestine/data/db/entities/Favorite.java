package com.example.imnuricrestine.data.db.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "Favorites",
        foreignKeys = @ForeignKey(
        entity = Hymn.class,
        parentColumns = "id",
        childColumns = "hymn_id")
)
public class Favorite {
    @PrimaryKey(autoGenerate = true)
    public short id;

    @NonNull
    public short hymn_id;

    public Favorite(short hymn_id) {
        this.hymn_id = hymn_id;
    }

    public Favorite(short id, short hymnId) {
        this.id = id;
        this.hymn_id = hymnId;
    }
}
