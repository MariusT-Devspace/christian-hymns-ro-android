package com.mtcnextlabs.imnuricrestine.data.db.entities;

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
    public int id;

    @NonNull
    public int hymn_id;

    public Favorite(int hymn_id) {
        this.hymn_id = hymn_id;
    }

    public Favorite(int id, int hymnId) {
        this.id = id;
        this.hymn_id = hymnId;
    }
}
