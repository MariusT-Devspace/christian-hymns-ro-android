package com.mtcnextlabs.imnuricrestine.data.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.mtcnextlabs.imnuricrestine.data.db.entities.FavoriteEntity;
import com.mtcnextlabs.imnuricrestine.data.db.models.FavoriteInsert;

@Dao
public interface FavoriteDao {
    @Query("SELECT * FROM Favorites WHERE hymn_id = :hymnId")
    FavoriteEntity getFavoriteByHymnId(int hymnId);

    @Insert(entity = FavoriteEntity.class)
    void insertFavorite(FavoriteInsert favorite);

    @Insert(entity = FavoriteEntity.class)
    void insertFavoriteWithId(FavoriteEntity favoriteEntity);

    @Query("DELETE FROM Favorites WHERE hymn_id = :hymnId")
    void deleteFavorite(int hymnId);
}
