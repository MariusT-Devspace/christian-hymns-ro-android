package com.example.imnuricrestine;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.imnuricrestine.entities.Hymn;
import com.example.imnuricrestine.entities.HymnWithLyrics;
import com.example.imnuricrestine.entities.Lyrics;

import java.util.List;
import java.util.Map;

@Dao
public interface HymnsDao {
    @Query(
            "SELECT * FROM Hymn " +
            "JOIN Lyrics ON Hymn.hymn_index = Lyrics.hymn_index"
    )
    @Transaction
    Map<Hymn, List<HymnWithLyrics>> getAll();
}
