package com.mtcnextlabs.imnuricrestine.data.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.mtcnextlabs.imnuricrestine.data.db.entities.Favorite;
import com.mtcnextlabs.imnuricrestine.data.db.models.FavoriteInsert;

import java.util.List;

@Dao
public interface FavoritesDao {
    @Query("SELECT * FROM Favorites ORDER BY id ASC")
    LiveData<List<Favorite>> getFavorites();

    @Insert(entity = Favorite.class)
    void insertFavorite(FavoriteInsert favorite);

    @Insert(entity = Favorite.class)
    void insertFavoriteWithId(Favorite favorite);

    @Delete
    void deleteFavorite(Favorite favorite);
}
