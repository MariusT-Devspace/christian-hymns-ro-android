package com.example.imnuricrestine.data.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import com.example.imnuricrestine.data.db.entities.Favorites;
import com.example.imnuricrestine.data.db.entities.Hymn;
import com.example.imnuricrestine.data.db.entities.Lyrics;

@Database(entities = {Hymn.class, Lyrics.class, Favorites.class}, version = 7)
public abstract class AppDatabase extends RoomDatabase {
    public abstract HymnsDao hymnsDao();
}
