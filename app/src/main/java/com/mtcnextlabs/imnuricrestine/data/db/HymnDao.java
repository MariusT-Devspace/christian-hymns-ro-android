package com.mtcnextlabs.imnuricrestine.data.db;

import androidx.lifecycle.LiveData;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

import com.mtcnextlabs.imnuricrestine.data.db.models.HymnWithFavoriteStatus;
import com.mtcnextlabs.imnuricrestine.data.db.models.HymnWithLyrics;

import java.util.List;

@Dao
public interface HymnDao {
    @Transaction
    @Query("""
            SELECT *,
            CASE WHEN Favorites.hymn_id IS NULL THEN 0 ELSE 1 END AS isFavorite
            FROM Hymns
            LEFT JOIN Favorites ON Hymns.id = Favorites.hymn_id 
    """)
    LiveData<List<HymnWithFavoriteStatus>> getHymns();

     @Query("""
            SELECT *,
            CASE WHEN Favorites.hymn_id IS NULL THEN 0 ELSE 1 END AS isFavorite
            FROM Hymns
            LEFT JOIN Favorites ON Hymns.id = Favorites.hymn_id
            WHERE Hymns.id = :hymnId
    """)
     LiveData<HymnWithFavoriteStatus> getHymnById(int hymnId);

    @Query("""
        SELECT Hymns.* FROM Hymns 
        INNER JOIN Favorites ON Hymns.id = Favorites.hymn_id
        ORDER BY Favorites.id ASC
    """)
    LiveData<List<HymnWithLyrics>> getFavoriteHymns();
}
