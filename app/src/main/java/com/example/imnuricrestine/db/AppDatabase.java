package com.example.imnuricrestine.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.imnuricrestine.db.entities.Hymn;
import com.example.imnuricrestine.db.entities.Lyrics;

@Database(entities = {Hymn.class, Lyrics.class}, version = 4)
public abstract class AppDatabase extends RoomDatabase {
    public abstract HymnsDao hymnsDao();
}
