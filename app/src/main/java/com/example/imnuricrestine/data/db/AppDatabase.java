package com.example.imnuricrestine.data.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import com.example.imnuricrestine.data.db.entities.Favorite;
import com.example.imnuricrestine.data.db.entities.Hymn;
import com.example.imnuricrestine.data.db.entities.Lyrics;

@Database(entities = { Hymn.class, Lyrics.class, Favorite.class }, version = 8)
public abstract class AppDatabase extends RoomDatabase {

    public abstract HymnsDao hymnsDao();
    public abstract FavoritesDao favoritesDao();
}
