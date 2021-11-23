package com.example.imnuricrestine;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Hymns.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    public abstract HymnsDao hymnsDao();
}
