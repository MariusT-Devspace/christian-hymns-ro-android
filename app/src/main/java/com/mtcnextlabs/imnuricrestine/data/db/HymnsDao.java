package com.mtcnextlabs.imnuricrestine.data.db;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

import com.mtcnextlabs.imnuricrestine.data.db.models.HymnWithFavoriteStatus;

import java.util.List;

@Dao
public interface HymnsDao {
    @Transaction
    @Query("""
            SELECT *,
            CASE WHEN Favorites.hymn_id IS NULL THEN 0 ELSE 1 END AS isFavorite
            FROM Hymns
            LEFT JOIN Favorites ON Hymns.id = Favorites.hymn_id 
    """)
    List<HymnWithFavoriteStatus> getHymns();
}
