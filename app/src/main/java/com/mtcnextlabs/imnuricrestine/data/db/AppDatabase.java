package com.mtcnextlabs.imnuricrestine.data.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import com.mtcnextlabs.imnuricrestine.data.db.entities.FavoriteEntity;
import com.mtcnextlabs.imnuricrestine.data.db.entities.HymnEntity;
import com.mtcnextlabs.imnuricrestine.data.db.entities.LyricsEntity;

@Database(entities = { HymnEntity.class, LyricsEntity.class, FavoriteEntity.class }, version = 10)
public abstract class AppDatabase extends RoomDatabase {

    public abstract HymnDao hymnDao();
    public abstract FavoriteDao favoriteDao();
}
