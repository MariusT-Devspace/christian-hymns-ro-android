package com.example.imnuricrestine.data.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.imnuricrestine.data.db.entities.Favorite;

import java.util.List;

@Dao
public interface FavoritesDao {
    @Query("SELECT * FROM Favorites")
    List<Favorite> getFavorites();

    @Insert
    void addFavorite(Favorite favorite);

    @Delete
    void deleteFavorite(Favorite favorite);
}
