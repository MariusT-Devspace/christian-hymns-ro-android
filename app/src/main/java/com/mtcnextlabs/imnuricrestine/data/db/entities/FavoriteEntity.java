package com.mtcnextlabs.imnuricrestine.data.db.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "Favorites",
        foreignKeys = @ForeignKey(
        entity = HymnEntity.class,
        parentColumns = "id",
        childColumns = "hymn_id")
)
public class FavoriteEntity {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @NonNull
    public int hymn_id;

    public FavoriteEntity(int id, int hymn_id) {
        this.id = id;
        this.hymn_id = hymn_id;
    }
}
