package com.example.imnuricrestine;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Hymns {
    @PrimaryKey
    @ColumnInfo(index = true)
    public int index;

    @NonNull
    public String title;

    @NonNull
    public String lyrics;
}
