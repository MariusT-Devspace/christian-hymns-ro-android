package com.mtcnextlabs.imnuricrestine.data.db;

import androidx.lifecycle.LiveData;

import androidx.room.Dao;
import androidx.room.Query;

import com.mtcnextlabs.imnuricrestine.data.db.models.HymnWithFavoriteStatus;

import java.util.List;

@Dao
public interface HymnsDao {
    public static final String GET_HYMNS_QUERY = """
            SELECT *,
            CASE WHEN Favorites.hymn_id IS NULL THEN 0 ELSE 1 END AS isFavorite
            FROM Hymns
            LEFT JOIN Favorites ON Hymns.id = Favorites.hymn_id 
    """;

    @Query(GET_HYMNS_QUERY)
    List<HymnWithFavoriteStatus> getHymns();

    @Query(GET_HYMNS_QUERY)
    LiveData<List<HymnWithFavoriteStatus>> getHymnsAsync();
}
