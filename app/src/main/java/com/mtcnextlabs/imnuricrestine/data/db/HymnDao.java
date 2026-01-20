package com.mtcnextlabs.imnuricrestine.data.db;

import androidx.lifecycle.LiveData;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

import com.mtcnextlabs.imnuricrestine.data.db.models.HymnDetailWithFavorite;
import com.mtcnextlabs.imnuricrestine.data.db.models.HymnWithFavorite;

import java.util.List;

@Dao
public interface HymnDao {
    @Transaction
    @Query("""
            SELECT *,
            CASE WHEN Favorites.hymn_id IS NULL THEN 0 ELSE 1 END AS is_favorite
            FROM Hymns
            LEFT JOIN Favorites ON Hymns.id = Favorites.hymn_id 
    """)
    LiveData<List<HymnWithFavorite>> getHymns();

     @Query("""
            SELECT *,
            CASE WHEN Favorites.hymn_id IS NULL THEN 0 ELSE 1 END AS is_favorite
            FROM Hymns
            LEFT JOIN Favorites ON Hymns.id = Favorites.hymn_id
            WHERE Hymns.id = :hymnId
    """)
     LiveData<HymnDetailWithFavorite> getHymnById(int hymnId);

    @Query("""
        SELECT Hymns.*, 1 AS is_favorite FROM Hymns
        INNER JOIN Favorites ON Hymns.id = Favorites.hymn_id
        ORDER BY Favorites.id ASC
    """)
    LiveData<List<HymnWithFavorite>> getFavoriteHymns();
}
