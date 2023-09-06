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

}
