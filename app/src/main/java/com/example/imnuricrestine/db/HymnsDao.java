package com.example.imnuricrestine.db;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.imnuricrestine.db.entities.Hymn;
import com.example.imnuricrestine.db.entities.HymnWithLyrics;

import java.util.Map;

@Dao
public interface HymnsDao {
    @Transaction
    @Query(
            "SELECT * FROM Hymn " +
            "JOIN Lyrics ON Hymn.hymn_index = Lyrics.hymn_index"
    )
    Map<Hymn, HymnWithLyrics> getAll();
}
