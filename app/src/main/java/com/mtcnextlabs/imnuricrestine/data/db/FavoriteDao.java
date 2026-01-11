package com.mtcnextlabs.imnuricrestine.data.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.mtcnextlabs.imnuricrestine.data.db.entities.Favorite;
import com.mtcnextlabs.imnuricrestine.data.db.models.FavoriteInsert;

@Dao
public interface FavoriteDao {
    @Query("SELECT * FROM favorites WHERE hymn_id = :hymnId")
    Favorite getFavoriteByHymnId(int hymnId);

    @Insert(entity = Favorite.class)
    void insertFavorite(FavoriteInsert favorite);

    @Insert(entity = Favorite.class)
    void insertFavoriteWithId(Favorite favorite);

    @Query("DELETE FROM favorites WHERE hymn_id = :hymnId")
    void deleteFavorite(int hymnId);
}
