package com.example.imnuricrestine.data.db;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.imnuricrestine.data.db.entities.Favorite;
import com.example.imnuricrestine.data.db.models.FavoriteInsert;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface FavoritesDao {
    @Query("SELECT * FROM Favorites")
    LiveData<List<Favorite>> getFavorites();

    @Insert(entity = Favorite.class)
    void insertFavorite(FavoriteInsert favorite);

    @Delete
    void deleteFavorite(Favorite favorite);
}
