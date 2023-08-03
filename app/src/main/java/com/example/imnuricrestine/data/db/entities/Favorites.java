package com.example.imnuricrestine.data.db.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(
        entity = Hymn.class,
        parentColumns = "id",
        childColumns = "hymn_id")
)

public class Favorites {
    @PrimaryKey
    public short id;

    @NonNull
    public short hymn_id;

}
