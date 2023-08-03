package com.example.imnuricrestine.data.db;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.imnuricrestine.data.db.entities.Favorites;
import com.example.imnuricrestine.data.db.entities.HymnWithLyrics;

import java.util.List;

@Dao
public interface HymnsDao {
    @Transaction
    @Query("SELECT * FROM Hymns")
    List<HymnWithLyrics> getAll();
    @Query("SELECT * FROM Favorites")
    List<Favorites> getFavorites();
}
