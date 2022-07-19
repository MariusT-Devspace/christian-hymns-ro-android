package com.example.imnuricrestine;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.imnuricrestine.entities.Hymn;
import com.example.imnuricrestine.entities.Lyrics;

@Database(entities = {Hymn.class, Lyrics.class}, version = 3)
public abstract class AppDatabase extends RoomDatabase {
    public abstract HymnsDao hymnsDao();
}
