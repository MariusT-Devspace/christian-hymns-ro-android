package com.example.imnuricrestine.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Hymn {
    @PrimaryKey
    @ColumnInfo(index = true)
    public int hymn_index;

    @NonNull
    public String title;
}


