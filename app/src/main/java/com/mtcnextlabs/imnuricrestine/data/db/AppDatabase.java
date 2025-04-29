package com.mtcnextlabs.imnuricrestine.data.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import com.mtcnextlabs.imnuricrestine.data.db.entities.Favorite;
import com.mtcnextlabs.imnuricrestine.data.db.entities.Hymn;
import com.mtcnextlabs.imnuricrestine.data.db.entities.Lyrics;

@Database(entities = { Hymn.class, Lyrics.class, Favorite.class }, version = 8)
public abstract class AppDatabase extends RoomDatabase {

    public abstract HymnsDao hymnsDao();
    public abstract FavoritesDao favoritesDao();
}
